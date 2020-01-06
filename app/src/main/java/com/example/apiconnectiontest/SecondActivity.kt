package com.example.apiconnectiontest

import android.content.Intent
import android.os.Bundle
import com.example.apiconnectiontest.util.GlobalData
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        supportActionBar?.hide()


        button_parent.setOnClickListener {
            //1. 약관동의 2.자녀등록 3.프로필 4.학부모홈


            var myIntent :Intent

            if(GlobalData.loginUser?.terms_time == "null") {
                myIntent = Intent(this, TermsActivity::class.java)
                startActivity(myIntent)
            }
            else if(GlobalData.loginUser?.child?.name == "null"){
                myIntent = Intent(this, LoginParentInfoActivity::class.java)
                startActivity(myIntent)
            }
            else if(GlobalData.loginUser?.profile_image_url == "null"){
                myIntent = Intent(mContext, ParentProfileActivity::class.java)
                startActivity(myIntent)
            }
            else{
                myIntent = Intent(mContext, ParentHomeActivity::class.java)
                startActivity(myIntent)
            }
        }



    }


}