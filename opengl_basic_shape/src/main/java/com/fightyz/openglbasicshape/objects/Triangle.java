package com.fightyz.openglbasicshape.objects;

import android.content.Context;

import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.data.VertexArray;
import com.fightyz.openglbasicshape.utils.ShaderHelper;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.Matrix.setIdentityM;

public class Triangle extends BaseShape {
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX = "u_Matrix";

    private int uColorLocation;
    private int aPositionLocation;
    private int uMatrixLocation;

    float[] triangleVertex = {
            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f
    };

    public Triangle(Context context) {
        super(context);
        program = ShaderHelper.buildProgram(context, R.raw.triangle_vertex_shader, R.raw.triangle_fragment_shader);
        glUseProgram(program);
        vertexArray = new VertexArray(triangleVertex);
        POSITION_COMPONENT_COUNT = 2;
    }

    @Override
    public void bindData() {
        super.bindData();
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        vertexArray.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, 0);
        setIdentityM(modelMatrix, 0);
    }

    @Override
    public void draw() {
        super.draw();
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, modelMatrix, 0);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    @Override
    public void draw(float[] mvpMatrix) {
        super.draw(mvpMatrix);
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 0.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
