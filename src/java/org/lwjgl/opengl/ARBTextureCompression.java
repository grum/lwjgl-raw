/* MACHINE GENERATED FILE, DO NOT EDIT */

package org.lwjgl.opengl;

import org.lwjgl.LWJGLException;
import org.lwjgl.BufferChecks;
import java.nio.*;

public final class ARBTextureCompression {
	public static final int GL_COMPRESSED_TEXTURE_FORMATS_ARB = 0x86a3;
	public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS_ARB = 0x86a2;
	public static final int GL_TEXTURE_COMPRESSED_ARB = 0x86a1;
	public static final int GL_TEXTURE_IMAGE_SIZE_ARB = 0x86a0;
	public static final int GL_TEXTURE_COMPRESSION_HINT_ARB = 0x84ef;
	public static final int GL_COMPRESSED_RGBA_ARB = 0x84ee;
	public static final int GL_COMPRESSED_RGB_ARB = 0x84ed;
	public static final int GL_COMPRESSED_INTENSITY_ARB = 0x84ec;
	public static final int GL_COMPRESSED_LUMINANCE_ALPHA_ARB = 0x84eb;
	public static final int GL_COMPRESSED_LUMINANCE_ARB = 0x84ea;
	public static final int GL_COMPRESSED_ALPHA_ARB = 0x84e9;

	private ARBTextureCompression() {
	}

	static native void initNativeStubs() throws LWJGLException;

	public static void glGetCompressedTexImageARB(int target, int lod, ByteBuffer pImg) {
		GLBufferChecks.ensurePackPBOdisabled();
		BufferChecks.checkDirect(pImg);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glGetCompressedTexImageARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglGetCompressedTexImageARB(target, lod, pImg, pImg.position(), function_pointer);
	}
	public static void glGetCompressedTexImageARB(int target, int lod, IntBuffer pImg) {
		GLBufferChecks.ensurePackPBOdisabled();
		BufferChecks.checkDirect(pImg);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glGetCompressedTexImageARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglGetCompressedTexImageARB(target, lod, pImg, pImg.position() << 2, function_pointer);
	}
	public static void glGetCompressedTexImageARB(int target, int lod, ShortBuffer pImg) {
		GLBufferChecks.ensurePackPBOdisabled();
		BufferChecks.checkDirect(pImg);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glGetCompressedTexImageARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglGetCompressedTexImageARB(target, lod, pImg, pImg.position() << 1, function_pointer);
	}
	public static void glGetCompressedTexImageARB(int target, int lod, FloatBuffer pImg) {
		GLBufferChecks.ensurePackPBOdisabled();
		BufferChecks.checkDirect(pImg);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glGetCompressedTexImageARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglGetCompressedTexImageARB(target, lod, pImg, pImg.position() << 2, function_pointer);
	}
	private static native void nglGetCompressedTexImageARB(int target, int lod, Buffer pImg, int pImg_position, long function_pointer);
	public static void glGetCompressedTexImageARB(int target, int lod, int pImg_buffer_offset) {
		GLBufferChecks.ensurePackPBOenabled();
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glGetCompressedTexImageARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglGetCompressedTexImageARBBO(target, lod, pImg_buffer_offset, function_pointer);
	}
	private static native void nglGetCompressedTexImageARBBO(int target, int lod, int pImg_buffer_offset, long function_pointer);

	public static void glCompressedTexSubImage3DARB(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, ByteBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, pData, pData.position(), function_pointer);
	}
	public static void glCompressedTexSubImage3DARB(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, IntBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, pData, pData.position() << 2, function_pointer);
	}
	public static void glCompressedTexSubImage3DARB(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, ShortBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, pData, pData.position() << 1, function_pointer);
	}
	public static void glCompressedTexSubImage3DARB(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, FloatBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage3DARB(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, pData, pData.position() << 2, function_pointer);
	}
	private static native void nglCompressedTexSubImage3DARB(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, Buffer pData, int pData_position, long function_pointer);
	public static void glCompressedTexSubImage3DARB(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, int pData_buffer_offset) {
		GLBufferChecks.ensureUnpackPBOenabled();
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage3DARBBO(target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, pData_buffer_offset, function_pointer);
	}
	private static native void nglCompressedTexSubImage3DARBBO(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, int pData_buffer_offset, long function_pointer);

	public static void glCompressedTexSubImage2DARB(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, ByteBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, format, imageSize, pData, pData.position(), function_pointer);
	}
	public static void glCompressedTexSubImage2DARB(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, IntBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, format, imageSize, pData, pData.position() << 2, function_pointer);
	}
	public static void glCompressedTexSubImage2DARB(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, ShortBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, format, imageSize, pData, pData.position() << 1, function_pointer);
	}
	public static void glCompressedTexSubImage2DARB(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, FloatBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage2DARB(target, level, xoffset, yoffset, width, height, format, imageSize, pData, pData.position() << 2, function_pointer);
	}
	private static native void nglCompressedTexSubImage2DARB(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer pData, int pData_position, long function_pointer);
	public static void glCompressedTexSubImage2DARB(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, int pData_buffer_offset) {
		GLBufferChecks.ensureUnpackPBOenabled();
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage2DARBBO(target, level, xoffset, yoffset, width, height, format, imageSize, pData_buffer_offset, function_pointer);
	}
	private static native void nglCompressedTexSubImage2DARBBO(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, int pData_buffer_offset, long function_pointer);

	public static void glCompressedTexSubImage1DARB(int target, int level, int xoffset, int width, int format, int imageSize, ByteBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage1DARB(target, level, xoffset, width, format, imageSize, pData, pData.position(), function_pointer);
	}
	public static void glCompressedTexSubImage1DARB(int target, int level, int xoffset, int width, int format, int imageSize, IntBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage1DARB(target, level, xoffset, width, format, imageSize, pData, pData.position() << 2, function_pointer);
	}
	public static void glCompressedTexSubImage1DARB(int target, int level, int xoffset, int width, int format, int imageSize, ShortBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage1DARB(target, level, xoffset, width, format, imageSize, pData, pData.position() << 1, function_pointer);
	}
	public static void glCompressedTexSubImage1DARB(int target, int level, int xoffset, int width, int format, int imageSize, FloatBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage1DARB(target, level, xoffset, width, format, imageSize, pData, pData.position() << 2, function_pointer);
	}
	private static native void nglCompressedTexSubImage1DARB(int target, int level, int xoffset, int width, int format, int imageSize, Buffer pData, int pData_position, long function_pointer);
	public static void glCompressedTexSubImage1DARB(int target, int level, int xoffset, int width, int format, int imageSize, int pData_buffer_offset) {
		GLBufferChecks.ensureUnpackPBOenabled();
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexSubImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexSubImage1DARBBO(target, level, xoffset, width, format, imageSize, pData_buffer_offset, function_pointer);
	}
	private static native void nglCompressedTexSubImage1DARBBO(int target, int level, int xoffset, int width, int format, int imageSize, int pData_buffer_offset, long function_pointer);

	public static void glCompressedTexImage3DARB(int target, int level, int internalformat, int width, int height, int depth, int border, int imageSize, ByteBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage3DARB(target, level, internalformat, width, height, depth, border, imageSize, pData, pData.position(), function_pointer);
	}
	public static void glCompressedTexImage3DARB(int target, int level, int internalformat, int width, int height, int depth, int border, int imageSize, IntBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage3DARB(target, level, internalformat, width, height, depth, border, imageSize, pData, pData.position() << 2, function_pointer);
	}
	public static void glCompressedTexImage3DARB(int target, int level, int internalformat, int width, int height, int depth, int border, int imageSize, ShortBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage3DARB(target, level, internalformat, width, height, depth, border, imageSize, pData, pData.position() << 1, function_pointer);
	}
	public static void glCompressedTexImage3DARB(int target, int level, int internalformat, int width, int height, int depth, int border, int imageSize, FloatBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage3DARB(target, level, internalformat, width, height, depth, border, imageSize, pData, pData.position() << 2, function_pointer);
	}
	private static native void nglCompressedTexImage3DARB(int target, int level, int internalformat, int width, int height, int depth, int border, int imageSize, Buffer pData, int pData_position, long function_pointer);
	public static void glCompressedTexImage3DARB(int target, int level, int internalformat, int width, int height, int depth, int border, int imageSize, int pData_buffer_offset) {
		GLBufferChecks.ensureUnpackPBOenabled();
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage3DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage3DARBBO(target, level, internalformat, width, height, depth, border, imageSize, pData_buffer_offset, function_pointer);
	}
	private static native void nglCompressedTexImage3DARBBO(int target, int level, int internalformat, int width, int height, int depth, int border, int imageSize, int pData_buffer_offset, long function_pointer);

	public static void glCompressedTexImage2DARB(int target, int level, int internalformat, int width, int height, int border, int imageSize, ByteBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage2DARB(target, level, internalformat, width, height, border, imageSize, pData, pData.position(), function_pointer);
	}
	public static void glCompressedTexImage2DARB(int target, int level, int internalformat, int width, int height, int border, int imageSize, IntBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage2DARB(target, level, internalformat, width, height, border, imageSize, pData, pData.position() << 2, function_pointer);
	}
	public static void glCompressedTexImage2DARB(int target, int level, int internalformat, int width, int height, int border, int imageSize, ShortBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage2DARB(target, level, internalformat, width, height, border, imageSize, pData, pData.position() << 1, function_pointer);
	}
	public static void glCompressedTexImage2DARB(int target, int level, int internalformat, int width, int height, int border, int imageSize, FloatBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage2DARB(target, level, internalformat, width, height, border, imageSize, pData, pData.position() << 2, function_pointer);
	}
	private static native void nglCompressedTexImage2DARB(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer pData, int pData_position, long function_pointer);
	public static void glCompressedTexImage2DARB(int target, int level, int internalformat, int width, int height, int border, int imageSize, int pData_buffer_offset) {
		GLBufferChecks.ensureUnpackPBOenabled();
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage2DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage2DARBBO(target, level, internalformat, width, height, border, imageSize, pData_buffer_offset, function_pointer);
	}
	private static native void nglCompressedTexImage2DARBBO(int target, int level, int internalformat, int width, int height, int border, int imageSize, int pData_buffer_offset, long function_pointer);

	public static void glCompressedTexImage1DARB(int target, int level, int internalformat, int width, int border, int imageSize, ByteBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage1DARB(target, level, internalformat, width, border, imageSize, pData, pData.position(), function_pointer);
	}
	public static void glCompressedTexImage1DARB(int target, int level, int internalformat, int width, int border, int imageSize, IntBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage1DARB(target, level, internalformat, width, border, imageSize, pData, pData.position() << 2, function_pointer);
	}
	public static void glCompressedTexImage1DARB(int target, int level, int internalformat, int width, int border, int imageSize, ShortBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage1DARB(target, level, internalformat, width, border, imageSize, pData, pData.position() << 1, function_pointer);
	}
	public static void glCompressedTexImage1DARB(int target, int level, int internalformat, int width, int border, int imageSize, FloatBuffer pData) {
		GLBufferChecks.ensureUnpackPBOdisabled();
		BufferChecks.checkDirect(pData);
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage1DARB(target, level, internalformat, width, border, imageSize, pData, pData.position() << 2, function_pointer);
	}
	private static native void nglCompressedTexImage1DARB(int target, int level, int internalformat, int width, int border, int imageSize, Buffer pData, int pData_position, long function_pointer);
	public static void glCompressedTexImage1DARB(int target, int level, int internalformat, int width, int border, int imageSize, int pData_buffer_offset) {
		GLBufferChecks.ensureUnpackPBOenabled();
		long function_pointer = GLContext.getCapabilities().ARB_texture_compression_glCompressedTexImage1DARB_pointer;
		BufferChecks.checkFunctionAddress(function_pointer);
		nglCompressedTexImage1DARBBO(target, level, internalformat, width, border, imageSize, pData_buffer_offset, function_pointer);
	}
	private static native void nglCompressedTexImage1DARBBO(int target, int level, int internalformat, int width, int border, int imageSize, int pData_buffer_offset, long function_pointer);
}
