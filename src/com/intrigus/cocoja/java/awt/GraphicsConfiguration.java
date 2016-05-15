/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.intrigus.cocoja.java.awt;

import java.awt.Rectangle;

/** The <code>GraphicsConfiguration</code> class describes the characteristics of a graphics destination such as a printer or
 * monitor. There can be many <code>GraphicsConfiguration</code> objects associated with a single graphics device, representing
 * different drawing modes or capabilities. The corresponding native structure will vary from platform to platform. For example,
 * on X11 windowing systems, each visual is a different <code>GraphicsConfiguration</code>. On Microsoft Windows,
 * <code>GraphicsConfiguration</code>s represent PixelFormats available in the current resolution and color depth.
 * <p>
 * In a virtual device multi-screen environment in which the desktop area could span multiple physical screen devices, the bounds
 * of the <code>GraphicsConfiguration</code> objects are relative to the virtual coordinate system. When setting the location of a
 * component, use {@link #getBounds() getBounds} to get the bounds of the desired <code>GraphicsConfiguration</code> and offset
 * the location with the coordinates of the <code>GraphicsConfiguration</code>, as the following code sample illustrates:
 * </p>
 *
 * <pre>
 * Frame f = new Frame(gc); // where gc is a GraphicsConfiguration
 * Rectangle bounds = gc.getBounds();
 * f.setLocation(10 + bounds.x, 10 + bounds.y);
 * </pre>
 *
 * <p>
 * To determine if your environment is a virtual device environment, call <code>getBounds</code> on all of the
 * <code>GraphicsConfiguration</code> objects in your system. If any of the origins of the returned bounds is not (0,&nbsp;0),
 * your environment is a virtual device environment.
 *
 * <p>
 * You can also use <code>getBounds</code> to determine the bounds of the virtual device. To do this, first call
 * <code>getBounds</code> on all of the <code>GraphicsConfiguration</code> objects in your system. Then calculate the union of all
 * of the bounds returned from the calls to <code>getBounds</code>. The union is the bounds of the virtual device. The following
 * code sample calculates the bounds of the virtual device.
 *
 * <pre>
 * {
 * 	&#64;code
 * 	Rectangle virtualBounds = new Rectangle();
 * 	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
 * 	GraphicsDevice[] gs = ge.getScreenDevices();
 * 	for (int j = 0; j < gs.length; j++) {
 * 		GraphicsDevice gd = gs[j];
 * 		GraphicsConfiguration[] gc = gd.getConfigurations();
 * 		for (int i = 0; i < gc.length; i++) {
 * 			virtualBounds = virtualBounds.union(gc[i].getBounds());
 * 		}
 * 	}
 * }
 * </pre>
 *
 * @see Window
 * @see Frame
 * @see GraphicsEnvironment
 * @see GraphicsDevice */
/*
 * REMIND: What to do about capabilities? The capabilities of the device can be determined by enumerating the possible
 * capabilities and checking if the GraphicsConfiguration implements the interface for that capability.
 *
 */

public abstract class GraphicsConfiguration {

	/** This is an abstract class that cannot be instantiated directly. Instances must be obtained from a suitable factory or query
	 * method.
	 *
	 * @see GraphicsDevice#getConfigurations
	 * @see GraphicsDevice#getDefaultConfiguration
	 * @see GraphicsDevice#getBestConfiguration
	 * @see Graphics2D#getDeviceConfiguration */
	protected GraphicsConfiguration () {
	}

	/** Returns the {@link GraphicsDevice} associated with this <code>GraphicsConfiguration</code>.
	 * @return a <code>GraphicsDevice</code> object that is associated with this <code>GraphicsConfiguration</code>. */
	public abstract GraphicsDevice getDevice ();

	/** Returns the bounds of the <code>GraphicsConfiguration</code> in the device coordinates. In a multi-screen environment with
	 * a virtual device, the bounds can have negative X or Y origins.
	 * @return the bounds of the area covered by this <code>GraphicsConfiguration</code>.
	 * @since 1.3 */
	public abstract Rectangle getBounds ();

}
