#include <com.intrigus.cocoja.ObjcClass.h>

//@line:22

	#import <Foundation/Foundation.h>
	#import <objc/runtime.h>
	#import <JavaNativeFoundation/JavaNativeFoundation.h>	
	JNIEXPORT jstring JNICALL Java_com_intrigus_cocoja_ObjcClass__1getIvarLayout(JNIEnv* env, jclass clazz, jlong address) {


//@line:55
	
	void * class = jlong_to_ptr(address);
	const char * className = class_getIvarLayout(class);
	return (*env)->NewStringUTF(env, className);
	

}

JNIEXPORT jstring JNICALL Java_com_intrigus_cocoja_ObjcClass__1getName(JNIEnv* env, jclass clazz, jlong address) {


//@line:75
	
	void * class = jlong_to_ptr(address);
	const char * className = class_getName(class);
	return (*env)->NewStringUTF(env, className);
	

}

JNIEXPORT jlong JNICALL Java_com_intrigus_cocoja_ObjcClass__1getSuperclass(JNIEnv* env, jclass clazz, jlong address) {


//@line:91
	
	void * class = jlong_to_ptr(address);
	return ptr_to_jlong(class_getSuperclass(class));
	

}

JNIEXPORT jstring JNICALL Java_com_intrigus_cocoja_ObjcClass__1getWeakIvarLayout(JNIEnv* env, jclass clazz, jlong address) {


//@line:106
	
	void * class = jlong_to_ptr(address);
	const char * className = class_getWeakIvarLayout(class);
	return (*env)->NewStringUTF(env, className);
	

}

JNIEXPORT jboolean JNICALL Java_com_intrigus_cocoja_ObjcClass__1isMetaClass(JNIEnv* env, jclass clazz, jlong address) {


//@line:118
	
	void * class = jlong_to_ptr(address);
	return class_isMetaClass(class);
	

}

JNIEXPORT void JNICALL Java_com_intrigus_cocoja_ObjcClass__1setWeakIvarLayout(JNIEnv* env, jclass clazz, jlong address, jstring obj_layout) {
	char* layout = (char*)(*env)->GetStringUTFChars(env, obj_layout, 0);


//@line:149
	
	void * class = jlong_to_ptr(address);
	class_setWeakIvarLayout(class, layout);
	
	(*env)->ReleaseStringUTFChars(env, obj_layout, layout);

}

