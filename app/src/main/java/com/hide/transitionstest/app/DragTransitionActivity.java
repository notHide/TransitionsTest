package com.hide.transitionstest.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.transitions.everywhere.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.hide.transitionplayer.lib.TransitionPlayer;
import com.hide.transitionstest.app.widget.DragLayout;

/**
 * * * * * * * * * * * * * * * * * * * * * * *
 * Created by zhaoyiding
 * Date: 15/10/12
 * * * * * * * * * * * * * * * * * * * * * * *
 **/
public class DragTransitionActivity extends Activity
        implements DragLayout.IDragListener {

    private View mDimView;
    private ViewGroup mSceneRoot;
    private TransitionPlayer mTransitionPlayer = new TransitionPlayer();
    private ImageView mHeadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_transition);

        mDimView = findViewById(R.id.dimView);
        mSceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        mHeadImg = (ImageView) findViewById(R.id.headimg);

        initImg();

        final DragLayout mDragLayout = (DragLayout) findViewById(R.id.drag_layout);
        mDragLayout.setOnDragListener(this);
        mDragLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mDragLayout.getViewTreeObserver().removeOnPreDrawListener(this);


                TransitionManager.beginDelayedTransition(mSceneRoot, mTransitionPlayer);
                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mHeadImg.getLayoutParams();
                marginParams.topMargin = 0;
                mHeadImg.setLayoutParams(marginParams);
                mHeadImg.setAlpha(1.0f);

                mHeadImg.post(new Runnable() {
                    @Override
                    public void run() {
                        mTransitionPlayer.setCurrentFraction(1.0f);

                    }
                });
                return false;
            }
        });

    }

    private void initImg() {

        int height = getResources().getDisplayMetrics().heightPixels / 3 - IndexActivity.sStatusBarHeight;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        params.topMargin = -height * 2 / 3;
        mHeadImg.setLayoutParams(params);
        mHeadImg.setAlpha(0f);
    }

    private void setDimViewAlpha(float fraction) {
        mDimView.setBackgroundColor(Color.argb((int) (255 * fraction), 0, 0, 0));
        mTransitionPlayer.setCurrentFraction(fraction);
    }

    @Override
    public void onDragChange(float fraction) {
        setDimViewAlpha(fraction);
    }
}
