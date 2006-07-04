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
package org.lwjgl.opengl;

/**
 * @author elias_naur
 */

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.CharBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;

import java.nio.charset.CharsetDecoder;
import java.nio.charset.Charset;

final class LinuxMouse {
	private static final int NUM_BUTTONS = 3;
	private static final int POINTER_WARP_BORDER = 10;
	// scale the mouse wheel according to DirectInput
	private static final int WHEEL_SCALE = 120;

	/* X11 constants */
	private final static int Button1 = 1;
	private final static int Button2 = 2;
	private final static int Button3 = 3;
	private final static int Button4 = 4;
	private final static int Button5 = 5;
	
	private final static int ButtonPress = 4;
	private final static int ButtonRelease = 5;

	private final long display;
	private final long window;
	private final IntBuffer query_pointer_buffer = BufferUtils.createIntBuffer(4);
	private final ByteBuffer event_buffer = ByteBuffer.allocate(Mouse.EVENT_SIZE);

	private int last_x;
	private int last_y;
	private int accum_dx;
	private int accum_dy;
	private int accum_dz;
	private byte[] buttons = new byte[NUM_BUTTONS];
	private EventQueue event_queue;
	private long last_event_nanos;

	public LinuxMouse(long display, long window) {
		this.display = display;
		this.window = window;
		reset();
	}

	private void reset() {
		event_queue = new EventQueue(event_buffer.capacity());
		accum_dx = accum_dy = 0;
	}

	public void read(ByteBuffer buffer) {
		event_queue.copyEvents(buffer);
	}

	public void poll(boolean grab, IntBuffer coord_buffer, ByteBuffer buttons_buffer) {
		if (grab) {
			coord_buffer.put(0, accum_dx);
			coord_buffer.put(1, accum_dy);
		} else {
			coord_buffer.put(0, last_x);
			coord_buffer.put(1, last_y);
		}
		coord_buffer.put(2, accum_dz);
		accum_dx = accum_dy = accum_dz = 0;
		for (int i = 0; i < buttons.length; i++)
			buttons_buffer.put(i, buttons[i]);
	}

	private void putMouseEventWithCoords(byte button, byte state, int coord1, int coord2, int dz, long nanos) {
		event_buffer.clear();
		event_buffer.put(button).put(state).putInt(coord1).putInt(coord2).putInt(dz).putLong(nanos);
		event_buffer.flip();
		event_queue.putEvent(event_buffer);
		last_event_nanos = nanos;
	}

	private void setCursorPos(boolean grab, int x, int y, long nanos) {
		y = transformY(y);
		int dx = x - last_x;
		int dy = y - last_y;
		if (dx != 0 || dy != 0) {
			accum_dx += dx;
			accum_dy += dy;
			last_x = x;
			last_y = y;
			if (grab) {
				putMouseEventWithCoords((byte)-1, (byte)0, dx, dy, 0, nanos);
			} else {
				putMouseEventWithCoords((byte)-1, (byte)0, x, y, 0, nanos);
			}
		}
	}

	private void doWarpPointer(int center_x, int center_y) {
		nSendWarpEvent(display, window, center_x, center_y);
		nWarpCursor(display, window, center_x, center_y);
	}
	private static native void nSendWarpEvent(long display, long window, int center_x, int center_y);

	private void doHandlePointerMotion(boolean grab, boolean pointer_grabbed, boolean should_grab, long root_window, int root_x, int root_y, int win_x, int win_y, long nanos) {
		setCursorPos(grab, win_x, win_y, nanos);
		if (!pointer_grabbed || !should_grab)
			return;
		int root_window_height = nGetWindowHeight(display, root_window);
		int root_window_width = nGetWindowWidth(display, root_window);
		int window_height = nGetWindowHeight(display, window);
		int window_width = nGetWindowWidth(display, window);

		// find the window position in root coordinates
		int win_left = root_x - win_x;
		int win_top = root_y - win_y;
		int win_right = win_left + window_width;
		int win_bottom = win_top + window_height;
		// cap the window position to the screen dimensions
		int border_left = Math.max(0, win_left);
		int border_top = Math.max(0, win_top);
		int border_right = Math.min(root_window_width, win_right);
		int border_bottom = Math.min(root_window_height, win_bottom);
		// determine whether the cursor is outside the bounds
		boolean outside_limits = root_x < border_left + POINTER_WARP_BORDER || root_y < border_top + POINTER_WARP_BORDER ||
			root_x > border_right - POINTER_WARP_BORDER || root_y > border_bottom - POINTER_WARP_BORDER;
		if (outside_limits) {
			// Find the center of the limits in window coordinates
			int center_x = (border_right - border_left)/2;
			int center_y = (border_bottom - border_top)/2;
			doWarpPointer(center_x, center_y);
		}
	}

	public void changeGrabbed(boolean grab, boolean pointer_grabbed, boolean should_grab) {
		reset();
		long root_window = nQueryPointer(display, window, query_pointer_buffer);
		doHandlePointerMotion(grab, pointer_grabbed, should_grab, root_window, query_pointer_buffer.get(0), query_pointer_buffer.get(1), query_pointer_buffer.get(2), query_pointer_buffer.get(3), last_event_nanos);
	}

	public int getButtonCount() {
		return buttons.length;
	}

	private int transformY(int y) {
		return nGetWindowHeight(display, window) - 1 - y;
	}
	private static native int nGetWindowHeight(long display, long window);
	private static native int nGetWindowWidth(long display, long window);

	private static native long nQueryPointer(long display, long window, IntBuffer result);
	
	public void setCursorPosition(int x, int y) {
		nWarpCursor(display, window, x, transformY(y));
	}
	private static native void nWarpCursor(long display, long window, int x, int y);

	public void handlePointerMotion(boolean grab, boolean pointer_grabbed, boolean should_grab, long millis, long root_window, int x_root, int y_root, int x, int y) {
	    doHandlePointerMotion(grab, pointer_grabbed, should_grab, root_window, x_root, y_root, x, y, millis*1000000);
	}
	
	private void handleButton(boolean grab, int button, byte state, long nanos) {
		byte button_num;
		switch (button) {
			case Button1:
				button_num = (byte)0;
				break;
			case Button2:
				button_num = (byte)2;
				break;
			case Button3:
				button_num = (byte)1;
				break;
			default:
				return;
		}
		buttons[button_num] = state;
		putMouseEvent(grab, button_num, state, 0, nanos);
	}

	private void putMouseEvent(boolean grab, byte button, byte state, int dz, long nanos) {
		if (grab)
			putMouseEventWithCoords(button, state, 0, 0, dz, nanos);
		else
			putMouseEventWithCoords(button, state, last_x, last_y, dz, nanos);
	}

	private void handleButtonPress(boolean grab, byte button, long nanos) {
		int delta = 0;
		switch (button) {
			case Button4:
				delta = WHEEL_SCALE;
				putMouseEvent(grab, (byte)-1, (byte)0, delta, nanos);
				accum_dz += delta;
				break;
			case Button5:
				delta = -WHEEL_SCALE;
				putMouseEvent(grab, (byte)-1, (byte)0, delta, nanos);
				accum_dz += delta;
				break;
			default:
				handleButton(grab, button, (byte)1, nanos);
				break;
		}
	}

	public void handleButtonEvent(boolean grab, long millis, int type, byte button) {
		long nanos = millis*1000000;
		switch (type) {
			case ButtonRelease:
				handleButton(grab, button, (byte)0, nanos);
				break;
			case ButtonPress:
				handleButtonPress(grab, button, nanos);
				break;
			default:
				break;
		}
	}
	
	private  void resetCursor(int x, int y) {
		last_x = x;
		last_y = transformY(y);
	}

	public void handleWarpEvent(int x, int y) {
		resetCursor(x, y);
	}
}
