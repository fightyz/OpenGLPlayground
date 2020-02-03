package com.fightyz.openglbasicshape.objects;

import android.content.Context;

import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.data.VertexArray;
import com.fightyz.openglbasicshape.utils.Constant;
import com.fightyz.openglbasicshape.utils.ShaderHelper;

import java.nio.ByteBuffer;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.Matrix.setIdentityM;

public class Cube extends BaseShape {
    private static final String U_COLOR = "u_Color";
    private static final String A_COLOR = "a_Color";
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX = "u_Matrix";

    private int aColorLocation;
    private int aPositionLocation;
    private int uMatrixLocation;
    private int uColorLocation;

    private ByteBuffer byteBuffer;

    float[] cubeVertex = {
            -0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,

            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
    };

    byte[] position = {
            // Front
            1, 3, 0,
            0, 3, 2,

            // Back
            4, 6, 5,
            5, 6, 7,

            // Left
            0, 2, 4,
            4, 2, 6,

            // Right
            5, 7, 1,
            1, 7, 3,

            // Top
            5, 1, 4,
            4, 1, 0,

            // Bottom
            6, 2, 7,
            7, 2, 3
    };

    final float[] cubeColor2 = {
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
    };

    final float[] cubeColor = {
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f
    };

    public Cube(Context context) {
        super(context);

        program = ShaderHelper.buildProgram(context, R.raw.cube_vertex_shader,
                R.raw.cube_fragment_shader);
        glUseProgram(program);
        POSITION_COMPONENT_COUNT = 3;
        vertexArray = new VertexArray(cubeVertex);
        indexArray = new VertexArray(cubeColor2);
        byteBuffer = ByteBuffer.allocateDirect(position.length * Constant.BYTES_PRE_FLOAT)
                .put(position);
        byteBuffer.position(0);
    }

    @Override
    public void bindData() {
        aColorLocation = glGetAttribLocation(program, A_COLOR);
//        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        vertexArray.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, 0);
        indexArray.setVertexAttribPointer(0, aColorLocation,
                POSITION_COMPONENT_COUNT + 1, 0);
        setIdentityM(mvpMatrix, 0);
    }

    @Override
    public void draw() {
//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawElements(GL_TRIANGLES, position.length, GL_UNSIGNED_BYTE, byteBuffer);
    }

    @Override
    public void draw(float[] mvpMatrix) {
//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawElements(GL_TRIANGLES, position.length, GL_UNSIGNED_BYTE, byteBuffer);
    }
}
