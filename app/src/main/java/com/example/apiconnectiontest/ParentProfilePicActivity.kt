package com.example.apiconnectiontest

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.apiconnectiontest.util.ConnectServer
import kotlinx.android.synthetic.main.activity_parent_profile_pic.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class ParentProfilePicActivity : BaseActivity() {     // 프로필 사진, 이메일등록 미완
    val Gallery = 0
    var image: File? = null
    var tempBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_profile_pic)

        supportActionBar?.hide() // 상단 바 감추기

        bt_profile.setOnClickListener {
            val intent = Intent()

            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(intent,"Load Picture"), Gallery)



        }


        bt_complete.setOnClickListener{

            ConnectServer.putRequestUserInfo(     // EditText에서 받은 값 서버 api 호출.
                mContext, bt_name.text.toString(), image, object : ConnectServer.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {
                        try {
                            if (json.getInt("code") == 200) {
                                runOnUiThread {
                                    Toast.makeText(mContext, "프로필 등록 완료", Toast.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Gallery){
            if(resultCode == RESULT_OK){
                var dataUri = data?.data
                try{
                    var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
                    tempBitmap = bitmap
                    imageView.setImageBitmap(bitmap)
                } catch (e:Exception){
                    Toast.makeText(this, "$e",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bitmapToFile(bitmap:Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")

        try{
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e:IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse(file.absolutePath)
    }
}
