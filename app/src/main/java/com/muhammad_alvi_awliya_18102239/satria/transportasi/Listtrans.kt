package com.muhammad_alvi_awliya_18102239.satria.transportasi

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
import kotlinx.android.synthetic.main.activity_listtrans.*
import kotlinx.android.synthetic.main.activity_listtrans.progressBar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Listtrans : AppCompatActivity(){

    private val TAG: String = "Listtrans"

    private lateinit var listtransAdapter: ListtransAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listtrans)
        supportActionBar!!.title = "Rental"

    }


    override fun onStart() {
        super.onStart()
        setupRecyclerView()
        getDataFromApi()
    }


    private fun setupRecyclerView(){
        listtransAdapter = ListtransAdapter(arrayListOf(), object : ListtransAdapter.OnAdapterListener {
            override fun onClick(results: ListTransModel) {
                startActivity(
                        Intent(this@Listtrans, DetailActivityTrans::class.java)
                                .putExtra("intent_title", results.name)
                                .putExtra("intent_foto", results.foto)
                                .putExtra("intent_desc", results.desc)
                                .putExtra("intent_telp", results.telp)
                                .putExtra("intent_map", results.map)

                )
            }
        })
        recyclerView_trans.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listtransAdapter
        }
    }

    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.data_trans()
                .enqueue(object : Callback<List<ListTransModel>> {
                    override fun onFailure(call: Call<List<ListTransModel>>, t: Throwable) {
                        printLog( t.toString() )
                        showLoading(false)
                    }
                    override fun onResponse(
                            call: Call<List<ListTransModel>>,
                            response: Response<List<ListTransModel>>
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
    private fun showData(results:  List<ListTransModel>) {
        for (result in results) printLog( "title: ${result.name}" )
        listtransAdapter.setData(results as MutableList<ListTransModel>)
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
                listtransAdapter.getFilter().filter(newText)
                return true
            }
        })
    }

}
