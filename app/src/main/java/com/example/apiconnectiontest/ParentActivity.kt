package com.example.apiconnectiontest

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.apiconnectiontest.datas.User
import com.example.apiconnectiontest.util.ConnectServer
import com.example.apiconnectiontest.util.ContextUtils
import com.google.firebase.iid.FirebaseInstanceId

import kotlinx.android.synthetic.main.activity_parent.*
import org.json.JSONException
import org.json.JSONObject

class ParentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)
        supportActionBar?.hide()

        button_parent_arrow_left.setOnClickListener {
            val myIntent = Intent(this, SecondActivity::class.java)
            startActivity(myIntent)
        }

        //인증번호 발송
        auth_button.setOnClickListener {

            val parent_phone_num = parent_phone.text.toString()

            //firebase device token check
            val device_token: String? = FirebaseInstanceId.getInstance().token
            Log.d("log", FirebaseInstanceId.getInstance().token)


            if (TextUtils.isEmpty(device_token)) {
                Log.d("token", "token is empty")
                //val intent = Intent(mContext, FCMIDService::class.java)
                //startService(intent)
            }
            ConnectServer.postRequestPhoneAuth(mContext, parent_phone_num, device_token, object : ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {
                                runOnUiThread {
                                    Toast.makeText(mContext, "인증번호가 발송되었습니다.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                val message = json.getString("message")
                                runOnUiThread {
                                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                })
        }


        //인증번호와 대조
        login_button.setOnClickListener{

            val parent_phone_num = parent_phone.text.toString() //입력 전화번호
            val parent_phone_auth_num = phone_auth_num.text.toString() //입력 인증번호

            ContextUtils.setLoginType(mContext, "parents")//type지정: parent
            ConnectServer.postRequestLogin(mContext, parent_phone_num, parent_phone_auth_num,"PARENTS", object: ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {

                                val token = json.getJSONObject("data").getString("token")
                                ContextUtils.setUserToken(mContext, token)

                                val user: User = User.getUserFromJson(json.getJSONObject("data").getJSONObject("user"))
                                ContextUtils.setLoginUser(mContext, user)

                                //GlobalData.loginUser = user 이게뭐노?
                                runOnUiThread {
                                    var intent: Intent? = null
                                    if (user.child == null) {////다시바꿔주기
                                        intent = Intent(mContext, SecondActivity::class.java)//연습
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        intent = Intent(mContext, LoginParentInfoActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                            } else {
                                val message = json.getString("message")
                                runOnUiThread {
                                    Toast.makeText(
                                        mContext,
                                        message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                })
        }


    }
}
