/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

import com.intrigus.cocoja.java.awt.GraphicsConfiguration;
import com.intrigus.cocoja.java.awt.GraphicsDevice;

public final class CGraphicsDevice extends GraphicsDevice {

	/** CoreGraphics display ID. This identifier can become non-valid at any time therefore methods, which is using this id should
	 * be ready to it. */
	private volatile int displayID;

	// Array of all GraphicsConfig instances for this device
	private final GraphicsConfiguration[] configs;

	// Default config (temporarily hard coded)
	private final int DEFAULT_CONFIG = 0;

	public CGraphicsDevice (final int displayID) {
		this.displayID = displayID;
		configs = new GraphicsConfiguration[] {new CGraphicsConfig(this)};
	}

	/** Returns CGDirectDisplayID, which is the same id as @"NSScreenNumber" in NSScreen.
	 *
	 * @return CoreGraphics display id. */
	public int getCGDisplayID () {
		return displayID;
	}

	/** Return a list of all configurations. */
	@Override
	public GraphicsConfiguration[] getConfigurations () {
		return configs.clone();
	}

	/** Return the default configuration. */
	@Override
	public GraphicsConfiguration getDefaultConfiguration () {
		return configs[DEFAULT_CONFIG];
	}

	/** Returns the type of the graphics device.
	 * @see #TYPE_RASTER_SCREEN
	 * @see #TYPE_PRINTER
	 * @see #TYPE_IMAGE_BUFFER */
	@Override
	public int getType () {
		return TYPE_RASTER_SCREEN;
	}

	public void invalidate (final int defaultDisplayID) {
		displayID = defaultDisplayID;
	}

}
