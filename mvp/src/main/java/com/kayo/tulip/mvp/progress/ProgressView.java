package com.kayo.tulip.mvp.progress;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * Kayo
 * 2018/12/19
 * 进度view
 */
public class ProgressView extends FrameLayout {
    public ProgressView(@NonNull Context context) {
        this(context, null, -1);
    }

    public ProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ProgressView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        View progressBg = new View(getContext());
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#885A647B"));
        gd.setCornerRadius(dp2px(5));
        progressBg.setBackground(gd);
        int bgSize = dp2px(100);
        LayoutParams bgParams = new LayoutParams(bgSize, bgSize);
        bgParams.gravity = Gravity.CENTER;
        addView(progressBg, bgParams);

        ProgressBar progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminateDrawable(new ProgressDrawable(getContext()));
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LayoutParams progressParams = new LayoutParams(
                bgSize / 2, bgSize / 2);
        progressParams.gravity = Gravity.CENTER;
        addView(progressBar, progressParams);

    }

    public void bindView(Object object){
        if (object instanceof Activity) {
            Activity activity = (Activity) object;
            ViewGroup view = activity.findViewById(android.R.id.content);
            view.addView(this);

        }else if (object instanceof Fragment){
            Fragment fragment = (Fragment) object;
            bindView(fragment.getActivity());
        }
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hidden() {
        setVisibility(GONE);
    }

    private int dp2px(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
