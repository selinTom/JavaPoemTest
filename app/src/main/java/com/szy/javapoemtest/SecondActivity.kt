package com.szy.javapoemtest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.util.SparseArray
import android.view.Choreographer
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Exception
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by devov on 2021/3/5.
 */

interface I{

}
class SecondActivity: AppCompatActivity(),Choreographer.FrameCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.i("RROREE","SecondActivity  onCreate")
        var i = 0
        thread {
            while (true){
                synchronized(this){
                    System.out.println("thread 1----->${i}")
                }
                Thread.yield()

            }
        }

        thread {
            while (true){
                synchronized(this) {
                    System.out.println("thread 2 ---------------->${i}")
                }
                Thread.yield()
            }
        }

        thread {
            var j = 0
            while (true){
                synchronized(this) {
                    j++
                    i = j
                    System.out.println("thread 3 ------------------------------------->${i}")
                }
                Thread.yield()
            }
        }




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Toast.makeText(applicationContext.applicationContext,"hahhaha",Toast.LENGTH_LONG).show()


//        Choreographer.getInstance().postFrameCallback(this)

        v2.setName("second v2")
        v2.post {  }
        v3.setName("second v3")
        v3.setOnClickListener {
            supportFragmentManager.commit {
                supportFragmentManager.findFragmentByTag(MyFragment::class.java.simpleName)?.let {
                    remove(it)
                }
            }

        }

        v2.setOnClickListener {
            supportFragmentManager.commit {
                add(R.id.frame,MyFragment.getInstance("111111"),MyFragment::class.java.simpleName)
            }

        }

        tv.setOnClickListener {
//            main.setBackgroundColor(Color.parseColor("#40000000"))
//            main.offsetTopAndBottom(300)
            startActivity(Intent(this,ThirdActivity::class.java))

        }



    }

    private suspend fun getData():String{
        return withContext(Dispatchers.IO) {
                Log.i("RROREE", "currentThread:${Thread.currentThread()}")
                resources.assets.open("data.txt").use { input ->
                    val byte = ByteArray(1024)
                    val outputStream = MyOutputStream()
                    outputStream.use { output ->
                        while (true) {
                            val length = input.read(byte)
                            if (length == -1) {
                                break
                            }
                            output.write(byte, 0, length)
                        }
                        String(output.toByteArray())
                    }
                }
        }
    }

    class MyOutputStream: ByteArrayOutputStream() {
        override fun close() {
            Log.i("RROREE","MyOutputStream  close")
            super.close()
        }

    }

    interface CallBack{
        fun invoke()
    }

    private fun invokeCallBack(callback:CallBack){
        callback.invoke()
    }

//    override fun onResume() {
//        Log.i("RROREE","SecondActivity  onResume")
//        super.onResume()
//    }
//
//    override fun onPause() {
//        Log.i("RROREE","SecondActivity  onPause")
//        super.onPause()
//    }
//
//    override fun onStop() {
//        Log.i("RROREE","SecondActivity  onStop")
//
//        super.onStop()
//    }
//
//    override fun onDetachedFromWindow() {
//        Log.i("RROREE","SecondActivity  onDetachedFromWindow")
//
//        super.onDetachedFromWindow()
//    }
//
//    override fun onAttachedToWindow() {
//        Log.i("RROREE","SecondActivity  onAttachedToWindow")
//
//        super.onAttachedToWindow()
//    }
//
//    override fun onDestroy() {
//        Log.i("RROREE","SecondActivity  onDestroy")
//
//        super.onDestroy()
//    }

    override fun doFrame(frameTimeNanos: Long) {
        Log.i("RROREE","new Frame init time:${System.currentTimeMillis()}      frameTimeNanos:${frameTimeNanos} -----------------------------------------------------------------------")
        Choreographer.getInstance().postFrameCallback(this)
    }
}