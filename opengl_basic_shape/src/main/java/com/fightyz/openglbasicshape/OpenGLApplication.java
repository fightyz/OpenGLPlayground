package com.fightyz.openglbasicshape;

import com.fightyz.comlib.BaseApplication;
import com.fightyz.openglbasicshape.utils.DisplayManager;

public class OpenGLApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayManager.getInstance().init(this);
    }
}
