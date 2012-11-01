/*
 * Copyright (c) 2002-2008 LWJGL Project
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
 * Base Win32 display
 *
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$
 */

#ifndef __LWJGL_CONTEXT_H
#define __LWJGL_CONTEXT_H

#include <Cocoa/Cocoa.h>
#include <OpenGL/gl.h>
#include <OpenGL/glext.h>
#include "common_tools.h"

@class NSOpenGLContext, NSOpenGLPixelFormat, MacOSXOpenGLView, MacOSXKeyableWindow;

typedef struct {
    MacOSXKeyableWindow *window;
    
    NSRect display_rect;

    MacOSXOpenGLView *view;
    NSOpenGLContext *context;
    
    // Native objects for Java callbacks
    jobject jdisplay;
    jobject jmouse;
    jobject jkeyboard;
    jboolean resized;
    
    // Cached for window creation
    NSApplicationPresentationOptions window_options;
} MacOSXWindowInfo;

@interface MacOSXKeyableWindow : NSWindow

- (BOOL)canBecomeKeyWindow;
@end

@interface MacOSXOpenGLView : NSView
{
    @public
    MacOSXWindowInfo*       _parent;
    
    @private
    NSOpenGLContext*        _openGLContext;
    NSOpenGLPixelFormat*    _pixelFormat;
    NSUInteger              _lastModifierFlags;
    NSUInteger              _modifierFlags;
}

+ (NSOpenGLPixelFormat*)defaultPixelFormat;
- (id)initWithFrame:(NSRect)frameRect pixelFormat:(NSOpenGLPixelFormat*)format;
- (void)setOpenGLContext:(NSOpenGLContext*)context;
- (NSOpenGLContext*)openGLContext;
- (void)clearGLContext;
- (void)prepareOpenGL;
- (void)update;
- (void)lockFocus;
- (void)setPixelFormat:(NSOpenGLPixelFormat*)pixelFormat;
- (NSOpenGLPixelFormat*)pixelFormat;
- (void)setParent:(MacOSXWindowInfo*)parent;
- (BOOL)acceptsFirstResponder;
@end

typedef struct {
	bool isWindowed;
    bool canDrawGL;
    MacOSXWindowInfo *window_info;
	NSOpenGLPixelFormat *pixel_format;
    NSOpenGLPixelBuffer *pbuffer;
} MacOSXPeerInfo;


NSOpenGLPixelFormat *choosePixelFormat(JNIEnv *env, jobject pixel_format, bool gl32, bool use_display_bpp, bool support_window, bool support_pbuffer, bool double_buffered);
#endif
