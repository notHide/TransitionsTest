package com.hide.transitionplayer.lib.interpolators;

import android.animation.TimeInterpolator;

/**
 * Created by linfaxin on 2015/8/9.
 * Email: linlinfaxin@163.com
 */
public class LockStartTimeInterpolator extends WrappedTimeInterpolator{
    public LockStartTimeInterpolator(TimeInterpolator wrapped) {
        super(wrapped);
    }
    @Override
    public float getInterpolation(float input) {
        return 0;
    }
}
