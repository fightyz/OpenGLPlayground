package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.Matrix;

import com.fightyz.comlib.LogUtil;
import com.fightyz.openglbasicshape.objects.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.setIdentityM;

public class TriangleRenderer extends BaseRenderer {
    private Triangle mTriangle;

    private static final int NUM = 360;

    private RotateThread rotateThread;

    // 分成 360 份，每一份的弧度
    public static final float radian = (float) (2 * Math.PI / NUM);

    float radius = 3;

    float angle = 0f;
    int angleX = 0;
    float positionX;
    float positionZ;

    float ratio;

    public TriangleRenderer(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);
        mTriangle = new Triangle(mContext);
        mTriangle.bindData();
        rotateThread = new RotateThread();
        LogUtil.d("radian is " + radian);
        rotateThread.start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        ratio = (float) width / height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, angleX, 1, 0, 0);

        positionX = (float) (radius * Math.sin(angle));
        positionZ = (float) (radius * Math.cos(angle));
        LogUtil.d("position X is " + positionX);
        LogUtil.d("position Z is " + positionZ);

        Matrix.setLookAtM(viewMatrix, 0, positionX, 0, positionZ,
                0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 2, 5);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, viewMatrix, 0, modelMatrix, 0);

        final float[] result = new float[16];
        Matrix.multiplyMM(result, 0, projectionMatrix, 0, temp, 0);

        System.arraycopy(result, 0, projectionMatrix, 0, result.length);

        mTriangle.draw(result);
    }

    /**
     * 这个线程是用来改变三角形角度用的
     */
    public class RotateThread extends Thread {

        public boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                angle += radian;
//                angleX++;
//                angleX = (angleX + 1) % 360;
                try {
                    Thread.sleep(20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
