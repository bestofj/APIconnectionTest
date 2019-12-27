package com.example.apiconnectiontest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_parent.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ParentActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)
        supportActionBar?.hide()

        button_parent_arrow_left.setOnClickListener {
            val myIntent = Intent(this, SecondActivity::class.java)
            startActivity(myIntent)
        }

        //입력 전화번호

        //입력 인증번호



        //인증번호 발송
        auth_button.setOnClickListener {
            val parent_phone_num = parent_phone.getText().toString() //입력 전화번호
            ConnectServer.postRequestPhoneAuth(parent_phone_num)
            /*
            client.newCall(request).enqueue(object: Callback{
                override fun onFailure(call: Call, e: IOException) {
                    println("API connection: fail")
                    //Toast.makeText(mContext, "인증번호 발송에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("API connection: OK")
                    //Toast.makeText(mContext, "인증번호가 발송되었습니다.", Toast.LENGTH_SHORT).show()

                }

            })
            */
        }

        //인증번호와 대조
        login_button.setOnClickListener{

            val parent_phone_num = parent_phone.getText().toString() //입력 전화번호
            val parent_phone_auth_num = phone_auth_num.getText().toString() //입력 인증번호

            ConnectServer.postRequestLogin(parent_phone_num, parent_phone_auth_num, object:ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject?) {
                        try {
                            if (json!!.getInt("code") == 200) {
                                runOnUiThread {
                                    val myIntent = Intent(mContext , LoginParentInfoActivity::class.java)
                                    startActivity(myIntent)
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





    }
}
