package com.example.apiconnectiontest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_parent.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ParentActivity : AppCompatActivity() {
    val mContext:Context = this
    val BASE_URL = "http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)
        supportActionBar?.hide()

        button_parent_arrow_left.setOnClickListener {
            val myIntent = Intent(this, SecondActivity::class.java)
            startActivity(myIntent)
        }

        //////ConnectServer 에 때려박기

        auth_button.setOnClickListener {

            val parent_phone_num = parent_phone.getText().toString()

            /////connection
            val client = OkHttpClient()

            val requestBody = FormBody.Builder()
                .add("phone_num", parent_phone_num)
                .add("device_token", "1")
                .add("os", "iOS")
                .build()

            val request = Request.Builder()
                .url("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/phone_auth")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("왜안되지?")

                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    //                Log.d("aaaa", "Response Body is " + response.body().string());
                    val body = response.body()!!.string()
                    Log.d("log", "서버에서 응답한 Body:$body")
                    try {
                        val json = JSONObject(body)
                        //if (handler != null)
                        //handler.onResponse(json)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            })

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

        login_button.setOnClickListener{
            ConnectServer.postRequestLogin("01036153132", "3242", object:ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject?) {
                        try {
                            if (json!!.getInt("code") == 200) {
                                runOnUiThread {
                                    val myIntent = Intent( mContext , LoginParentInfoActivity::class.java)
                                    startActivity(myIntent)
                                }
                                println("got 200 code")
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
