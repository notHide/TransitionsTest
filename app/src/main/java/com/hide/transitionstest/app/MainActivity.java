package com.hide.transitionstest.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.transitions.everywhere.Fade;
import android.transitions.everywhere.TransitionManager;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends FragmentActivity
        implements View.OnClickListener {

    private ViewGroup mRootView;
    private View mRedBox, mGreenBox, mBlueBox, mBlackBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootView = (ViewGroup) findViewById(android.R.id.content);
        mRootView.setOnClickListener(this);

        mRedBox = findViewById(R.id.red_box);
        mRedBox.setOnClickListener(this);
        mGreenBox = findViewById(R.id.green_box);
        mGreenBox.setOnClickListener(this);
        mBlueBox = findViewById(R.id.blue_box);
        mBlueBox.setOnClickListener(this);
        mBlackBox = findViewById(R.id.black_box);
        mBlackBox.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.red_box:
                break;
            default:
                TransitionManager.beginDelayedTransition(mRootView, new Fade());
                toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
                break;
        }
    }

    private static void toggleVisibility(View... views) {
        for (View view : views) {
            boolean isVisible = view.getVisibility() == View.VISIBLE;
            view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
