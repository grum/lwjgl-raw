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

public interface ARB_texture_rectangle {
	/*
	 * Accepted by the <cap> parameter of Enable, Disable and
	 * IsEnabled; by the <pname> parameter of GetBooleanv, GetIntegerv,
	 * GetFloatv and GetDoublev; and by the <target> parameter of
	 * BindTexture, GetTexParameterfv, GetTexParameteriv,
	 * TexParameterf, TexParameteri, TexParameterfv and TexParameteriv:

	 * Accepted by the <target> parameter of GetTexImage,
	 * GetTexLevelParameteriv, GetTexLevelParameterfv, TexImage2D,
	 * CopyTexImage2D, TexSubImage2D and CopySubTexImage2D:
	*/
	public static final int GL_TEXTURE_RECTANGLE_ARB = 0x84F5;

	/*
	 * Accepted by the <pname> parameter of GetBooleanv, GetIntegerv,
	 * GetFloatv and GetDoublev:
	*/
	public static final int GL_TEXTURE_BINDING_RECTANGLE_ARB = 0x84F6;

	/*
	* Accepted by the <target> parameter of GetTexLevelParameteriv,
	* GetTexLevelParameterfv, GetTexParameteriv and TexImage2D:
	*/
	public static final int GL_PROXY_TEXTURE_RECTANGLE_ARB = 0x84F7;

	/*
	* Accepted by the <pname> parameter of GetBooleanv, GetDoublev,
	* GetIntegerv and GetFloatv:
	*/
	public static final int GL_MAX_RECTANGLE_TEXTURE_SIZE_ARB = 0x84F8;
}
