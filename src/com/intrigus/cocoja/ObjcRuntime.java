
package com.intrigus.cocoja;

public class ObjcRuntime {

	// @off
	/*JNI
	#import <Foundation/Foundation.h>
	#import <objc/runtime.h>
	#import <JavaNativeFoundation/JavaNativeFoundation.h>	
	*/
	// @on

	public static String ivar_getName (Pointer ivar) {
		return null;
	}

	public static long ivar_getOffset (Pointer ivar) {
		return 0;
	}

	public static String ivar_getTypeEncoding (Pointer ivar) {
		return null;
	}

	public static String method_copyArgumentType (Pointer method, int index) {
		return null;
	}

	public static String method_copyReturnType (Pointer method) {
		return null;
	}

	public static void method_exchangeImplementations (Pointer m1, Pointer m2) {
	}

	public static void method_getArgumentType (Pointer method, int index, Pointer dst, long dst_len) {
	}

	public static Pointer method_getImplementation (Pointer method) {
		return method;
	}

	public static Pointer method_getName (Pointer method) {
		return method;
	}

	public static int method_getNumberOfArguments (Pointer method) {
		return 0;
	}

	public static void method_getReturnType (Pointer method, Pointer dst, long dst_len) {
	}

	public static String method_getTypeEncoding (Pointer method) {
		return null;
	}

	public static Pointer method_setImplementation (Pointer method, Pointer imp) {
		return imp;
	}

	public static Pointer objc_allocateClassPair (Pointer superclass, String name, long extraBytes) {
		return superclass;
	}

	public static Pointer[] objc_copyProtocolList (Pointer outCount) {
		return null;
	}

	public static Pointer objc_getAssociatedObject (Pointer object, String key) {
		return object;
	}

	public static ObjcClass objc_getClass (String name) {
		return new ObjcClass(_objc_getClass(name));
	}

	//@off
	private static native long _objc_getClass(String name);/*
	return ptr_to_jlong(objc_getClass(name));
	*/
	//@on

	public static int objc_getClassList (Pointer buffer, int bufferlen) {
		return bufferlen;
	}

	public static Pointer objc_getFutureClass (String name) {
		return null;
	}

	public static ObjcClass objc_getMetaClass (String name) {
		return new ObjcClass(_objc_getMetaClass(name));
	}

	//@off
	private static native long _objc_getMetaClass(String name);/*
	return ptr_to_jlong(objc_getMetaClass(name));
	*/
	//@on

	public static Pointer objc_getProtocol (String name) {
		return null;
	}

	public static Pointer objc_getRequiredClass (String name) {
		return null;
	}

	public static ObjcClass objc_lookUpClass (String name) {
		return new ObjcClass(_objc_lookUpClass(name));
	}

	//@off
	private static native long _objc_lookUpClass(String name);/*
	return ptr_to_jlong(objc_lookUpClass(name));
	*/
	//@on

	public static long objc_msgSend (Pointer theReceiver, Pointer theSelector, Object... arguments) {
		return 0;
	}

	public static double objc_msgSend_fpret (Pointer self, Pointer op, Object... arguments) {
		return 0;
	}

	public static void objc_msgSend_stret (Pointer stretAddr, Pointer theReceiver, Pointer theSelector, Object... arguments) {
	}

	public static long objc_msgSendSuper (Pointer superClassStruct, Pointer op, Object... arguments) {
		return 0;
	}

	public static long objc_msgSendSuper_stret (Pointer superClassStruct, Pointer op, Object... arguments) {
		return 0;
	}

	public static void objc_registerClassPair (ObjcClass cls) {
	}

	public static void objc_removeAssociatedObjects (Pointer object) {
	}

	public static void objc_setAssociatedObject (Pointer object, Pointer key, Pointer value, Pointer policy) {
	}

	public static void objc_setFutureClass (ObjcClass cls, String name) {
	}

	public static Pointer object_copy (Pointer obj, long size) {
		return obj;
	}

	public static Pointer object_dispose (Pointer obj) {
		return obj;
	}

	public static Pointer object_getClass (Pointer object) {
		return object;
	}

	public static String object_getClassName (Pointer obj) {
		return null;
	}

	public static Pointer object_getIndexedIvars (Pointer obj) {
		return obj;
	}

	public static Pointer object_getInstanceVariable (Pointer obj, String name, Pointer outValue) {
		return outValue;
	}

	public static Pointer object_getIvar (Pointer object, Pointer ivar) {
		return ivar;
	}

	public static Pointer object_setClass (Pointer object, ObjcClass cls) {
		return cls;
	}

	public static Pointer object_setInstanceVariable (Pointer obj, String name, Pointer value) {
		return value;
	}

	public static void object_setIvar (Pointer object, Pointer ivar, Pointer value) {
	}

	public static String property_getAttributes (Pointer property) {
		return null;
	}

	public static boolean protocol_conformsToProtocol (Pointer proto, Pointer other) {
		return false;
	}

	/*
	 * public static Structure protocol_copyMethodDescriptionList (Pointer protocol, boolean isRequiredMethod, boolean
	 * isInstanceMethod, Pointer outCount){}
	 */

	public static Pointer protocol_copyPropertyList (Pointer proto, Pointer outCount) {
		return outCount;
	}

	public static Pointer protocol_copyProtocolList (Pointer proto, Pointer outCount) {
		return outCount;
	}

	public static Pointer protocol_getMethodDescription (Pointer proto, Pointer aSel, boolean isRequiredMethod,
		boolean isInstanceMethod) {
		return aSel;
	}

	public static String protocol_getName (Pointer proto) {
		return null;
	}

	public static Pointer protocol_getProperty (Pointer proto, String name, boolean isRequiredProperty,
		boolean isInstanceProperty) {
		return proto;
	}

	public static boolean protocol_isEqual (Pointer protocol, Pointer other) {
		return false;
	}

	public static String sel_getName (Pointer aSelector) {
		return null;
	}

	public static Pointer sel_getUid (String name) {
		return null;
	}

	public static boolean sel_isEqual (Pointer lhs, Pointer rhs) {
		return false;
	}

	public static Pointer sel_registerName (String name) {
		return null;
	}
}
