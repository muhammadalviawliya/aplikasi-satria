package com.muhammad_alvi_awliya_18102239.satria.hotel

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.muhammad_alvi_awliya_18102239.satria.R
import kotlinx.android.synthetic.main.activity_detail_hotel.*
import kotlinx.android.synthetic.main.activity_detail_hotel.btnmap
import kotlinx.android.synthetic.main.activity_detail_hotel.btntelp
import kotlinx.android.synthetic.main.activity_detail_hotel.imageViewdetail
import kotlinx.android.synthetic.main.activity_detail_hotel.textView
import kotlinx.android.synthetic.main.activity_detail_hotel.textView2
import kotlinx.android.synthetic.main.activity_detail_trans.*

class DetailActivityHotel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_hotel)
        val intentTitle = intent.getStringExtra("intent_title")
        val intentFoto = intent.getStringExtra("intent_foto")
        val intentDesc = intent.getStringExtra("intent_desc")
        val intentHarga = intent.getStringExtra("intent_harga")
        val intentMap = intent.getStringExtra("intent_map")
        val intentTelp = intent.getStringExtra("intent_telp")
        val intentWeb = intent.getStringExtra("intent_web")
        supportActionBar!!.title = intentTitle
        Glide.with(this)
            .load(intentFoto )
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(imageViewdetail)


        textView.text = intentTitle
        textView2.text = intentDesc
        textView3.text = intentHarga
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
        btnweb.setOnClickListener{
            val intent1 = Intent(Intent.ACTION_VIEW)
            intent1.addCategory(Intent.CATEGORY_BROWSABLE)
            intent1.data = Uri.parse(intentWeb)
            startActivity(intent1)
        }

    }
}