package com.beanfactory.woorischool.datas

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.util.*

class School : Serializable {
    var id = 0
    var name: String? = null
    var number: String? = null
    var created_at: Calendar? = null
    var updated_at: Calendar? = null

    companion object {
        fun getSchoolFromJson(json: JSONObject): School {
            val school = School()
            try {
                school.id = json.getInt("id")
                school.name = json.getString("name")
                school.number = json.getString("number")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return school
        }
    }
}