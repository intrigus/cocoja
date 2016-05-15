/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.intrigus.cocoja.sun.awt;

import java.util.HashMap;
import java.util.Map;

import com.intrigus.cocoja.java.awt.GraphicsDevice;
import com.intrigus.cocoja.java.awt.GraphicsEnvironment;

//@off
/*JNI
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
 */
//@on

/** This is an implementation of a GraphicsEnvironment object for the default local GraphicsEnvironment used by the Java Runtime
 * Environment for Mac OS X GUI environments.
 *
 * @see GraphicsDevice
 * @see GraphicsConfiguration */
public final class CGraphicsEnvironment extends GraphicsEnvironment {

	// Global initialization of the Cocoa runtime.
	//@off
	private static native void initCocoa ();/*
	printf("byte: \n");
	*/
	//@on

	/** Fetch an array of all valid CoreGraphics display identifiers. */
	//@off
	private static native int[] getDisplayIDs ();/*
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
	*/
	//@on

	/** Fetch the CoreGraphics display ID for the 'main' display. */
	//@off
	private static native int getMainDisplayID ();/*
	return CGMainDisplayID();
	*/
	//@on

	/** Noop function that just acts as an entry point for someone to force a static initialization of this class. */
	public static void init () {
	}

	static {
		//initCocoa();
		

		// Install the correct surface manager factory.
		// SurfaceManagerFactory.setInstance(new MacosxSurfaceManagerFactory());
	}

	/** Register the instance with CGDisplayRegisterReconfigurationCallback(). The registration uses a weak global reference -- if
	 * our instance is garbage collected, the reference will be dropped.
	 *
	 * @return Return the registration context (a pointer). */
	//@off
	private native long registerDisplayReconfiguration ();/*
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
	*/
	//@on

	/** Remove the instance's registration with CGDisplayRemoveReconfigurationCallback() */
	//@off
	private native void deregisterDisplayReconfiguration (long context);/*
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
	*/
	//@on

	/** Available CoreGraphics displays. */
	private final Map<Integer, CGraphicsDevice> devices = new HashMap<>(5);

	/** Reference to the display reconfiguration callback context. */
	private final long displayReconfigContext;

	/** Construct a new instance. */
	public CGraphicsEnvironment () {
		/*
		 * if (isHeadless()) { displayReconfigContext = 0L; return; }
		 */

		/* Populate the device table */
		initDevices();

		/* Register our display reconfiguration listener */
		displayReconfigContext = registerDisplayReconfiguration();
		if (displayReconfigContext == 0L) {
			throw new RuntimeException("Could not register CoreGraphics display reconfiguration callback");
		}
	}

	/** Called by the CoreGraphics Display Reconfiguration Callback.
	 *
	 * @param displayId CoreGraphics displayId
	 * @param removed true if displayId was removed, false otherwise. */
	void _displayReconfiguration (final int displayId, final boolean removed) {
		synchronized (this) {
			if (removed && devices.containsKey(displayId)) {
				final CGraphicsDevice gd = devices.remove(displayId);
				gd.invalidate(getMainDisplayID());
				// gd.displayChanged();
			}
		}
		initDevices();
	}

	@Override
	protected void finalize () throws Throwable {
		try {
			super.finalize();
		} finally {
			deregisterDisplayReconfiguration(displayReconfigContext);
		}
	}

	/** (Re)create all CGraphicsDevices, reuses a devices if it is possible. */
	private void initDevices () {
		synchronized (this) {
			final Map<Integer, CGraphicsDevice> old = new HashMap<>(devices);
			devices.clear();

			int mainID = getMainDisplayID();

			// initialization of the graphics device may change
			// list of displays on hybrid systems via an activation
			// of discrete video.
			// So, we initialize the main display first, and then
			// retrieve actual list of displays.
			if (!old.containsKey(mainID)) {
				old.put(mainID, new CGraphicsDevice(mainID));
			}

			for (final int id : getDisplayIDs()) {
				devices.put(id, old.containsKey(id) ? old.get(id) : new CGraphicsDevice(id));
			}
		}
		// displayChanged();
	}

	@Override
	public synchronized GraphicsDevice getDefaultScreenDevice () {
		final int mainDisplayID = getMainDisplayID();
		CGraphicsDevice d = devices.get(mainDisplayID);
		if (d == null) {
			// we do not expect that this may happen, the only response
			// is to re-initialize the list of devices
			initDevices();

			d = devices.get(mainDisplayID);
			if (d == null) {
				throw new RuntimeException("no screen devices");
			}
		}
		return d;
	}

	@Override
	public synchronized GraphicsDevice[] getScreenDevices () {
		return devices.values().toArray(new CGraphicsDevice[devices.values().size()]);
	}

	public synchronized GraphicsDevice getScreenDevice (int displayID) {
		return devices.get(displayID);
	}

	static String[] sLogicalFonts = {"Serif", "SansSerif", "Monospaced", "Dialog", "DialogInput"};

}
