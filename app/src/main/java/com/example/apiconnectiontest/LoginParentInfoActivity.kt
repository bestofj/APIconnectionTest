package com.example.apiconnectiontest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.beanfactory.woorischool.adapters.SchoolSpinnerAdapter
import com.beanfactory.woorischool.datas.School
import com.example.apiconnectiontest.util.ConnectServer
import com.example.apiconnectiontest.util.ContextUtils
import kotlinx.android.synthetic.main.activity_loginparentinfo.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*



class LoginParentInfoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginparentinfo)
        supportActionBar?.hide()
        button_addchild_arrow_left.setOnClickListener {
            val myIntent = Intent(this, ParentActivity::class.java)
            startActivity(myIntent)
        }


        var adapter: SchoolSpinnerAdapter
        val list = ArrayList<School>()



        fun getSchoolList() {
            ConnectServer.getRequestSchoolList(mContext, "", object : ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {
                                Log.d("log",json.toString())
                                val school = json.getJSONObject("data").getJSONArray("school")
                                list.clear()
                                val first = School()
                                first.id = -1
                                first.name = "선택해주세요"
                                list.add(first)
                                for (i in 0 until school.length()) {
                                    list.add(School.getSchoolFromJson(school.getJSONObject(i)))
                                }
                                runOnUiThread {
                                    adapter = SchoolSpinnerAdapter(mContext, list)
                                    spinner.setAdapter(adapter)
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

        }//get schoolList

        getSchoolList()





        complete.setOnClickListener(View.OnClickListener {
            val selected = spinner.getSelectedItem() as School
            if (selected.id == -1) {
                Toast.makeText(mContext, "학교를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            ConnectServer.postRequestRegistChild(mContext, name.getText().toString(), password.getText().toString(), selected.number,
                grade.getText().toString(), devision.getText().toString(), class_num.getText().toString(), object: ConnectServer.JsonResponseHandler{
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {
                                runOnUiThread {
                                    val intent =
                                        Intent(mContext, ParentActivity::class.java) //연습
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                val message = json.getString("message")
                                runOnUiThread {
                                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                })
        })


    }




}