package com.example.apiconnectiontest.datas

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.util.*

class StudentInfo : Serializable {
    var id = 0
    var student_id = 0
    var years = 0
    var school_id = 0
    var grade = 0
    var class_number = 0
    var number = 0
    var created_at: Calendar? = null
    var updated_at: Calendar? = null

    companion object {
        fun getInfoFromJson(json: JSONObject): StudentInfo {
            val info = StudentInfo()
            try {
                info.id = json.getInt("id")
                info.years = json.getInt("years")
                info.grade = json.getInt("grade")
                info.class_number = json.getInt("class_number")
                info.number = json.getInt("number")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return info
        }
    }
}