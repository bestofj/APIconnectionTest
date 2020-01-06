package com.example.apiconnectiontest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apiconnectiontest.util.GlobalData
import kotlinx.android.synthetic.main.activity_loginparentinfo.*
import kotlinx.android.synthetic.main.activity_parent_home.*

class ParentHomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_home)
        supportActionBar?.hide()

        var name = GlobalData.loginUser?.child?.name
        txt_greet.setText(name + " 학부모님\n안녕하세요")
    }
}