package com.fightyz.openglbasicshape.data;

import com.fightyz.openglbasicshape.utils.Constant;

import java.nio.ByteBuffer;

public class PositionArray {
    private final ByteBuffer byteBuffer;

    public PositionArray(byte[] positionData) {
        byteBuffer = ByteBuffer.allocateDirect(positionData.length * Constant.BYTES_PRE_FLOAT)
                .put(positionData);
    }
}
