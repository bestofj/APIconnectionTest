package com.example.apiconnectiontest.datas

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.util.*

class School : Serializable {
    var id :Int = 0
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

    fun School() {}

    fun GetId(): Int {
        return id
    }

    fun SetId(id: Int) {
        this@School.id = id
    }

    fun GetName(): String? {
        return name
    }

    fun SetName(name: String) {
        this@School.name = name
    }

    fun GetNumber(): String? {
        return number
    }

    fun SetNumber(number: String) {
        this@School.number = number
    }

    fun GetCreated_at(): Calendar? {
        return created_at
    }

    fun SetCreated_at(created_at: Calendar) {
        this@School.created_at = created_at
    }

    fun GetUpdated_at(): Calendar? {
        return updated_at
    }

    fun SetUpdated_at(updated_at: Calendar) {
        this@School.updated_at = updated_at
    }

}