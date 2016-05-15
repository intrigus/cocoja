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

package com.intrigus.cocoja.tests;

import java.nio.ByteBuffer;

import javax.swing.JFrame;

import com.intrigus.cocoja.Cocoja;
import com.intrigus.cocoja.NSApplication;
import com.intrigus.cocoja.ObjcClass;
import com.intrigus.cocoja.ObjcRuntime;
import com.intrigus.cocoja.Pointer;

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
		// app.doSth();
		ObjcClass cls = ObjcRuntime.objc_lookUpClass("NSMenu");
		System.out.println("Name of class NSMenu:" + cls.getName());
		System.out.println("WeakIvarLayout of NSMenu:" + cls.getWeakIvarLayout());
		System.out.println("IvarLayout of NSMenu:" + cls.getIvarLayout());
		System.out.println("Name of superclass of NSMenu:" + cls.getSuperclass().getName());
		Pointer p = ObjcRuntime.sel_getUid("stringWithUTF8String:");
		System.out.println("Pointer to 'stringWithUTF8String:' :" + p);
		System.out.println("Name of pointer to 'stringWithUTF8String:' :" + ObjcRuntime.sel_getName(p));

	}

}
