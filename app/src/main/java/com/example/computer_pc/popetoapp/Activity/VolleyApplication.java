package com.example.computer_pc.popetoapp.Activity;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Tomer on 16/04/2016.
 */
public class VolleyApplication extends Application {
    private static VolleyApplication sInstance;

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);

        sInstance = this;
    }

    public synchronized static VolleyApplication getsInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return  mRequestQueue;
    }
}
