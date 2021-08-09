package com.ciro.montebravotest.network
import com.ciro.montebravotest.models.ResponseData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/estimates?")
    fun getAcoes(@Query("origin") origin : String, @Query("is_source_favorite_recomendation") is_source_favorite_recomendation : Boolean, @Query("recommendations[]") recommendations : Array<String>) : Call<ResponseData>

    @GET("query?apikey=RRS6RPB4DPOYZC0K&outputsize=compact")
    fun getChartData(@Query("symbol") symbol : String, @Query("function") function : String, ) : Call<ResponseBody>
}