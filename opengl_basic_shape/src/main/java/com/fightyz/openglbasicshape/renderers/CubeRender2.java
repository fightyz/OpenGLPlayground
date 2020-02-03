package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.Matrix;

import com.fightyz.openglbasicshape.objects.Cube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.setIdentityM;

public class CubeRender2 extends BaseRenderer {
    private Cube cube;

    private static final int NUM = 360;

    private RotateThread rotateThread; // 该线程用来改变图形角度

    // 分成 360 份，每一份的弧度
    public static final float radian = (float) (2 * Math.PI / NUM);

    float radius = 15f;

    float angle = 0f;
    float angleX = 0f;
    float angleY = 0f;
    float positionX;
    float positionZ;

    float ratio;

    float[][] xDistance = {
            {0.0f, 0.0f, 0.0f},
            {3.0f, 0.0f, 0.0f},
            {0.0f, 3.0f, 0.0f},
            {-3.8f, -2.0f, -12.3f},
            {2.4f, -0.4f, -3.5f},
            {1.3f, -2.0f, 2.5f},
            {1.5f, 2.0f, -2.5f},
            {1.5f, 0.2f, -1.5f},
            {-1.3f, 1.0f, -1.5f}
    };

    public CubeRender2(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_CULL_FACE);
//        glEnable(GL_DEPTH_TEST);
        cube = new Cube(mContext);
        cube.bindData();

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
//        super.onDrawFrame(gl10);
        glClear(GL_COLOR_BUFFER_BIT);
        for (int i = 0; i < xDistance.length; i++) {
            setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0, xDistance[i][0], xDistance[i][1],
                    xDistance[i][2]);
            Matrix.rotateM(modelMatrix, 0, angleX, 1f, 0, 1f);
            angleY = 20.0f * i;

            positionX = (float) (radius * Math.sin(angle));
            positionZ = (float) (radius * Math.cos(angle));

            // 摄像机位置旋转
            Matrix.setLookAtM(viewMatrix, 0, 0f, 0, 15f,
                    0f, 0f, 0f, 0f, 1.0f, 0f);
            Matrix.perspectiveM(projectionMatrix, 0, 45.0f, ratio, 1f, 40.0f);

            final float[] temp = new float[16];
            Matrix.multiplyMM(temp, 0, viewMatrix, 0, modelMatrix, 0);
            final float[] result = new float[16];
            Matrix.multiplyMM(result, 0, projectionMatrix, 0, temp, 0);
//            System.arraycopy(result, 0, projectionMatrix, 0, result.length);
            cube.draw(result);
        }
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
