package com.fightyz.openglbasicshape.objects;

import android.content.Context;

import com.fightyz.comlib.LogUtil;
import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.data.VertexArray;
import com.fightyz.openglbasicshape.utils.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.Matrix.setIdentityM;

public class Sphere extends BaseShape {
    private static final String U_MATRIX = "u_Matrix";
    private static final String A_POSITION = "a_Position";
//    private static final String U_COLOR = "u_Color";

    private int uMatrixLocation;
    private int aPositionLocation;
//    private int uColorLocation;

    float[] sphereVertex;

    private float step = 2.0f;
    private float step2 = 4.0f;
    private float radius = 1.0f;
    private int length;

    private IntBuffer intBuffer;
    private ShortBuffer indexBuffer;
    private int[] position;
    private short[] indices;

    public Sphere(Context context) {
        super(context);

        program = ShaderHelper.buildProgram(context, R.raw.sphere_vertex_shader, R.raw.sphere_fragment_shader);
        glUseProgram(program);
        POSITION_COMPONENT_COUNT = 3;
        initSphereVertex2();
//        sphereVertex = initSphereVertex();
        vertexArray = new VertexArray(sphereVertex);
        length = sphereVertex.length / 3;
//        intBuffer = ByteBuffer.allocateDirect(position.length * 4)
//                .asIntBuffer().put(position);
//        intBuffer.position(0);
        indexBuffer = ByteBuffer.allocateDirect(indices.length * 2).order(ByteOrder.nativeOrder())
                .asShortBuffer().put(indices);
        indexBuffer.position(0);
    }

    @Override
    public void bindData() {
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
//        uColorLocation = glGetUniformLocation(program, U_COLOR);

        vertexArray.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, 0);;
        setIdentityM(modelMatrix, 0);
    }

    @Override
    public void draw() {
        glUniformMatrix4fv(uMatrixLocation, 1, false, modelMatrix, 0);
//        glUniform4f(uColorLocation, 0.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, length);
    }

    @Override
    public void draw(float[] mvpMatrix) {
        super.draw(mvpMatrix);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mvpMatrix, 0);
//        glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
//        glDrawArrays(GL_TRIANGLE_STRIP, 0, length);
        // 通过索引来绘制圆
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_SHORT, indexBuffer);
    }

    private void initSphereVertex3() {

        int rings = 90;
        int sectors = 90;

        float PI = 3.1415926535f;
        float PI_2 = 1.57079632679f;


        float R = 1f / (float) (rings - 1);
        float S = 1f / (float) (sectors - 1);
        short r, s;
        float x, y, z;

        sphereVertex = new float[rings * sectors * 3];

        // 相当于 rings = 90
        // 相当于 sectors = 90
        int t = 0, v = 0, n = 0;
        for (r = 0; r < rings; r++) {
            for (s = 0; s < sectors; s++) {
                y = (float) Math.sin(-PI_2 + PI * r * R);
                x = (float) (Math.cos(2 * PI * s * S) * Math.sin(PI * r * R));
                z = (float) (Math.sin(2 * PI * s * S) * Math.sin(PI * r * R));

                sphereVertex[v++] = radius * x;
                sphereVertex[v++] = radius * y;
                sphereVertex[v++] = radius * z;
            }
        }


        int counter = 0;
        indices = new short[rings * sectors * 6];
        for (r = 0; r < rings - 1; r++) {
            for (s = 0; s < sectors - 1; s++) {
                indices[counter++] = (short) (r * sectors + s);       //(a)
                indices[counter++] = (short) (r * sectors + (s + 1));    //(b)
                indices[counter++] = (short) ((r + 1) * sectors + (s + 1));  // (c)
                indices[counter++] = (short) ((r + 1) * sectors + (s + 1));  // (c)
                indices[counter++] = (short) (r * sectors + (s + 1));    //(b)
                indices[counter++] = (short) ((r + 1) * sectors + s);     //(d)
            }
        }


    }

    private void initSphereVertex2() {

        int rings = 90;
        int sectors = 90;

        float r;
        float y;
        float sin;
        float cos;

        sphereVertex = new float[rings * sectors * 3];

        /**
         * 只遍历一边顶点就好了
         */
        int count = 0;
        for (float i = -90.0f; i < 90.0f; i += step) {

            r = (float) Math.cos(i * Math.PI / 180.0);
            y = (float) Math.sin(i * Math.PI / 180.0);

            for (float j = 0.0f; j < 360.0f; j += step2) {

                cos = (float) Math.cos(j * Math.PI / 180.0);
                sin = (float) Math.sin(j * Math.PI / 180.0);
                sphereVertex[count++] = r * sin;
                sphereVertex[count++] = y;
                sphereVertex[count++] = r * cos;
            }
        }

        LogUtil.d("count num is " + sphereVertex.length);

        // 对于不存在的顶点索引 也可以绘制 嘛？
        int counter = 0;
        indices = new short[rings * sectors * 6 * 2];
        for (int i = 0; i < rings * 2; i++) {
            for (int j = 0; j < sectors; j++) {
                indices[counter++] = (short) (i * sectors + j);       //(a)
                indices[counter++] = (short) (i * sectors + (j + 1));    //(b)
                indices[counter++] = (short) ((i + 1) * sectors + j);  // (c)
                indices[counter++] = (short) ((i + 1) * sectors + j);  // (c)
                indices[counter++] = (short) (i * sectors + (j + 1));    //(b)
                indices[counter++] = (short) ((i + 1) * sectors + (j + 1));     //(d)
                if (i == rings * 2 - 1) {
                    LogUtil.d("count is " + ((i + 1) * sectors + (j + 1)));
                }
            }
        }

    }

    private float[] initSphereVertex() {
        float r1;
        float r2;
        float y1;
        float y2;
        float cos;
        float sin;
        ArrayList<Float> data = new ArrayList<>();

        for (float i = -90.0f; i <= 90.0f; i += step) {
            r1 = (float) Math.cos(i * Math.PI / 180.0);
            r2 = (float) Math.cos((i + step) * Math.PI / 180.0f);

            y1 = (float) Math.sin(i * Math.PI / 180.0f);
            y2 = (float) Math.sin((i + step) * Math.PI / 180.0);

            for (float j = 0.0f; j <= 360.0f; j += step2) {
                cos = (float) Math.cos(j * Math.PI / 180.0);
                sin = -(float) Math.sin(j * Math.PI / 180.0f);

                data.add(r2 * cos);
                data.add(y2);
                data.add(r2 * sin);

                data.add(r1 * cos);
                data.add(y1);
                data.add(r1 * sin);
            }
        }

        float[] f = new float[data.size()];
        for (int i = 0; i < f.length; i++) {
            f[i] = data.get(i);
        }
        return f;
    }
}
