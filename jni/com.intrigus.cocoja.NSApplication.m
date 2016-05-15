#include <com.intrigus.cocoja.NSApplication.h>

//@line:25

	#import <Foundation/Foundation.h>
	#import <AppKit/AppKit.h>
	#import <JavaNativeFoundation/JavaNativeFoundation.h>	
	JNIEXPORT void JNICALL Java_com_intrigus_cocoja_NSApplication__1doSth(JNIEnv* env, jclass clazz) {


//@line:43

	JNF_COCOA_ENTER(env);
	void (^block)(void);
     block = ^(void){
	NSMenu *mainMenu = [[NSApplication sharedApplication] mainMenu];
	NSMenu *appMenu = [[mainMenu itemAtIndex:0] submenu];
   NSMenuItem *aboutMenu = (NSMenuItem*)[appMenu itemAtIndex:0];
   if(aboutMenu == nil) {
       return;
   }
   [aboutMenu setEnabled:YES];
   //[aboutMenu setTarget:self];
   [aboutMenu setAction:@selector(NSLog)];
	
	};
     if ( [NSThread isMainThread]){
        block();
     } else {
        [JNFRunLoop performOnMainThreadWaiting:YES withBlock:block];
     }
    JNF_COCOA_EXIT(env);
	

}

JNIEXPORT void JNICALL Java_com_intrigus_cocoja_NSApplication__1setApplicationIconImageBuffer(JNIEnv* env, jclass clazz, jobject obj_buffer, jint width, jint height) {
	char* buffer = (char*)(obj_buffer?(*env)->GetDirectBufferAddress(env, obj_buffer):0);


//@line:90

	JNF_COCOA_ENTER(env);
	void (^block)(void);
     block = ^(void){
	NSImage* image;
   NSBitmapImageRep* rep;

    rep = [[NSBitmapImageRep alloc]
        initWithBitmapDataPlanes:NULL
                      pixelsWide:width
                      pixelsHigh:height
                   bitsPerSample:8
                 samplesPerPixel:4
                        hasAlpha:YES
                        isPlanar:NO
                  colorSpaceName:NSCalibratedRGBColorSpace
                    bitmapFormat:NSAlphaNonpremultipliedBitmapFormat
                     bytesPerRow:width * 4
                    bitsPerPixel:32];

    memcpy([rep bitmapData], buffer, width * height * 4);

    image = [[NSImage alloc] initWithSize:NSMakeSize(width, height)];
    [image addRepresentation: rep];
    [NSApp setApplicationIconImage: image];
    
    [image release];
    [rep release];
    
    };
     if ( [NSThread isMainThread]){
        block();
     } else {
        [JNFRunLoop performOnMainThreadWaiting:YES withBlock:block];
     }
    JNF_COCOA_EXIT(env);
	

}

JNIEXPORT void JNICALL Java_com_intrigus_cocoja_NSApplication__1setBadgeLabel(JNIEnv* env, jclass clazz, jstring badgeText) {

//@line:130
	
	JNF_COCOA_ENTER(env);
	
	NSString *nstext = JNFJavaToNSString(env, badgeText);
	
	void (^block)(void);
     block = ^(void){
        [[NSApp dockTile] setBadgeLabel:nstext];	
     };
     if ( [NSThread isMainThread]){
        block();
     } else {
        [JNFRunLoop performOnMainThreadWaiting:YES withBlock:block];
     }
	
	
	JNF_COCOA_EXIT(env);
	
}

