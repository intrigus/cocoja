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

package com.intrigus.cocoja;

public class ObjcClass extends Pointer {

	// @off
	/*JNI
	#import <Foundation/Foundation.h>
	#import <objc/runtime.h>
	#import <JavaNativeFoundation/JavaNativeFoundation.h>	
	*/
	// @on

	public ObjcClass (long address) {
		super(address);
		// TODO Auto-generated constructor stub
	}

	public Pointer getClassMethod (ObjcClass cls, Pointer aSelector) {
		return new Pointer(address);
	}

	public Pointer getInstanceMethod (ObjcClass cls, Pointer aSelector) {
		return new Pointer(address);
	}

	public int getInstanceSize (ObjcClass cls) {
		return 0;
	}

	public Pointer getInstanceVariable (ObjcClass cls, String name) {
		return new Pointer(address);
	}

	public String getIvarLayout () {
		return _getIvarLayout(address);
	}

	//@off
	private static native String _getIvarLayout (long address);/*	
	void * class = jlong_to_ptr(address);
	const char * className = class_getIvarLayout(class);
	return (*env)->NewStringUTF(env, className);
	*/
	//@on

	public Pointer getMethodImplementation (ObjcClass cls, Pointer name) {
		return new Pointer(address);
	}

	public Pointer getMethodImplementation_stret (ObjcClass cls, Pointer name) {
		return new Pointer(address);
	}

	public String getName () {
		return _getName(address);
	}

	//@off
	private static native String _getName (long address);/*	
	void * class = jlong_to_ptr(address);
	const char * className = class_getName(class);
	return (*env)->NewStringUTF(env, className);
	*/
   //@on

	public Pointer getProperty (ObjcClass cls, String name) {
		return new Pointer(address);
	}

	public ObjcClass getSuperclass () {
		return new ObjcClass(_getSuperclass(address));
	}

	//@off
	private static native long _getSuperclass (long address);/*	
	void * class = jlong_to_ptr(address);
	return ptr_to_jlong(class_getSuperclass(class));
	*/
	//@on

	public int getVersion (ObjcClass cls) {
		return 0;
	}

	public String getWeakIvarLayout () {
		return _getWeakIvarLayout(address);
	}

	//@off
	private static native String _getWeakIvarLayout (long address);/*	
	void * class = jlong_to_ptr(address);
	const char * className = class_getWeakIvarLayout(class);
	return (*env)->NewStringUTF(env, className);
	*/
	//@on

	public boolean isMetaClass () {
		return _isMetaClass(address);
	}

	//@off
	private static native boolean _isMetaClass (long address);/*	
	void * class = jlong_to_ptr(address);
	return class_isMetaClass(class);
	*/
	//@on

	public Pointer replaceMethod (ObjcClass cls, Pointer name, Pointer imp, String types) {
		return new Pointer(address);
	}

	public Pointer respondsToSelector (ObjcClass cls, Pointer sel) {
		return new Pointer(address);
	}

	public void setIvarLayout (ObjcClass cls, String layout) {
		return;
	}

	public Pointer setSuperclass (ObjcClass cls, Pointer newSuper) {
		return new Pointer(address);
	}

	public void setVersion (Pointer theClass, int version) {
		// return;
	}

	public void setWeakIvarLayout (String layout) {
		_setWeakIvarLayout(address, layout);
	}

	//@off
	private static native void _setWeakIvarLayout (long address, String layout);/*	
	void * class = jlong_to_ptr(address);
	class_setWeakIvarLayout(class, layout);
	*/
	//@on
}
