package com.fightyz.openglbasicshape.objects;

import android.content.Context;

import com.fightyz.openglbasicshape.R;
import com.fightyz.openglbasicshape.data.VertexArray;
import com.fightyz.openglbasicshape.utils.ShaderHelper;
import com.fightyz.openglbasicshape.utils.TextureHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.Matrix.setIdentityM;

public class Rectangle extends BaseShape {
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX = "u_Matrix";
    private static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    private static final String U_TEXTURE_UNIT = "u_TextureUnit";

    private int uColorLocation;
    private int aPositionLocation;
    private int uMatrixLocation;
    private int aTextureCoordinatesLocation;
    private int uTextureUnitLocation;

    float[] rectangleVertex = {
            // 物体 (x, y)，纹理 (s, t)
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,

            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
    };

    float[] rectangleElementVertex = {
            0f, 0f,
            -0.5f, -0.8f,
            0.5f, -0.8f,
            0.5f, 0.8f,
            -0.5f, 0.8f
    };

    private ShortBuffer indexBuffer;

    float[] textureElementVertex = {
            0.5f, 0.5f,
            0f, 0.1f,
            1f, 0.1f,
            1f, 0.9f,
            0f, 0.9f
//            0.5f, 0.5f,
//            0f, 0.9f,
//            1f, 0.9f,
//            1f, 0.1f,
//            0f, 0.1f
    };
    private short[] indices = {
            0, 1, 2,
            0, 2, 3,
            0, 3, 4,
            0, 4, 1
    };


    VertexArray textureVertexArray;

    public Rectangle(Context context) {
        super(context);
        program = ShaderHelper.buildProgram(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        glUseProgram(program);
        initRectangleVertex();
        vertexArray = new VertexArray(rectangleElementVertex);
        textureVertexArray = new VertexArray(textureElementVertex);
        POSITION_COMPONENT_COUNT = 2;
        TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
//        STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constant.BYTES_PRE_FLOAT;
        STRIDE = 0;
        indexBuffer = ByteBuffer.allocateDirect(indices.length * 2).order(ByteOrder.nativeOrder())
                .asShortBuffer().put(indices);
        indexBuffer.position(0);
    }

    /**
     * 采用 glDrawElement 方式来定义顶点数组、索引位置、还有纹理位置
     */
    private void initRectangleVertex() {

    }

    @Override
    public void bindData() {
        super.bindData();
//        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
        vertexArray.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, STRIDE);
//        vertexArray.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, STRIDE);
//        vertexArray.setVertexAttribPointer(2, aTextureCoordinatesLocation,
//                TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE);

        textureVertexArray.setVertexAttribPointer(0, aTextureCoordinatesLocation, TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE);
        setIdentityM(mvpMatrix, 0);
        int texture = TextureHelper.loadTexture(context, R.drawable.image);

        // OpenGL 在使用纹理进行绘制时，不需要直接给着色器传递纹理
        // 相反，我们使用纹理单元保存那个纹理，因为，一个 GPU 只能同时绘制数量有限的纹理
        // 它使用那些纹理单元表示当前正在被绘制的活动的纹理

        // 通过调用 glActiveTexture 把活动的纹理单元设置为纹理单元 0
        glActiveTexture(GL_TEXTURE0);
        // 然后通过调用 glBindTexture 把纹理绑定到这个单元
        glBindTexture(GL_TEXTURE_2D, texture);
        // 接着通过调用 glUniform1i 把被选定的纹理单元传递给片段着色器中的 u_TextureUnit
        glUniform1i(uTextureUnitLocation, 0);
    }

    @Override
    public void draw(float[] mvpMatrix) {
        super.draw(mvpMatrix);

//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }

    @Override
    public void draw() {
        super.draw();
//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
//        glUniformMatrix4fv(uMatrixLocation, 1, false, mvpMatrix, 0);
//        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        glUniformMatrix4fv(uMatrixLocation, 1, false, mvpMatrix, 0);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_SHORT, indexBuffer);
    }
}
