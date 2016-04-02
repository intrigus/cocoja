
package com.intrigus.cocoja.tests;

import java.nio.ByteBuffer;

import javax.swing.JFrame;

import com.intrigus.cocoja.Cocoja;
import com.intrigus.cocoja.NSApplication;
import com.intrigus.cocoja.ObjcClass;
import com.intrigus.cocoja.ObjcRuntime;

public class NSApplicationTest {

	public static void main (String s[]) {
		JFrame frame = new JFrame("JFrame Example");
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Cocoja.init();
		NSApplication app = NSApplication.getApplication();
		app.setBadgeLabel("Hello !");
		app.setApplicationIconImage(ByteBuffer.allocateDirect(128 * 128 * 4), 128, 128);
		app.doSth();
		ObjcClass cls = ObjcRuntime.objc_lookUpClass("NSMenu");
		System.out.println("Name of class NSMenu:" + cls.getName());
		System.out.println("WeakIvarLayout of NSMenu:" + cls.getWeakIvarLayout());
		System.out.println("IvarLayout of NSMenu:" + cls.getIvarLayout());
		System.out.println("Name of superclass of NSMenu:" + cls.getSuperclass().getName());

	}

}
