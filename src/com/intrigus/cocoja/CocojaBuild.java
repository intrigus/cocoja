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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.intrigus.mavigen.AntScriptGenerator;
import com.intrigus.mavigen.BuildConfig;
import com.intrigus.mavigen.BuildExecutor;
import com.intrigus.mavigen.BuildTarget;
import com.intrigus.mavigen.BuildTarget.TargetOs;
import com.intrigus.mavigen.NativeCodeGenerator;

public class CocojaBuild {
	public static void main (String[] args) throws Exception {
		// generate C/C++ code
		new NativeCodeGenerator().forceGeneration(true).generate("src", "bin", "jni", null, null);

		// generate build scripts
		BuildConfig buildConfig = new BuildConfig("cocoja");
		BuildTarget mac = BuildTarget.newDefaultTarget(TargetOs.MacOsX, true);
		mac.cIncludes = new String[] {"**/*.m"};
		String sdkRoot = getSdkRoot();
		mac.cFlags += " -F" + sdkRoot + "/System/Library/Frameworks/JavaVM.framework/Frameworks";
		mac.linkerFlags += " -F" + sdkRoot
			+ "/System/Library/Frameworks/JavaVM.framework/Frameworks -framework Foundation -framework AppKit -framework JavaNativeFoundation";
		new AntScriptGenerator().generate(buildConfig, mac);

		BuildExecutor.executeAnt("jni/build.xml", "-v");
	}

	private static String getSdkRoot () {
		return startProcess("xcrun -sdk macosx -show-sdk-path");
	}

	/** Starts a process and returns the output of the process */
	private static String startProcess (String command) {
		final StringBuilder result = new StringBuilder();
		try {
			final Process process = new ProcessBuilder(command.split(" ")).redirectErrorStream(true).start();

			Thread t = new Thread(new Runnable() {
				@Override
				public void run () {
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null;
					try {
						while ((line = reader.readLine()) != null) {
							result.append(line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			t.setDaemon(true);
			t.start();
			process.waitFor();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
