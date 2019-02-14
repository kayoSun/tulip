package com.kayo.tulip;

import android.os.Bundle;

import com.kayo.tulip.mvp.TulipActivity;
import com.kayo.tulip.mvp.TulipPresenter;

public class MainActivity extends TulipActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected TulipPresenter initPresenter() {
        return null;
    }

}
