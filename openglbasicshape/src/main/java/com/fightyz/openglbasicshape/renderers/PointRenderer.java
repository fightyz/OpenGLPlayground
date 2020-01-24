package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.utils.GlUtil;
import com.fightyz.openglbasicshape.utils.ShaderHelper;
import com.fightyz.openglbasicshape.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import timber.log.Timber;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class PointRenderer extends BaseRenderer {
    private static final int POSITION_COMPONENT_COUNT = 2;

    private float[] pointVertex = {
            0f, 0f
    };

    private FloatBuffer vertexData;
    private int program;

    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";

    private int aColorLocation;
    private int aPositionLocation;

    public PointRenderer(Context context) {
        super(context);
        vertexData = ByteBuffer.allocateDirect(pointVertex.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(pointVertex);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // 设置调用 glClear 时，颜色缓冲区中应填充什么值
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.point_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.point_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        ShaderHelper.validateProgram(program);
        glUseProgram(program);

        aColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        vertexData.position(0);
        Timber.d("enable vertex attribute");

        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
//        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        glUniform4f(aColorLocation, 0.0f, 1.0f, 0.5f, 1.0f);
        glDrawArrays(GL_POINTS, 0, 1);
    }
}
