package com.muhammad_alvi_awliya_18102239.satria.retrofit

import com.muhammad_alvi_awliya_18102239.satria.berita.BeritaModel
import com.muhammad_alvi_awliya_18102239.satria.hotel.ListHotelModel
import com.muhammad_alvi_awliya_18102239.satria.restaurant.ListRestModel
import com.muhammad_alvi_awliya_18102239.satria.transportasi.ListTransModel
import com.muhammad_alvi_awliya_18102239.satria.wisata.ListwisataModel
import retrofit2.Call
import retrofit2.http.GET


interface ApiEndpoint {

    @GET("index.php/Wisata")
    fun data_wisata(): Call<List<ListwisataModel>>

    @GET("index.php/Transportasi")
    fun data_trans(): Call<List<ListTransModel>>

    @GET("index.php/Restaurant")
    fun data_rest(): Call<List<ListRestModel>>

    @GET("index.php/Hotel")
    fun data_hotel(): Call<List<ListHotelModel>>

    @GET("index.php/Berita")
    fun berita(): Call<List<BeritaModel>>
}