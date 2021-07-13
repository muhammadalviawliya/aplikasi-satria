package com.muhammad_alvi_awliya_18102239.satria


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammad_alvi_awliya_18102239.satria.berita.BeritaAdapter
import com.muhammad_alvi_awliya_18102239.satria.berita.BeritaModel
import com.muhammad_alvi_awliya_18102239.satria.berita.DetailActivityBerita
import com.muhammad_alvi_awliya_18102239.satria.berita.Listberita
import com.muhammad_alvi_awliya_18102239.satria.hotel.Listhotel
import com.muhammad_alvi_awliya_18102239.satria.restaurant.Listrest
import com.muhammad_alvi_awliya_18102239.satria.retrofit.ApiService
import com.muhammad_alvi_awliya_18102239.satria.transportasi.Listtrans
import com.muhammad_alvi_awliya_18102239.satria.wisata.Listwisata
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

//slide show
    var sampleImage = intArrayOf(
        R.drawable.gambar1,
        R.drawable.gambar2
    )

    var cities = arrayOf(
        "small world purwokerto",
        "lokawisata baturaden"
    )

    //--slide show--

    private val TAG: String = "home"

    private lateinit var beritaAdapter: BeritaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnwisata.setOnClickListener {
            val bIntent = Intent(this, Listwisata::class.java)
            startActivity(bIntent)
            Toast.makeText(this, "open dasboard", Toast.LENGTH_SHORT).show()
        }

        btntrans.setOnClickListener {
            val bIntent = Intent(this, Listtrans::class.java)
            startActivity(bIntent)
            Toast.makeText(this, "open dasboard", Toast.LENGTH_SHORT).show()
        }


        btnrest.setOnClickListener {
            val bIntent = Intent(this, Listrest::class.java)
            startActivity(bIntent)
            Toast.makeText(this, "open dasboard", Toast.LENGTH_SHORT).show()
        }

        btnhotel.setOnClickListener {
            val bIntent = Intent(this, Listrest::class.java)
            startActivity(bIntent)
            Toast.makeText(this, "open dasboard", Toast.LENGTH_SHORT).show()
        }

        btnsee.setOnClickListener {
            val bIntent = Intent(this, Listberita::class.java)
            startActivity(bIntent)
            Toast.makeText(this, "open dasboard", Toast.LENGTH_SHORT).show()
        }

        //slide show
        carouselView.pageCount = cities.size

        carouselView.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImage[position])
        }
        carouselView.setImageClickListener { position ->
            Toast.makeText(applicationContext, cities[position], Toast.LENGTH_SHORT).show()
        }
        //slide show

        btntrans.setOnClickListener{
            val trans = Intent(this@MainActivity, Listtrans::class.java)
            startActivity(trans)
        }

        btnwisata.setOnClickListener{
            val wisata = Intent(this@MainActivity,   Listwisata::class.java)
            startActivity(wisata)
        }

        btnrest.setOnClickListener{
            val rest = Intent(this@MainActivity,   Listrest::class.java)
            startActivity(rest)
        }
        btnhotel.setOnClickListener{
            val hotel = Intent(this@MainActivity,   Listhotel::class.java)
            startActivity(hotel)
        }
        btnsee.setOnClickListener{
            val see = Intent(this@MainActivity,   Listberita::class.java)
            startActivity(see)
        }


    }


    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        getDataFromApi()
    }


    private fun setupRecyclerView(){
        beritaAdapter = BeritaAdapter(arrayListOf(), object : BeritaAdapter.OnAdapterListener {
            override fun onClick(results: BeritaModel) {
                startActivity(
                        Intent(this@MainActivity, DetailActivityBerita::class.java)
                                .putExtra("intent_judul", results.judul)
                                .putExtra("intent_tanggal", results.tanggal)
                                .putExtra("intent_gambar", results.gambar)
                                .putExtra("intent_isi", results.isi)


                )
            }
        })
        recyclerView_berita.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = beritaAdapter
        }
    }

    private fun getDataFromApi(){
        ApiService.endpoint.berita()
                .enqueue(object : Callback<List<BeritaModel>> {
                    override fun onFailure(call: Call<List<BeritaModel>>, t: Throwable) {
                        printLog( t.toString() )
                    }
                    override fun onResponse(
                            call: Call<List<BeritaModel>>,
                            response: Response<List<BeritaModel>>
                    ) {
                        if (response.isSuccessful) {
                            showData( response.body()!! )
                        }
                    }
                })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showData(results:  List<BeritaModel>) {
        for (result in results) printLog( "title: ${result.judul}" )
        beritaAdapter.setData(results as MutableList<BeritaModel>)
    }


}
