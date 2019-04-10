package com.zl.demo.zlratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Zhanglu on 2019/4/9
 * <p>
 * 星级评分条
 */
public class MyRatingBar extends LinearLayout {

    int mStarNum;                   //    星星数量

    boolean isIndicator;            //    是不是指示器

    Bitmap mStarNorol, mSelectStar; //  未选中,以及选中的星星的样式

    float mStarWidth, mStarHeight, mStarDistance;  //  单个星星的高宽及间距

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
       mStarNorol = BitmapFactory.decodeResource(getResources(),starNoSelectId);
       mSelectStar = BitmapFactory.decodeResource(getResources(),starSelectId);

//        获取星星的宽高以及间距
        mStarWidth = typedArray.getDimension(R.styleable.MyRatingBar_starWihth, 0);
        mStarHeight = typedArray.getDimension(R.styleable.MyRatingBar_starHeight, 0);
        mStarDistance = typedArray.getDimension(R.styleable.MyRatingBar_starDistance, 0);
//        获取星星的数量以及是不是指示器
        mStarNum = typedArray.getInt(R.styleable.MyRatingBar_starNum, 0);
        isIndicator = typedArray.getBoolean(R.styleable.MyRatingBar_isIndicator, false);
        typedArray.recycle();



    }


}
