
package com.intrigus.cocoja.java.awt;

import java.security.AccessController;

import com.intrigus.cocoja.sun.awt.CGraphicsEnvironment;

import sun.security.action.GetPropertyAction;

/** The <code>GraphicsEnvironment</code> class describes the collection of {@link GraphicsDevice} objects and
 * {@link java.awt.Font} objects available to a Java(tm) application on a particular platform. The resources in this
 * <code>GraphicsEnvironment</code> might be local or on a remote machine. <code>GraphicsDevice</code> objects can be screens,
 * printers or image buffers and are the destination of {@link Graphics2D} drawing methods. Each <code>GraphicsDevice</code> has a
 * number of {@link GraphicsConfiguration} objects associated with it. These objects specify the different configurations in which
 * the <code>GraphicsDevice</code> can be used.
 * @see GraphicsDevice
 * @see GraphicsConfiguration */

public abstract class GraphicsEnvironment {
	private static GraphicsEnvironment localEnv;

	/** This is an abstract class and cannot be instantiated directly. Instances must be obtained from a suitable factory or query
	 * method. */
	protected GraphicsEnvironment () {
	}

	/** Returns the local <code>GraphicsEnvironment</code>.
	 * @return the local <code>GraphicsEnvironment</code> */
	public static synchronized GraphicsEnvironment getLocalGraphicsEnvironment () {
		if (localEnv == null) {
			localEnv = createGE();
		}

		return localEnv;
	}

	/** Creates and returns the GraphicsEnvironment, according to the system property 'java.awt.graphicsenv'.
	 *
	 * @return the graphics environment */
	private static GraphicsEnvironment createGE () {
		/*GraphicsEnvironment ge;
		String nm = AccessController.doPrivileged(new GetPropertyAction("java.awt.graphicsenv", null));
		try {
// long t0 = System.currentTimeMillis();
			Class<GraphicsEnvironment> geCls;
			try {
				// First we try if the bootclassloader finds the requested
				// class. This way we can avoid to run in a privileged block.
				geCls = (Class<GraphicsEnvironment>)Class.forName(nm);
			} catch (ClassNotFoundException ex) {
				// If the bootclassloader fails, we try again with the
				// application classloader.
				ClassLoader cl = ClassLoader.getSystemClassLoader();
				geCls = (Class<GraphicsEnvironment>)Class.forName(nm, true, cl);
			}
			ge = geCls.newInstance();
// long t1 = System.currentTimeMillis();
// System.out.println("GE creation took " + (t1-t0)+ "ms.");

		} catch (ClassNotFoundException e) {
			throw new Error("Could not find class: " + nm);
		} catch (InstantiationException e) {
			throw new Error("Could not instantiate Graphics Environment: " + nm);
		} catch (IllegalAccessException e) {
			throw new Error("Could not access Graphics Environment: " + nm);
		}
		return ge;*/
		return new CGraphicsEnvironment();
	}

	/** Returns an array of all of the screen <code>GraphicsDevice</code> objects.
	 * @return an array containing all the <code>GraphicsDevice</code> objects that represent screen devices
	 * @exception HeadlessException if isHeadless() returns true
	 * @see #isHeadless() */
	public abstract GraphicsDevice[] getScreenDevices ();

	/** Returns the default screen <code>GraphicsDevice</code>.
	 * @return the <code>GraphicsDevice</code> that represents the default screen device
	 * @exception HeadlessException if isHeadless() returns true
	 * @see #isHeadless() */
	public abstract GraphicsDevice getDefaultScreenDevice ();

}
