package com.szy.javapoemtest

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type

/**
 * Created by devov on 2021/3/24.
 */
class MyJsonDeserializer:JsonDeserializer<Any> {
    private var mGson = Gson()
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Any? {
        if(typeOfT != null) {
            Log.i("KKOKE","json:${json}")
            Log.i("KKOKE","type:${typeOfT}")
            if (typeOfT::class.java !is GenericArrayType && !json.isJsonArray){

                return mGson.fromJson(json, typeOfT)
            }
        }
        return null
    }
}