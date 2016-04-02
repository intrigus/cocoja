/*******************************************************************************
 * Copyright 2016 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.intrigus.cocoja;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class NSApplication {

	// @off
	/*JNI
	#import <Foundation/Foundation.h>
	#import <AppKit/AppKit.h>
	#import <JavaNativeFoundation/JavaNativeFoundation.h>	
	*/
	// @on

	private static NSApplication app = new NSApplication();

	private NSApplication () {

	}

	public void doSth () {
		_doSth();
	}

	// @off
	private static native void _doSth(); /*
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
	*/
   // @on

	public static NSApplication getApplication () {
		return app;
	}

	public void setBadgeLabel (String text) {
		_setBadgeLabel(text);
	}

	public void setApplicationIconImage (NSImage image) {

	}

	public void setApplicationIconImage (BufferedImage image) {
		// image.getRaster().
	}

	public void setApplicationIconImage (ByteBuffer buffer, int width, int height) {
		_setApplicationIconImageBuffer(buffer, width, height);
	}

	// @off
	private static native void _setApplicationIconImageBuffer(ByteBuffer buffer, int width, int height); /*
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
	*/
	// @on

	// @off
	private static native void _setBadgeLabel(String badgeText); /*MANUAL	
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
	*/
	// @on
}
