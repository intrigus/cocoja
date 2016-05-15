#include <com.intrigus.cocoja.sun.awt.CGraphicsEnvironment.h>

//@line:35

 // Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 // DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 //
 // This code is free software; you can redistribute it and/or modify it
 // under the terms of the GNU General Public License version 2 only, as
 // published by the Free Software Foundation.  Oracle designates this
 // particular file as subject to the "Classpath" exception as provided
 // by Oracle in the LICENSE file that accompanied this code.
 //
 // This code is distributed in the hope that it will be useful, but WITHOUT
 // ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 // FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 // version 2 for more details (a copy is included in the LICENSE file that
 // accompanied this code).
 //
 // You should have received a copy of the GNU General Public License version
 // 2 along with this work; if not, write to the Free Software Foundation,
 // Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 //
 // Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 // or visit www.oracle.com if you need additional information or have any
 // questions.


#import <JavaNativeFoundation/JavaNativeFoundation.h>

#define MAX_DISPLAYS 64

static void displaycb_handle
(CGDirectDisplayID display, CGDisplayChangeSummaryFlags flags, void *userInfo)
{
    if (flags == kCGDisplayBeginConfigurationFlag) return;

    JNFPerformEnvBlock(JNFThreadDetachImmediately, ^(JNIEnv *env) {
        JNFWeakJObjectWrapper *wrapper = (JNFWeakJObjectWrapper *)userInfo;

        jobject graphicsEnv = [wrapper jObjectWithEnv:env];
        if (graphicsEnv == NULL) return; // ref already GC'd
        static JNF_CLASS_CACHE(jc_CGraphicsEnvironment, "sun/awt/CGraphicsEnvironment");
        static JNF_MEMBER_CACHE(jm_displayReconfiguration, jc_CGraphicsEnvironment, "_displayReconfiguration", "(IZ)V");
        JNFCallVoidMethod(env, graphicsEnv, jm_displayReconfiguration,
                            (jint) display, 
                            (jboolean) flags & kCGDisplayRemoveFlag);
    });
}
 JNIEXPORT void JNICALL Java_com_intrigus_cocoja_sun_awt_CGraphicsEnvironment_initCocoa(JNIEnv* env, jclass clazz) {


//@line:93

	printf("byte: \n");
	

}

JNIEXPORT jintArray JNICALL Java_com_intrigus_cocoja_sun_awt_CGraphicsEnvironment_getDisplayIDs(JNIEnv* env, jclass clazz) {


//@line:100

	    jintArray ret = NULL;

JNF_COCOA_ENTER(env);

    // Get the count 
    CGDisplayCount displayCount;
    if (CGGetActiveDisplayList(MAX_DISPLAYS, NULL, &displayCount) != kCGErrorSuccess) {
        [JNFException raise:env
                         //as:kInternalError
                         as:kRuntimeException
                     reason:"CGGetOnlineDisplayList() failed to get display count"];
        return NULL;
    }

    // Allocate an array and get the size list of display Ids 
    CGDirectDisplayID displays[MAX_DISPLAYS];
    if (CGGetActiveDisplayList(displayCount, displays, &displayCount) != kCGErrorSuccess) {
        [JNFException raise:env
                          //as:kInternalError
                         as:kRuntimeException
                     reason:"CGGetOnlineDisplayList() failed to get display list"];
        return NULL;
    }

    // Allocate a java array for display identifiers 
    ret = JNFNewIntArray(env, displayCount);

    // Initialize and return the backing int array 
    assert(sizeof(jint) >= sizeof(CGDirectDisplayID));
    jint *elems = (*env)->GetIntArrayElements(env, ret, 0);

    CGDisplayCount i;
    for (i = 0; i < displayCount; i++) {
        elems[i] = displays[i];
    }

    (*env)->ReleaseIntArrayElements(env, ret, elems, 0);

    JNF_COCOA_EXIT(env);

    return ret;
	

}

JNIEXPORT jint JNICALL Java_com_intrigus_cocoja_sun_awt_CGraphicsEnvironment_getMainDisplayID(JNIEnv* env, jclass clazz) {


//@line:147

	return CGMainDisplayID();
	

}

JNIEXPORT jlong JNICALL Java_com_intrigus_cocoja_sun_awt_CGraphicsEnvironment_registerDisplayReconfiguration(JNIEnv* env, jobject object) {


//@line:169

	jlong ret = 0L;

    JNF_COCOA_ENTER(env);

    JNFWeakJObjectWrapper *wrapper = [JNFWeakJObjectWrapper wrapperWithJObject:object withEnv:env];
    CFRetain(wrapper); // pin from ObjC-GC

    // Register the callback 
    if (CGDisplayRegisterReconfigurationCallback(&displaycb_handle, wrapper) != kCGErrorSuccess) {
        [JNFException raise:env
                          //as:kInternalError
                         as:kRuntimeException
                     reason:"CGDisplayRegisterReconfigurationCallback() failed"];
        return 0L;
    }

    ret = ptr_to_jlong(wrapper);

    JNF_COCOA_EXIT(env);

    return ret;
	

}

JNIEXPORT void JNICALL Java_com_intrigus_cocoja_sun_awt_CGraphicsEnvironment_deregisterDisplayReconfiguration(JNIEnv* env, jobject object, jlong context) {


//@line:196

	JNF_COCOA_ENTER(env);

    JNFWeakJObjectWrapper *wrapper = (JNFWeakJObjectWrapper *)jlong_to_ptr(context);
    if (!wrapper) return;

    // Remove the registration 
    if (CGDisplayRemoveReconfigurationCallback(&displaycb_handle, wrapper) != kCGErrorSuccess) {
        [JNFException raise:env
                          //as:kInternalError
                         as:kRuntimeException
                     reason:"CGDisplayRemoveReconfigurationCallback() failed, leaking the callback context!"];
        return;
    }

    [wrapper setJObject:NULL withEnv:env]; // more efficiant to pre-clear

    CFRelease(wrapper);

JNF_COCOA_EXIT(env);
	

}

