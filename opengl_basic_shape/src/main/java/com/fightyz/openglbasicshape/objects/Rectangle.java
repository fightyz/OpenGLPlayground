package com.fightyz.openglbasicshape.objects;

import android.content.Context;

import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.data.VertexArray;
import com.fightyz.openglbasicshape.utils.Constant;
import com.fightyz.openglbasicshape.utils.ShaderHelper;
import com.fightyz.openglbasicshape.utils.TextureHelper;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.Matrix.setIdentityM;

public class Rectangle extends BaseShape {
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX = "u_Matrix";
    private static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    private static final String U_TEXTURE_UNIT = "u_TextureUnit";

    private int uColorLocation;
    private int aPositionLocation;
    private int uMatrixLocation;
    private int aTextureCoordinatesLocation;
    private int uTextureUnitLocation;

    float[] rectangleVertex = {
            // 物体 (x, y)，纹理 (s, t)
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,

            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
    };

    public Rectangle(Context context) {
        super(context);
        program = ShaderHelper.buildProgram(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        glUseProgram(program);
        vertexArray = new VertexArray(rectangleVertex);
        POSITION_COMPONENT_COUNT = 2;
        TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
        STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constant.BYTES_PRE_FLOAT;
    }

    @Override
    public void bindData() {
        super.bindData();
//        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
        vertexArray.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, 0);
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT, aTextureCoordinatesLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE);
        setIdentityM(mvpMatrix, 0);
        int texture = TextureHelper.loadTexture(context, R.drawable.image);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);
        glUniform1i(uTextureUnitLocation, 0);
    }

    @Override
    public void draw(float[] mvpMatrix) {
        super.draw(mvpMatrix);

//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }

    @Override
    public void draw() {
        super.draw();


//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
}
