/* 
 * Copyright (c) 2002-2004 LWJGL Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 * * Redistributions of source code must retain the above copyright 
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of 
 *   its contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.lwjgl.test;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;

/**
 * Small class for testing that the Window is creatable
 * If this class can't run, LWJGL wont work!
 * 
 * @author Brian Matzon <brian@matzon.dk>
 */
public class WindowCreationTest {

  /**
   * Main entry point
   * 
   * @param args ignored params to app
   */
	public static void main(String[] args) {
    // get avaialble modes, and print out
    DisplayMode[] modes = Display.getAvailableDisplayModes();
    System.out.println("Found " + modes.length + " display modes");
    
    // Create the actual window
    try {
      Display.create();
    } catch (Exception e) {
			e.printStackTrace();
      System.out.println("Unable to create window!, exiting...");
      System.exit(-1);
		}

    System.out.println("Window created");
    System.out.println(Display.getDisplayMode().getHeight() + ", " + Display.getDisplayMode().getWidth() + ", " + Display.getTitle());

    // wait for user to close window
    while(!Display.isCloseRequested()) {    
      Display.update();
      try {
        Thread.sleep(100);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      
      if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
       break; 
      }
    }
    
    // nuke window and get out
    Display.destroy();
	}
}
