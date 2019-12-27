package com.example.apiconnectiontest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        supportActionBar?.hide()


        button_parent.setOnClickListener {
            var myIntent = Intent(this, ParentActivity::class.java)
            startActivity(myIntent)
        }



    }


}