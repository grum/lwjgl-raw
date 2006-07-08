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
 * Linux specific display functions.
 *
 * @author elias_naur <elias_naur@users.sourceforge.net>
 * @version $Revision$
 */

#include <X11/X.h>
#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <X11/extensions/xf86vmode.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <jni.h>
#include <jawt.h>
#include "common_tools.h"
#include "extgl.h"
#include "extgl_glx.h"
#include "Window.h"
#include "context.h"
#include "display.h"
#include "org_lwjgl_opengl_LinuxDisplay.h"
#include "org_lwjgl_opengl_LinuxDisplayPeerInfo.h"

#define ERR_MSG_SIZE 1024

typedef struct {
	unsigned long flags;
	unsigned long functions;
	unsigned long decorations;
	long input_mode;
	unsigned long status;
} MotifWmHints;

#define MWM_HINTS_DECORATIONS   (1L << 1)

static GLXWindow glx_window = None;

static Atom delete_atom;
static Colormap cmap;
static Window current_win;
static int current_depth;
static Pixmap current_icon_pixmap;	

static Visual *current_visual;

static int current_screen;
static Display *display_connection = NULL;
static bool async_x_error;
static char error_message[ERR_MSG_SIZE];
static Atom warp_atom;

int getCurrentScreen(void) {
	return current_screen;
}

bool checkXError(JNIEnv *env, Display *disp) {
	XSync(disp, False);
	if (async_x_error) {
		async_x_error = false;
		if (env != NULL)
			throwException(env, error_message);
		else
			printfDebug(error_message);
		return false;
	} else
		return true;
}

static int errorHandler(Display *disp, XErrorEvent *error) {
	char err_msg_buffer[ERR_MSG_SIZE];
	XGetErrorText(disp, error->error_code, err_msg_buffer, ERR_MSG_SIZE);
	err_msg_buffer[ERR_MSG_SIZE - 1] = '\0';
	snprintf(error_message, ERR_MSG_SIZE, "X Error - serial: %d, error_code: %s, request_code: %d, minor_code: %d", (int)error->serial, err_msg_buffer, (int)error->request_code, (int)error->minor_code);
	error_message[ERR_MSG_SIZE - 1] = '\0';
	async_x_error = true;
	return 0;
}

Display *getDisplay(void) {
	return display_connection;
}

static void openDisplay(JNIEnv *env) {
	async_x_error = false;
	XSetErrorHandler(errorHandler);
	display_connection = XOpenDisplay(NULL);
	if (display_connection == NULL) {
		throwException(env, "Could not open X display connection");
		return;
	}
	current_screen = XDefaultScreen(getDisplay());
	warp_atom = XInternAtom(display_connection, "_LWJGL_WARP", False);
}

Atom getWarpAtom(void) {
	return warp_atom;
}

static void closeDisplay(void) {
	XCloseDisplay(display_connection);
	display_connection = NULL;
}

static void waitMapped(Window win) {
	XEvent event;
	do {
		XMaskEvent(getDisplay(), StructureNotifyMask, &event);
	} while ((event.type != MapNotify) || (event.xmap.event != win));
}

static void __attribute__ ((destructor)) my_fini(void) { 
	Display *disp = XOpenDisplay(NULL);
	if (disp == NULL) {
		return;
	}
	XKeyboardControl repeat_mode;
	repeat_mode.auto_repeat_mode = AutoRepeatModeDefault;
	XChangeKeyboardControl(disp, KBAutoRepeatMode, &repeat_mode);
	XCloseDisplay(disp);
} 

static void setDecorations(int dec) {
	Atom motif_hints_atom = XInternAtom(getDisplay(), "_MOTIF_WM_HINTS", False);
	MotifWmHints motif_hints;
	motif_hints.flags = MWM_HINTS_DECORATIONS;
	motif_hints.decorations = dec;
	XChangeProperty (getDisplay(), getCurrentWindow(), motif_hints_atom, motif_hints_atom, 32, PropModeReplace, (unsigned char *)&motif_hints, sizeof(MotifWmHints)/sizeof(long));
}

static bool isLegacyFullscreen(jint window_mode) {
	return window_mode == org_lwjgl_opengl_LinuxDisplay_FULLSCREEN_LEGACY;
}

static void handleMessages(JNIEnv *env, jobject disp_obj) {
	XEvent event;
	jclass disp_class = (*env)->GetObjectClass(env, disp_obj);
	if (disp_class == NULL)
		return;
	jmethodID handleKeyEvent_method = (*env)->GetMethodID(env, disp_class, "handleKeyEvent", "(JJIII)V");
	if (handleKeyEvent_method == NULL)
		return;
	jmethodID handleButtonEvent_method = (*env)->GetMethodID(env, disp_class, "handleButtonEvent", "(JIII)V");
	if (handleButtonEvent_method == NULL)
		return;
	jmethodID handlePointerMotionEvent_method = (*env)->GetMethodID(env, disp_class, "handlePointerMotionEvent", "(JJIIIII)V");
	if (handlePointerMotionEvent_method == NULL)
		return;
	jmethodID handleWarpEvent_method = (*env)->GetMethodID(env, disp_class, "handleWarpEvent", "(II)V");
	if (handleWarpEvent_method == NULL)
		return;
	jmethodID handleMapNotifyEvent_method = (*env)->GetMethodID(env, disp_class, "handleMapNotifyEvent", "()V");
	if (handleMapNotifyEvent_method == NULL)
		return;
	jmethodID handleUnmapNotifyEvent_method = (*env)->GetMethodID(env, disp_class, "handleUnmapNotifyEvent", "()V");
	if (handleUnmapNotifyEvent_method == NULL)
		return;
	jmethodID handleExposeEvent_method = (*env)->GetMethodID(env, disp_class, "handleExposeEvent", "()V");
	if (handleExposeEvent_method == NULL)
		return;
	jmethodID handleCloseEvent_method = (*env)->GetMethodID(env, disp_class, "handleCloseEvent", "()V");
	if (handleCloseEvent_method == NULL)
		return;
	while (!(*env)->ExceptionOccurred(env) && XPending(getDisplay()) > 0) {
		XNextEvent(getDisplay(), &event);
		if (XFilterEvent(&event, None) == True)
			continue;
		// Ignore events from old windows
		if (event.xany.window != getCurrentWindow())
			continue;
		switch (event.type) {
			case ClientMessage:
				if (event.xclient.message_type == warp_atom) {
					(*env)->CallVoidMethod(env, disp_obj, handleWarpEvent_method, (jint)event.xclient.data.l[0], (jint)event.xclient.data.l[1]);
				} else if ((event.xclient.format == 32) && ((Atom)event.xclient.data.l[0] == delete_atom))
					(*env)->CallVoidMethod(env, disp_obj, handleCloseEvent_method);
				break;
			case MapNotify:
				(*env)->CallVoidMethod(env, disp_obj, handleMapNotifyEvent_method);
				break;
			case UnmapNotify:
				(*env)->CallVoidMethod(env, disp_obj, handleUnmapNotifyEvent_method);
				break;
			case Expose:
				(*env)->CallVoidMethod(env, disp_obj, handleExposeEvent_method);
				break;
			case ButtonPress: /* Fall through */
			case ButtonRelease:
				(*env)->CallVoidMethod(env, disp_obj, handleButtonEvent_method, (jlong)event.xbutton.time, (jint)event.xbutton.type, (jint)event.xbutton.button, (jint)event.xbutton.state);
				break;
			case MotionNotify:
				(*env)->CallVoidMethod(env, disp_obj, handlePointerMotionEvent_method, (jlong)event.xbutton.time, (jlong)event.xbutton.root, (jint)event.xbutton.x_root, (jint)event.xbutton.y_root, (jint)event.xbutton.x, (jint)event.xbutton.y, (jint)event.xbutton.state);
				break;
			case KeyPress:
			case KeyRelease:
				(*env)->CallVoidMethod(env, disp_obj, handleKeyEvent_method, (jlong)(intptr_t)&(event.xkey), (jlong)event.xkey.time, (jint)event.xkey.type, (jint)event.xkey.keycode, (jint)event.xkey.state);
				break;
		}
	}
}

static void setWindowTitle(const char *title) {
	XStoreName(getDisplay(), current_win, title);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_openDisplay(JNIEnv *env, jclass clazz) {
	openDisplay(env);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_closeDisplay(JNIEnv *env, jclass clazz) {
	closeDisplay();
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplayPeerInfo_initDrawable(JNIEnv *env, jclass clazz, jobject peer_info_handle) {
	X11PeerInfo *peer_info = (*env)->GetDirectBufferAddress(env, peer_info_handle);
	if (peer_info->glx13)
		peer_info->drawable = glx_window;
	else
		peer_info->drawable = getCurrentWindow();
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplayPeerInfo_initDefaultPeerInfo(JNIEnv *env, jclass clazz, jobject peer_info_handle, jobject pixel_format) {
	initPeerInfo(env, peer_info_handle, getDisplay(), getCurrentScreen(), pixel_format, true, GLX_WINDOW_BIT, true, false);
}
  
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nSetTitle(JNIEnv * env, jclass clazz, jstring title_obj) {
	char * title = GetStringNativeChars(env, title_obj);
	setWindowTitle(title);
	free(title);
}

static void freeIconPixmap() {
	if (current_icon_pixmap != 0) {
		XFreePixmap(getDisplay(), current_icon_pixmap);
		current_icon_pixmap = 0;
	}
}

static void destroyWindow(JNIEnv *env) {
	if (glx_window != None) {
		lwjgl_glXDestroyWindow(getDisplay(), glx_window);
		glx_window = None;
	}
	XDestroyWindow(getDisplay(), current_win);
	XFreeColormap(getDisplay(), cmap);
	freeIconPixmap();
}

static bool isNetWMFullscreenSupported(JNIEnv *env) {
	unsigned long nitems;
	Atom actual_type;
	int actual_format;
	unsigned long bytes_after;
	Atom *supported_list;
	Atom netwm_supported_atom = XInternAtom(getDisplay(), "_NET_SUPPORTED", False);
	int result = XGetWindowProperty(getDisplay(), RootWindow(getDisplay(), getCurrentScreen()), netwm_supported_atom, 0, 10000, False, AnyPropertyType, &actual_type, &actual_format, &nitems, &bytes_after, (void *)&supported_list);
	if (result != Success) {
		throwException(env, "Unable to query _NET_SUPPORTED window property");
		return false;
	}
	Atom fullscreen_atom = XInternAtom(getDisplay(), "_NET_WM_STATE_FULLSCREEN", False);
	bool supported = false;
	unsigned long i;
	for (i = 0; i < nitems; i++) {
		if (fullscreen_atom == supported_list[i]) {
			supported = true;
			break;
		}
	}
	XFree(supported_list);
	return supported;
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nIsNetWMFullscreenSupported(JNIEnv *env, jclass unused) {
	return isNetWMFullscreenSupported(env) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nReshape(JNIEnv *env, jclass clazz, jint x, jint y, jint width, jint height) {
	XMoveWindow(getDisplay(), getCurrentWindow(), x, y);
}

static bool createWindow(JNIEnv* env, jint window_mode, X11PeerInfo *peer_info, int x, int y, int width, int height) {
	bool undecorated = getBooleanProperty(env, "org.lwjgl.opengl.Window.undecorated");
	Window root_win;
	Window win;
	XSetWindowAttributes attribs;
	int attribmask;

	root_win = RootWindow(getDisplay(), getCurrentScreen());
	XVisualInfo *vis_info = getVisualInfoFromPeerInfo(env, peer_info);
	if (vis_info == NULL)
		return false;
	cmap = XCreateColormap(getDisplay(), root_win, vis_info->visual, AllocNone);
	attribs.colormap = cmap;
	attribs.event_mask = ExposureMask | /*FocusChangeMask | */VisibilityChangeMask | StructureNotifyMask | KeyPressMask | KeyReleaseMask | ButtonPressMask | ButtonReleaseMask | PointerMotionMask;
	attribs.background_pixel = 0xFF000000;
	attribs.win_gravity = NorthWestGravity;
	attribmask = CWColormap | CWBackPixel | CWEventMask | CWWinGravity;
	if (isLegacyFullscreen(window_mode)) {
		attribmask |= CWOverrideRedirect;
		attribs.override_redirect = True;
	}
	win = XCreateWindow(getDisplay(), root_win, x, y, width, height, 0, vis_info->depth, InputOutput, vis_info->visual, attribmask, &attribs);
	
	current_depth = vis_info->depth;
	current_visual = vis_info->visual;
	
	XFree(vis_info);
	if (!checkXError(env, getDisplay())) {
		XFreeColormap(getDisplay(), cmap);
		return false;
	}
	printfDebugJava(env, "Created window");
	current_win = win;
	if (window_mode != org_lwjgl_opengl_LinuxDisplay_WINDOWED || undecorated) {
		// Use Motif decoration hint property and hope the window manager respects them
		setDecorations(0);
	}
	XSizeHints * size_hints = XAllocSizeHints();
	size_hints->flags = PMinSize | PMaxSize;
	size_hints->min_width = width;
	size_hints->max_width = width;
	size_hints->min_height = height;
	size_hints->max_height = height;
	XSetWMNormalHints(getDisplay(), win, size_hints);
	XFree(size_hints);
	delete_atom = XInternAtom(getDisplay(), "WM_DELETE_WINDOW", False);
	XSetWMProtocols(getDisplay(), win, &delete_atom, 1);
	if (window_mode == org_lwjgl_opengl_LinuxDisplay_FULLSCREEN_NETWM) {
		Atom fullscreen_atom = XInternAtom(getDisplay(), "_NET_WM_STATE_FULLSCREEN", False);
		XChangeProperty(getDisplay(), getCurrentWindow(), XInternAtom(getDisplay(), "_NET_WM_STATE", False),
						XInternAtom(getDisplay(), "ATOM", False), 32, PropModeReplace, (const unsigned char*)&fullscreen_atom, 1);
	}
	XMapRaised(getDisplay(), win);
	waitMapped(win);
	XClearWindow(getDisplay(), win);
	if (!checkXError(env, getDisplay())) {
		destroyWindow(env);
		return false;
	}
	return true;
}

Window getCurrentWindow(void) {
	return current_win;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nUpdate
  (JNIEnv *env, jobject disp_obj) {
	handleMessages(env, disp_obj);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nCreateWindow(JNIEnv *env, jclass clazz, jobject peer_info_handle, jobject mode, jint window_mode, jint x, jint y) {
	X11PeerInfo *peer_info = (*env)->GetDirectBufferAddress(env, peer_info_handle);
	GLXFBConfig *fb_config = NULL;
	if (peer_info->glx13) {
		fb_config = getFBConfigFromPeerInfo(env, peer_info);
		if (fb_config == NULL)
			return;
	}
	jclass cls_displayMode = (*env)->GetObjectClass(env, mode);
	jfieldID fid_width = (*env)->GetFieldID(env, cls_displayMode, "width", "I");
	jfieldID fid_height = (*env)->GetFieldID(env, cls_displayMode, "height", "I");
	int width = (*env)->GetIntField(env, mode, fid_width);
	int height = (*env)->GetIntField(env, mode, fid_height);
	bool window_created = createWindow(env, window_mode, peer_info, x, y, width, height);
	if (!window_created) {
		return;
	}
	if (peer_info->glx13) {
		glx_window = lwjgl_glXCreateWindow(getDisplay(), *fb_config, getCurrentWindow(), NULL);
		XFree(fb_config);
	}
	if (!checkXError(env, getDisplay())) {
		lwjgl_glXDestroyWindow(getDisplay(), glx_window);
		destroyWindow(env);
	}
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nDestroyWindow(JNIEnv *env, jclass clazz) {
	destroyWindow(env);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nLockAWT(JNIEnv *env, jclass clazz) {
	JAWT jawt;
	jawt.version = JAWT_VERSION_1_4;
	if (JAWT_GetAWT(env, &jawt) != JNI_TRUE) {
		throwException(env, "GetAWT failed");
		return;
	}
	jawt.Lock(env);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nUnlockAWT(JNIEnv *env, jclass clazz) {
	JAWT jawt;
	jawt.version = JAWT_VERSION_1_4;
	if (JAWT_GetAWT(env, &jawt) != JNI_TRUE) {
		throwException(env, "GetAWT failed");
		return;
	}
	jawt.Unlock(env);
}

static void setIcon(JNIEnv *env, char *data, int icon_size, int width,int height) {
	XWMHints* win_hints;
	freeIconPixmap();
	current_icon_pixmap = XCreatePixmap(getDisplay(), getCurrentWindow(), width, height, current_depth);
	/* We need to copy the image data since XDestroyImage will also free its data buffer, which can't be allowed
	 * since the data buffer is managed by the jvm (it's the storage for the direct ByteBuffer)
	 */
	char *icon_copy = (char *)malloc(sizeof(*icon_copy)*icon_size);

	if (icon_copy == NULL) {
		throwException(env, "malloc failed");
		return;
	}
	memcpy(icon_copy, data, icon_size);
	XImage *image = XCreateImage(getDisplay(), current_visual, current_depth, ZPixmap, 0, icon_copy, width, height, 32, 0);
	if (image == NULL) {
		freeIconPixmap();
		free(icon_copy);
		throwException(env, "XCreateImage failed");
		return;
	}
	
	GC gc = XCreateGC(getDisplay(), current_icon_pixmap, 0, NULL);
	XPutImage(getDisplay(), current_icon_pixmap, gc, image, 0, 0, 0, 0, width, height);
	XFreeGC(getDisplay(), gc);
	XDestroyImage(image);
	// We won't free icon_copy because it is freed by XDestroyImage
	
	win_hints = XAllocWMHints();
	if (win_hints == NULL) {
		throwException(env, "XAllocWMHints failed");
		return;
	}
	
	win_hints->flags = IconPixmapHint;               
	win_hints->icon_pixmap = current_icon_pixmap;
	
	XSetWMHints(getDisplay(), getCurrentWindow(), win_hints);
	XFree(win_hints);
	XFlush(getDisplay());
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nSetWindowIcon
  (JNIEnv *env, jclass clazz, jobject iconBuffer, jint icon_size, jint width, jint height)
{
	char *imgData = (char *)(*env)->GetDirectBufferAddress(env, iconBuffer);

	setIcon(env, imgData, icon_size, width, height);
}

JNIEXPORT jlong JNICALL Java_org_lwjgl_opengl_LinuxDisplay_getDisplay(JNIEnv *env, jclass unused) {
	return (intptr_t)getDisplay();
}

JNIEXPORT jlong JNICALL Java_org_lwjgl_opengl_LinuxDisplay_getWindow(JNIEnv *env, jclass unused) {
	return getCurrentWindow();
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nUngrabKeyboard(JNIEnv *env, jclass unused, jlong display_ptr) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	return XUngrabKeyboard(disp, CurrentTime);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nGrabKeyboard(JNIEnv *env, jclass unused, jlong display_ptr, jlong window_ptr) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	Window win = (Window)window_ptr;
	return XGrabKeyboard(disp, win, False, GrabModeAsync, GrabModeAsync, CurrentTime);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nGrabPointer(JNIEnv *env, jclass unused, jlong display_ptr, jlong window_ptr) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	Window win = (Window)window_ptr;
	int grab_mask = PointerMotionMask | ButtonPressMask | ButtonReleaseMask;
	return XGrabPointer(disp, win, False, grab_mask, GrabModeAsync, GrabModeAsync, win, None, CurrentTime);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nSetViewPort(JNIEnv *env, jclass unused, jlong display_ptr, jlong window_ptr, jint screen) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	Window win = (Window)window_ptr;
	XWindowAttributes win_attribs;

	XGetWindowAttributes(disp, win, &win_attribs);
	XF86VidModeSetViewPort(disp, screen, win_attribs.x, win_attribs.y);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nUngrabPointer(JNIEnv *env, jclass unused, jlong display_ptr) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	return XUngrabPointer(disp, CurrentTime);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nDefineCursor(JNIEnv *env, jclass unused, jlong display_ptr, jlong window_ptr, jobject cursor_handle) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	Window win = (Window)window_ptr;
	Cursor cursor;
	if (cursor_handle != NULL)
		cursor = *((Cursor *)(*env)->GetDirectBufferAddress(env, cursor_handle));
	else
		cursor = None;
	XDefineCursor(disp, win, cursor);
}

JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nCreateBlankCursor(JNIEnv *env, jclass unused, jlong display_ptr, jlong window_ptr) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	Window win = (Window)window_ptr;
	jobject handle_buffer = newJavaManagedByteBuffer(env, sizeof(Cursor));
	if (handle_buffer == NULL) {
		return NULL;
	}
	Cursor *cursor = (Cursor *)(*env)->GetDirectBufferAddress(env, handle_buffer);
	unsigned int best_width, best_height;
	if (XQueryBestCursor(disp, win, 1, 1, &best_width, &best_height) == 0) {
		throwException(env, "Could not query best cursor size");
		return false;
	}
	Pixmap mask = XCreatePixmap(disp, win, best_width, best_height, 1);
	XGCValues gc_values;
	gc_values.foreground = 0;
	GC gc = XCreateGC(disp, mask, GCForeground, &gc_values);
	XFillRectangle(disp, mask, gc, 0, 0, best_width, best_height);
	XFreeGC(disp, gc);
	XColor dummy_color;
	*cursor = XCreatePixmapCursor(disp, mask, mask, &dummy_color, &dummy_color, 0, 0);
	XFreePixmap(disp, mask);
	return handle_buffer;
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_LinuxDisplay_getScreen(JNIEnv *env, jclass unsused) {
	return getCurrentScreen();
}

JNIEXPORT jlong JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nGetInputFocus(JNIEnv *env, jclass unused, jlong display_ptr) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	int revert_mode;
	Window win;
	XGetInputFocus(disp, &win, &revert_mode);
	return win;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nSetRepeatMode(JNIEnv *env, jclass unused, jlong display_ptr, jint mode) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	XKeyboardControl repeat_mode;
	repeat_mode.auto_repeat_mode = mode;
	XChangeKeyboardControl(disp, KBAutoRepeatMode, &repeat_mode);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_LinuxDisplay_nIconifyWindow(JNIEnv *env, jclass unused, jlong display_ptr, jlong window_ptr, jint screen) {
	Display *disp = (Display *)(intptr_t)display_ptr;
	Window win = (Window)window_ptr;
	XIconifyWindow(disp, win, screen);
}
