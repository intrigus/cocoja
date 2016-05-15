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

import java.awt.Rectangle;

import com.intrigus.cocoja.java.awt.GraphicsConfiguration;
//@off
/*JNI
 // Copyright (c) 2011, 2012, Oracle and/or its affiliates. All rights reserved.
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

//#include "LWCToolkit.h"
//#include "GeomUtilities.h"

//#include "sun_awt_CGraphicsConfig.h"
#import <Cocoa/Cocoa.h>
#import <JavaNativeFoundation/JavaNativeFoundation.h>


//#import "GeomUtilities.h"

static JNF_CLASS_CACHE(sjc_Point2D, "java/awt/geom/Point2D");
static JNF_MEMBER_CACHE(jm_pt_getX, sjc_Point2D, "getX", "()D");
static JNF_MEMBER_CACHE(jm_pt_getY, sjc_Point2D, "getY", "()D");

static JNF_CLASS_CACHE(sjc_Dimension2D, "java/awt/geom/Dimension2D");
static JNF_MEMBER_CACHE(jm_sz_getWidth, sjc_Dimension2D, "getWidth", "()D");
static JNF_MEMBER_CACHE(jm_sz_getHeight, sjc_Dimension2D, "getHeight", "()D");

static JNF_CLASS_CACHE(sjc_Rectangle2D, "java/awt/geom/Rectangle2D");
static JNF_MEMBER_CACHE(jm_rect_getX, sjc_Rectangle2D, "getX", "()D");
static JNF_MEMBER_CACHE(jm_rect_getY, sjc_Rectangle2D, "getY", "()D");
static JNF_MEMBER_CACHE(jm_rect_getWidth, sjc_Rectangle2D, "getWidth", "()D");
static JNF_MEMBER_CACHE(jm_rect_getHeight, sjc_Rectangle2D, "getHeight", "()D");


static jobject NewJavaRect(JNIEnv *env, jdouble x, jdouble y, jdouble w, jdouble h) {
    static JNF_CLASS_CACHE(sjc_Rectangle2DDouble, "java/awt/geom/Rectangle2D$Double");
    static JNF_CTOR_CACHE(ctor_Rectangle2DDouble, sjc_Rectangle2DDouble, "(DDDD)V");
    return JNFNewObject(env, ctor_Rectangle2DDouble, x, y, w, h);
}

jobject CGToJavaRect(JNIEnv *env, CGRect rect) {
   return NewJavaRect(env,
                      rect.origin.x,
                      rect.origin.y,
                      rect.size.width,
                      rect.size.height);
}
*/
//@on

public class CGraphicsConfig extends GraphicsConfiguration {

	private final CGraphicsDevice device;

	protected CGraphicsConfig (CGraphicsDevice device) {
		this.device = device;
	}

	//@off
	private static native Rectangle nativeGetBounds (int displayID);/*
	jobject jrect = NULL;

	JNF_COCOA_ENTER(env);

    	CGRect rect = CGDisplayBounds((CGDirectDisplayID)displayID);
    	jrect = CGToJavaRect(env, rect);

	JNF_COCOA_EXIT(env);

    return jrect;
	*/
	//@on

	@Override
	public Rectangle getBounds () {
		final Rectangle nativeBounds = nativeGetBounds(device.getCGDisplayID());
		return nativeBounds.getBounds(); // does integer rounding
	}

	@Override
	public CGraphicsDevice getDevice () {
		return device;
	}

}
