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

/**
 * $Id$
 *
 * Simple java test program.
 *
 * @author elias_naur <elias_naur@users.sourceforge.net>
 * @version $Revision$
 */

package org.lwjgl.test.opengl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.glu.GLU;

public class Grass {

	static class Aslod {
		float angle;
		float value;
		float ripple;
		float count;
	}
	private static boolean finished = false;
	private static Random rand = new Random();

	static {
		try {
			int mode = -1;
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			for (int i = 0; i < modes.length; i++) {
				if (modes[i].getWidth ()== 640
					&& modes[i].getHeight() == 480
					&& modes[i].getBitsPerPixel() >= 16) {
					mode = i;
					break;
				}
			}

			if (mode == -1) {
				System.out.println("did not find suitable mode");
			} else {
				System.out.println("Display mode: " + modes[mode]);
			}
			// For now let's just pick a mode we're certain to have

			Display.setDisplayMode(modes[mode]);
			System.out.println("Created display.");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	static {
		try {
			Display.create();
			Keyboard.create();
			Keyboard.enableBuffer();
			Mouse.create();
			System.out.println("Created OpenGL.");
		} catch (Exception e) {
			System.err.println("Failed to create OpenGL due to " + e);
			System.exit(1);
		}

	}

	private static Aslod aslod = new Aslod();

	private static int mesh;
	private static int program_handle;

	private static byte[] loadFile(String file) {
		int next;
		java.util.Vector bytes = new java.util.Vector();
		try {
			ClassLoader loader = Grass.class.getClassLoader();
			InputStream stream = new BufferedInputStream(loader.getResourceAsStream(file));
			while ((next = (stream.read())) != -1)
				bytes.add(new Byte((byte) next));
			stream.close();
			byte[] result = new byte[bytes.size()];
			for (int i = 0; i < result.length; i++)
				result[i] = ((Byte) bytes.get(i)).byteValue();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println("Vertex program supported: " + GLContext.GL_NV_vertex_program);
		IntBuffer int_buf = BufferUtils.createIntBuffer(1);
		NVProgram.glGenProgramsNV(int_buf);
		if (int_buf.get(0) == 0)
			throw new RuntimeException("Could not allocate new vertex program id!");

		program_handle = int_buf.get(0);
		byte[] program = loadFile("cg_grass2.vp");
		ByteBuffer program_buf = BufferUtils.createByteBuffer(program.length);
		program_buf.order(ByteOrder.nativeOrder());
		program_buf.put(program);
		program_buf.flip();
		NVProgram.glLoadProgramNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			program_handle,
			program_buf);
		/*GL11.glGetInteger(GL11.PROGRAM_ERROR_POSITION_NV, int_buf);
		System.out.println("error position: " + int_buf.get(0));*/

		genMesh();

		float[] LightDiffuse = { 1.0f, 0.0f, 0.0f, 1.0f };
		float[] LightPosition = { 1.0f, 1.0f, 1.0f, 0.0f };
		FloatBuffer light_buf_f = BufferUtils.createFloatBuffer(4);
		light_buf_f.put(LightDiffuse);
		light_buf_f.flip();

		GL11.glLight(
			GL11.GL_LIGHT0,
			GL11.GL_DIFFUSE,
			light_buf_f);
		light_buf_f.clear();
		light_buf_f.put(LightPosition);
		light_buf_f.flip();
		GL11.glLight(
			GL11.GL_LIGHT0,
			GL11.GL_POSITION,
			light_buf_f);
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GLU.gluPerspective(40.0f, 1.0f, 1.0f, 50.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GLU.gluLookAt(14.0f, 10.0f, -16.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

		aslod.angle = 2.6179935f;
		aslod.value = 0.2f;
		aslod.ripple = 0.0f;
		aslod.count = 0.0f;

		while (!finished) {
			if (Display.isCloseRequested()) {
				finished = true;
			} else if (Display.isVisible()) {
				keyPoll();
				float degree = (1.0f + (aslod.value * 20.0f)) * 0.01745329f;

				degree *= (0.5 + myrand());

				ptrAnimate(degree);
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

				//ptrDraw();

				grsDraw();
			}
			Display.update();
		}
		Display.destroy();
	}

	private static float myrand() {
		// returns a value between 0 and 1
		return rand.nextFloat();
	}

	private static void genGrass(
		float fFaceHeight,
		float fFaceWidth,
		float fX,
		float fZ) {
		int cFaces;
		int numFaces;
		float cWidth;
		float fDecWidth, frndWidth, frndHeight;
		float fRotate;
		float fRigid;

		numFaces = 5;
		frndHeight =
			fFaceHeight
				+ ((fFaceHeight / 1.5f)
					* (float) java.lang.Math.cos(
						java.lang.Math.abs(rand.nextInt())));
		frndWidth =
			fFaceWidth
				+ ((fFaceWidth / 4.0f)
					* (float) java.lang.Math.cos(
						java.lang.Math.abs(rand.nextInt())));
		fDecWidth = frndWidth / 5.0f;
		fRotate = myrand() * 3.1415f;
		fRigid = ((fRigid = myrand()) < 0.2f) ? 0.2f : fRigid;

		if (myrand() < 0.3)
			GL11.glBegin(GL11.GL_LINE_STRIP);
		else
			GL11.glBegin(GL11.GL_QUAD_STRIP);

		for (cFaces = 0; cFaces < numFaces; cFaces++) {
			for (cWidth = frndWidth;
				cWidth >= -frndWidth;
				cWidth -= (frndWidth * 2.0f)) {
				GL11.glColor4f(fX, fRigid, fZ, (float) cFaces / (float) numFaces);
				GL11.glVertex3f(
					(float) (((cFaces - 2) * 0.1f)
						* java.lang.Math.cos(fRotate)
						+ (cWidth) * java.lang.Math.sin(fRotate)),
					cFaces * frndHeight,
					- (float)
						(((cFaces - 2) * 0.1f) * java.lang.Math.sin(fRotate)
							+ (cWidth) * java.lang.Math.cos(fRotate)));
			}
			frndWidth -= fDecWidth;
		}
		GL11.glEnd();

	}

	private static void genMesh() {
		float cI, cJ, fArea;

		fArea = 20.0f;
		mesh = GL11.glGenLists(1);
		GL11.glNewList(mesh, GL11.GL_COMPILE);
		for (cI = -fArea / 2; cI < fArea / 2; cI += 0.25f) {
			for (cJ = -fArea / 2; cJ < fArea / 2; cJ += 0.25f) {
				genGrass(0.5f, 0.1f, cI, cJ);
			}
		}
		GL11.glEndList();
	}

	private static void grsDraw() {
		GL11.glEnable(NVVertexProgram.GL_VERTEX_PROGRAM_NV);
		NVProgram.glBindProgramNV(NVVertexProgram.GL_VERTEX_PROGRAM_NV, program_handle);
		NVVertexProgram.glTrackMatrixNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			0,
			NVVertexProgram.GL_MODELVIEW_PROJECTION_NV,
			NVVertexProgram.GL_IDENTITY_NV);

		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			4,
			0.0f,
			0.0f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			5,
			0.0f,
			0.0f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			6,
			1.763609f,
			0.496495f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			7,
			-0.943599f,
			3.203737f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			8,
			4.101107f,
			0.943413f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			9,
			-1.218603f,
			6.259399f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			10,
			7.214299f,
			1.352961f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			11,
			-1.540748f,
			10.080958f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			12,
			10.880035f,
			1.759046f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			13,
			-1.852705f,
			14.468674f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			14,
			14.292879f,
			1.973329f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			15,
			-1.973387f,
			18.506531f,
			0.0f,
			0.0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			16,
			(float) (java.lang.Math.sin(aslod.angle)
				* (aslod.value + aslod.ripple)),
			0.0f,
			(float) (java.lang.Math.cos(aslod.angle)
				* (aslod.value + aslod.ripple)),
			0.0f);

		NVVertexProgram.glProgramParameter4fNV(NVVertexProgram.GL_VERTEX_PROGRAM_NV, 17, 1.7f, 5f, 2f, 0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			18,
			-0.0187293f,
			0.074261f,
			0.2121144f,
			1.570729f);
		NVVertexProgram.glProgramParameter4fNV(NVVertexProgram.GL_VERTEX_PROGRAM_NV, 20, 0f, 0.5f, 1f, 0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			21,
			0.25f,
			-9f,
			0.75f,
			0.1591549f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			22,
			24.9808f,
			-24.9808f,
			-60.14581f,
			60.14581f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			23,
			85.45379f,
			-85.45379f,
			-64.93935f,
			64.93935f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			24,
			19.73921f,
			-19.73921f,
			-1f,
			1f);
		NVVertexProgram.glProgramParameter4fNV(NVVertexProgram.GL_VERTEX_PROGRAM_NV, 25, 0f, 4f, 0f, 0f);
		NVVertexProgram.glProgramParameter4fNV(
			NVVertexProgram.GL_VERTEX_PROGRAM_NV,
			19,
			1f,
			3.141593f,
			0.5f,
			1f);
		NVVertexProgram.glProgramParameter4fNV(NVVertexProgram.GL_VERTEX_PROGRAM_NV, 26, 0.7f, 0.4f, 0f, 0f);
		GL11.glCallList(mesh);
		GL11.glDisable(NVVertexProgram.GL_VERTEX_PROGRAM_NV);

	}

	private static void ptrAnimate(float degree) {
		aslod.count += degree;
		aslod.ripple = (float) (java.lang.Math.cos(aslod.count) / 80.0);

	}

	private static void keyPoll() {
		for (int i = 0; i < Keyboard.getNumKeyboardEvents(); i++) {
			Keyboard.next();
			if (!Keyboard.getEventKeyState())
				continue;
			switch (Keyboard.getEventKey()) {
				case Keyboard.KEY_A :
					aslod.angle += 0.1;
					break;
				case Keyboard.KEY_D :
					aslod.angle -= 0.1;
					break;
				case Keyboard.KEY_W :
					aslod.value += (aslod.value >= 0.15) ? 0.0 : 0.0025;
					break;
				case Keyboard.KEY_S :
					aslod.value -= (aslod.value <= 0.005) ? 0.0 : 0.0025;
					break;
				case Keyboard.KEY_ESCAPE :
					finished = true;
					break;
			}
		}
	}

}
