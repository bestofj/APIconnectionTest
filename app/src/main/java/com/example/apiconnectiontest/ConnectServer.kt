package com.example.apiconnectiontest

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ConnectServer : AppCompatActivity(){
    interface JsonResponseHandler {
        fun onResponse(json: JSONObject?)
    }

    companion object {



    val BASE_URL = "http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/"


    fun postRequestPhoneAuth(parent_phone_num: String) {

        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("phone_num", parent_phone_num)
            .add("device_token", "1")
            .add("os", "iOS")
            .build()

        val request = Request.Builder()
            .url("${BASE_URL}phone_auth")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
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
                    //    handler.onResponse(json)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        })
    }


    fun postRequestLogin(phone_num: String, phone_auth_num: String, handler: JsonResponseHandler) {

        val client = OkHttpClient()

        //Request Body에 서버에 보낼 데이터 작성
        //Request Body에 서버에 보낼 데이터 작성
        val requestBody: RequestBody = FormBody.Builder()
            .add("phone_num", phone_num)
            .add("phone_auth_num", phone_auth_num)
            .add("type", "PARENT")
            .build()

        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        val request: Request = Request.Builder()
            .url("http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/phone_auth_num_check")
            .post(requestBody)
            .build()

        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("왜안되지?")
                Log.d("error", "Connect Server Error is $e")
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) { //                Log.d("aaaa", "Response Body is " + response.body().string());
                val body = response.body()!!.string()
                Log.d("log", "서버에서 응답한 Body:$body")
                try {
                    val json = JSONObject(body)
                    if (handler != null) handler.onResponse(json)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

}
}