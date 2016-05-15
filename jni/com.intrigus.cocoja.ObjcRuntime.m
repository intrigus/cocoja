#include <com.intrigus.cocoja.ObjcRuntime.h>

//@line:22

	#import <Foundation/Foundation.h>
	#import <objc/runtime.h>
	#import <objc/message.h>
	#import <JavaNativeFoundation/JavaNativeFoundation.h>	
	static inline jlong wrapped_Java_com_intrigus_cocoja_ObjcRuntime__1objc_1getClass
(JNIEnv* env, jclass clazz, jstring obj_name, char* name) {

//@line:96

	return ptr_to_jlong(objc_getClass(name));
	
}

JNIEXPORT jlong JNICALL Java_com_intrigus_cocoja_ObjcRuntime__1objc_1getClass(JNIEnv* env, jclass clazz, jstring obj_name) {
	char* name = (char*)(*env)->GetStringUTFChars(env, obj_name, 0);

	jlong JNI_returnValue = wrapped_Java_com_intrigus_cocoja_ObjcRuntime__1objc_1getClass(env, clazz, obj_name, name);

	(*env)->ReleaseStringUTFChars(env, obj_name, name);

	return JNI_returnValue;
}

static inline jlong wrapped_Java_com_intrigus_cocoja_ObjcRuntime__1objc_1getMetaClass
(JNIEnv* env, jclass clazz, jstring obj_name, char* name) {

//@line:114

	return ptr_to_jlong(objc_getMetaClass(name));
	
}

JNIEXPORT jlong JNICALL Java_com_intrigus_cocoja_ObjcRuntime__1objc_1getMetaClass(JNIEnv* env, jclass clazz, jstring obj_name) {
	char* name = (char*)(*env)->GetStringUTFChars(env, obj_name, 0);

	jlong JNI_returnValue = wrapped_Java_com_intrigus_cocoja_ObjcRuntime__1objc_1getMetaClass(env, clazz, obj_name, name);

	(*env)->ReleaseStringUTFChars(env, obj_name, name);

	return JNI_returnValue;
}

static inline jlong wrapped_Java_com_intrigus_cocoja_ObjcRuntime__1objc_1lookUpClass
(JNIEnv* env, jclass clazz, jstring obj_name, char* name) {

//@line:132

	return ptr_to_jlong(objc_lookUpClass(name));
	
}

JNIEXPORT jlong JNICALL Java_com_intrigus_cocoja_ObjcRuntime__1objc_1lookUpClass(JNIEnv* env, jclass clazz, jstring obj_name) {
	char* name = (char*)(*env)->GetStringUTFChars(env, obj_name, 0);

	jlong JNI_returnValue = wrapped_Java_com_intrigus_cocoja_ObjcRuntime__1objc_1lookUpClass(env, clazz, obj_name, name);

	(*env)->ReleaseStringUTFChars(env, obj_name, name);

	return JNI_returnValue;
}

JNIEXPORT jlong JNICALL Java_com_intrigus_cocoja_ObjcRuntime__1objc_1msgSend(JNIEnv* env, jclass clazz, jlong receiver, jlong selector, jobjectArray arguments) {


//@line:143

	return ptr_to_jlong(objc_msgSend(receiver, selector, arguments));
	

}

JNIEXPORT jstring JNICALL Java_com_intrigus_cocoja_ObjcRuntime__1sel_1getName(JNIEnv* env, jclass clazz, jlong address) {


//@line:268

	void* selector = jlong_to_ptr(address);
	const char * selectorName = sel_getName(selector);
	return (*env)->NewStringUTF(env, selectorName);
	

}

static inline jlong wrapped_Java_com_intrigus_cocoja_ObjcRuntime__1sel_1getUid
(JNIEnv* env, jclass clazz, jstring obj_name, char* name) {

//@line:280

	return ptr_to_jlong(sel_getUid(name));
	
}

JNIEXPORT jlong JNICALL Java_com_intrigus_cocoja_ObjcRuntime__1sel_1getUid(JNIEnv* env, jclass clazz, jstring obj_name) {
	char* name = (char*)(*env)->GetStringUTFChars(env, obj_name, 0);

	jlong JNI_returnValue = wrapped_Java_com_intrigus_cocoja_ObjcRuntime__1sel_1getUid(env, clazz, obj_name, name);

	(*env)->ReleaseStringUTFChars(env, obj_name, name);

	return JNI_returnValue;
}

