#import <AppKit/AppKit.h>
#import <Carbon/Carbon.h>
#import <Foundation/Foundation.h>
#import <ImageIO/ImageIO.h>
#import <CoreGraphics/CoreGraphics.h>

#include <algorithm>
#include <array>
#include <cmath>
#include <cstdarg>
#include <cstdint>
#include <cstdlib>
#include <cstdio>
#include <cstring>
#include <string>
#include <mutex>
#include <unordered_map>
#include <vector>

extern "C" {
int bf_host_window_create(int width,int height,const char *title);
void bf_host_window_destroy(void);
void bf_host_window_set_title(const char *title);
void bf_host_window_present(const uint32_t *pixels,int width,int height);
void bf_host_window_pump_events(void);
int bf_host_window_should_close(void);
int bf_host_load_image_rgba(const char *path,uint32_t **pixels,int *width,int *height);
int bf_host_text_width(const char *text,const char *fontName,int fontHeight,int bold,int italic,int underline);
int bf_host_font_line_height(const char *fontName,int fontHeight,int bold,int italic,int underline);
void bf_host_draw_text_rgba(uint32_t *pixels,int width,int height,int x,int y,const char *text,int argb,const char *fontName,int fontHeight,int bold,int italic,int underline,int centerX,int centerY);
int bf_host_text_needs_vertical_flip(uint32_t *pixels);
int bf_host_key_down(int code);
int bf_host_key_hit(int code);
int bf_host_get_key(void);
void bf_host_flush_keys(void);
int bf_host_mouse_down(int button);
int bf_host_mouse_hit(int button);
int bf_host_mouse_x(void);
int bf_host_mouse_y(void);
int bf_host_mouse_x_speed(void);
int bf_host_mouse_y_speed(void);
int bf_host_mouse_z(void);
int bf_host_mouse_z_speed(void);
void bf_host_flush_mouse(void);
void bf_host_mouse_set_position(int x,int y);
void bf_host_show_pointer(int visible);
typedef struct { float x,y,z,u,v,w,nx,ny,nz; uint32_t color; } bf_host_mesh_vertex;
typedef struct { int v0,v1,v2; } bf_host_mesh_triangle;
typedef struct { uint32_t vertexCount; bf_host_mesh_vertex *vertices; uint32_t triCount; bf_host_mesh_triangle *triangles; uint32_t color; int fx; int blend; float shininess; int textureFlags; int textureBlend; float texturePosU; float texturePosV; float textureScaleU; float textureScaleV; float textureRotation; char *texturePath; } bf_host_mesh_surface;
typedef struct { uint32_t surfaceCount; bf_host_mesh_surface *surfaces; } bf_host_mesh_data;
int bf_host_load_b3d_mesh(const char *path,bf_host_mesh_data *out);
void bf_host_free_b3d_mesh(bf_host_mesh_data *data);
}

static NSWindow *g_window=nil;
static NSView *g_view=nil;
static id g_delegate=nil;
static CGImageRef g_frame_image=nullptr;
static std::vector<uint32_t> g_present_pixels;
static int g_present_width=0;
static int g_present_height=0;
static bool g_should_close=false;
static bool g_app_ready=false;
static bool g_cursor_hidden=false;
static int g_mouse_x=0;
static int g_mouse_y=0;
static int g_mouse_dx=0;
static int g_mouse_dy=0;
static int g_mouse_z=0;
static int g_mouse_dz=0;
static std::array<int,4> g_mouse_down={0,0,0,0};
static std::array<int,4> g_mouse_hit={0,0,0,0};
static std::array<int,256> g_key_down={};
static std::array<int,256> g_key_hit={};
static std::vector<int> g_key_queue;
static std::mutex g_text_draw_mutex;

static void bf_reset_input_state(void){
    g_mouse_x=0;
    g_mouse_y=0;
    g_mouse_dx=0;
    g_mouse_dy=0;
    g_mouse_z=0;
    g_mouse_dz=0;
    g_mouse_down={0,0,0,0};
    g_mouse_hit={0,0,0,0};
    g_key_down.fill(0);
    g_key_hit.fill(0);
    g_key_queue.clear();
}

static bool bf_trace_enabled(){
    static int state=-1;
    if(state<0){
        const char *v=std::getenv("BF_MACOS_TRACE");
        state=(v && *v && std::strcmp(v,"0")!=0) ? 1 : 0;
    }
    return state!=0;
}

static void bf_tracef(const char *fmt,...){
    if(!bf_trace_enabled()) return;
    va_list ap;
    va_start(ap,fmt);
    std::fprintf(stderr,"[bf-macos-host] ");
    std::vfprintf(stderr,fmt,ap);
    std::fprintf(stderr,"\n");
    std::fflush(stderr);
    va_end(ap);
}

static bool bf_trace_text_enabled(){
    static int state=-1;
    if(state<0){
        const char *v=std::getenv("BF_TRACE_TEXT");
        state=(v && *v && std::strcmp(v,"0")!=0) ? 1 : 0;
    }
    return state!=0;
}

static const char *bf_dump_present_path(){
    static int init=0;
    static const char *path=nullptr;
    if(!init){
        path=std::getenv("BF_DUMP_FIRST_PRESENT");
        init=1;
    }
    return (path && *path) ? path : nullptr;
}

static int bf_dump_present_index(){
    static int init=0;
    static int index=1;
    if(!init){
        if(const char *v=std::getenv("BF_DUMP_PRESENT_INDEX")){
            const int parsed=std::atoi(v);
            if(parsed>0) index=parsed;
        }
        init=1;
    }
    return index;
}

static void bf_dump_present_image_once(CGImageRef image){
    static int presentCount=0;
    static bool dumped=false;
    if(!image) return;
    ++presentCount;
    if(dumped || presentCount!=bf_dump_present_index()) return;
    const char *path=bf_dump_present_path();
    if(!path) return;
    @autoreleasepool {
        NSURL *url=[NSURL fileURLWithPath:[NSString stringWithUTF8String:path]];
        CGImageDestinationRef dest=CGImageDestinationCreateWithURL((CFURLRef)url,CFSTR("public.png"),1,nullptr);
        if(dest){
            CGImageDestinationAddImage(dest,image,nullptr);
            CGImageDestinationFinalize(dest);
            CFRelease(dest);
            dumped=true;
            bf_tracef("dumped present #%d to '%s'",presentCount,path);
        }
    }
}

@interface BFWindowView : NSView
@end

@implementation BFWindowView
- (BOOL)isFlipped { return YES; }
- (BOOL)acceptsFirstResponder { return YES; }
- (void)drawRect:(NSRect)dirtyRect {
    (void)dirtyRect;
    static int traceCount=0;
    if(traceCount<12){
        bf_tracef("drawRect bounds=%.0fx%.0f image=%p present=%dx%d",self.bounds.size.width,self.bounds.size.height,(void*)g_frame_image,g_present_width,g_present_height);
        ++traceCount;
    }
    [[NSColor blackColor] setFill];
    NSRectFill(self.bounds);
    if(!g_frame_image) return;
    CGContextRef ctx=[[NSGraphicsContext currentContext] CGContext];
    if(!ctx) return;
    CGContextSaveGState(ctx);
    CGContextSetInterpolationQuality(ctx,kCGInterpolationNone);
    // The runtime canvas is top-left oriented. Core Graphics image draws still
    // expect a bottom-left image space here, so flip the final blit explicitly.
    CGContextTranslateCTM(ctx,0.0,self.bounds.size.height);
    CGContextScaleCTM(ctx,1.0,-1.0);
    CGContextDrawImage(ctx,CGRectMake(0.0,0.0,self.bounds.size.width,self.bounds.size.height),g_frame_image);
    CGContextRestoreGState(ctx);
}
@end

@interface BFWindowDelegate : NSObject <NSWindowDelegate>
@end

@implementation BFWindowDelegate
- (BOOL)windowShouldClose:(id)sender {
    (void)sender;
    g_should_close=true;
    return YES;
}
- (void)windowWillClose:(NSNotification *)notification {
    (void)notification;
    g_should_close=true;
    g_window=nil;
    g_view=nil;
}
@end

static inline uint32_t bf_argb_to_bgra(uint32_t argb){
    const uint32_t a=(argb>>24)&0xffu;
    const uint32_t r=(argb>>16)&0xffu;
    const uint32_t g=(argb>>8)&0xffu;
    const uint32_t b=argb&0xffu;
    return (a<<24)|(r)|(g<<8)|(b<<16);
}

static inline uint32_t bf_bgra_to_argb(uint32_t bgra){
    const uint32_t a=(bgra>>24)&0xffu;
    const uint32_t b=(bgra>>16)&0xffu;
    const uint32_t g=(bgra>>8)&0xffu;
    const uint32_t r=bgra&0xffu;
    return (a<<24)|(r<<16)|(g<<8)|b;
}

static NSString *bf_host_string_from_cstr(const char *text){
    if(!text || !*text) return @"";
    NSString *string=[[NSString alloc] initWithCString:text encoding:NSWindowsCP1252StringEncoding];
    if(!string) string=[[NSString alloc] initWithCString:text encoding:NSUTF8StringEncoding];
    if(!string) string=[[NSString alloc] initWithCString:text encoding:NSISOLatin1StringEncoding];
    if(!string) return @"";
#if __has_feature(objc_arc)
    return string;
#else
    return [string autorelease];
#endif
}

static NSFont *bf_host_resolve_font(const char *fontName,int fontHeight,int bold,int italic){
    const CGFloat size=(CGFloat)(fontHeight>0 ? fontHeight : 16);
    NSString *name=bf_host_string_from_cstr(fontName);
    NSFont *font=nil;
    if([name length]>0) font=[NSFont fontWithName:name size:size];
    if(!font) font=bold ? [NSFont boldSystemFontOfSize:size] : [NSFont systemFontOfSize:size];
    NSFontTraitMask traits=0;
    if(bold) traits|=NSBoldFontMask;
    if(italic) traits|=NSItalicFontMask;
    if(traits){
        NSFont *converted=[[NSFontManager sharedFontManager] convertFont:font toHaveTrait:traits];
        if(converted) font=converted;
    }
    return font ?: [NSFont systemFontOfSize:size];
}

static NSDictionary *bf_host_text_attributes(NSFont *font,int argb,int underline){
    const CGFloat r=(CGFloat)((argb>>16)&255u)/255.0;
    const CGFloat g=(CGFloat)((argb>>8)&255u)/255.0;
    const CGFloat b=(CGFloat)(argb&255u)/255.0;
    const CGFloat a=(CGFloat)(((argb>>24)&255u)?((argb>>24)&255u):255u)/255.0;
    NSMutableDictionary *attrs=[NSMutableDictionary dictionaryWithObjectsAndKeys:
        font,NSFontAttributeName,
        [NSColor colorWithCalibratedRed:r green:g blue:b alpha:a],NSForegroundColorAttributeName,
        @0,NSLigatureAttributeName,
        nil];
    if(underline) attrs[NSUnderlineStyleAttributeName]=@(NSUnderlineStyleSingle);
    return attrs;
}

static uint32_t bf_host_argb_from_bitmap_pixel(const unsigned char *pixel,NSInteger bytesPerPixel,NSBitmapFormat format){
    if(!pixel || bytesPerPixel<=0) return 0xff000000u;
    const bool alphaFirst=(format & NSBitmapFormatAlphaFirst) != 0;
    uint32_t a=255u,r=0u,g=0u,b=0u;
    if(bytesPerPixel>=4){
        if(alphaFirst){
            a=(uint32_t)pixel[0];
            r=(uint32_t)pixel[1];
            g=(uint32_t)pixel[2];
            b=(uint32_t)pixel[3];
        }else{
            r=(uint32_t)pixel[0];
            g=(uint32_t)pixel[1];
            b=(uint32_t)pixel[2];
            a=(uint32_t)pixel[3];
        }
    }else if(bytesPerPixel==3){
        r=(uint32_t)pixel[0];
        g=(uint32_t)pixel[1];
        b=(uint32_t)pixel[2];
    }else if(bytesPerPixel==2){
        r=g=b=(uint32_t)pixel[0];
        a=(uint32_t)pixel[1];
    }else{
        r=g=b=(uint32_t)pixel[0];
    }
    return (a<<24)|(r<<16)|(g<<8)|b;
}

static NSSize bf_host_measure_text(const char *text,const char *fontName,int fontHeight,int bold,int italic,int underline){
    NSString *string=bf_host_string_from_cstr(text);
    NSFont *font=bf_host_resolve_font(fontName,fontHeight,bold,italic);
    NSDictionary *attrs=bf_host_text_attributes(font,0xffffffffu,underline);
    NSRect bounds=[string boundingRectWithSize:NSMakeSize(CGFLOAT_MAX,CGFLOAT_MAX)
                                       options:NSStringDrawingUsesLineFragmentOrigin|NSStringDrawingUsesFontLeading
                                    attributes:attrs];
    NSSize size=bounds.size;
    if(size.width<=0.0 || size.height<=0.0) size=[string sizeWithAttributes:attrs];
    if(size.width<=0.0) size.width=1.0;
    if(size.height<=0.0) size.height=(CGFloat)std::max(1,fontHeight>0 ? fontHeight : 16);
    return size;
}

static void bf_ensure_app(){
    if(g_app_ready) return;
    @autoreleasepool {
        ProcessSerialNumber psn={0,kCurrentProcess};
        TransformProcessType(&psn,kProcessTransformToForegroundApplication);
        [NSApplication sharedApplication];
        [NSApp setActivationPolicy:NSApplicationActivationPolicyRegular];
        [NSApp finishLaunching];
        g_app_ready=true;
    }
}

static int bf_map_mac_key(unsigned short kc){
    switch(kc){
        case 53: return 1;
        case 18: return 2;
        case 19: return 3;
        case 20: return 4;
        case 21: return 5;
        case 23: return 6;
        case 22: return 7;
        case 26: return 8;
        case 28: return 9;
        case 25: return 10;
        case 29: return 11;
        case 27: return 12;
        case 24: return 13;
        case 51: return 14;
        case 48: return 15;
        case 12: return 16;
        case 13: return 17;
        case 14: return 18;
        case 15: return 19;
        case 17: return 20;
        case 16: return 21;
        case 32: return 22;
        case 34: return 23;
        case 31: return 24;
        case 35: return 25;
        case 33: return 26;
        case 30: return 27;
        case 36: return 28;
        case 59: return 29;
        case 62: return 157;
        case 0: return 30;
        case 1: return 31;
        case 2: return 32;
        case 3: return 33;
        case 5: return 34;
        case 4: return 35;
        case 38: return 36;
        case 40: return 37;
        case 37: return 38;
        case 41: return 39;
        case 39: return 40;
        case 50: return 41;
        case 56: return 42;
        case 60: return 54;
        case 42: return 43;
        case 6: return 44;
        case 7: return 45;
        case 8: return 46;
        case 9: return 47;
        case 11: return 48;
        case 45: return 49;
        case 46: return 50;
        case 43: return 51;
        case 47: return 52;
        case 44: return 53;
        case 58: return 56;
        case 61: return 184;
        case 49: return 57;
        case 57: return 58;
        case 122: return 59;
        case 120: return 60;
        case 99: return 61;
        case 118: return 62;
        case 96: return 63;
        case 97: return 64;
        case 98: return 65;
        case 100: return 66;
        case 101: return 67;
        case 109: return 68;
        case 103: return 87;
        case 111: return 88;
        case 82: return 82;
        case 83: return 79;
        case 84: return 80;
        case 85: return 81;
        case 86: return 75;
        case 87: return 76;
        case 88: return 77;
        case 89: return 71;
        case 91: return 72;
        case 92: return 73;
        case 65: return 83;
        case 67: return 55;
        case 69: return 78;
        case 71: return 69;
        case 75: return 181;
        case 76: return 28;
        case 78: return 74;
        case 81: return 13;
        case 114: return 210;
        case 115: return 199;
        case 116: return 201;
        case 117: return 211;
        case 119: return 207;
        case 121: return 209;
        case 123: return 203;
        case 124: return 205;
        case 125: return 208;
        case 126: return 200;
        default: return 0;
    }
}

static int bf_ascii_from_event(NSEvent *event){
    NSString *chars=[event characters];
    if(!chars || [chars length]==0) return 0;
    const unichar ch=[chars characterAtIndex:0];
    if(ch==NSBackspaceCharacter || ch==0x7f) return 8;
    if(ch=='\r' || ch=='\n') return 13;
    if(ch=='\t') return 9;
    if(ch==0x1b) return 27;
    if(ch>=32 && ch<127) return (int)ch;
    return 0;
}

static void bf_update_mouse_location(NSEvent *event){
    if(!g_view || !event) return;
    NSPoint p=[g_view convertPoint:[event locationInWindow] fromView:nil];
    const int nx=(int)lround(p.x);
    const int ny=(int)lround(p.y);
    g_mouse_dx=nx-g_mouse_x;
    g_mouse_dy=ny-g_mouse_y;
    g_mouse_x=nx;
    g_mouse_y=ny;
}

static void bf_process_event(NSEvent *event){
    if(!event) return;
    switch([event type]){
        case NSEventTypeKeyDown: {
            const int code=bf_map_mac_key([event keyCode]);
            if(code>0 && code<(int)g_key_down.size()){
                if(!g_key_down[(size_t)code]) g_key_hit[(size_t)code]=1;
                g_key_down[(size_t)code]=1;
            }
            const int ascii=bf_ascii_from_event(event);
            if(ascii>0) g_key_queue.push_back(ascii);
            break;
        }
        case NSEventTypeKeyUp: {
            const int code=bf_map_mac_key([event keyCode]);
            if(code>0 && code<(int)g_key_down.size()) g_key_down[(size_t)code]=0;
            break;
        }
        case NSEventTypeLeftMouseDown:
            bf_update_mouse_location(event);
            g_mouse_down[1]=1;
            g_mouse_hit[1]=1;
            break;
        case NSEventTypeLeftMouseUp:
            bf_update_mouse_location(event);
            g_mouse_down[1]=0;
            break;
        case NSEventTypeRightMouseDown:
            bf_update_mouse_location(event);
            g_mouse_down[2]=1;
            g_mouse_hit[2]=1;
            break;
        case NSEventTypeRightMouseUp:
            bf_update_mouse_location(event);
            g_mouse_down[2]=0;
            break;
        case NSEventTypeOtherMouseDown:
            bf_update_mouse_location(event);
            g_mouse_down[3]=1;
            g_mouse_hit[3]=1;
            break;
        case NSEventTypeOtherMouseUp:
            bf_update_mouse_location(event);
            g_mouse_down[3]=0;
            break;
        case NSEventTypeMouseMoved:
        case NSEventTypeLeftMouseDragged:
        case NSEventTypeRightMouseDragged:
        case NSEventTypeOtherMouseDragged:
            bf_update_mouse_location(event);
            break;
        case NSEventTypeScrollWheel: {
            bf_update_mouse_location(event);
            const CGFloat dy=[event scrollingDeltaY];
            const int step=(dy>0.0) ? 1 : ((dy<0.0) ? -1 : 0);
            g_mouse_dz=step;
            g_mouse_z+=step;
            break;
        }
        default:
            break;
    }
}

extern "C" int bf_host_window_create(int width,int height,const char *title){
    bf_ensure_app();
    @autoreleasepool {
        if(g_window){
            bf_tracef("window resize request width=%d height=%d title='%s'",width,height,(title && *title)?title:"");
            [g_window setContentSize:NSMakeSize((CGFloat)width,(CGFloat)height)];
            if(title && *title) [g_window setTitle:[NSString stringWithUTF8String:title]];
            return 1;
        }
        bf_tracef("window create width=%d height=%d title='%s'",width,height,(title && *title)?title:"");
        const NSRect frame=NSMakeRect(200.0,120.0,(CGFloat)width,(CGFloat)height);
        const NSUInteger style=NSWindowStyleMaskTitled|NSWindowStyleMaskClosable|NSWindowStyleMaskMiniaturizable;
        g_window=[[NSWindow alloc] initWithContentRect:frame styleMask:style backing:NSBackingStoreBuffered defer:NO];
        if(!g_window) return 0;
        [g_window setReleasedWhenClosed:NO];
        [g_window setAnimationBehavior:NSWindowAnimationBehaviorNone];
        if([g_window respondsToSelector:@selector(setTabbingMode:)]){
            [g_window setTabbingMode:NSWindowTabbingModeDisallowed];
        }
        g_delegate=[[BFWindowDelegate alloc] init];
        [g_window setDelegate:g_delegate];
        g_view=[[BFWindowView alloc] initWithFrame:NSMakeRect(0.0,0.0,(CGFloat)width,(CGFloat)height)];
        [g_view setAutoresizingMask:NSViewWidthSizable|NSViewHeightSizable];
        [g_window setContentView:g_view];
        [g_window setAcceptsMouseMovedEvents:YES];
        [g_window makeFirstResponder:g_view];
        [g_window center];
        [g_window setFrame:NSMakeRect(g_window.frame.origin.x,g_window.frame.origin.y,(CGFloat)width,(CGFloat)height) display:YES];
        [g_window setTitle:(title && *title) ? [NSString stringWithUTF8String:title] : @"BlitzForge"];
        [g_window makeKeyAndOrderFront:nil];
        [NSApp activateIgnoringOtherApps:YES];
        [[NSRunLoop currentRunLoop] runUntilDate:[NSDate dateWithTimeIntervalSinceNow:0.01]];
        // Discard activation and focus-transition input so apps do not auto-click through startup menus.
        bf_reset_input_state();
        bf_tracef("window created frame=%.0fx%.0f content=%.0fx%.0f",g_window.frame.size.width,g_window.frame.size.height,g_window.contentView.frame.size.width,g_window.contentView.frame.size.height);
        return 1;
    }
}

extern "C" void bf_host_window_destroy(void){
    @autoreleasepool {
        bf_tracef("window destroy");
        if(g_frame_image){ CGImageRelease(g_frame_image); g_frame_image=nullptr; }
        if(g_window){ [g_window orderOut:nil]; [g_window close]; g_window=nil; }
        g_view=nil;
        g_delegate=nil;
        g_should_close=false;
        bf_reset_input_state();
    }
}

extern "C" void bf_host_window_set_title(const char *title){
    @autoreleasepool {
        bf_tracef("window title='%s'",(title && *title)?title:"");
        if(g_window && title && *title) [g_window setTitle:[NSString stringWithUTF8String:title]];
    }
}

extern "C" void bf_host_window_present(const uint32_t *pixels,int width,int height){
    bf_ensure_app();
    if(!pixels || width<=0 || height<=0) return;
    @autoreleasepool {
        if(!g_window) bf_host_window_create(width,height,"BlitzForge");
        static int traceCount=0;
        if(traceCount<8){
            bf_tracef("present width=%d height=%d content=%.0fx%.0f title='%s'",width,height,g_window?g_window.contentView.frame.size.width:0.0,g_window?g_window.contentView.frame.size.height:0.0,g_window?g_window.title.UTF8String:"");
            ++traceCount;
        }
        g_present_width=width;
        g_present_height=height;
        g_present_pixels.resize((size_t)width*(size_t)height);
        for(size_t i=0,n=(size_t)width*(size_t)height;i<n;++i) g_present_pixels[i]=bf_argb_to_bgra(pixels[i]);
        if(g_frame_image){ CGImageRelease(g_frame_image); g_frame_image=nullptr; }
        CGColorSpaceRef cs=CGColorSpaceCreateDeviceRGB();
        CGContextRef ctx=CGBitmapContextCreate(g_present_pixels.data(),(size_t)width,(size_t)height,8u,(size_t)width*4u,cs,kCGImageAlphaPremultipliedFirst|kCGBitmapByteOrder32Little);
        if(ctx){
            g_frame_image=CGBitmapContextCreateImage(ctx);
            CGContextRelease(ctx);
        }
        CGColorSpaceRelease(cs);
        bf_dump_present_image_once(g_frame_image);
        if(g_view){
            [g_view setNeedsDisplay:YES];
            [g_view displayIfNeeded];
        }
        if(g_window){
            [g_window displayIfNeeded];
        }
    }
}

extern "C" void bf_host_window_pump_events(void){
    if(!g_app_ready) return;
    @autoreleasepool {
        for(;;){
            NSEvent *event=[NSApp nextEventMatchingMask:NSEventMaskAny untilDate:[NSDate distantPast] inMode:NSDefaultRunLoopMode dequeue:YES];
            if(!event) break;
            bf_process_event(event);
            [NSApp sendEvent:event];
        }
        [[NSRunLoop currentRunLoop] runUntilDate:[NSDate dateWithTimeIntervalSinceNow:0.001]];
        [NSApp updateWindows];
        if(g_view) [g_view displayIfNeeded];
    }
}

extern "C" int bf_host_window_should_close(void){
    return g_should_close ? 1 : 0;
}

extern "C" int bf_host_load_image_rgba(const char *path,uint32_t **pixels,int *width,int *height){
    if(pixels) *pixels=nullptr;
    if(width) *width=0;
    if(height) *height=0;
    if(!path || !*path || !pixels || !width || !height) return 0;
    @autoreleasepool {
        NSString *nsPath=[NSString stringWithUTF8String:path];
        if(!nsPath || ![nsPath length]) return 0;
        NSData *data=[NSData dataWithContentsOfFile:nsPath];
        if(!data || [data length]==0) return 0;
        NSBitmapImageRep *rep=[NSBitmapImageRep imageRepWithData:data];
        if(!rep) return 0;
        const NSInteger w=[rep pixelsWide];
        const NSInteger h=[rep pixelsHigh];
        if(w<=0 || h<=0) return 0;
        uint32_t *out=(uint32_t*)malloc(w*h*sizeof(uint32_t));
        if(!out) return 0;
        if(![rep isPlanar] && [rep bitsPerSample]==8 && [rep bitmapData]){
            const NSInteger bytesPerRow=[rep bytesPerRow];
            const NSInteger bytesPerPixel=std::max<NSInteger>(1,[rep bitsPerPixel]/8);
            const NSBitmapFormat format=[rep bitmapFormat];
            const unsigned char *bitmap=[rep bitmapData];
            for(NSInteger y=0;y<h;++y){
                const unsigned char *row=bitmap + y*bytesPerRow;
                for(NSInteger x=0;x<w;++x){
                    out[y*w+x]=bf_host_argb_from_bitmap_pixel(row + x*bytesPerPixel,bytesPerPixel,format);
                }
            }
        }else{
            // Slow fallback for uncommon bitmap layouts. NSBitmapImageRep reports
            // coordinates bottom-up through colorAtX:y, so invert Y to keep the
            // engine's top-left buffer convention.
            for(NSInteger y=0;y<h;++y){
                for(NSInteger x=0;x<w;++x){
                    NSColor *color=[[rep colorAtX:x y:(h-1)-y] colorUsingColorSpace:[NSColorSpace deviceRGBColorSpace]];
                    if(!color){
                        out[y*w+x]=0xff000000u;
                        continue;
                    }
                    const uint32_t a=(uint32_t)lround([color alphaComponent]*255.0);
                    const uint32_t r=(uint32_t)lround([color redComponent]*255.0);
                    const uint32_t g=(uint32_t)lround([color greenComponent]*255.0);
                    const uint32_t b=(uint32_t)lround([color blueComponent]*255.0);
                    out[y*w+x]=(a<<24)|(r<<16)|(g<<8)|b;
                }
            }
        }
        *pixels=out;
        *width=(int)w;
        *height=(int)h;
        return 1;
    }
}

namespace {

struct BFHostVec3 {
    float x=0.0f;
    float y=0.0f;
    float z=0.0f;
};

struct BFHostQuat {
    float w=1.0f;
    float x=0.0f;
    float y=0.0f;
    float z=0.0f;
};

struct BFHostMat4 {
    float m[4][4]{};
};

struct BFHostTextureDef {
    std::string path;
    int flags=0;
    int blend=0;
    float posU=0.0f;
    float posV=0.0f;
    float scaleU=1.0f;
    float scaleV=1.0f;
    float rotation=0.0f;
};

struct BFHostBrushDef {
    uint32_t color=0x00ffffffu;
    int fx=0;
    int blend=0;
    float shininess=0.0f;
    int textureIndex=-1;
};

static BFHostMat4 bf_host_mat4_identity(){
    BFHostMat4 out{};
    for(int i=0;i<4;++i) out.m[i][i]=1.0f;
    return out;
}

static BFHostMat4 bf_host_mat4_mul(const BFHostMat4 &a,const BFHostMat4 &b){
    BFHostMat4 out{};
    for(int r=0;r<4;++r){
        for(int c=0;c<4;++c){
            float v=0.0f;
            for(int k=0;k<4;++k) v+=a.m[r][k]*b.m[k][c];
            out.m[r][c]=v;
        }
    }
    return out;
}

static BFHostQuat bf_host_quat_normalize(BFHostQuat q){
    const float len=std::sqrt(q.w*q.w + q.x*q.x + q.y*q.y + q.z*q.z);
    if(len<=0.000001f) return BFHostQuat{};
    q.w/=len;
    q.x/=len;
    q.y/=len;
    q.z/=len;
    return q;
}

static BFHostMat4 bf_host_mat4_from_trs(const BFHostVec3 &pos,const BFHostVec3 &scale,BFHostQuat rot){
    rot=bf_host_quat_normalize(rot);
    const float xx=rot.x*rot.x, yy=rot.y*rot.y, zz=rot.z*rot.z;
    const float xy=rot.x*rot.y, xz=rot.x*rot.z, yz=rot.y*rot.z;
    const float wx=rot.w*rot.x, wy=rot.w*rot.y, wz=rot.w*rot.z;
    BFHostMat4 out=bf_host_mat4_identity();
    out.m[0][0]=(1.0f-2.0f*(yy+zz))*scale.x;
    out.m[0][1]=(2.0f*(xy-wz))*scale.y;
    out.m[0][2]=(2.0f*(xz+wy))*scale.z;
    out.m[1][0]=(2.0f*(xy+wz))*scale.x;
    out.m[1][1]=(1.0f-2.0f*(xx+zz))*scale.y;
    out.m[1][2]=(2.0f*(yz-wx))*scale.z;
    out.m[2][0]=(2.0f*(xz-wy))*scale.x;
    out.m[2][1]=(2.0f*(yz+wx))*scale.y;
    out.m[2][2]=(1.0f-2.0f*(xx+yy))*scale.z;
    out.m[0][3]=pos.x;
    out.m[1][3]=pos.y;
    out.m[2][3]=pos.z;
    return out;
}

static BFHostVec3 bf_host_transform_point(const BFHostMat4 &m,const BFHostVec3 &v){
    return BFHostVec3{
        m.m[0][0]*v.x + m.m[0][1]*v.y + m.m[0][2]*v.z + m.m[0][3],
        m.m[1][0]*v.x + m.m[1][1]*v.y + m.m[1][2]*v.z + m.m[1][3],
        m.m[2][0]*v.x + m.m[2][1]*v.y + m.m[2][2]*v.z + m.m[2][3]
    };
}

static BFHostVec3 bf_host_transform_vector(const BFHostMat4 &m,const BFHostVec3 &v){
    BFHostVec3 out{
        m.m[0][0]*v.x + m.m[0][1]*v.y + m.m[0][2]*v.z,
        m.m[1][0]*v.x + m.m[1][1]*v.y + m.m[1][2]*v.z,
        m.m[2][0]*v.x + m.m[2][1]*v.y + m.m[2][2]*v.z
    };
    const float len=std::sqrt(out.x*out.x + out.y*out.y + out.z*out.z);
    if(len>0.000001f){
        out.x/=len;
        out.y/=len;
        out.z/=len;
    }else{
        out=BFHostVec3{0.0f,0.0f,1.0f};
    }
    return out;
}

static std::string bf_host_normalize_slashes(std::string path){
    for(char &ch : path) if(ch=='\\') ch='/';
    return path;
}

static std::string bf_host_dirname(const std::string &path){
    const std::string normalized=bf_host_normalize_slashes(path);
    const std::string::size_type slash=normalized.find_last_of('/');
    return (slash==std::string::npos) ? std::string() : normalized.substr(0,slash);
}

static bool bf_host_path_is_absolute(const std::string &path){
    return (!path.empty() && path[0]=='/') || (path.size()>1 && path[1]==':');
}

static std::string bf_host_join_path(const std::string &base,const std::string &child){
    if(base.empty()) return child;
    if(child.empty()) return base;
    if(base.back()=='/') return base + child;
    return base + "/" + child;
}

static std::string bf_host_resolve_texture_path(const std::string &meshPath,const std::string &texPath){
    const std::string normalizedTex=bf_host_normalize_slashes(texPath);
    if(normalizedTex.empty() || bf_host_path_is_absolute(normalizedTex)) return normalizedTex;
    return bf_host_join_path(bf_host_dirname(meshPath),normalizedTex);
}

class BFHostB3DReader {
public:
    explicit BFHostB3DReader(FILE *fp):fp_(fp){}

    bool enterChunk(char tagOut[5]){
        if(std::fread(tagOut,1,4,fp_)!=4) return false;
        int32_t size=0;
        if(std::fread(&size,4,1,fp_)!=1) return false;
        tagOut[4]='\0';
        chunkEnds_.push_back(std::ftell(fp_) + (long)size);
        return true;
    }

    void exitChunk(){
        if(chunkEnds_.empty()) return;
        std::fseek(fp_,chunkEnds_.back(),SEEK_SET);
        chunkEnds_.pop_back();
    }

    long chunkSize() const{
        if(chunkEnds_.empty()) return 0;
        return chunkEnds_.back() - std::ftell(fp_);
    }

    int32_t readInt(){
        int32_t value=0;
        std::fread(&value,4,1,fp_);
        return value;
    }

    float readFloat(){
        float value=0.0f;
        std::fread(&value,4,1,fp_);
        return value;
    }

    std::string readString(){
        std::string out;
        for(;;){
            int ch=std::fgetc(fp_);
            if(ch==EOF || ch==0) break;
            out.push_back((char)ch);
        }
        return out;
    }

private:
    FILE *fp_=nullptr;
    std::vector<long> chunkEnds_;
};

static uint32_t bf_host_color_from_floats(float r,float g,float b,float a){
    auto clamp=[](float v){ return std::max(0.0f,std::min(1.0f,v)); };
    const uint32_t rr=(uint32_t)std::lround(clamp(r)*255.0f);
    const uint32_t gg=(uint32_t)std::lround(clamp(g)*255.0f);
    const uint32_t bb=(uint32_t)std::lround(clamp(b)*255.0f);
    const uint32_t aa=(uint32_t)std::lround(clamp(a)*255.0f);
    return (aa<<24)|(rr<<16)|(gg<<8)|bb;
}

static void bf_host_parse_texs(BFHostB3DReader &reader,std::vector<BFHostTextureDef> &textures){
    while(reader.chunkSize()>0){
        BFHostTextureDef tex;
        tex.path=reader.readString();
        tex.flags=reader.readInt();
        tex.blend=reader.readInt();
        tex.posU=reader.readFloat();
        tex.posV=reader.readFloat();
        tex.scaleU=reader.readFloat();
        tex.scaleV=reader.readFloat();
        tex.rotation=reader.readFloat();
        textures.push_back(tex);
    }
}

static void bf_host_parse_brus(BFHostB3DReader &reader,std::vector<BFHostBrushDef> &brushes){
    const int texCount=reader.readInt();
    while(reader.chunkSize()>0){
        BFHostBrushDef brush;
        (void)reader.readString();
        const float r=reader.readFloat();
        const float g=reader.readFloat();
        const float b=reader.readFloat();
        const float a=reader.readFloat();
        brush.color=bf_host_color_from_floats(r,g,b,a);
        brush.shininess=reader.readFloat();
        brush.blend=reader.readInt();
        brush.fx=reader.readInt();
        for(int i=0;i<texCount;++i){
            const int texId=reader.readInt();
            if(i==0) brush.textureIndex=texId;
        }
        brushes.push_back(brush);
    }
}

static BFHostBrushDef bf_host_effective_brush(const std::vector<BFHostBrushDef> &brushes,int meshBrush,int triBrush){
    const int brushIndex=(triBrush>=0) ? triBrush : meshBrush;
    if(brushIndex>=0 && brushIndex<(int)brushes.size()) return brushes[(size_t)brushIndex];
    return BFHostBrushDef{};
}

static std::string bf_host_texture_path_for_brush(const BFHostBrushDef &brush,const std::vector<BFHostTextureDef> &textures,const std::string &meshPath){
    if(brush.textureIndex<0 || brush.textureIndex>=(int)textures.size()) return std::string();
    return bf_host_resolve_texture_path(meshPath,textures[(size_t)brush.textureIndex].path);
}

static BFHostTextureDef bf_host_texture_for_brush(const BFHostBrushDef &brush,const std::vector<BFHostTextureDef> &textures){
    if(brush.textureIndex<0 || brush.textureIndex>=(int)textures.size()) return BFHostTextureDef{};
    return textures[(size_t)brush.textureIndex];
}

static void bf_host_push_surface(std::vector<bf_host_mesh_surface> &surfaces,
                                 const std::vector<bf_host_mesh_vertex> &rawVertices,
                                 BFHostB3DReader &reader,
                                 int meshBrush,
                                 const BFHostMat4 &world,
                                 const std::vector<BFHostBrushDef> &brushes,
                                 const std::vector<BFHostTextureDef> &textures,
                                 const std::string &meshPath){
    const int triBrush=reader.readInt();
    const BFHostBrushDef brush=bf_host_effective_brush(brushes,meshBrush,triBrush);
    const BFHostTextureDef texture=bf_host_texture_for_brush(brush,textures);
    std::unordered_map<int,int> remap;
    std::vector<bf_host_mesh_vertex> vertices;
    std::vector<bf_host_mesh_triangle> triangles;
    while(reader.chunkSize()>=12){
        const int src0=reader.readInt();
        const int src1=reader.readInt();
        const int src2=reader.readInt();
        const int srcIndices[3]={src0,src1,src2};
        bf_host_mesh_triangle tri{};
        bool valid=true;
        for(int corner=0;corner<3;++corner){
            const int srcIndex=srcIndices[corner];
            if(srcIndex<0 || srcIndex>=(int)rawVertices.size()){
                valid=false;
                break;
            }
            auto it=remap.find(srcIndex);
            if(it==remap.end()){
                bf_host_mesh_vertex vertex=rawVertices[(size_t)srcIndex];
                const BFHostVec3 pos=bf_host_transform_point(world,BFHostVec3{vertex.x,vertex.y,vertex.z});
                const BFHostVec3 normal=bf_host_transform_vector(world,BFHostVec3{vertex.nx,vertex.ny,vertex.nz});
                vertex.x=pos.x;
                vertex.y=pos.y;
                vertex.z=pos.z;
                vertex.nx=normal.x;
                vertex.ny=normal.y;
                vertex.nz=normal.z;
                remap.emplace(srcIndex,(int)vertices.size());
                tri.v0=tri.v1=tri.v2=0;
                vertices.push_back(vertex);
                it=remap.find(srcIndex);
            }
            const int localIndex=it->second;
            if(corner==0) tri.v0=localIndex;
            else if(corner==1) tri.v1=localIndex;
            else tri.v2=localIndex;
        }
        if(valid) triangles.push_back(tri);
    }
    if(vertices.empty() || triangles.empty()) return;
    bf_host_mesh_surface surface{};
    surface.vertexCount=(uint32_t)vertices.size();
    surface.vertices=(bf_host_mesh_vertex*)std::calloc(vertices.size(),sizeof(bf_host_mesh_vertex));
    std::memcpy(surface.vertices,vertices.data(),vertices.size()*sizeof(bf_host_mesh_vertex));
    surface.triCount=(uint32_t)triangles.size();
    surface.triangles=(bf_host_mesh_triangle*)std::calloc(triangles.size(),sizeof(bf_host_mesh_triangle));
    std::memcpy(surface.triangles,triangles.data(),triangles.size()*sizeof(bf_host_mesh_triangle));
    surface.color=brush.color;
    surface.fx=brush.fx;
    surface.blend=brush.blend;
    surface.shininess=brush.shininess;
    surface.textureFlags=texture.flags;
    surface.textureBlend=texture.blend;
    surface.texturePosU=texture.posU;
    surface.texturePosV=texture.posV;
    surface.textureScaleU=texture.scaleU;
    surface.textureScaleV=texture.scaleV;
    surface.textureRotation=texture.rotation;
    const std::string texturePath=bf_host_texture_path_for_brush(brush,textures,meshPath);
    if(!texturePath.empty()){
        surface.texturePath=(char*)std::calloc(texturePath.size()+1u,1u);
        std::memcpy(surface.texturePath,texturePath.c_str(),texturePath.size());
    }
    surfaces.push_back(surface);
}

static void bf_host_parse_mesh(BFHostB3DReader &reader,
                               const BFHostMat4 &world,
                               std::vector<bf_host_mesh_surface> &surfaces,
                               const std::vector<BFHostBrushDef> &brushes,
                               const std::vector<BFHostTextureDef> &textures,
                               const std::string &meshPath){
    const int meshBrush=reader.readInt();
    std::vector<bf_host_mesh_vertex> vertices;
    while(reader.chunkSize()>0){
        char tag[5];
        if(!reader.enterChunk(tag)) break;
        if(std::strcmp(tag,"VRTS")==0){
            const int flags=reader.readInt();
            const int texSets=reader.readInt();
            const int texSize=reader.readInt();
            while(reader.chunkSize()>0){
                bf_host_mesh_vertex vertex{};
                vertex.x=reader.readFloat();
                vertex.y=reader.readFloat();
                vertex.z=reader.readFloat();
                if(flags&1){
                    vertex.nx=reader.readFloat();
                    vertex.ny=reader.readFloat();
                    vertex.nz=reader.readFloat();
                }else{
                    vertex.nz=1.0f;
                }
                if(flags&2){
                    const float r=reader.readFloat();
                    const float g=reader.readFloat();
                    const float b=reader.readFloat();
                    const float a=reader.readFloat();
                    vertex.color=bf_host_color_from_floats(r,g,b,a);
                }else{
                    vertex.color=0xffffffffu;
                }
                for(int set=0;set<texSets;++set){
                    float tc[4]{0.0f,0.0f,0.0f,0.0f};
                    for(int i=0;i<texSize && i<4;++i) tc[i]=reader.readFloat();
                    for(int i=4;i<texSize;++i) (void)reader.readFloat();
                    if(set==0){
                        vertex.u=tc[0];
                        vertex.v=tc[1];
                        vertex.w=tc[2];
                    }
                }
                vertices.push_back(vertex);
            }
        }else if(std::strcmp(tag,"TRIS")==0){
            bf_host_push_surface(surfaces,vertices,reader,meshBrush,world,brushes,textures,meshPath);
        }
        reader.exitChunk();
    }
}

static void bf_host_parse_node(BFHostB3DReader &reader,
                               const BFHostMat4 &parent,
                               std::vector<bf_host_mesh_surface> &surfaces,
                               const std::vector<BFHostBrushDef> &brushes,
                               const std::vector<BFHostTextureDef> &textures,
                               const std::string &meshPath){
    (void)reader.readString();
    const BFHostVec3 pos{reader.readFloat(),reader.readFloat(),reader.readFloat()};
    const BFHostVec3 scale{reader.readFloat(),reader.readFloat(),reader.readFloat()};
    const BFHostQuat rot{reader.readFloat(),reader.readFloat(),reader.readFloat(),reader.readFloat()};
    const BFHostMat4 world=bf_host_mat4_mul(parent,bf_host_mat4_from_trs(pos,scale,rot));
    while(reader.chunkSize()>0){
        char tag[5];
        if(!reader.enterChunk(tag)) break;
        if(std::strcmp(tag,"MESH")==0){
            bf_host_parse_mesh(reader,world,surfaces,brushes,textures,meshPath);
        }else if(std::strcmp(tag,"NODE")==0){
            bf_host_parse_node(reader,world,surfaces,brushes,textures,meshPath);
        }
        reader.exitChunk();
    }
}

} // namespace

extern "C" void bf_host_free_b3d_mesh(bf_host_mesh_data *data){
    if(!data || !data->surfaces) return;
    for(uint32_t i=0;i<data->surfaceCount;++i){
        bf_host_mesh_surface &surface=data->surfaces[i];
        std::free(surface.vertices);
        std::free(surface.triangles);
        std::free(surface.texturePath);
    }
    std::free(data->surfaces);
    data->surfaces=nullptr;
    data->surfaceCount=0u;
}

extern "C" int bf_host_load_b3d_mesh(const char *path,bf_host_mesh_data *out){
    if(out){
        out->surfaceCount=0u;
        out->surfaces=nullptr;
    }
    if(!path || !*path || !out) return 0;
    FILE *fp=std::fopen(path,"rb");
    if(!fp) return 0;
    BFHostB3DReader reader(fp);
    char tag[5];
    if(!reader.enterChunk(tag) || std::strcmp(tag,"BB3D")!=0){
        std::fclose(fp);
        return 0;
    }
    const int version=reader.readInt();
    if(version>1){
        reader.exitChunk();
        std::fclose(fp);
        return 0;
    }
    std::vector<BFHostTextureDef> textures;
    std::vector<BFHostBrushDef> brushes;
    std::vector<bf_host_mesh_surface> surfaces;
    while(reader.chunkSize()>0){
        if(!reader.enterChunk(tag)) break;
        if(std::strcmp(tag,"TEXS")==0){
            bf_host_parse_texs(reader,textures);
        }else if(std::strcmp(tag,"BRUS")==0){
            bf_host_parse_brus(reader,brushes);
        }else if(std::strcmp(tag,"NODE")==0){
            bf_host_parse_node(reader,bf_host_mat4_identity(),surfaces,brushes,textures,path);
        }
        reader.exitChunk();
    }
    reader.exitChunk();
    std::fclose(fp);
    if(surfaces.empty()) return 0;
    out->surfaceCount=(uint32_t)surfaces.size();
    out->surfaces=(bf_host_mesh_surface*)std::calloc(surfaces.size(),sizeof(bf_host_mesh_surface));
    if(!out->surfaces){
        for(bf_host_mesh_surface &surface : surfaces){
            std::free(surface.vertices);
            std::free(surface.triangles);
            std::free(surface.texturePath);
        }
        return 0;
    }
    std::memcpy(out->surfaces,surfaces.data(),surfaces.size()*sizeof(bf_host_mesh_surface));
    return 1;
}

extern "C" int bf_host_text_width(const char *text,const char *fontName,int fontHeight,int bold,int italic,int underline){
    @autoreleasepool {
        const NSSize size=bf_host_measure_text(text,fontName,fontHeight,bold,italic,underline);
        return std::max(1,(int)std::ceil(size.width));
    }
}

extern "C" int bf_host_font_line_height(const char *fontName,int fontHeight,int bold,int italic,int underline){
    @autoreleasepool {
        (void)underline;
        NSFont *font=bf_host_resolve_font(fontName,fontHeight,bold,italic);
        const CGFloat lineHeight=std::ceil(font.ascender - font.descender + font.leading);
        return std::max(1,(int)lineHeight);
    }
}

extern "C" void bf_host_draw_text_rgba(uint32_t *pixels,int width,int height,int x,int y,const char *text,int argb,const char *fontName,int fontHeight,int bold,int italic,int underline,int centerX,int centerY){
    if(!pixels || width<=0 || height<=0 || !text || !*text) return;
    @autoreleasepool {
        std::lock_guard<std::mutex> lock(g_text_draw_mutex);
        NSString *string=bf_host_string_from_cstr(text);
        NSFont *font=bf_host_resolve_font(fontName,fontHeight,bold,italic);
        NSDictionary *attrs=bf_host_text_attributes(font,argb,underline);
        if(bf_trace_text_enabled()){
            std::fprintf(stderr,"[bf-text] raw=");
            for(int i=0;text[i] && i<32;++i){
                std::fprintf(stderr,"%02x ",(unsigned int)(unsigned char)text[i]);
            }
            std::fprintf(stderr," text='%s' ns='%s' font='%s' size=%d b=%d i=%d u=%d dst=%dx%d at=%d,%d\n",
                text,
                [[string description] UTF8String],
                [[font fontName] UTF8String],
                fontHeight,bold,italic,underline,width,height,x,y);
            std::fflush(stderr);
        }
        const NSSize size=bf_host_measure_text(text,fontName,fontHeight,bold,italic,underline);
        const int lineHeight=bf_host_font_line_height(fontName,fontHeight,bold,italic,underline);
        const int textW=std::max(1,(int)std::ceil(size.width)+2);
        const int textH=std::max(1,std::max(lineHeight,(int)std::ceil(size.height))+2);
        const int dstX=centerX ? (int)std::lround((CGFloat)x - size.width/2.0) : x;
        const int dstY=centerY ? (int)std::lround((CGFloat)y - (CGFloat)lineHeight/2.0) : y;
        std::vector<uint32_t> textPixels((size_t)textW*(size_t)textH,0u);
        CGColorSpaceRef cs=CGColorSpaceCreateDeviceRGB();
        if(!cs) return;
        CGContextRef ctx=CGBitmapContextCreate(
            textPixels.data(),
            (size_t)textW,
            (size_t)textH,
            8u,
            (size_t)textW*4u,
            cs,
            kCGImageAlphaPremultipliedFirst|kCGBitmapByteOrder32Little
        );
        CGColorSpaceRelease(cs);
        if(!ctx) return;
        NSGraphicsContext *gc=[NSGraphicsContext graphicsContextWithCGContext:ctx flipped:NO];
        if(!gc){
            CGContextRelease(ctx);
            return;
        }
        [NSGraphicsContext saveGraphicsState];
        [NSGraphicsContext setCurrentContext:gc];
        [gc setShouldAntialias:NO];
        [[NSColor clearColor] set];
        NSRectFill(NSMakeRect(0.0,0.0,(CGFloat)textW,(CGFloat)textH));
        [string drawAtPoint:NSMakePoint(1.0,1.0) withAttributes:attrs];
        [NSGraphicsContext restoreGraphicsState];
        CGContextRelease(ctx);
        const int flipRows=bf_host_text_needs_vertical_flip(pixels);
        for(int sy=0;sy<textH;++sy){
            const int py=dstY+sy;
            if(py<0 || py>=height) continue;
            for(int sx=0;sx<textW;++sx){
                const int px=dstX+sx;
                if(px<0 || px>=width) continue;
                // Off-screen texture buffers and the live backbuffer do not currently share the
                // same row orientation under AppKit text rasterization. Query the runtime for the
                // target canvas policy so Gooey screen text and F-UI texture text both stay upright.
                const int srcY=flipRows ? ((textH-1)-sy) : sy;
                const uint32_t srcArgb=bf_bgra_to_argb(textPixels[(size_t)srcY*(size_t)textW+(size_t)sx]);
                const uint32_t sa=(srcArgb>>24)&255u;
                if(sa==0u) continue;
                uint32_t *dst=&pixels[(size_t)py*(size_t)width+(size_t)px];
                if(sa>=255u){
                    *dst=srcArgb;
                    continue;
                }
                const uint32_t inv=255u-sa;
                const uint32_t sr=(srcArgb>>16)&255u;
                const uint32_t sg=(srcArgb>>8)&255u;
                const uint32_t sb=srcArgb&255u;
                const uint32_t dr=(*dst>>16)&255u;
                const uint32_t dg=(*dst>>8)&255u;
                const uint32_t db=*dst&255u;
                const uint32_t rr=(sr*sa + dr*inv)/255u;
                const uint32_t rg=(sg*sa + dg*inv)/255u;
                const uint32_t rb=(sb*sa + db*inv)/255u;
                *dst=0xff000000u|(rr<<16)|(rg<<8)|rb;
            }
        }
    }
}

extern "C" int bf_host_key_down(int code){
    return (code>=0 && code<(int)g_key_down.size()) ? g_key_down[(size_t)code] : 0;
}

extern "C" int bf_host_key_hit(int code){
    if(code<0 || code>=(int)g_key_hit.size()) return 0;
    const int hit=g_key_hit[(size_t)code];
    g_key_hit[(size_t)code]=0;
    return hit;
}

extern "C" int bf_host_get_key(void){
    if(g_key_queue.empty()) return 0;
    const int ch=g_key_queue.front();
    g_key_queue.erase(g_key_queue.begin());
    return ch;
}

extern "C" void bf_host_flush_keys(void){
    g_key_hit.fill(0);
    g_key_queue.clear();
}

extern "C" int bf_host_mouse_down(int button){
    return (button>=1 && button<=3) ? g_mouse_down[(size_t)button] : 0;
}

extern "C" int bf_host_mouse_hit(int button){
    if(button<1 || button>3) return 0;
    const int hit=g_mouse_hit[(size_t)button];
    g_mouse_hit[(size_t)button]=0;
    return hit;
}

extern "C" int bf_host_mouse_x(void){ return g_mouse_x; }
extern "C" int bf_host_mouse_y(void){ return g_mouse_y; }
extern "C" int bf_host_mouse_x_speed(void){ const int v=g_mouse_dx; g_mouse_dx=0; return v; }
extern "C" int bf_host_mouse_y_speed(void){ const int v=g_mouse_dy; g_mouse_dy=0; return v; }
extern "C" int bf_host_mouse_z(void){ return g_mouse_z; }
extern "C" int bf_host_mouse_z_speed(void){ const int v=g_mouse_dz; g_mouse_dz=0; return v; }
extern "C" void bf_host_flush_mouse(void){ g_mouse_hit={0,0,0,0}; g_mouse_dx=0; g_mouse_dy=0; g_mouse_dz=0; }

extern "C" void bf_host_mouse_set_position(int x,int y){
    if(!g_window || !g_view) return;
    @autoreleasepool {
        NSRect frame=[g_window frame];
        const CGFloat px=frame.origin.x + (CGFloat)x;
        const CGFloat py=frame.origin.y + frame.size.height - (CGFloat)y;
        CGWarpMouseCursorPosition(CGPointMake(px,py));
        g_mouse_dx=x-g_mouse_x;
        g_mouse_dy=y-g_mouse_y;
        g_mouse_x=x;
        g_mouse_y=y;
    }
}

extern "C" void bf_host_show_pointer(int visible){
    @autoreleasepool {
        if(visible){
            if(g_cursor_hidden){ [NSCursor unhide]; g_cursor_hidden=false; }
        }else{
            if(!g_cursor_hidden){ [NSCursor hide]; g_cursor_hidden=true; }
        }
    }
}
