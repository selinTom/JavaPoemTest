package com.szy.javapoemtest

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.REVERSE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.android.synthetic.main.item_fragment.*

/**
 * Created by devov on 2021/4/9.
 */
class MyFragment: Fragment() {
    private var name = ""
    private var text:String by Delegate()

    val viewmodel by activityViewModels<MyViewModel>()


    fun interface Action{
        fun run(i:Int,j:Int)
    }

    fun runAction(action:Action){
        action.run(1,2)
    }


    companion object {
        fun getInstance(name:String) = MyFragment().apply {
            arguments = Bundle().apply {
                putString("name",name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("name","")?:""
        Log.i("DELEGATEEE","text:${text}")
        text = "Rocket"
        Log.i("DELEGATEEE","text:${text}")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.item_fragment,container,false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(object :LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.i("RRORE","LifecycleEventObserver:${event}")
            }
        })

        printLog("onViewCreated")
        tv.text = name

        viewmodel.data.observe(viewLifecycleOwner){
            Log.i("MyViewModel","${name} received:$it")
        }
        view.postDelayed({
            val myHeight = tv.layoutParams.height
            ValueAnimator.ofInt(0,100).apply {
                duration = 3000
                addUpdateListener {
                    val value = it.animatedValue as Int
                    tv.layoutParams = tv.layoutParams.apply {
                        height = myHeight + value*10
                    }

                }
                repeatMode = REVERSE
                start()
            }

        },1000)
    }

    override fun onStart() {
        printLog("onStart")
        super.onStart()
    }

    override fun onResume() {
        printLog("onResume")
        super.onResume()
    }

    override fun onPause() {
        printLog("onPause")
        super.onPause()
    }

    override fun onStop() {
        printLog("onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        printLog("onDestroyView")
        super.onDestroyView()

    }

    override fun onDestroy() {
        printLog("onDestroy")
        super.onDestroy()

    }

    override fun onDetach() {
        printLog("onDetach")
        super.onDetach()

    }

    private fun printLog(log:String){
        Log.i("RRORE","${name}------------>${log}")
    }
}