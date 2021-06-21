package com.szy.javapoemtest

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by devov on 2021/3/23.
 */
@Parcelize
class CustomEmptyInfo:Parcelable {
    var id = 0
    var pic_url = ""
    var info =""
    var extra_info=""
    var btn_info = ""
    var link = ""
    var error = ""
    var list = ArrayList<Id>()
    var obj_id:Id? = null
    override fun toString(): String {
        return "\nid:$id\n " +
                "pic_url:$pic_url\n " +
                "info:$info\n " +
                "extra_info:$extra_info\n " +
                "btn_info:$btn_info\n " +
                "link:$link \n " +
                "error:$error \n " +
                "list:$list \n " +
                "obj_id:${obj_id}"
    }

}
@Parcelize
class Id:Parcelable{
    var id = 0

    override fun toString(): String {
        return "sub id:$id"
    }
}