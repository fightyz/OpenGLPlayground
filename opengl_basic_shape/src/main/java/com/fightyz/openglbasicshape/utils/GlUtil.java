package com.fightyz.openglbasicshape.utils;

import android.opengl.GLES20;

import timber.log.Timber;

public class GlUtil {
    public static void checkGlError(String op) {
        int error = GLES20.glGetError();
        if (error != GLES20.GL_NO_ERROR) {
            String msg = op + ": glError 0x" + Integer.toHexString(error);
            Timber.e(msg);
            throw new RuntimeException(msg);
        }
    }
}
