package com.szy.javapoemtest

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * Created by devov on 2021/4/22.
 */
class ViewPagerFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.frgament_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter= Adapter(requireContext())
        view.findViewById<MultiHeightViewPager>(R.id.mhvp).run {
            setData(adapter, adapter.getHeightList(),1)
            setCallback{
                val cl = view.findViewById<ConstraintLayout>(R.id.cl)
                ConstraintSet().apply {
                    clone(cl)
                    setHorizontalBias(R.id.v, it)
                    applyTo(cl)
                }
            }


        }

    }
}


class MultiHeightViewPager:ViewPager{
    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet):super(context, attrs)

    private lateinit var mViewHeightList:IntArray
    fun interface Callback{
        fun change(bias:Float)
    }
    private var callback:Callback? = null
    fun setCallback(c: Callback){
        callback = c

    }

    fun setData(adapter: PagerAdapter, height: IntArray,currentItem:Int = 0){
        setAdapter(adapter)
        mViewHeightList = height
        setCurrentItem(currentItem,false)
        layoutParams = layoutParams.apply {
            this.height = mViewHeightList[currentItem]
        }

    }

    init {
        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val count = mViewHeightList.size
                callback?.change((position.toFloat()+positionOffset)/(count - 1))


                Log.i("RRORE","position:${position}   offset:${positionOffset}   positionOffsetPixels:${positionOffsetPixels}")
                if (position == mViewHeightList.size - 1) {//用于defaultHeight初始化之前的拦截,即等到获取到第一张图片再做操作
                    return
                }
                val finalHeight = (mViewHeightList[position].toFloat()) * (1 - positionOffset) + (mViewHeightList[position + 1].toFloat()) * positionOffset

                //为ViewPager设置高度
                layoutParams = layoutParams.apply {
                    height = finalHeight.toInt()
                }


            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })
    }
}


class Adapter(private val context: Context):PagerAdapter(){

    private val rv1 by lazy{
        initRecyclerView(data1)
    }

    private val rv2 by lazy{
        initRecyclerView(data2)
    }
    private val rv3 by lazy{
        initRecyclerView(data3)
    }
    private val rv4 by lazy{
        initRecyclerView(data1)
    }

    private val rv5 by lazy{
        initRecyclerView(data2)
    }
    private val rv6 by lazy{
        initRecyclerView(data3)
    }
    private val rv7 by lazy{
        initRecyclerView(data1)
    }

    private val rv8 by lazy{
        initRecyclerView(data2)
    }
    private val rv9 by lazy{
        initRecyclerView(data3)
    }

    private val rvList by lazy{
        arrayOf(rv1, rv2,rv3,rv4, rv5,rv6,rv7, rv8,rv9)
    }

    private val rvHeightList by lazy{
        IntArray(count){
            val data:List<Int> = when(it){
                0,3,6-> data1
                1,4,7-> data2
                2,5,8-> data3
                else-> data1
            }
            300*(data.size/5 + if(data.size%5 ==0)0 else 1)
        }

    }
    fun getHeightList() = rvHeightList

    private val data1 = listOf(1, 2, 3, 4, 5)
    private val data2 = listOf(1, 2, 3,4,5,6,7,8,9,10)
    private val data3 = listOf(1, 2, 3,4,5,6,7,8,9,10)


    private fun initRecyclerView(data: List<Int>):RecyclerView{
        return RecyclerView(context).apply {
            layoutParams = ViewPager.LayoutParams().apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = 300*(data.size/5 + if(data.size%5 ==0)0 else 1)
            }
            layoutManager = GridLayoutManager(context, 5)
            adapter = ItemAdapter(data)
        }
    }
    override fun getCount() = rvList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = rvList[position]
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
       container.removeView(rvList[position])
    }

}

class ItemAdapter(private val mDataList: List<Int>):RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MyViewHolder(LayoutInflater.from(parent.context).inflate((R.layout.item_view), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindViewHolder(position)
    }

    override fun getItemCount() = mDataList.size

}

class MyViewHolder(view: View):RecyclerView.ViewHolder(view){

     fun bindViewHolder(index: Int){
        itemView.findViewById<TextView>(R.id.tv).text = "$index"
    }

}