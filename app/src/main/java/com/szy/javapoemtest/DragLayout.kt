package com.szy.javapoemtest

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper

/**
 * Created by devov on 2021/2/24.
 */
class DragLayout : FrameLayout {

    interface ActionListener{
        fun onDragInActionView(view: View,actionView:View)
        fun onDragOutActionView(view: View,actionView: View)
        fun startAction(view:View,actionView: View,viewInActionView:Boolean,left:Int,right:Int)
    }

    companion object{
        const val LONG_CLICK_TIME = 300L
        const val THRESHOLD = 20
    }
    constructor(context: Context) : super(context) {
        initDrag()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initDrag()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initDrag()
    }

    private var mNeedLongClick = false
    private var mDragHelper: ViewDragHelper? = null
    private var mIsDragging = false
    private var mStartX = 0
    private var mStartY = 0
    private val mTargetViewList = ArrayList<View>()
    private var mActionView:View?=null

    private var mConstraintRect :Rect? = null
    private var mActionListener:ActionListener? = null
    private var mActionRect:Rect? = null
    private var mDragIn = false
    private var mDragViewX = -1
    private var mDragViewY = -1


    fun setConstraintRect(rect: Rect){
        mConstraintRect = rect
    }

    private val mVibratorRunnable = Runnable {
        startDrag()
    }

    fun setUseLongClickMode(flag:Boolean){
        mNeedLongClick = flag
    }

    private fun startDrag(){
        mIsDragging = true
        if(mNeedLongClick){
            MediaPlayerUtils.showVibrator(context,20)
        }
        mActionView?.visibility = View.VISIBLE
    }

    fun setActionListener(listener: ActionListener){
        mActionListener = listener
    }

    fun addDragView(v:View){
        if(!mTargetViewList.contains(v)){
            mTargetViewList.add(v)
        }
    }

    fun removeDragView(v:View){
        mTargetViewList.remove(v)

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if(childCount>0){
            mActionView = getChildAt(0)
            mActionView?.visibility = View.GONE
        }
    }


    private fun initDrag() {

        val callback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                if(mActionView!=null) {
                    mActionListener?.startAction(releasedChild, mActionView!!,mDragIn,mDragViewX,mDragViewY)
                }
                reset()
            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            }

            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                if(!mTargetViewList.contains(child)){
                    return false
                }
                mStartX = child.left
                mStartY = child.top
                if(mNeedLongClick){
                    postDelayed(mVibratorRunnable, LONG_CLICK_TIME)
                }
                return true
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                if(mNeedLongClick){
                    if(!mIsDragging){
                        if(Math.abs(left - mStartX)> THRESHOLD){
                            removeCallbacks(mVibratorRunnable)
                            mDragHelper?.cancel()
                        }
                        return mStartX
                    }

                }else{
                    if(!mIsDragging && Math.abs(left - mStartX)>3){
                        startDrag()
                    }
                }
                initActionRect()
                var result = Math.min(left, width-paddingRight - child.width -(mConstraintRect?.right?:0) )
                result = Math.max(paddingLeft+(mConstraintRect?.left?:0),result)
                mDragViewX = result
                checkTargetViewPosition(child)
                return result
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {

                if(mNeedLongClick){
                    if(!mIsDragging){
                        if(Math.abs(top - mStartY)> THRESHOLD){
                            removeCallbacks(mVibratorRunnable)
                            mDragHelper?.cancel()
                        }
                        return mStartY
                    }

                }else{
                    if(!mIsDragging && Math.abs(top - mStartY)>3 ){
                        startDrag()
                    }
                }
                initActionRect()
                var result = Math.min(top,height - paddingBottom - child.height - (mConstraintRect?.bottom?:0))
                result = Math.max(paddingTop+(mConstraintRect?.top?:0),result)
                mDragViewY = result
                checkTargetViewPosition(child)
                return result
            }
        }
        mDragHelper = ViewDragHelper.create(this, callback)
    }
    
    private fun reset(){
        mIsDragging = false
        if(mNeedLongClick){
            removeCallbacks(mVibratorRunnable)
        }
        mActionRect = null
        mActionView?.visibility = View.INVISIBLE     
        mDragIn = false
        mDragViewX = -1
        mDragViewY = -1
    }
    
    private fun initActionRect(){
        if(mActionRect==null && mActionView?.width!=0 && mActionView?.height!=0){
            mActionRect = Rect(mActionView?.left?:0,mActionView?.top?:0,mActionView?.right?:0,mActionView?.bottom?:0)
        }
    }
    
    private fun checkTargetViewPosition(view: View){
        if(mDragViewX!=-1 && mDragViewX!=-1 && mActionView!=null){
            mActionRect?.let {
                if(it.contains(mDragViewX,mDragViewY)){
                    if(!mDragIn){
                        mActionListener?.onDragInActionView(view,mActionView!!)
                        mDragIn = true
                    }
                }else{
                    if(mDragIn){
                        mActionListener?.onDragOutActionView(view,mActionView!!)
                        mDragIn = false
                    }
                }
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mDragHelper!!.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result =  !mIsDragging && super.onTouchEvent(event)
        mDragHelper!!.processTouchEvent(event)
        return result
    }
}