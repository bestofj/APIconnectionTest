package com.example.apiconnectiontest.util

import android.content.Context
import com.example.apiconnectiontest.datas.User

class ContextUtils {
    companion object {
        private var loginUser: User? = null
        private const val prefName = "Woori_Android"
        private const val FIRST_START_APP = "FIRST_START_APP"
        private const val USER_TOKEN = "USER_TOKEN"
        private const val LOGIN_TYPE = "LOGIN_TYPE"
        private const val USER_ID = "USER_ID"
        private const val USER_EMAIL = "USER_EMAIL"
        private const val USER_NAME = "USER_NAME"
        private const val USER_PHONE = "USER_PHONE"
        private const val USER_TICKET = "USER_TICKET"
        private const val USER_ATTENDANCE = "USER_ATTENDANCE"
        private const val USER_ATTENDANCE_COUNT = "USER_ATTENDANCE_COUNT"
        fun getLoginType(context: Context): String? {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(LOGIN_TYPE, "")
        }

        fun setLoginType(context: Context, cookie: String?) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(LOGIN_TYPE, cookie).apply()
        }

        fun getUserToken(context: Context): String? {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(USER_TOKEN, "")
        }

        fun setUserToken(context: Context, token: String?) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(USER_TOKEN, token).apply()
        }

        fun getUserId(context: Context): Int {
            val pref = context.getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE
            )
            return pref.getInt(USER_ID, 0)
        }

        fun setUserId(context: Context, id: Int) {
            val pref = context.getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE
            )
            pref.edit().putInt(USER_ID, id).apply()
        }

        fun setLoginUser(context: Context, loginUser: User) {
            val pref = context.getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE
            )
            pref.edit().putInt(USER_ID, loginUser.GetId()).apply()
            pref.edit().putString(USER_NAME, loginUser.GetName()).apply()
            pref.edit().putString(USER_PHONE, loginUser.GetPhone_num()).apply()
        }

        fun getUserData(context: Context): User? {
            val pref = context.getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE
            )
            if (pref.getInt(USER_ID, -1) != -1) {
                if (loginUser == null) {
                    loginUser = User()
                }
                loginUser!!.SetId(pref.getInt(USER_ID, -1))
                loginUser!!.SetName(pref.getString(USER_NAME, ""))
                loginUser!!.SetPhone_num(pref.getString(USER_PHONE, ""))
            } else {
                loginUser = null
            }
            return loginUser
        }

        //
        fun logout(context: Context) {
            loginUser = null
            val pref = context.getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE
            )
            pref.edit().putInt(USER_ID, -1).commit()
            pref.edit().putString(USER_NAME, "").commit()
            pref.edit().putString(USER_PHONE, "").commit()
            pref.edit().putString(USER_EMAIL, "").commit()
            pref.edit().putString(USER_TOKEN, "").commit()
            pref.edit().putString(LOGIN_TYPE, "").commit()
        }

        fun isUserAttendance(context: Context): Boolean {
            val pref = context.getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE
            )
            return pref.getBoolean(USER_ATTENDANCE, false)
        }

        fun setUserAttendance(
            context: Context,
            attendance: Boolean
        ) {
            val pref = context.getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE
            )
            pref.edit().putBoolean(USER_ATTENDANCE, attendance).commit()
        }

        fun getUserAttendanceCount(context: Context): Int {
            val pref = context.getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE
            )
            return pref.getInt(USER_ATTENDANCE_COUNT, 0)
        }

        fun setUserAttendanceCount(context: Context, count: Int) {
            val pref = context.getSharedPreferences(
                prefName,
                Context.MODE_PRIVATE
            )
            pref.edit().putInt(USER_ATTENDANCE_COUNT, count).commit()
        }
    }//companion object
}