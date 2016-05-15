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

/** The <code>GraphicsDevice</code> class describes the graphics devices that might be available in a particular graphics
 * environment. These include screen and printer devices. Note that there can be many screens and many printers in an instance of
 * {@link GraphicsEnvironment}. Each graphics device has one or more {@link GraphicsConfiguration} objects associated with it.
 * These objects specify the different configurations in which the <code>GraphicsDevice</code> can be used.
 * <p>
 * In a multi-screen environment, the <code>GraphicsConfiguration</code> objects can be used to render components on multiple
 * screens. The following code sample demonstrates how to create a <code>JFrame</code> object for each
 * <code>GraphicsConfiguration</code> on each screen device in the <code>GraphicsEnvironment</code>:
 * 
 * <pre>
 * {
 * 	&#64;code
 * 	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
 * 	GraphicsDevice[] gs = ge.getScreenDevices();
 * 	for (int j = 0; j < gs.length; j++) {
 * 		GraphicsDevice gd = gs[j];
 * 		GraphicsConfiguration[] gc = gd.getConfigurations();
 * 		for (int i = 0; i < gc.length; i++) {
 * 			JFrame f = new JFrame(gs[j].getDefaultConfiguration());
 * 			Canvas c = new Canvas(gc[i]);
 * 			Rectangle gcBounds = gc[i].getBounds();
 * 			int xoffs = gcBounds.x;
 * 			int yoffs = gcBounds.y;
 * 			f.getContentPane().add(c);
 * 			f.setLocation((i * 50) + xoffs, (i * 60) + yoffs);
 * 			f.show();
 * 		}
 * 	}
 * }
 * </pre>
 * <p>
 * For more information on full-screen exclusive mode API, see the
 * <a href="https://docs.oracle.com/javase/tutorial/extra/fullscreen/index.html"> Full-Screen Exclusive Mode API Tutorial</a>.
 *
 * @see GraphicsEnvironment
 * @see GraphicsConfiguration */
public abstract class GraphicsDevice {

	/** This is an abstract class that cannot be instantiated directly. Instances must be obtained from a suitable factory or query
	 * method.
	 * @see GraphicsEnvironment#getScreenDevices
	 * @see GraphicsEnvironment#getDefaultScreenDevice
	 * @see GraphicsConfiguration#getDevice */
	protected GraphicsDevice () {
	}

	/** Device is a raster screen. */
	public final static int TYPE_RASTER_SCREEN = 0;

	/** Device is a printer. */
	public final static int TYPE_PRINTER = 1;

	/** Device is an image buffer. This buffer can reside in device or system memory but it is not physically viewable by the
	 * user. */
	public final static int TYPE_IMAGE_BUFFER = 2;

	/** Returns the type of this <code>GraphicsDevice</code>.
	 * @return the type of this <code>GraphicsDevice</code>, which can either be TYPE_RASTER_SCREEN, TYPE_PRINTER or
	 *         TYPE_IMAGE_BUFFER.
	 * @see #TYPE_RASTER_SCREEN
	 * @see #TYPE_PRINTER
	 * @see #TYPE_IMAGE_BUFFER */
	public abstract int getType ();

	/** Returns all of the <code>GraphicsConfiguration</code> objects associated with this <code>GraphicsDevice</code>.
	 * @return an array of <code>GraphicsConfiguration</code> objects that are associated with this <code>GraphicsDevice</code>. */
	public abstract GraphicsConfiguration[] getConfigurations ();

	/** Returns the default <code>GraphicsConfiguration</code> associated with this <code>GraphicsDevice</code>.
	 * @return the default <code>GraphicsConfiguration</code> of this <code>GraphicsDevice</code>. */
	public abstract GraphicsConfiguration getDefaultConfiguration ();

}
