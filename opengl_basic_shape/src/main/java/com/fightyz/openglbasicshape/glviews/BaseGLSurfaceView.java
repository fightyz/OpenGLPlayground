package com.fightyz.openglbasicshape.glviews;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.fightyz.comlib.LogUtil;
import com.fightyz.openglbasicshape.renderers.BaseRenderer;

public class BaseGLSurfaceView extends GLSurfaceView {
    private BaseRenderer baseRenderer;

    public BaseGLSurfaceView(Context context, BaseRenderer renderer) {
        super(context);
        setEGLContextClientVersion(2);

        baseRenderer = renderer;
        setRenderer(baseRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("onTouchEvent");

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }

                baseRenderer.setAngleX(
                        baseRenderer.getAngleX() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                requestRender();

                break;
            default:
                break;
        }
        return true;
    }
}
