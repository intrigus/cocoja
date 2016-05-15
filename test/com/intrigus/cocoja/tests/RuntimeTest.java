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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.intrigus.cocoja.Cocoja;
import com.intrigus.cocoja.ObjcClass;
import com.intrigus.cocoja.ObjcRuntime;
import com.intrigus.cocoja.Pointer;

public class RuntimeTest {

	@Before
	public void setUp () throws Exception {
		Cocoja.init();
	}

	@Test
	public void test () {
		// Load NSString class
		ObjcClass nsStringClass = ObjcRuntime.objc_lookUpClass("NSString");

		// Get the name of the class that we just loaded (should be NSString)
		String clsName = nsStringClass.getName();

		// Assert that the class name is what we expect
		assertEquals("NSString", clsName);

		// Get the UID for the stringWithUTF8String: selector
		Pointer strWithUTF8StringSelector = ObjcRuntime.sel_getUid("stringWithUTF8String:");

		// Get the name of the selector from its UID
		String selName = ObjcRuntime.sel_getName(strWithUTF8StringSelector);

		// Assert that the selector name is what we expect
		assertEquals("stringWithUTF8String:", selName);

		// Create a new string with the stringWithUTF8String: message
		// We are sending the message directly to the NSString class.
		long string = ObjcRuntime.objc_msgSend(nsStringClass, strWithUTF8StringSelector, "Test String");
		assertNotEquals(string, 0);

		// Now that we have our string let's send a message to it
		Pointer utf8StringSelector = ObjcRuntime.sel_getUid("UTF8String");

		// objc_msgSend takes a pointer, not a long so we need to wrap our string
		// inside a com.sun.jna.Pointer object
		Pointer stringPtr = new Pointer(string);

		long outStringPtr = ObjcRuntime.objc_msgSend(stringPtr, utf8StringSelector);
		assertNotEquals(outStringPtr, 0);
		// outStringPtr is a pointer to a CString, so let's convert it into
		// a Java string so we can check to make sure it matches what
		// we expect

		String outString = new Pointer(outStringPtr).getFromCString();
		assertEquals("Test String", outString);
	}

}
