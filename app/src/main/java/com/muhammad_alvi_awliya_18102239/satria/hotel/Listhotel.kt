package com.muhammad_alvi_awliya_18102239.satria.hotel

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Listhotel : AppCompatActivity(){

    private val TAG: String = "Listhotel"

    private lateinit var listhotelAdapter: ListhotelAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listrest)
        supportActionBar!!.title = "Hotel"

    }


    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        getDataFromApi()
    }


    private fun setupRecyclerView(){
        listhotelAdapter = ListhotelAdapter(arrayListOf(), object : ListhotelAdapter.OnAdapterListener {
            override fun onClick(results: ListHotelModel) {
                startActivity(
                        Intent(this@Listhotel, DetailActivityHotel::class.java)
                                .putExtra("intent_title", results.name)
                                .putExtra("intent_foto", results.foto)
                                .putExtra("intent_desc", results.desc)
                                .putExtra("intent_harga", results.harga)
                                .putExtra("intent_map", results.map)
                                .putExtra("intent_telp", results.telp)
                                .putExtra("intent_web", results.web)

                )
            }
        })
        recyclerView_rest.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listhotelAdapter
        }
    }

    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.data_hotel()
                .enqueue(object : Callback<List<ListHotelModel>> {
                    override fun onFailure(call: Call<List<ListHotelModel>>, t: Throwable) {
                        printLog( t.toString() )
                        showLoading(false)
                    }
                    override fun onResponse(
                            call: Call<List<ListHotelModel>>,
                            response: Response<List<ListHotelModel>>
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
    private fun showData(results:  List<ListHotelModel>) {
        for (result in results) printLog( "title: ${result.name}" )
        listhotelAdapter.setData(results as MutableList<ListHotelModel>)
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
                listhotelAdapter.getFilter().filter(newText)
                return true
            }
        })
    }

}
