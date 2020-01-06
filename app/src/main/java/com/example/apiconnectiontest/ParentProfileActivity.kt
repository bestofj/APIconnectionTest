package com.example.apiconnectiontest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apiconnectiontest.util.ConnectServer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_parent_profile.*
import org.json.JSONException
import org.json.JSONObject

class ParentProfileActivity : AppCompatActivity() {     // 프로필 사진, 이메일등록 미완
    val mContext: Context = this // 현재 창 선언.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_profile)

        supportActionBar?.hide() // 상단 바 감추기

        bt_complete.setOnClickListener(){
            ConnectServer.postRequestProfileLogin(     // EditText에서 받은 값 서버 api 호출.
                mContext,
                bt_name.text.toString(),
                object : ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {
                                runOnUiThread {
                                    Toast.makeText(
                                        mContext,
                                        "프로필 등록 완료",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
            var intent = Intent(mContext, ParentHomeActivity::class.java)
            startActivity(intent)
        }
    }
}