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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.generator.*;

public interface EXT_vertex_shader {
	public static final int GL_VERTEX_SHADER_EXT = 0x8780;
	public static final int GL_VERTEX_SHADER_BINDING_EXT = 0x8781;
	public static final int GL_OP_INDEX_EXT = 0x8782;
	public static final int GL_OP_NEGATE_EXT = 0x8783;
	public static final int GL_OP_DOT3_EXT = 0x8784;
	public static final int GL_OP_DOT4_EXT = 0x8785;
	public static final int GL_OP_MUL_EXT = 0x8786;
	public static final int GL_OP_ADD_EXT = 0x8787;
	public static final int GL_OP_MADD_EXT = 0x8788;
	public static final int GL_OP_FRAC_EXT = 0x8789;
	public static final int GL_OP_MAX_EXT = 0x878A;
	public static final int GL_OP_MIN_EXT = 0x878B;
	public static final int GL_OP_SET_GE_EXT = 0x878C;
	public static final int GL_OP_SET_LT_EXT = 0x878D;
	public static final int GL_OP_CLAMP_EXT = 0x878E;
	public static final int GL_OP_FLOOR_EXT = 0x878F;
	public static final int GL_OP_ROUND_EXT = 0x8790;
	public static final int GL_OP_EXP_BASE_2_EXT = 0x8791;
	public static final int GL_OP_LOG_BASE_2_EXT = 0x8792;
	public static final int GL_OP_POWER_EXT = 0x8793;
	public static final int GL_OP_RECIP_EXT = 0x8794;
	public static final int GL_OP_RECIP_SQRT_EXT = 0x8795;
	public static final int GL_OP_SUB_EXT = 0x8796;
	public static final int GL_OP_CROSS_PRODUCT_EXT = 0x8797;
	public static final int GL_OP_MULTIPLY_MATRIX_EXT = 0x8798;
	public static final int GL_OP_MOV_EXT = 0x8799;
	public static final int GL_OUTPUT_VERTEX_EXT = 0x879A;
	public static final int GL_OUTPUT_COLOR0_EXT = 0x879B;
	public static final int GL_OUTPUT_COLOR1_EXT = 0x879C;
	public static final int GL_OUTPUT_TEXTURE_COORD0_EXT = 0x879D;
	public static final int GL_OUTPUT_TEXTURE_COORD1_EXT = 0x879E;
	public static final int GL_OUTPUT_TEXTURE_COORD2_EXT = 0x879F;
	public static final int GL_OUTPUT_TEXTURE_COORD3_EXT = 0x87A0;
	public static final int GL_OUTPUT_TEXTURE_COORD4_EXT = 0x87A1;
	public static final int GL_OUTPUT_TEXTURE_COORD5_EXT = 0x87A2;
	public static final int GL_OUTPUT_TEXTURE_COORD6_EXT = 0x87A3;
	public static final int GL_OUTPUT_TEXTURE_COORD7_EXT = 0x87A4;
	public static final int GL_OUTPUT_TEXTURE_COORD8_EXT = 0x87A5;
	public static final int GL_OUTPUT_TEXTURE_COORD9_EXT = 0x87A6;
	public static final int GL_OUTPUT_TEXTURE_COORD10_EXT = 0x87A7;
	public static final int GL_OUTPUT_TEXTURE_COORD11_EXT = 0x87A8;
	public static final int GL_OUTPUT_TEXTURE_COORD12_EXT = 0x87A9;
	public static final int GL_OUTPUT_TEXTURE_COORD13_EXT = 0x87AA;
	public static final int GL_OUTPUT_TEXTURE_COORD14_EXT = 0x87AB;
	public static final int GL_OUTPUT_TEXTURE_COORD15_EXT = 0x87AC;
	public static final int GL_OUTPUT_TEXTURE_COORD16_EXT = 0x87AD;
	public static final int GL_OUTPUT_TEXTURE_COORD17_EXT = 0x87AE;
	public static final int GL_OUTPUT_TEXTURE_COORD18_EXT = 0x87AF;
	public static final int GL_OUTPUT_TEXTURE_COORD19_EXT = 0x87B0;
	public static final int GL_OUTPUT_TEXTURE_COORD20_EXT = 0x87B1;
	public static final int GL_OUTPUT_TEXTURE_COORD21_EXT = 0x87B2;
	public static final int GL_OUTPUT_TEXTURE_COORD22_EXT = 0x87B3;
	public static final int GL_OUTPUT_TEXTURE_COORD23_EXT = 0x87B4;
	public static final int GL_OUTPUT_TEXTURE_COORD24_EXT = 0x87B5;
	public static final int GL_OUTPUT_TEXTURE_COORD25_EXT = 0x87B6;
	public static final int GL_OUTPUT_TEXTURE_COORD26_EXT = 0x87B7;
	public static final int GL_OUTPUT_TEXTURE_COORD27_EXT = 0x87B8;
	public static final int GL_OUTPUT_TEXTURE_COORD28_EXT = 0x87B9;
	public static final int GL_OUTPUT_TEXTURE_COORD29_EXT = 0x87BA;
	public static final int GL_OUTPUT_TEXTURE_COORD30_EXT = 0x87BB;
	public static final int GL_OUTPUT_TEXTURE_COORD31_EXT = 0x87BC;
	public static final int GL_OUTPUT_FOG_EXT = 0x87BD;
	public static final int GL_SCALAR_EXT = 0x87BE;
	public static final int GL_VECTOR_EXT = 0x87BF;
	public static final int GL_MATRIX_EXT = 0x87C0;
	public static final int GL_VARIANT_EXT = 0x87C1;
	public static final int GL_INVARIANT_EXT = 0x87C2;
	public static final int GL_LOCAL_CONSTANT_EXT = 0x87C3;
	public static final int GL_LOCAL_EXT = 0x87C4;
	public static final int GL_MAX_VERTEX_SHADER_INSTRUCTIONS_EXT = 0x87C5;
	public static final int GL_MAX_VERTEX_SHADER_VARIANTS_EXT = 0x87C6;
	public static final int GL_MAX_VERTEX_SHADER_INVARIANTS_EXT = 0x87C7;
	public static final int GL_MAX_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 0x87C8;
	public static final int GL_MAX_VERTEX_SHADER_LOCALS_EXT = 0x87C9;
	public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_INSTRUCTIONS_EXT = 0x87CA;
	public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_VARIANTS_EXT = 0x87CB;
	public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_INVARIANTS_EXT = 0x87CC;
	public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 0x87CD;
	public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_LOCALS_EXT = 0x87CE;
	public static final int GL_VERTEX_SHADER_INSTRUCTIONS_EXT = 0x87CF;
	public static final int GL_VERTEX_SHADER_VARIANTS_EXT = 0x87D0;
	public static final int GL_VERTEX_SHADER_INVARIANTS_EXT = 0x87D1;
	public static final int GL_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 0x87D2;
	public static final int GL_VERTEX_SHADER_LOCALS_EXT = 0x87D3;
	public static final int GL_VERTEX_SHADER_OPTIMIZED_EXT = 0x87D4;
	public static final int GL_X_EXT = 0x87D5;
	public static final int GL_Y_EXT = 0x87D6;
	public static final int GL_Z_EXT = 0x87D7;
	public static final int GL_W_EXT = 0x87D8;
	public static final int GL_NEGATIVE_X_EXT = 0x87D9;
	public static final int GL_NEGATIVE_Y_EXT = 0x87DA;
	public static final int GL_NEGATIVE_Z_EXT = 0x87DB;
	public static final int GL_NEGATIVE_W_EXT = 0x87DC;
	public static final int GL_ZERO_EXT = 0x87DD;
	public static final int GL_ONE_EXT = 0x87DE;
	public static final int GL_NEGATIVE_ONE_EXT = 0x87DF;
	public static final int GL_NORMALIZED_RANGE_EXT = 0x87E0;
	public static final int GL_FULL_RANGE_EXT = 0x87E1;
	public static final int GL_CURRENT_VERTEX_EXT = 0x87E2;
	public static final int GL_MVP_MATRIX_EXT = 0x87E3;
	public static final int GL_VARIANT_VALUE_EXT = 0x87E4;
	public static final int GL_VARIANT_DATATYPE_EXT = 0x87E5;
	public static final int GL_VARIANT_ARRAY_STRIDE_EXT = 0x87E6;
	public static final int GL_VARIANT_ARRAY_TYPE_EXT = 0x87E7;
	public static final int GL_VARIANT_ARRAY_EXT = 0x87E8;
	public static final int GL_VARIANT_ARRAY_POINTER_EXT = 0x87E9;
	public static final int GL_INVARIANT_VALUE_EXT = 0x87EA;
	public static final int GL_INVARIANT_DATATYPE_EXT = 0x87EB;
	public static final int GL_LOCAL_CONSTANT_VALUE_EXT = 0x87EC;
	public static final int GL_LOCAL_CONSTANT_DATATYPE_EXT = 0x87ED;

	public void glBeginVertexShaderEXT();

	public void glEndVertexShaderEXT();

	public void glBindVertexShaderEXT(@GLuint int id);

	public @GLuint int glGenVertexShadersEXT(@GLuint int range);

	public void glDeleteVertexShaderEXT(@GLuint int id);

	public void glShaderOp1EXT(@GLenum int op, @GLuint int res, @GLuint int arg1);

	public void glShaderOp2EXT(@GLenum int op, @GLuint int res, @GLuint int arg1, @GLuint int arg2);

	public void glShaderOp3EXT(@GLenum int op, @GLuint int res, @GLuint int arg1, @GLuint int arg2, @GLuint int arg3);

	public void glSwizzleEXT(@GLuint int res, @GLuint int in, @GLenum int outX, @GLenum int outY, @GLenum int outZ, @GLenum int outW);

	public void glWriteMaskEXT(@GLuint int res, @GLuint int in, @GLenum int outX, @GLenum int outY, @GLenum int outZ, @GLenum int outW);

	public void glInsertComponentEXT(@GLuint int res, @GLuint int src, @GLuint int num);

	public void glExtractComponentEXT(@GLuint int res, @GLuint int src, @GLuint int num);

	public @GLuint int glGenSymbolsEXT(@GLenum int dataType, @GLenum int storageType, @GLenum int range, @GLuint int components);

	public void glSetInvariantEXT(@GLuint int id, @AutoType("pAddr") @GLenum int type,
			@Check("4")
			@GLbyte
			@GLubyte
			@GLshort
			@GLushort
			@GLint
			@GLuint
			@GLfloat
			Buffer pAddr);

	public void glSetLocalConstantEXT(@GLuint int id, @AutoType("pAddr") @GLenum int type,
			@Check("4")
			@GLbyte
			@GLubyte
			@GLshort
			@GLushort
			@GLint
			@GLuint
			@GLfloat
			Buffer pAddr);

	@StripPostfix("pAddr")
	public void glVariantbvEXT(@GLuint int id, @Check("4") ByteBuffer pAddr);
	@StripPostfix("pAddr")
	public void glVariantsvEXT(@GLuint int id, @Check("4") ShortBuffer pAddr);
	@StripPostfix("pAddr")
	public void glVariantivEXT(@GLuint int id, @Check("4") IntBuffer pAddr);
	@StripPostfix("pAddr")
	public void glVariantfvEXT(@GLuint int id, @Check("4") FloatBuffer pAddr);

	@StripPostfix("pAddr")
	public void glVariantubvEXT(@GLuint int id, @Check("4") @GLubyte ByteBuffer pAddr);
	@StripPostfix("pAddr")
	public void glVariantusvEXT(@GLuint int id, @Check("4") @GLushort ShortBuffer pAddr);
	@StripPostfix("pAddr")
	public void glVariantuivEXT(@GLuint int id, @Check("4") @GLuint IntBuffer pAddr);

	public void glVariantPointerEXT(@GLuint int id, @AutoType("pAddr") @GLenum int type, @GLuint int stride,
			@BufferObject(BufferKind.ArrayVBO)
			@Check
			@GLbyte
			@GLshort
			@GLint
			@GLubyte
			@GLushort
			@GLuint
			@GLfloat
			Buffer pAddr);

	public void glEnableVariantClientStateEXT(@GLuint int id);

	public void glDisableVariantClientStateEXT(@GLuint int id);

	public @GLuint int glBindLightParameterEXT(@GLenum int light, @GLenum int value);

	public @GLuint int glBindMaterialParameterEXT(@GLenum int face, @GLenum int value);

	public @GLuint int glBindTexGenParameterEXT(@GLenum int unit, @GLenum int coord, @GLenum int value);

	public @GLuint int glBindTextureUnitParameterEXT(@GLenum int unit, @GLenum int value);

	public @GLuint int glBindParameterEXT(@GLenum int value);

	public boolean glIsVariantEnabledEXT(@GLuint int id, @GLenum int cap);

	@StripPostfix("pbData")
	public void glGetVariantBooleanvEXT(@GLuint int id, @GLenum int value, @Check("4") ByteBuffer pbData);
	@StripPostfix("pbData")
	public void glGetVariantIntegervEXT(@GLuint int id, @GLenum int value, @Check("4") IntBuffer pbData);
	@StripPostfix("pbData")
	public void glGetVariantFloatvEXT(@GLuint int id, @GLenum int value, @Check("4") FloatBuffer pbData);
	@StripPostfix("pbData")
	public void glGetVariantPointervEXT(@GLuint int id, @GLenum int value, @Result @GLvoid ByteBuffer pbData);

	@StripPostfix("pbData")
	public void glGetInvariantBooleanvEXT(@GLuint int id, @GLenum int value, @Check("4") ByteBuffer pbData);
	@StripPostfix("pbData")
	public void glGetInvariantIntegervEXT(@GLuint int id, @GLenum int value, @Check("4") IntBuffer pbData);
	@StripPostfix("pbData")
	public void glGetInvariantFloatvEXT(@GLuint int id, @GLenum int value, @Check("4") FloatBuffer pbData);

	@StripPostfix("pbData")
	public void glGetLocalConstantBooleanvEXT(@GLuint int id, @GLenum int value, @Check("4") ByteBuffer pbData);
	@StripPostfix("pbData")
	public void glGetLocalConstantIntegervEXT(@GLuint int id, @GLenum int value, @Check("4") IntBuffer pbData);
	@StripPostfix("pbData")
	public void glGetLocalConstantFloatvEXT(@GLuint int id, @GLenum int value, @Check("4") FloatBuffer pbData);
}
