package com.szy.javapoemtest

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Choreographer
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_third.*
import kotlin.concurrent.thread

/**
 * Created by devov on 2021/3/15.
 */
class ThirdActivity: AppCompatActivity(),Choreographer.FrameCallback {
    var  i =0
    var flag = true
    val a = Any()
    val b = Any()

    val obj:Any
        get() = if(true)a else b


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        var fragment1:Fragment? = null
        var fragment2:Fragment? = null
        var fragment3:Fragment? = null
        var list  = ArrayList<Int>()

        var  i = 0
        Log.i("RROKK","--------------->${getSharedPreferences("java",0).getString("xiba","null")}")


        var v :View? = null
        v1.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }
        v2.setName("third v2")
        v3.setName("third v3")
        v2.postDelayed({
            val alertDialog = AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("配合相关部门监管要求，本APP音视频通话及直播过程全程录制存档，严禁色情、辱骂、暴恐、涉政等违规内容")
                    .setPositiveButton("确定") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }.create()
            alertDialog.show()

        },3000)

        v2.setOnClickListener {


//            flag = true
//            Choreographer.getInstance().postFrameCallback(this)
//            v2.post {
//                Log.i("RROREE","v2 post!")
//                flag = false
//            }
//           list.add(i++)
//            fragment2 = FirstFragment.getInstance("second")
//            supportFragmentManager.beginTransaction().add(R.id.container,fragment2!!,"FirstFragment").commit()
        }

        v3.setOnClickListener {
//            v?.run {
//                setBackgroundColor(Color.parseColor("#00ff00"))
//                layoutParams = FrameLayout.LayoutParams(500,300)
//            }
//
//            v3.invalidate()

            Glide.with(this)
                    .load("ssss")
                    .into(ImageView(this))
//            supportFragmentManager.beginTransaction().remove(fragment2!!).commit()
        }


    }

    override fun doFrame(frameTimeNanos: Long) {
        Log.i("RROREE", "i:${i++}")
//        v2.postInvalidateOnAnimation()
        if(flag) {
            Choreographer.getInstance().postFrameCallback(this)
        }
    }

    override fun onResume() {
        Log.i("RROREE","ThirdActivity  onResume")
        super.onResume()
        v3.postDelayed({
            Log.i("RRORRR","state:${lifecycle.currentState}")
        },300)
    }

    override fun onPause() {
        Log.i("RROREE","ThirdActivity  onPause")
        super.onPause()
        Log.i("RRORRR","state:${lifecycle.currentState}")
    }

    override fun onStop() {
        Log.i("RROREE","ThirdActivity  onStop")

        super.onStop()
    }

    override fun onDetachedFromWindow() {
        Log.i("RROREE","ThirdActivity  onDetachedFromWindow")

        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        Log.i("RROREE","ThirdActivity  onAttachedToWindow")

        super.onAttachedToWindow()
    }

    override fun onDestroy() {
        Log.i("RROREE","ThirdActivity  onDestroy")

        super.onDestroy()
    }
}