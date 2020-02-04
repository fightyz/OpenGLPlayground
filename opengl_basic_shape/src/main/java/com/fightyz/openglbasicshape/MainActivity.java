package com.fightyz.openglbasicshape;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fightyz.openglbasicshape.renderers.BaseRenderer;
import com.fightyz.openglbasicshape.renderers.CircleRenderer;
import com.fightyz.openglbasicshape.renderers.CubeRender;
import com.fightyz.openglbasicshape.renderers.CubeRender2;
import com.fightyz.openglbasicshape.renderers.LineRenderer;
import com.fightyz.openglbasicshape.renderers.PointRenderer;
import com.fightyz.openglbasicshape.renderers.RectangleRender;
import com.fightyz.openglbasicshape.renderers.SphereRenderer;
import com.fightyz.openglbasicshape.renderers.TriangleRenderer;
import com.fightyz.openglbasicshape.utils.Constant;

/**
 * @author joe.yang@dji.com
 * @date 2020-01-15 14:41
 */
public class MainActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;
    private int mType;
    private SparseArray<BaseRenderer> mRendererArray = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRendererMap();

        glSurfaceView = new GLSurfaceView(this);

        // Request an OpenGL ES 2.0 compatible context.
        glSurfaceView.setEGLContextClientVersion(2);

        mType = getIntent().getIntExtra(Constant.RENDERER_TYPE, 0);
        final BaseRenderer renderer = mRendererArray.get(mType);
        glSurfaceView.setRenderer(renderer);
        // Assign our renderer.
//        glSurfaceView = new BaseGLSurfaceView(this, renderer);
         // 两种绘图模式，第一种连续不断的画，适用于动画；第二种有需要时再画，通过 requestRender 调用
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
//        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                float x = 0.0f;
//                float y = 0.0f;
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        x = event.getX();
//                        y = event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        float dx = event.getX() - x;
//                        LogUtil.e("dx = " + dx);
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });

        setContentView(glSurfaceView);
    }

    private void initRendererMap() {
        mRendererArray.put(0, new PointRenderer(this));
        mRendererArray.put(1, new LineRenderer(this));
        mRendererArray.put(2, new TriangleRenderer(this));
        mRendererArray.put(3, new RectangleRender(this));
        mRendererArray.put(4, new CircleRenderer(this));
        mRendererArray.put(5, new CubeRender(this));
        mRendererArray.put(6, new CubeRender2(this));
        mRendererArray.put(7, new SphereRenderer(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
}
