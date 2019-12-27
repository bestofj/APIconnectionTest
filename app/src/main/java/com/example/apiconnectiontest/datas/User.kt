package com.example.apiconnectiontest.datas

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.util.*

class User : Serializable {
    var id = 0
    var type: String? = null
    var name: String? = null
    var phone_num: String? = null
    var phone_auth_num: String? = null
    var created_at: Calendar? = null
    var updated_at: Calendar? = null
    var attendance: String? = null
    var child: User? = null
    var studentInfo: StudentInfo? = null
    var parent: MutableList<User> =
        ArrayList()

    companion object {
        fun getUserFromJson(json: JSONObject): User {
            val user =
                User()
            try {
                user.id = json.getInt("id")
                user.type = json.getString("type")
                user.name = json.getString("name")
                user.phone_num = json.getString("phone_num")
                if (! json.isNull("child") && json.getString("child") != "") {
                    user.child = getUserFromJson(
                        json.getJSONObject("child")
                    )
                }
                if (! json.isNull("student_info")) {
                    user.studentInfo =
                        StudentInfo.getInfoFromJson(json.getJSONObject("student_info"))
                }
                user.parent.clear()
                if (! json.isNull("parents")) {
                    val parents = json.getJSONArray("parents")
                    for (i in 0 until parents.length()) {
                        user.parent.add(
                            getUserFromJson(
                                parents.getJSONObject(i)
                            )
                        )
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return user
        }
    }

    fun User() {}

    fun GetId(): Int {
        return id
    }

    fun SetId(id: Int) {
        this.id = id
    }

    fun GetType(): String? {
        return type
    }

    fun SetType(type: String?) {
        this.type = type
    }

    fun GetName(): String? {
        return name
    }

    fun SetName(name: String?) {
        this.name = name
    }

    fun GetPhone_num(): String? {
        return phone_num
    }

    fun SetPhone_num(phone_num: String?) {
        this.phone_num = phone_num
    }

    fun GetPhone_auth_num(): String? {
        return phone_auth_num
    }

    fun SetPhone_auth_num(phone_auth_num: String?) {
        this.phone_auth_num = phone_auth_num
    }

    fun GetCreated_at(): Calendar? {
        return created_at
    }

    fun SetCreated_at(created_at: Calendar?) {
        this.created_at = created_at
    }

    fun GetUpdated_at(): Calendar? {
        return updated_at
    }

    fun SetUpdated_at(updated_at: Calendar?) {
        this.updated_at = updated_at
    }

    fun GetAttendance(): String? {
        return attendance
    }

    fun SetAttendance(attendance: String?) {
        this.attendance = attendance
    }

    fun GetChild(): com.example.apiconnectiontest.datas.User? {
        return child
    }

    fun SetChild(child: com.example.apiconnectiontest.datas.User?) {
        this.child = child
    }

    fun GetStudentInfo(): StudentInfo? {
        return studentInfo
    }

    fun SetStudentInfo(studentInfo: StudentInfo?) {
        this.studentInfo = studentInfo
    }

    fun GetParent(): List<com.example.apiconnectiontest.datas.User?>? {
        return parent
    }

//    fun SetParent(parent: List<com.example.apiconnectiontest.datas.User?>?) {
//        this.parent = parent
//    }
}