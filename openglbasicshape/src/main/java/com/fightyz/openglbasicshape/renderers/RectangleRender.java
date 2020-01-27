package com.fightyz.openglbasicshape.renderers;

import android.content.Context;

import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class RectangleRender extends BaseRenderer {
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private int aColorLocation;
    private int aPositionLocaiton;

    float[] rectangleVertex = {
            0.5f, 0.5f,
            -0.5f, 0.5f,
            0.5f, -0.5f,

            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
    };

    public static final int POSITION_COMPONENT_COUNT = 2;
    private FloatBuffer vertexData;

    public RectangleRender(Context context) {
        super(context);
        vertexData = ByteBuffer.allocateDirect(rectangleVertex.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(rectangleVertex);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        aColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocaiton = glGetAttribLocation(program, A_POSITION);
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocaiton, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocaiton);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        glUniform4f(aColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }

    @Override
    public void readShaderSource() {
        vertexShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.rectangle_vertex_shader);
        fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.rectangle_fragment_shader);
    }
}
