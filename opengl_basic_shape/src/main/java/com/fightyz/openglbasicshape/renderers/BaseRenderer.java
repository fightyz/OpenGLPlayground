package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.utils.ShaderHelper;
import com.fightyz.openglbasicshape.utils.TextResourceReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glUseProgram;

/**
 * @author joe.yang@dji.com
 * @date 2020-01-15 14:57
 */
public class BaseRenderer implements GLSurfaceView.Renderer {
    /**
     * 一个 float 数据占 4 个字节
     */
    protected int BYTES_PER_FLOAT = 4;

    protected Context mContext;

    protected int program;

    String vertexShaderSource;
    String fragmentShaderSource;

    public BaseRenderer(Context context) {
        this.mContext = context;
        readShaderSource();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        if (vertexShaderSource == null || fragmentShaderSource == null) {
            throw new RuntimeException("Please set Shader Source First");
        }

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        ShaderHelper.validateProgram(program);
        glUseProgram(program);
    }

    /**
     * Surface 刚创建的时候，它的 size 是 0，也就是说在画第一次图之前它会被调用一次
     * @param gl10
     * @param i
     * @param i1
     */
    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }

    protected void readShaderSource() {

    }
}
