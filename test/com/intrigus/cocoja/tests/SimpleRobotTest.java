
package com.intrigus.cocoja.tests;

import com.intrigus.cocoja.Cocoja;
import com.intrigus.cocoja.java.awt.Robot;

public class SimpleRobotTest {

	public static void main (String[] args) {
		Cocoja.init();
		Robot r = new com.intrigus.cocoja.java.awt.Robot();
		r.mouseMove(100, 200);
		int i = 0;
		while (i < 11111) {
			if (i % 2 == 0)
				r.mouseMove(100, 200);
			else
				r.mouseMove(100, 100);
			i++;
		}
	}
}
