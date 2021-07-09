package com.muhammad_alvi_awliya_18102239.satria.wisata

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
import kotlinx.android.synthetic.main.activity_listwisata.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Listwisata : AppCompatActivity(){

    private val TAG: String = "Listwisata"

    private lateinit var listwisataAdapter: ListwisataAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listwisata)
        supportActionBar!!.title = "Wisata"

    }


    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        getDataFromApi()
    }


    private fun setupRecyclerView(){
        listwisataAdapter = ListwisataAdapter(arrayListOf(), object : ListwisataAdapter.OnAdapterListener {
            override fun onClick(results: ListwisataModel) {
                startActivity(
                    Intent(this@Listwisata, DetailActivity::class.java)
                        .putExtra("intent_title", results.name)
                        .putExtra("intent_image", results.image)
                            .putExtra("intent_description", results.description)
                            .putExtra("intent_maps", results.maps)

                )
            }
        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listwisataAdapter
        }
    }

    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.data_wisata()
            .enqueue(object : Callback<List<ListwisataModel>> {
                override fun onFailure(call: Call<List<ListwisataModel>>, t: Throwable) {
                    printLog( t.toString() )
                    showLoading(false)
                }
                override fun onResponse(
                        call: Call<List<ListwisataModel>>,
                        response: Response<List<ListwisataModel>>
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

    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }
    private fun showData(results:  List<ListwisataModel>) {
        for (result in results) printLog( "title: ${result.name}" )
        listwisataAdapter.setData(results as MutableList<ListwisataModel>)
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
                listwisataAdapter.getFilter().filter(newText)
                return true
            }
        })
    }

}
