package com.muhammad_alvi_awliya_18102239.satria.restaurant

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


class Listrest : AppCompatActivity(){

    private val TAG: String = "Listrestoran"

    private lateinit var listrestAdapter: ListrestAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listrest)
        supportActionBar!!.title = "Restoran"

    }


    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        getDataFromApi()
    }


    private fun setupRecyclerView(){
        listrestAdapter = ListrestAdapter(arrayListOf(), object : ListrestAdapter.OnAdapterListener {
            override fun onClick(results: ListRestModel) {
                startActivity(
                        Intent(this@Listrest, DetailActivityRest::class.java)
                                .putExtra("intent_title", results.name)
                                .putExtra("intent_foto", results.foto)
                                .putExtra("intent_desc", results.desc)
                                .putExtra("intent_map", results.map)

                )
            }
        })
        recyclerView_rest.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listrestAdapter
        }
    }

    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.data_rest()
                .enqueue(object : Callback<List<ListRestModel>> {
                    override fun onFailure(call: Call<List<ListRestModel>>, t: Throwable) {
                        printLog( t.toString() )
                        showLoading(false)
                    }
                    override fun onResponse(
                            call: Call<List<ListRestModel>>,
                            response: Response<List<ListRestModel>>
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
    private fun showData(results:  List<ListRestModel>) {
        for (result in results) printLog( "title: ${result.name}" )
        listrestAdapter.setData(results as MutableList<ListRestModel>)
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
                listrestAdapter.getFilter().filter(newText)
                return true
            }
        })
    }

}
