package com.fightyz.openglbasicshape.objects;

import android.content.Context;
import android.opengl.Matrix;

import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.data.VertexArray;
import com.fightyz.openglbasicshape.utils.ShaderHelper;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.Matrix.setIdentityM;

public class Line extends BaseShape {
    float[] lineVertex = {
            -0.5f, 0.5f,
            0.5f, -0.5f
    };

    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX = "u_Matrix";
    private int uColorLocation;
    private int aPositionLocaiton;
    private int uMatrixLocation;

    public Line(Context context) {
        super(context);
        program = ShaderHelper.buildProgram(context, R.raw.line_vertex_shader,
                R.raw.line_fragment_shader);
        glUseProgram(program);
        vertexArray = new VertexArray(lineVertex);
        POSITION_COMPONENT_COUNT = 2;
    }

    @Override
    public void bindData() {
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocaiton = glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        vertexArray.setVertexAttribPointer(0, aPositionLocaiton, POSITION_COMPONENT_COUNT, 0);
        setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0.5f, 0, 0);
    }

    @Override
    public void draw() {
        super.draw();
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);

        // 使用矩阵平移，将坐标 x 轴平移 0.5 个单位
        glUniformMatrix4fv(uMatrixLocation, 1, false, modelMatrix, 0);
        glDrawArrays(GL_LINES, 0, 2);
    }
}
