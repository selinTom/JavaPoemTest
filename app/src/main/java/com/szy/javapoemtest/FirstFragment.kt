package com.szy.javapoemtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_second.*

/**
 * Created by devov on 2021/3/15.
 */
class FirstFragment :Fragment() {

    companion object{
        fun getInstance(str:String) = FirstFragment().apply {
            arguments = Bundle().apply {
                putString("item",str)

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.activity_second,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv.text = arguments?.getString("item")

    }
}