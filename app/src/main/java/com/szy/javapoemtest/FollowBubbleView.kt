package com.szy.javapoemtest

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.util.Pools
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by devov on 2021/3/8.
 */
class FollowBubbleView :View {

    private val mMipmapList = intArrayOf(
            R.mipmap.icon_live_good,
            R.mipmap.icon_live_a,
            R.mipmap.icon_live_hot,
            R.mipmap.icon_live_b,
            R.mipmap.icon_live_sixsixsix,
            R.mipmap.icon_live_c,
            R.mipmap.icon_live_sofa,
            R.mipmap.icon_live_book)
    private val mBitmapList = ArrayList<Bitmap>()
    private val mBubbleList = ArrayList<BubbleItem>()
    private val mBubblePool by lazy{
        Pools.SimplePool<BubbleItem>(10)
    }
    private val mRandom by lazy{
        Random()
    }
    private var isDetach = false



    constructor(context: Context):super(context)
    constructor(context: Context, attr: AttributeSet):super(context,attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr:Int):super(context,attr,defStyleAttr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr:Int, defStyleRes:Int):super(context,attr,defStyleAttr,defStyleRes)


    private val destroyItemCallback = { item:BubbleItem->
        mBubbleList.remove(item)
        mBubblePool.release(item)
        Unit
    }
    private val dp1 by lazy{
        3f
    }

    init {
        mMipmapList.forEach {
            mBitmapList.add(zoomImg(BitmapFactory.decodeResource(resources,it),80*dp1.toInt(),80*dp1.toInt()))
        }
    }


    private fun addBubble(x:Float,y:Float){
        val item = getBubbleItem()
        item.initData(500,5f,20f,this,getBitmap(),x,y,destroyItemCallback)
        mBubbleList.add(item)
    }

    private fun  zoomImg( bm:Bitmap, newWidth:Int,newHeight:Int):Bitmap {
        // 获得图片的宽高
        val width = bm.width
        val height = bm.height
        // 计算缩放比例
        val scaleWidth = ( newWidth.toFloat()) / width
        val scaleHeight =  (newHeight.toFloat()) / height
        // 取得想要缩放的matrix参数
        val matrix =  Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_UP){
            addBubble(event.x,event.y)
        }
        return super.onTouchEvent(event)
    }

    private fun getBubbleItem():BubbleItem{
        var item = mBubblePool.acquire()
        if(item == null){
            item = BubbleItem()
        }
        return item
    }

    private fun getBitmap(): Bitmap {
        return mBitmapList[mRandom.nextInt(mBitmapList.size)]
    }

    override fun onDraw(canvas: Canvas?) {
        mBubbleList.forEach{
            it.draw(canvas)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if(isDetach){
            isDetach = false
            mMipmapList.forEach {
                mBitmapList.add(BitmapFactory.decodeResource(resources,it))
            }
        }
    }


    override fun onDetachedFromWindow() {
        isDetach = true
        mBubbleList.forEach {
            it.destroy()
        }
        mBubbleList.clear()
        var item = mBubblePool.acquire()
        while(item!=null){
            item.destroy()
            item = mBubblePool.acquire()
        }
        mBitmapList.forEach {
            it.recycle()
        }
        mBitmapList.clear()

        super.onDetachedFromWindow()
    }

}

class BubbleItem{
    private val matrix: Matrix = Matrix()
    private val paint = Paint()
    private val animator = ValueAnimator.ofFloat(0f,1f)

    private val animatorUpdateListener by lazy{
        ValueAnimator.AnimatorUpdateListener {
            animationUpdate(it.animatedValue as Float)
        }
    }

    private lateinit var bitmap: Bitmap

    private var startX = -1f
    private var startY = 0f
    private var width = 0
    private var height = 0
    private var destroyItem:((BubbleItem)->Unit)? = null
    private var canDisplay = false
    private var maxDegrees = 0f
    private var shakeSpeed = 0f
    private var targetView:View? = null
    companion object{
        const val CHANGE_POINT = 0.7f
    }

    fun initData(animationDuration:Long, maxDegrees:Float, shakeSpeed:Float, targetView:View, bitmap: Bitmap, startX:Float, startY:Float, destroyItem:(BubbleItem)->Unit){
        canDisplay = false
        animator.duration = animationDuration
        this.bitmap = bitmap
        width = bitmap.width
        height = bitmap.height
        this.startX = startX
        this.startY = startY
        this.destroyItem = destroyItem
        this.maxDegrees = maxDegrees
        this.shakeSpeed = shakeSpeed
        this.targetView = targetView


        animator.removeAllUpdateListeners()
        animator.addUpdateListener(animatorUpdateListener)
        targetView.post{
            animator.start()
        }

    }

    private fun animationUpdate(value: Float){
        canDisplay = true
        val scale = getScale(value)
        val centerX = startX - width/2
        val centerY = startY - height

        matrix.setScale(scale,scale,width/2f,height/2f)
        matrix.postTranslate(centerX,centerY)
        val degrees = if(value> CHANGE_POINT){
            0f
        }else{
            if(((value*shakeSpeed)%(4*maxDegrees))<maxDegrees*2){
                (value*shakeSpeed)%(maxDegrees*2) -maxDegrees
            }else{
                maxDegrees - (value*shakeSpeed)%(maxDegrees*2)
            }
        }
        matrix.postRotate(degrees,startX,centerY+height/2)
        paint.alpha = (getAlpha(value)*255).toInt()
        targetView?.postInvalidate()
        if(value == 1f){
            completed()
        }
    }


    private fun completed(){
        animator.removeAllUpdateListeners()
        destroyItem?.invoke(this)
        targetView = null
    }

    fun destroy(){
        animator.removeAllUpdateListeners()
        targetView = null
    }

    fun draw(canvas: Canvas?){
        if(canDisplay){
            canvas?.drawBitmap(bitmap,matrix,paint)
        }
    }
    private fun getScale(value:Float):Float{
        val normal = 1f
        if(value<= CHANGE_POINT){
            return normal
        }
        return normal+(value - CHANGE_POINT)/(1f - CHANGE_POINT)*normal
    }

    private fun getAlpha(value:Float):Float{
        val normal = 1f
        if(value< CHANGE_POINT){
            return normal
        }
        return normal-(value - CHANGE_POINT)/((1f- CHANGE_POINT))
    }
}