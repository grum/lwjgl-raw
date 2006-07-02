/* 
 * Copyright (c) 2006 LWJGL Project
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
package org.lwjgl.test.applet;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class OpenGL extends AWTGLCanvas implements Test {

	float	angle	= 0;

	public OpenGL() throws LWJGLException {
		Thread t = new Thread() {

			public void run() {
				while (true) {
					if (isVisible())
						repaint();
					Display.sync(60);
				}
			}
		};
		t.setDaemon(true);
		t.start();
	}

	public void paintGL() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION_MATRIX);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 640, 0, 480, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW_MATRIX);

		GL11.glPushMatrix();
		GL11.glTranslatef(320, 240, 0.0f);
		GL11.glRotatef(angle, 0, 0, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2i(-50, -50);
		GL11.glVertex2i(50, -50);
		GL11.glVertex2i(50, 50);
		GL11.glVertex2i(-50, 50);
		GL11.glEnd();
		GL11.glPopMatrix();

		angle += 1;

		try {
			swapBuffers();
		} catch (Exception e) {/*OK*/
		}
	}

	public void start() {
	}

	public void stop() {
	}
}
