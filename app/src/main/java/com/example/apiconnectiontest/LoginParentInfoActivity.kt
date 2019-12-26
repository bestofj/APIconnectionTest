package com.example.apiconnectiontest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_loginparentinfo.*

class LoginParentInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginparentinfo)
        supportActionBar?.hide()


        button_addchild_arrow_left.setOnClickListener {
            val myIntent = Intent(this, ParentActivity::class.java)
            startActivity(myIntent)
        }

    }
}