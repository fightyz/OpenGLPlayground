package com.fightyz.openglbasicshape;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fightyz.openglbasicshape.glviews.TouchSurfaceView;

public class TouchActivity extends AppCompatActivity {
    TouchSurfaceView touchSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        touchSurfaceView = findViewById(R.id.touchSurface);
    }

    @Override
    protected void onPause() {
        super.onPause();
        touchSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        touchSurfaceView.onResume();
    }
}
