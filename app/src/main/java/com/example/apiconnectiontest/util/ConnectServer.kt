package com.example.apiconnectiontest.util

import android.content.Context
import android.util.Log
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException


class ConnectServer{

    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }

    companion object {
        //API기본주소
        val BASE_URL = "http://ec2-52-78-148-252.ap-northeast-2.compute.amazonaws.com/"


        fun putRequestUserInfo(context:Context, name: String?, image: File?, handler: JsonResponseHandler) {
//                if (!checkIntenetSetting(context)) {
//                    return;
//                }
            val client = OkHttpClient();

            val formBody: MultipartBody.Builder = MultipartBody.Builder()
            formBody.setType(MultipartBody.FORM)
            formBody.addFormDataPart("name", name);
            if (image != null) {
                formBody.addFormDataPart("image", image.getName(), RequestBody.create(MediaType.parse("image/jpeg"), image))
            }

            val requestBody: RequestBody = formBody.build()

            val request: Request = Request.Builder()
                .header("X-Http-Token", ContextUtils.getUserToken(context))
                .url(BASE_URL + "user_info")
                .put(requestBody)
                .build();

            //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("error", "Connect Server Error is " + e.toString());
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    //                Log.d("aaaa", "Response Body is " + response.body().string());
                    val body: String? = response.body()?.string()
                    Log.d("log", "서버에서 응답한 Body:" + body)
                    try {
                        val json: JSONObject = JSONObject(body);
                        if (handler != null)
                            handler.onResponse(json)
                    } catch (e: JSONException) {
                        e.printStackTrace();
                    }

                }
            })
        }





        fun getRequestParentInfo(context: Context?, date: String?, token: String?, handler: JsonResponseHandler?) {
//            if (! ConnectServer.checkIntenetSetting(context)) {
//                return
//            }
            val client = OkHttpClient()
            val urlBuilder =
                HttpUrl.parse(BASE_URL + "me_info") !!.newBuilder()
            if (date != "") {
                urlBuilder.addEncodedQueryParameter("date", date)
            }
            urlBuilder.addEncodedQueryParameter("device_token", token)
            urlBuilder.addEncodedQueryParameter("os", "Android")
            val requestUrl = urlBuilder.build().toString()
            val request = Request.Builder()
                .header("X-Http-Token", ContextUtils.getUserToken(context !!))
                .url(requestUrl)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("error", "Connect Server Error is $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body() !!.string()
                    Log.d("log", "서버에서 응답한 Body:$body")
                    try {
                        val json = JSONObject(body)
                        handler?.onResponse(json)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
        }
        fun postRequestPhoneAuth(context: Context, parent_phone_num: String, device_token:String?, handler:JsonResponseHandler) {
            val client = OkHttpClient()

            val requestBody = FormBody.Builder()
                .add("phone_num", parent_phone_num)
                .add("device_token", device_token)
                .add("os", "iOS")
                .build()

            val request = Request.Builder()
                .header("X-Http-Token", ContextUtils.getUserToken(context))
                .url("${BASE_URL}phone_auth")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("인증번호발송 실패")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    //                Log.d("aaaa", "Response Body is " + response.body().string());
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



    fun postRequestLogin(context :Context, phone_num:String, phone_auth_num :String, type:String, handler:JsonResponseHandler) {

        val client = OkHttpClient()

        //Request Body에 서버에 보낼 데이터 작성
        //Request Body에 서버에 보낼 데이터 작성
        val requestBody = FormBody.Builder()
            .add("phone_num", phone_num)
            .add("phone_auth_num", phone_auth_num)
            .add("type", type)
            .build()

        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        val request = Request.Builder()
            .url("${BASE_URL}auth")
            .post(requestBody)
            .build()

        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("error", "Connect Server Error is $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //Log.d("aaaa", "Response Body is " + response.body().string());
                val body = response.body()?.string()
                Log.d("log", "서버에서 응답한 Body:$body")
                try {
                    val json = JSONObject(body)
                    if (handler != null) {
                        handler.onResponse(json)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }




        fun postRequestRegistChild(context: Context?, name: String?, password: String?, school_number: String?, grade: String?, class_number: String?,
                                   number: String?, handler: JsonResponseHandler?) {

            val client = OkHttpClient()
            //Request Body에 서버에 보낼 데이터 작성
            val requestBody: RequestBody = FormBody.Builder()
                .add("name", name)
                .add("password", password)
                .add("school_number", school_number)
                .add("grade", grade)
                .add("class_number", class_number)
                .add("number", number)
                .build()
            //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
            val request = Request.Builder()
                //.header("X-Http-Token", ContextUtils.getUserToken(context))
                .url(BASE_URL + "parent_student")
                .post(requestBody)
                .build()
            //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("error", "Connect Server Error is $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response
                ) { //                Log.d("aaaa", "Response Body is " + response.body().string());
                    val body = response.body()!!.string()
                    Log.d("log", "서버에서 응답한 Body:$body")
                    try {
                        val json = JSONObject(body)
                        handler?.onResponse(json)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
        }

        fun getRequestSchoolList(context: Context, name: String?, handler: JsonResponseHandler?) {
            val client = OkHttpClient()
            val urlBuilder =
                HttpUrl.parse(BASE_URL + "school")!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("name", name)
            val requestUrl = urlBuilder
                .build().toString()
            val request =
                Request.Builder()
                    .header("X-Http-Token" , ContextUtils.getUserToken(context))
                    .url(requestUrl)
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("error", "Connect Server Error is $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body()!!.string()
                    Log.d("log", "서버에서 응답한 Body:$body")
                    try {
                        val json = JSONObject(body)
                        handler?.onResponse(json)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
        }

        fun userService(context: Context, terms: Int, privacy: Int, notify_msg:Int, handler: JsonResponseHandler){
            val client = OkHttpClient()

            val requestBody: RequestBody = FormBody.Builder()
                .add("terms", terms.toString())
                .add("privacy", privacy.toString())
                .add("notify_msg", notify_msg.toString())
                .build()
            //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
            val request = Request.Builder()
                .header("X-Http-Token", ContextUtils.getUserToken(context))
                .url(BASE_URL + "user_service")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("error", "Connect Server Error is $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    //                Log.d("aaaa", "Response Body is " + response.body().string());
                    val body = response.body()?.string()
                    Log.d("log", "서버에서 응답한 Body:$body")
                    try {
                        val json = JSONObject(body)
                        if (handler != null) {
                            handler.onResponse(json)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
        }


    }//compaion object
}