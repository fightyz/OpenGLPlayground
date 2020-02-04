package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.Matrix;

import com.fightyz.openglbasicshape.objects.Sphere;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.setIdentityM;

public class SphereRenderer extends BaseRenderer {
    Sphere sphere;

    float radius = 15f;

    float angle = 0f;
    float angleX = 0f;
    float angleY = 0f;
    float positionX;
    float positionZ;

    float ratio;

    private RotateThread rotateThread; // 该线程用来改变图形的角度

    public SphereRenderer(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        sphere = new Sphere(mContext);
        sphere.bindData();

        rotateThread = new RotateThread();
        rotateThread.start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        ratio = (float) width / height;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        super.onDrawFrame(gl10);
        setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, angleX, 0f, 1f, 0);
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 30);
        // 设置相机位置
        Matrix.setLookAtM(viewMatrix, 0, 1.0f, -10.0f, -4.0f, 0f, 0f, 0f,
                0f, 1.0f, 0.0f);
        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, viewMatrix, 0, modelMatrix, 0);
        // 计算变换矩阵
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, temp, 0);
        sphere.draw(mvpMatrix);
    }

    /**
     * 这个线程是用来改变三角形角度用的
     */
    public class RotateThread extends Thread {

        public boolean flag = true;

        @Override
        public void run() {
            while (flag) {
//                angle += radian;
                angleX = (angleX + 1.0f) % 360;
                try {
                    Thread.sleep(20);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
