package com.muhammad_alvi_awliya_18102239.satria.berita

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.muhammad_alvi_awliya_18102239.satria.R
import kotlinx.android.synthetic.main.activity_detail_berita.*


class DetailActivityBerita : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_berita)
        val intentTitle = intent.getStringExtra("intent_judul")
        val intentTanggal = intent.getStringExtra("intent_tanggal")
        val intentFoto = intent.getStringExtra("intent_gambar")
        val intentDesc = intent.getStringExtra("intent_isi")
        supportActionBar!!.title = "Satria News"
        Glide.with(this)
            .load(intentFoto )
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(imageViewdetail)


        textView.text = intentTitle
        textView2.text = intentTanggal
        textView3.text = intentDesc


    }
}