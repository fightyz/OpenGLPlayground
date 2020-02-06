package com.fightyz.openglbasicshape.glviews;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.fightyz.comlib.LogUtil;
import com.fightyz.openglbasicshape.renderers.TriangleRenderer;

public class TouchSurfaceView extends GLSurfaceView {
    TriangleRenderer mRender;
    private Context mContext;

    private float[] Point1;

    public TouchSurfaceView(Context context) {
        super(context);
        mContext = context;
        mRender = new TriangleRenderer(context);
        setEGLContextClientVersion(2);
        setRenderer(mRender);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        Point1 = new float[2];
    }

    public TouchSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mRender = new TriangleRenderer(context);
        setEGLContextClientVersion(2);
        setRenderer(mRender);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        Point1 = new float[2];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Point1[0] = event.getX();
                Point1[1] = event.getY();
                LogUtil.d("Point[0] is " + Point1[0] + " Point[1] is " + Point1[1]);
                mRender.isInCubeArea(Point1);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
