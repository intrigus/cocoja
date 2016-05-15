#include <com.intrigus.cocoja.Pointer.h>
JNIEXPORT jstring JNICALL Java_com_intrigus_cocoja_Pointer__1getFromCString(JNIEnv* env, jclass clazz, jlong address) {


//@line:31
	
	return (*env)->NewStringUTF(env, address);
	

}

