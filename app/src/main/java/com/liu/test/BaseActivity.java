package com.liu.test;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BaseActivity extends Activity {
    abstract int setLayoutResID();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResID());
        String sString[] = BindsActivity.inject(BaseActivity.this);
        StringBuffer mStringBuffer = new StringBuffer();
        if (sString != null && sString.length > 0) {
            for (int i = 0; i < sString.length; i++) {
                mStringBuffer.append(sString[i]).append(",");
            }
        }
        System.out.println("====mStringBuffer=" + mStringBuffer.toString());
    }

}
