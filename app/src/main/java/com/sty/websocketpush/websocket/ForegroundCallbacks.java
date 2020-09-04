package com.sty.websocketpush.websocket;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 监听应用前后台切换的变化
 * @Author: tian
 * @UpdateDate: 2020/9/4 2:47 PM
 */
public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = ForegroundCallbacks.class.getSimpleName();
    private static final long CHECK_DELAY = 600;
    private static ForegroundCallbacks instance;
    private boolean foreground = false;
    private boolean paused = false;
    private Handler mHandler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList<>();
    private Runnable check;

    public static ForegroundCallbacks init(Application application) {
        if(instance == null) {
            instance = new ForegroundCallbacks();
            application.registerActivityLifecycleCallbacks(instance);
        }
        return instance;
    }

    public static ForegroundCallbacks get(Application application) {
        if(instance == null) {
            init(application);
        }
        return instance;
    }

    public static ForegroundCallbacks get(Context context) {
        if(instance == null) {
            Context appCtx = context.getApplicationContext();
            if(appCtx instanceof Application) {
                init((Application) appCtx);
            }
            throw new IllegalStateException("Foreground is not initialised and cannot obtain the Application object");
        }
        return instance;
    }

    public static ForegroundCallbacks get() {
        return instance;
    }

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        paused = false;
        boolean wasBackground = !foreground;
        foreground = true;
        if(check != null) {
            mHandler.removeCallbacks(check);
        }
        if(wasBackground) {
            for (Listener listener : listeners) {
                try {
                    listener.onBecameForeground();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            //do nothing
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        paused = true;
        if(check != null) {
            mHandler.removeCallbacks(check);
        }
        mHandler.postDelayed(check = new Runnable() {
            @Override
            public void run() {
                if(foreground && paused) {
                    foreground = false;
                    for (Listener listener : listeners) {
                        try {
                            listener.onBecameBackground();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    //do nothing
                }
            }
        },CHECK_DELAY);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    public interface Listener {
        public void onBecameForeground();

        public void onBecameBackground();
    }
}
