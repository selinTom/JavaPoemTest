package com.szy.javapoemtest

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by devov on 2021/3/11.
 */
class MyViewGroup:ConstraintLayout {
     constructor(context: Context):super(context)
     constructor(context: Context, attrs: AttributeSet):super(context, attrs)
     constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int):super(
         context,
         attrs,
         defStyleAttr
     )


//    override fun onDraw(canvas: Canvas?) {
//        Log.i("RROREE", " parent  onDraw    canvas:"+canvas?.toString())
//
//        super.onDraw(canvas)
//    }
//
//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        Log.i("RROREE", " parent  onLayout")
//
//        super.onLayout(changed, left, top, right, bottom)
//    }
//
//    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
//        Log.i("RROREE", " parent  drawChild")
//
//        return super.drawChild(canvas, child, drawingTime)
//    }
//
//    override fun draw(canvas: Canvas?) {
//        Log.i("RROREE", " parent  draw")
//        super.draw(canvas)
//    }

}