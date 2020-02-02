package com.fightyz.openglbasicshape.renderers;

import android.content.Context;

import com.fightyz.openglbasicshape.objects.Circle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glViewport;

public class CircleRenderer extends BaseRenderer {
    private Circle circle;

    public CircleRenderer(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);
        circle = new Circle(mContext);
        circle.bindData();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        super.onDrawFrame(gl10);
        circle.draw();
    }
}
