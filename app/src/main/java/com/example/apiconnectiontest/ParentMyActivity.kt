package com.example.apiconnectiontest

import android.content.Intent
import android.os.Bundle
import com.example.apiconnectiontest.util.GlobalData
import kotlinx.android.synthetic.main.activity_loginparentinfo.*
import kotlinx.android.synthetic.main.activity_parent_home.*
import kotlinx.android.synthetic.main.activity_parent_my.*

class ParentMyActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_my)
        supportActionBar?.hide()

        editPicBTN.setOnClickListener {
            var intent = Intent(mContext, ParentProfilePicActivity::class.java)
            startActivity(intent)
        }

    }
}