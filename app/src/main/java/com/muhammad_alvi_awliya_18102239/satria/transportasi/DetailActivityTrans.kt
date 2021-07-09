package com.muhammad_alvi_awliya_18102239.satria.transportasi

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.muhammad_alvi_awliya_18102239.satria.R
import kotlinx.android.synthetic.main.activity_detail_trans.*

class DetailActivityTrans : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_trans)
        val intentTitle = intent.getStringExtra("intent_title")
        val intentFoto = intent.getStringExtra("intent_foto")
        val intentDesc = intent.getStringExtra("intent_desc")
        val intentTelp = intent.getStringExtra("intent_telp")
        val intentMap = intent.getStringExtra("intent_map")
        supportActionBar!!.title = intentTitle
        Glide.with(this)
            .load(intentFoto )
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

        btntelp.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.fromParts("tel",intentTelp,null)
                startActivity(intent)
        }

    }
}