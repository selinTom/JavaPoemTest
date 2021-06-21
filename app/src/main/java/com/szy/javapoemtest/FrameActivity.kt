package com.szy.javapoemtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.framelayout.*

/**
 * Created by devov on 2021/4/22.
 */
class FrameActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Test().sequentialDigits(530,99000)

        setContentView(R.layout.framelayout)
        main.postDelayed({
            supportFragmentManager.commit {
                add(R.id.main,ViewPagerFragment(),"ViewPagerFragment")
            }
        },3000)

    }

    override fun onResume() {
        super.onResume()
        Log.i("RRORE","onResume")
    }
}