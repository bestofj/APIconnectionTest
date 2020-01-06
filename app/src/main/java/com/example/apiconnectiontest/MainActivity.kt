package com.example.apiconnectiontest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apiconnectiontest.datas.User
import com.example.apiconnectiontest.util.ConnectServer
import com.example.apiconnectiontest.util.ContextUtils
import com.example.apiconnectiontest.util.GlobalData
import com.google.firebase.iid.FirebaseInstanceId
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val myDateFormat = SimpleDateFormat("yyyy-MM-dd")
        getParentInfo(myDateFormat.format(Calendar.getInstance().time))

        Handler().postDelayed({ startActivity(Intent(this@MainActivity, SecondActivity::class.java)) }, 1200)

    }

    private fun getParentInfo(date: String?) {
        Log.d("date", date)
        val device_token = FirebaseInstanceId.getInstance().token
        if (TextUtils.isEmpty(device_token)) {
            Log.d("token", "token is empty")

            //val intent = Intent(mContext, FCMIDService::class.java)
            //startService(intent)
        }
        ConnectServer.getRequestParentInfo(mContext, date, device_token, object : ConnectServer.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    try {
                        if (json.getInt("code") == 200) {
                            val user: User = User.getUserFromJson(json.getJSONObject("data").getJSONObject("user"))
                            //ContextUtils.setLoginUser(mContext, user)
                            GlobalData.loginUser = user

                            runOnUiThread {
                                    val message = json.getString("message")
                                    runOnUiThread {
                                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        runOnUiThread {
                        }
                    }
                }
            })
    }
}
