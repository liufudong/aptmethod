package com.liu.test;

import com.liu.annotation.MyAnnotation;

@MyAnnotation(sString = {"text1", "text2", "text3"})
public class TestActivity extends BaseActivity {
    @Override
    int setLayoutResID() {
        return R.layout.test;
    }

}
