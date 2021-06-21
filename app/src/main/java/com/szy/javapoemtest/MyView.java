package com.szy.javapoemtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by devov on 2021/2/24.
 */

public class MyView extends View {


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //do something 1


    private String name = "";
    public void  setName(String name){
        this.name = name;

    }
//    @Override
//    protected void onAttachedToWindow() {
//        Log.i("RROREE",name+" child  onAttachedToWindow");
//        super.onAttachedToWindow();
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        Log.i("RROREE",name+" child  onDetachedFromWindow");
//
//        super.onDetachedFromWindow();
//    }
//
//    @Override
//    public void draw(Canvas canvas) {
//        Log.i("RROREE",name+" child  draw"+"   canvas:"+canvas.toString());
//        super.draw(canvas);
//
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i("RROREE",name+" child  onMeasure");
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        Log.i("RROREE",name+" child  onDraw");
//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        super.onDraw(canvas);
//    }
}
