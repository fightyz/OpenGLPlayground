package com.fightyz.openglbasicshape.objects;

import android.content.Context;

import com.fightyz.openglbasicshape.data.VertexArray;

public abstract class BaseShape {
    protected Context context;
    protected VertexArray vertexArray;
    protected VertexArray indexArray;
    protected int program;

    protected float[] modelMatrix = new float[16];
    protected float[] viewMatrix = new float[16];
    protected float[] projectionMatrix = new float[16];
    protected float[] mvpMatrix = new float[16];

    protected int POSITION_COMPONENT_COUNT;

    protected int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;

    protected int STRIDE;

    public BaseShape(Context context) {
        this.context = context;
    }

    public void draw() {}

    public void draw(float[] mvpMatrix) {}

    public void bindData() {}
}
