package com.fightyz.openglbasicshape;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fightyz.openglbasicshape.renderers.BaseRenderer;
import com.fightyz.openglbasicshape.renderers.LineRenderer;
import com.fightyz.openglbasicshape.renderers.PointRenderer;
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
        // Assign our renderer.
        glSurfaceView.setRenderer(mRendererArray.get(mType));
        setContentView(glSurfaceView);
    }

    private void initRendererMap() {
        mRendererArray.put(0, new PointRenderer(this));
        mRendererArray.put(1, new LineRenderer(this));
        mRendererArray.put(2, new TriangleRenderer(this));
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
