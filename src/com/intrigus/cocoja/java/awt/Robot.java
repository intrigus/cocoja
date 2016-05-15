/*
 * Copyright (c) 1999, 2014, Oracle and/or its affiliates. All rights reserved.
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

import com.intrigus.cocoja.java.awt.GraphicsDevice;
import com.intrigus.cocoja.java.awt.GraphicsEnvironment;
import com.intrigus.cocoja.java.awt.event.InputEvent;
import com.intrigus.cocoja.java.awt.peer.RobotPeer;
import com.intrigus.cocoja.sun.awt.CGraphicsDevice;
import com.intrigus.cocoja.sun.lwawt.macosx.CRobot;

/** This class is used to generate native system input events for the purposes of test automation, self-running demos, and other
 * applications where control of the mouse and keyboard is needed. The primary purpose of Robot is to facilitate automated testing
 * of Java platform implementations.
 * <p>
 * Using the class to generate input events differs from posting events to the AWT event queue or AWT components in that the
 * events are generated in the platform's native input queue. For example, <code>Robot.mouseMove</code> will actually move the
 * mouse cursor instead of just generating mouse move events.
 * <p>
 * Note that some platforms require special privileges or extensions to access low-level input control. If the current platform
 * configuration does not allow input control, an <code>AWTException</code> will be thrown when trying to construct Robot objects.
 * For example, X-Window systems will throw the exception if the XTEST 2.2 standard extension is not supported (or not enabled) by
 * the X server.
 * <p>
 * Applications that use Robot for purposes other than self-testing should handle these error conditions gracefully.
 *
 * @author Robi Khan
 * @since 1.3 */
public class Robot {
	private static final int MAX_DELAY = 60000;
	private RobotPeer peer;
	private boolean isAutoWaitForIdle = false;
	private int autoDelay = 0;
	private static int LEGAL_BUTTON_MASK = 0;

	/** Constructs a Robot object in the coordinate system of the primary screen.
	 * <p>
	 *
	 * @throws AWTException if the platform configuration does not allow low-level input control. This exception is always thrown
	 *            when GraphicsEnvironment.isHeadless() returns true
	 * @throws SecurityException if <code>createRobot</code> permission is not granted
	 * @see java.awt.GraphicsEnvironment#isHeadless
	 * @see SecurityManager#checkPermission
	 * @see AWTPermission */
	public Robot () {
		init(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}

	/** Creates a Robot for the given screen device. Coordinates passed to Robot method calls like mouseMove and
	 * createScreenCapture will be interpreted as being in the same coordinate system as the specified screen. Note that depending
	 * on the platform configuration, multiple screens may either:
	 * <ul>
	 * <li>share the same coordinate system to form a combined virtual screen</li>
	 * <li>use different coordinate systems to act as independent screens</li>
	 * </ul>
	 * This constructor is meant for the latter case.
	 * <p>
	 * If screen devices are reconfigured such that the coordinate system is affected, the behavior of existing Robot objects is
	 * undefined.
	 *
	 * @param screen A screen GraphicsDevice indicating the coordinate system the Robot will operate in.
	 * @throws AWTException if the platform configuration does not allow low-level input control. This exception is always thrown
	 *            when GraphicsEnvironment.isHeadless() returns true.
	 * @throws IllegalArgumentException if <code>screen</code> is not a screen GraphicsDevice.
	 * @throws SecurityException if <code>createRobot</code> permission is not granted
	 * @see java.awt.GraphicsEnvironment#isHeadless
	 * @see GraphicsDevice
	 * @see SecurityManager#checkPermission
	 * @see AWTPermission */
	public Robot (GraphicsDevice screen) {
		checkIsScreenDevice(screen);
		init(screen);
	}

	private void init (GraphicsDevice screen) {
		// Toolkit toolkit = Toolkit.getDefaultToolkit();
		// if (toolkit instanceof ComponentFactory) {
		peer = new CRobot(this, (CGraphicsDevice)screen);
		// peer = ((ComponentFactory)toolkit).createRobot(this, screen);
		// }
		initLegalButtonMask();
	}

	private static synchronized void initLegalButtonMask () {
		if (LEGAL_BUTTON_MASK != 0) return;

		int tmpMask = 0;
		if (true) {
			if (true) {
				final int buttonsNumber = 5;
				for (int i = 0; i < buttonsNumber; i++) {
					tmpMask |= InputEvent.getMaskForButton(i + 1);
				}
			}
		}
		tmpMask |= InputEvent.BUTTON1_MASK | InputEvent.BUTTON2_MASK | InputEvent.BUTTON3_MASK | InputEvent.BUTTON1_DOWN_MASK
			| InputEvent.BUTTON2_DOWN_MASK | InputEvent.BUTTON3_DOWN_MASK;
		LEGAL_BUTTON_MASK = tmpMask;
	}

	/* check if the given device is a screen device */
	private void checkIsScreenDevice (GraphicsDevice device) {
		if (device == null || device.getType() != GraphicsDevice.TYPE_RASTER_SCREEN) {
			throw new IllegalArgumentException("not a valid screen device");
		}
	}

	private transient Object anchor = new Object();

	/** Moves mouse pointer to given screen coordinates.
	 * @param x X position
	 * @param y Y position */
	public synchronized void mouseMove (int x, int y) {
		peer.mouseMove(x, y);
		afterEvent();
	}

	/** Presses one or more mouse buttons. The mouse buttons should be released using the {@link #mouseRelease(int)} method.
	 *
	 * @param buttons the Button mask; a combination of one or more mouse button masks.
	 *           <p>
	 *           It is allowed to use only a combination of valid values as a {@code buttons} parameter. A valid combination
	 *           consists of {@code InputEvent.BUTTON1_DOWN_MASK}, {@code InputEvent.BUTTON2_DOWN_MASK},
	 *           {@code InputEvent.BUTTON3_DOWN_MASK} and values returned by the {@link InputEvent#getMaskForButton(int)
	 *           InputEvent.getMaskForButton(button)} method.
	 *
	 *           The valid combination also depends on a {@link Toolkit#areExtraMouseButtonsEnabled()
	 *           Toolkit.areExtraMouseButtonsEnabled()} value as follows:
	 *           <ul>
	 *           <li>If support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() disabled} by Java then
	 *           it is allowed to use only the following standard button masks: {@code InputEvent.BUTTON1_DOWN_MASK},
	 *           {@code InputEvent.BUTTON2_DOWN_MASK}, {@code InputEvent.BUTTON3_DOWN_MASK}.
	 *           <li>If support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() enabled} by Java then it
	 *           is allowed to use the standard button masks and masks for existing extended mouse buttons, if the mouse has more
	 *           then three buttons. In that way, it is allowed to use the button masks corresponding to the buttons in the range
	 *           from 1 to {@link java.awt.MouseInfo#getNumberOfButtons() MouseInfo.getNumberOfButtons()}. <br>
	 *           It is recommended to use the {@link InputEvent#getMaskForButton(int) InputEvent.getMaskForButton(button)} method
	 *           to obtain the mask for any mouse button by its number.
	 *           </ul>
	 *           <p>
	 *           The following standard button masks are also accepted:
	 *           <ul>
	 *           <li>{@code InputEvent.BUTTON1_MASK}
	 *           <li>{@code InputEvent.BUTTON2_MASK}
	 *           <li>{@code InputEvent.BUTTON3_MASK}
	 *           </ul>
	 *           However, it is recommended to use {@code InputEvent.BUTTON1_DOWN_MASK}, {@code InputEvent.BUTTON2_DOWN_MASK},
	 *           {@code InputEvent.BUTTON3_DOWN_MASK} instead. Either extended {@code _DOWN_MASK} or old {@code _MASK} values
	 *           should be used, but both those models should not be mixed.
	 * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button and support for
	 *            extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() disabled} by Java
	 * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button that does not exist on
	 *            the mouse and support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() enabled} by Java
	 * @see #mouseRelease(int)
	 * @see InputEvent#getMaskForButton(int)
	 * @see Toolkit#areExtraMouseButtonsEnabled()
	 * @see java.awt.MouseInfo#getNumberOfButtons()
	 * @see java.awt.event.MouseEvent */
	public synchronized void mousePress (int buttons) {
		checkButtonsArgument(buttons);
		peer.mousePress(buttons);
		afterEvent();
	}

	/** Releases one or more mouse buttons.
	 *
	 * @param buttons the Button mask; a combination of one or more mouse button masks.
	 *           <p>
	 *           It is allowed to use only a combination of valid values as a {@code buttons} parameter. A valid combination
	 *           consists of {@code InputEvent.BUTTON1_DOWN_MASK}, {@code InputEvent.BUTTON2_DOWN_MASK},
	 *           {@code InputEvent.BUTTON3_DOWN_MASK} and values returned by the {@link InputEvent#getMaskForButton(int)
	 *           InputEvent.getMaskForButton(button)} method.
	 *
	 *           The valid combination also depends on a {@link Toolkit#areExtraMouseButtonsEnabled()
	 *           Toolkit.areExtraMouseButtonsEnabled()} value as follows:
	 *           <ul>
	 *           <li>If the support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() disabled} by Java
	 *           then it is allowed to use only the following standard button masks: {@code InputEvent.BUTTON1_DOWN_MASK},
	 *           {@code InputEvent.BUTTON2_DOWN_MASK}, {@code InputEvent.BUTTON3_DOWN_MASK}.
	 *           <li>If the support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() enabled} by Java
	 *           then it is allowed to use the standard button masks and masks for existing extended mouse buttons, if the mouse
	 *           has more then three buttons. In that way, it is allowed to use the button masks corresponding to the buttons in
	 *           the range from 1 to {@link java.awt.MouseInfo#getNumberOfButtons() MouseInfo.getNumberOfButtons()}. <br>
	 *           It is recommended to use the {@link InputEvent#getMaskForButton(int) InputEvent.getMaskForButton(button)} method
	 *           to obtain the mask for any mouse button by its number.
	 *           </ul>
	 *           <p>
	 *           The following standard button masks are also accepted:
	 *           <ul>
	 *           <li>{@code InputEvent.BUTTON1_MASK}
	 *           <li>{@code InputEvent.BUTTON2_MASK}
	 *           <li>{@code InputEvent.BUTTON3_MASK}
	 *           </ul>
	 *           However, it is recommended to use {@code InputEvent.BUTTON1_DOWN_MASK}, {@code InputEvent.BUTTON2_DOWN_MASK},
	 *           {@code InputEvent.BUTTON3_DOWN_MASK} instead. Either extended {@code _DOWN_MASK} or old {@code _MASK} values
	 *           should be used, but both those models should not be mixed.
	 * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button and support for
	 *            extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() disabled} by Java
	 * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button that does not exist on
	 *            the mouse and support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() enabled} by Java
	 * @see #mousePress(int)
	 * @see InputEvent#getMaskForButton(int)
	 * @see Toolkit#areExtraMouseButtonsEnabled()
	 * @see java.awt.MouseInfo#getNumberOfButtons()
	 * @see java.awt.event.MouseEvent */
	public synchronized void mouseRelease (int buttons) {
		checkButtonsArgument(buttons);
		peer.mouseRelease(buttons);
		afterEvent();
	}

	private void checkButtonsArgument (int buttons) {
		if ((buttons | LEGAL_BUTTON_MASK) != LEGAL_BUTTON_MASK) {
			throw new IllegalArgumentException("Invalid combination of button flags");
		}
	}

	/** Rotates the scroll wheel on wheel-equipped mice.
	 *
	 * @param wheelAmt number of "notches" to move the mouse wheel Negative values indicate movement up/away from the user,
	 *           positive values indicate movement down/towards the user.
	 *
	 * @since 1.4 */
	public synchronized void mouseWheel (int wheelAmt) {
		peer.mouseWheel(wheelAmt);
		afterEvent();
	}

	/** Presses a given key. The key should be released using the <code>keyRelease</code> method.
	 * <p>
	 * Key codes that have more than one physical key associated with them (e.g. <code>KeyEvent.VK_SHIFT</code> could mean either
	 * the left or right shift key) will map to the left key.
	 *
	 * @param keycode Key to press (e.g. <code>KeyEvent.VK_A</code>)
	 * @throws IllegalArgumentException if <code>keycode</code> is not a valid key
	 * @see #keyRelease(int)
	 * @see java.awt.event.KeyEvent */
	public synchronized void keyPress (int keycode) {
		checkKeycodeArgument(keycode);
		peer.keyPress(keycode);
		afterEvent();
	}

	/** Releases a given key.
	 * <p>
	 * Key codes that have more than one physical key associated with them (e.g. <code>KeyEvent.VK_SHIFT</code> could mean either
	 * the left or right shift key) will map to the left key.
	 *
	 * @param keycode Key to release (e.g. <code>KeyEvent.VK_A</code>)
	 * @throws IllegalArgumentException if <code>keycode</code> is not a valid key
	 * @see #keyPress(int)
	 * @see java.awt.event.KeyEvent */
	public synchronized void keyRelease (int keycode) {
		checkKeycodeArgument(keycode);
		peer.keyRelease(keycode);
		afterEvent();
	}

	private void checkKeycodeArgument (int keycode) {
		// rather than build a big table or switch statement here, we'll
		// just check that the key isn't VK_UNDEFINED and assume that the
		// peer implementations will throw an exception for other bogus
		// values e.g. -1, 999999
		if (keycode == 0x0 /* KeyEvent.VK_UNDEFINED */) {
			throw new IllegalArgumentException("Invalid key code");
		}
	}

	/*
	 * Called after an event is generated
	 */
	private void afterEvent () {
		autoWaitForIdle();
		autoDelay();
	}

	/** Returns whether this Robot automatically invokes <code>waitForIdle</code> after generating an event.
	 * @return Whether <code>waitForIdle</code> is automatically called */
	public synchronized boolean isAutoWaitForIdle () {
		return isAutoWaitForIdle;
	}

	/** Sets whether this Robot automatically invokes <code>waitForIdle</code> after generating an event.
	 * @param isOn Whether <code>waitForIdle</code> is automatically invoked */
	public synchronized void setAutoWaitForIdle (boolean isOn) {
		isAutoWaitForIdle = isOn;
	}

	/*
	 * Calls waitForIdle after every event if so desired.
	 */
	private void autoWaitForIdle () {
		if (isAutoWaitForIdle) {
			waitForIdle();
		}
	}

	/** Returns the number of milliseconds this Robot sleeps after generating an event. */
	public synchronized int getAutoDelay () {
		return autoDelay;
	}

	/** Sets the number of milliseconds this Robot sleeps after generating an event.
	 * @throws IllegalArgumentException If <code>ms</code> is not between 0 and 60,000 milliseconds inclusive */
	public synchronized void setAutoDelay (int ms) {
		checkDelayArgument(ms);
		autoDelay = ms;
	}

	/*
	 * Automatically sleeps for the specified interval after event generated.
	 */
	private void autoDelay () {
		delay(autoDelay);
	}

	/** Sleeps for the specified time. To catch any <code>InterruptedException</code>s that occur, <code>Thread.sleep()</code> may
	 * be used instead.
	 * @param ms time to sleep in milliseconds
	 * @throws IllegalArgumentException if <code>ms</code> is not between 0 and 60,000 milliseconds inclusive
	 * @see java.lang.Thread#sleep */
	public synchronized void delay (int ms) {
		checkDelayArgument(ms);
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ite) {
			ite.printStackTrace();
		}
	}

	private void checkDelayArgument (int ms) {
		if (ms < 0 || ms > MAX_DELAY) {
			throw new IllegalArgumentException("Delay must be to 0 to 60,000ms");
		}
	}

	/** Waits until all events currently on the event queue have been processed.
	 * @throws IllegalThreadStateException if called on the AWT event dispatching thread */
	public synchronized void waitForIdle () {
		// checkNotDispatchThread();
		// post a dummy event to the queue so we know when
		// all the events before it have been processed
		/*
		 * try { SunToolkit.flushPendingEvents(); EventQueue.invokeAndWait(new Runnable() { public void run () { // dummy
		 * implementation } }); } catch (InterruptedException ite) { System.err.println(
		 * "Robot.waitForIdle, non-fatal exception caught:"); ite.printStackTrace(); } catch (InvocationTargetException ine) {
		 * System.err.println("Robot.waitForIdle, non-fatal exception caught:"); ine.printStackTrace(); }
		 */
	}

	/*
	 * private void checkNotDispatchThread () { if (EventQueue.isDispatchThread()) { throw new IllegalThreadStateException(
	 * "Cannot call method from the event dispatcher thread"); } }
	 */

	/** Returns a string representation of this Robot.
	 *
	 * @return the string representation. */
	public synchronized String toString () {
		String params = "autoDelay = " + getAutoDelay() + ", " + "autoWaitForIdle = " + isAutoWaitForIdle();
		return getClass().getName() + "[ " + params + " ]";
	}
}
