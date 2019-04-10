package com.zl.demo.zlratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Zhanglu on 2019/4/9
 * <p>
 * 星级评分条
 */
public class MyRatingBar extends LinearLayout {

    int mStarNum, mStarSelectNum;                    //    星星数量

    boolean isIndicator;                             //    是不是指示器

    Bitmap mStarNorol, mSelectStar;                  //  未选中,以及选中的星星的样式

    float mStarWidth, mStarHeight, mStarDistance;    //  单个星星的高宽及间距

    private Paint mPaint = new Paint();              //   初始化画笔

    private OnStarChangeListener onStarChangeListener;//监听星星变化接口

    public MyRatingBar(Context context) {
        this(context, null);
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        获取MyRatingBar的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar);

//        没有选中以及被选中的资源的Id的星星的Id
        int starNoSelectId = typedArray.getResourceId(R.styleable.MyRatingBar_starNoSelect, 0);
        if (starNoSelectId == 0) {
            throw new IllegalArgumentException("没有被选中的资源属性没有被设置");
        }
        int starSelectId = typedArray.getResourceId(R.styleable.MyRatingBar_starSelect, 0);
        if (starSelectId == 0) {
            throw new IllegalArgumentException("被选中的资源属性未设置");
        }

//        获取图片
        mStarNorol = BitmapFactory.decodeResource(getResources(), starNoSelectId);
        mSelectStar = BitmapFactory.decodeResource(getResources(), starSelectId);

//        获取星星的宽高以及间距
        mStarWidth = typedArray.getDimension(R.styleable.MyRatingBar_starWihth, 0);
        mStarHeight = typedArray.getDimension(R.styleable.MyRatingBar_starHeight, 0);
        mStarDistance = typedArray.getDimension(R.styleable.MyRatingBar_starDistance, 0);
//        获取星星的数量以及是不是指示器
        mStarNum = typedArray.getInt(R.styleable.MyRatingBar_starNum, 0);
        if (mStarNum == 0) {
            mStarNum = 5;
        }
        mStarSelectNum = typedArray.getInt(R.styleable.MyRatingBar_starSelectNum, 0);
        isIndicator = typedArray.getBoolean(R.styleable.MyRatingBar_isIndicator, false);
        typedArray.recycle();

//        设置星星的宽高
//        int starWidth = (int) Math.max(mStarWidth, mStarHeight);
//        if (starWidth > 0) {
////            对星星进行重置
//            mSelectStar = resetBitmap(mSelectStar, starWidth);
//            mStarNorol = resetBitmap(mStarNorol, starWidth);
//        }

    }

    // TODO: 2019/4/10 测量评分条的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//       控件的高度 = 上下内边距 + 控件高度
        int height = getPaddingBottom() + getPaddingTop() + mStarNorol.getHeight();
//       宽 = 单个星星的宽度 * mStarNum + 间距 * (mStarNum - 1) + 左右内边距
        int width = (int) (mStarNorol.getWidth() * mStarNum + mStarDistance * (mStarNum - 1) + getPaddingLeft() + getPaddingRight());
        setMeasuredDimension(width, height);
    }

    // TODO: 2019/4/10 绘制控件
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        首先绘制大的框架
        for (int i = 0; i < mStarNum; i++) {
//           计算第一个星星的左边距
            float left = getPaddingLeft();
//          当从第二个开始左边距开始不断变化
            if (i > 0) {
                left = getPaddingLeft() + i * (mStarNorol.getWidth() + mStarDistance);
            }
            int top = getPaddingTop();
//                设置选中的星星
            if (i < mStarSelectNum) {
                canvas.drawBitmap(mSelectStar, left, top, mPaint);
            } else {
                canvas.drawBitmap(mStarNorol, left, top, mPaint);
            }

        }

    }

    // TODO: 2019/4/10 事件的处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        if (x < 0) x = 0;
        if (x > getMeasuredWidth()) x = getMeasuredWidth();

//        单个星星的宽度
        float v = getMeasuredWidth() / mStarNum;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setSelectStar(x / v);
                break;
            case MotionEvent.ACTION_MOVE:
                setSelectStar(x / v);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    private void setSelectStar(float v) {

        mStarSelectNum = (int)Math.ceil(v);
        if (this.onStarChangeListener != null) {
            this.onStarChangeListener.onStarChange(mStarSelectNum);  //调用监听接口
        }
        invalidate();

    }

    public int getmStarSelectNum() {
        return mStarSelectNum;
    }

    // TODO: 2019/4/10 重置图片
    private Bitmap resetBitmap(Bitmap bitmap, int v) {
        return Bitmap.createScaledBitmap(bitmap, v, v, true);
    }

    /**
     * 定义星星点击的监听接口
     */
    private interface OnStarChangeListener {
        void onStarChange(float mSelectNum);
    }

    /**
     * 设置监听
     *
     * @param onStarChangeListener 点击星星图标后触发
     */
    public void setOnStarChangeListener(OnStarChangeListener onStarChangeListener) {
        this.onStarChangeListener = onStarChangeListener;

    }
}
