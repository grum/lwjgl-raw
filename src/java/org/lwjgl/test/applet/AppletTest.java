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

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.applet.LWJGLInstaller;

public class AppletTest extends Applet {
	
	Test test = null;

	@Override
	public void destroy() {
		super.destroy();
		System.out.println("*** destroy ***");
	}

	@Override
	public void start() {
		super.start();
		System.out.println("*** start ***");
	}

	@Override
	public void stop() {
		super.stop();
		System.out.println("*** stop ***");
		test.stop();
	}

	public void init() {
		System.out.println("*** init ***");
		
		try {
			LWJGLInstaller.tempInstall();
		} catch (LWJGLException le) {
			/* screwed */
			le.printStackTrace();
		}
		
		setLayout(new BorderLayout());
		try {
			test = (Test) Class.forName(getParameter("test")).newInstance();
			Canvas canvas = (Canvas) test;
			canvas.setSize(getWidth(), getHeight());
			add(canvas);
		} catch (Exception e) {
			e.printStackTrace();
		}
		test.start();
	}
}
