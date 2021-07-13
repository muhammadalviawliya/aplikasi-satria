package com.muhammad_alvi_awliya_18102239.satria.berita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammad_alvi_awliya_18102239.satria.R
import com.muhammad_alvi_awliya_18102239.satria.retrofit.ApiService
import kotlinx.android.synthetic.main.activity_listrest.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Listberita : AppCompatActivity(){

    private val TAG: String = "Listberita"

    private lateinit var beritaAdapter: BeritaAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listberita)
        supportActionBar!!.title = "Satria News"

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
                        Intent(this@Listberita, DetailActivityBerita::class.java)
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
                        showLoading(false)
                    }
                    override fun onResponse(
                            call: Call<List<BeritaModel>>,
                            response: Response<List<BeritaModel>>
                    ) {
                        showLoading(false)
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

    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        val search: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(search) as SearchView
        search(searchView)
        return true
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                beritaAdapter.getFilter().filter(newText)
                return true
            }
        })
    }
}
