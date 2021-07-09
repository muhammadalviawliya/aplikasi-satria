package com.muhammad_alvi_awliya_18102239.satria.wisata

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.muhammad_alvi_awliya_18102239.satria.R
import kotlinx.android.synthetic.main.activity_detail_wisata.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_wisata)
        val intentTitle = intent.getStringExtra("intent_title")
        val intentImage = intent.getStringExtra("intent_image")
        val intentDesc = intent.getStringExtra("intent_description")
        val intentMap = intent.getStringExtra("intent_maps")
        supportActionBar!!.title = intentTitle
        Glide.with(this)
            .load(intentImage )
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(imageViewdetail)


        textView.text = intentTitle
        textView2.text = intentDesc
        btnmap.setOnClickListener{
            val intentt = Intent(Intent.ACTION_VIEW)
            intentt.data = Uri.parse(intentMap)
            startActivity(intentt)
        }
        
    }
}