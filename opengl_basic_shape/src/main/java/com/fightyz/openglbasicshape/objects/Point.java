package com.fightyz.openglbasicshape.objects;

import android.content.Context;

import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.data.VertexArray;
import com.fightyz.openglbasicshape.utils.ShaderHelper;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;

public class Point extends BaseShape {

    // 着色器中定义的变量，在 Java 层绑定并赋值
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private int uColorLocation;
    private int aPositionLocation;

    float[] pointVertex = {
            0f, 0f
    };

    public Point(Context context) {
        super(context);
        program = ShaderHelper.buildProgram(context, R.raw.point_vertex_shader
                , R.raw.point_fragment_shader);
        glUseProgram(program);
        vertexArray = new VertexArray(pointVertex);
        POSITION_COMPONENT_COUNT = 2;
    }

    @Override
    public void bindData() {
        // 绑定值
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        // 给绑定的值赋值，也就是从顶点数据那里开始读取，每次读取间隔是多少
        vertexArray.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT,
                0);
    }

    @Override
    public void draw() {
        // 给绑定的值赋值
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 0, 1);
    }
}
