package com.dalong.zwlviewpager;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 *
 * Created by zwl on 16/6/22.
 */

public class SlidingBallPageTransformer implements ViewPager.PageTransformer {

    private  float mScale = 0.5f;//缩放比例

    private  float mAlpha = 0.7f;//左右透明度

    /**
     * 构造方法
     */
    public SlidingBallPageTransformer(){}

    /**
     * 构造方法
     * @param mScale 缩放比例
     * @param mAlpha 透明度
     */
    public SlidingBallPageTransformer(float mScale,float mAlpha){
        this.mAlpha=mAlpha;
        this.mScale=mScale;
    }

    @Override
    public void transformPage(View page, float position) {
        Log.v("transformPage","position:"+position);
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();
        if (position < -1) { // [-Infinity,-1) 此范围是停止滑动左边屏幕的部分
            page.setAlpha(mAlpha);
            page.setScaleX(mScale);
            page.setScaleY(mScale);
        } else if (position <= 1) { // [-1,1]  滑动过程中的设置view的缩放和通明度
            float scaleFactor = Math.max(mScale, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                page.setTranslationX(horzMargin - vertMargin / 2);
                page.setScaleX(1 + (1-mScale) * position);
                page.setScaleY(1 + (1-mScale) * position);
            } else {
                page.setTranslationX(-horzMargin + vertMargin / 2);

                page.setScaleX(1 - (1-mScale) * position);
                page.setScaleY(1 - (1-mScale) * position);
            }

            // 设置通明度在最低mAlpha 到1的通明度
            page.setAlpha(mAlpha + (scaleFactor - mScale) / (1 - mScale) * (1 - mAlpha));

        } else { // (1,+Infinity] 这个范围是停止滑动的右面部分
            page.setAlpha(mAlpha);
            page.setScaleX(mScale);
            page.setScaleY(mScale);

        }
    }
}
