package com.szy.javapoemtest

import kotlin.reflect.KProperty

/**
 * Created by devov on 2021/4/16.
 */
class Delegate {
    private var temp = ""

    operator fun getValue(thisRef: Any?, property: KProperty<*>):String{
        return "${thisRef}  key:${property.name}   value:${temp}"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        temp = "|$value|"
    }
}