package com.kayo.tulip.mvp.progress;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Kayo
 * 2018/12/19
 * 16:00
 * 进度条样式文件
 */
public class ProgressDrawable extends Drawable {
    private Paint mBackgroundCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mDefaultWidth;
    private int mDefaultHeight;
    private float mStrokeWidth;
    private RectF mBounds = new RectF();

    private float mCurrentRotateDegree;
    private float mCurrentCircleDegreeStart;
    private float mCurrentCircleDegreeEnd;
    private final static int MAX_CIRCLE_DEGREE = 330;

    public ProgressDrawable(Context context) {
        this(context,Color.parseColor("#88e3e3e3"),Color.parseColor("#ffffff"));
    }

    public ProgressDrawable(Context context, int backgroundColor, int arcColor) {
        final Resources resources = context.getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        mStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                displayMetrics);
        mBackgroundCirclePaint.setColor(backgroundColor);
        mBackgroundCirclePaint.setStyle(Paint.Style.STROKE);
        mBackgroundCirclePaint.setStrokeWidth(mStrokeWidth);

        mArcPaint.setColor(arcColor);
        mArcPaint.setStrokeWidth(mStrokeWidth);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mDefaultWidth = mDefaultHeight = (int) TypedValue.applyDimension(TypedValue
                .COMPLEX_UNIT_DIP, 100, displayMetrics);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        Rect bounds = getBounds();
        final float halfStroke = mStrokeWidth / 2;
        final RectF arcBounds = mBounds;
        arcBounds.set(bounds.left + halfStroke, bounds.top + halfStroke, bounds.right - halfStroke,
                bounds.bottom - halfStroke);
        canvas.rotate(mCurrentRotateDegree, arcBounds.centerX(), arcBounds.centerY());
        canvas.drawOval(arcBounds, mBackgroundCirclePaint);
        canvas.drawArc(arcBounds, mCurrentCircleDegreeStart, mCurrentCircleDegreeEnd -
                mCurrentCircleDegreeStart, false, mArcPaint);
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {
        mBackgroundCirclePaint.setAlpha(alpha);
        mArcPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mArcPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    protected boolean onLevelChange(int level) {
        float factor = level * 1.0f / (10000 / 4) % 2;
        boolean isReverse = factor >= 1;
        mCurrentCircleDegreeStart = isReverse ? Math.min(MAX_CIRCLE_DEGREE, (factor - 1) * 360) : 0;
        mCurrentCircleDegreeEnd = isReverse ? MAX_CIRCLE_DEGREE : Math.min((factor * 360),
                MAX_CIRCLE_DEGREE);
        mCurrentRotateDegree = level * 1.0f / 10000 * 720;
        return super.onLevelChange(level);
    }

    @Override
    public int getIntrinsicWidth() {
        return mDefaultWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mDefaultHeight;
    }

}
