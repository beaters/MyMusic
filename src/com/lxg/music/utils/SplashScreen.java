package com.lxg.music.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lxg.music.R;

/**
 * 引导页图片，停留若干秒，然后自动消失。
 * 
 */
public class SplashScreen {
    
    public final static int SLIDE_LEFT = 1;
    public final static int SLIDE_UP = 2;
    public final static int FADE_OUT = 3;
    
    private Dialog splashDialog;
    
    private Activity activity;
    
    public SplashScreen(Activity activity){
        this.activity = activity;
    }

    public void show(final int animation){
        Runnable runnable = new Runnable() {
            public void run() {
                LayoutInflater layoutInflater=activity.getLayoutInflater();
                View root=layoutInflater.inflate(R.layout.splash_screen, null);
                Animation animation1= AnimationUtils.loadAnimation(activity, R.anim.splash);
                root.setAnimation(animation1);
                splashDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
                if ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                        == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
                    splashDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }

                Window window = splashDialog.getWindow();
                switch(animation){
                    case SLIDE_LEFT:
                        window.setWindowAnimations(R.style.dialog_anim_slide_left);
                        break;
                    case SLIDE_UP:
                        window.setWindowAnimations(R.style.dialog_anim_slide_up);
                        break;
                    case FADE_OUT:
                        window.setWindowAnimations(R.style.dialog_anim_fade_out);
                        break;
                }
            
                splashDialog.setContentView(root);
                splashDialog.setCancelable(false);
                splashDialog.show();

                // Set Runnable to remove splash screen just in case
                /*final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        removeSplashScreen();
                    }
                }, millis);*/
            }
        };
        activity.runOnUiThread(runnable);
    }
    
    public void removeSplashScreen(){
        if (splashDialog != null && splashDialog.isShowing()) {
            splashDialog.dismiss();
            splashDialog = null;
        }
    }

}
