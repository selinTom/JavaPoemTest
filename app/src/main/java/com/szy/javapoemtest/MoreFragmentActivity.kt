package com.szy.javapoemtest

import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.act_fragment.*

/**
 * Created by devov on 2021/4/9.
 */
class MoreFragmentActivity:AppCompatActivity() {

    val TAG = MoreFragmentActivity::class.java.simpleName+"KKK"

    val f1 by lazy{
        MyFragment.getInstance("F 1")
    }
    val f2 by lazy{
        MyFragment.getInstance("F 2")
    }
    val f3 by lazy{
        MyFragment.getInstance("F 3")
    }

    val viewmodel by viewModels<MyViewModel>()


    override fun onBackPressed() {
        super.onBackPressed()
        Log.i(TAG,"activity onBackPressed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_fragment)
        showTargetFragment(f1)

        tv1.setOnClickListener {
            postValue("tv1")
         showTargetFragment(f1)
        }
        tv2.setOnClickListener {
            postValue("tv2")
            showTargetFragment(f2)
        }
        tv3.setOnClickListener {
            postValue("tv3")
            showTargetFragment(f3)

        }

        tv1.postInvalidate()
        viewmodel.data.observe(this){
            Log.i("MyViewModel","activity received:$it")

        }

    }

    private fun postValue(tag:String){
        (0 .. 100).forEach {
            viewmodel.data.setValue("click $tag   index:$it")
        }

    }

    private fun showTargetFragment(target: Fragment){
        val fragmentList = mutableListOf(f1, f2, f3).apply {
            remove(target)
        }
        supportFragmentManager.beginTransaction().run {
            if (!target.isAdded) {
                Log.i("RRORE", target::class.java.simpleName)
                add(R.id.container, target, target::class.java.simpleName)
            }
            fragmentList.forEach {
                if(it.isAdded) {
                    setMaxLifecycle(it, Lifecycle.State.STARTED)
                    hide(it)
                }
            }
            setMaxLifecycle(target, Lifecycle.State.RESUMED)
            show(target)
            commitAllowingStateLoss()
        }
    }
}

class MyViewModel(app:Application):AndroidViewModel(app){


    val data = MutableLiveData<String>()


}