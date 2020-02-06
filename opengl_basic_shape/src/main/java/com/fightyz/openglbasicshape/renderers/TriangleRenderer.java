package com.fightyz.openglbasicshape.renderers;

import android.content.Context;
import android.opengl.GLU;
import android.opengl.Matrix;

import com.fightyz.comlib.LogUtil;
import com.fightyz.openglbasicshape.objects.Triangle;
import com.fightyz.openglbasicshape.utils.DisplayManager;

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
    public int[] view_matrix = new int[4];

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
        view_matrix[0] = 0;
        view_matrix[1] = 0;
        view_matrix[2] = width;
        view_matrix[3] = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        setIdentityM(modelMatrix, 0);
        setIdentityM(viewMatrix, 0);
        setIdentityM(projectionMatrix, 0);

        Matrix.rotateM(modelMatrix, 0, 180, 0, 1, 0);

        positionX = (float) (radius * Math.sin(angle));
        positionZ = (float) (radius * Math.cos(angle));
//        LogUtil.d("position X is " + positionX);
//        LogUtil.d("position Z is " + positionZ);

        Matrix.setLookAtM(viewMatrix, 0, positionX, 0, positionZ,
                0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 2, 5);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, viewMatrix, 0, modelMatrix, 0);

        final float[] result = new float[16];
        Matrix.multiplyMM(result, 0, projectionMatrix, 0, temp, 0);

        System.arraycopy(result, 0, mvpMatrix, 0, result.length);

        mTriangle.draw(mvpMatrix);
    }

    public int isInCubeArea(float[] Win) {
        float[] winXY = new float[3];
        float[] xyz = new float[] {
                -0.5f, 0f, 0
        };

        LogUtil.d("screen width is " + DisplayManager.getInstance().getmScreenWidth());
        LogUtil.d("screen height is " + DisplayManager.getInstance().getmScreenHeight());

//        Transform(modelMatrix, xyz);
        LogUtil.d("after transform x " + xyz[0] + ", y " + xyz[1] + ", z " + xyz[2]);

        int result = GLU.gluProject(xyz[0], xyz[1], xyz[2], viewMatrix, 0,
                projectionMatrix, 0, view_matrix, 0, winXY, 0);

        LogUtil.d("gluProject result is " + result);
        LogUtil.d("winX is " + winXY[0]);
        LogUtil.d("winY is " + winXY[1]);
        LogUtil.d("winZ is " + winXY[2]);

        LogUtil.d("view port y is " + view_matrix[3]);
        LogUtil.d("view port x is " + view_matrix[2]);

        LogUtil.d("win y is " + (view_matrix[3] - Win[1]));
        return 0;
    }

    private void Transform(float[] matrix, float[] Point) {
        float w = 1.0f;
        float x, y, z, ww;

        x = matrix[0] * Point[0] + matrix[4] * Point[1] + matrix[8] * Point[2] + matrix[12] * w;
        y = matrix[1] * Point[0] + matrix[5] * Point[1] + matrix[9] * Point[2] + matrix[13] * w;
        z = matrix[2] * Point[0] + matrix[6] * Point[1] + matrix[10] * Point[2] + matrix[14] * w;
        ww = matrix[3] * Point[0] + matrix[7] * Point[1] + matrix[11] * Point[2] + matrix[15] * w;

        Point[0] = x / ww;
        Point[1] = y / ww;
        Point[2] = z / ww;
    }

    /**
     * 这个线程是用来改变三角形角度用的
     */
    public class RotateThread extends Thread {

        public boolean flag = false;

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
