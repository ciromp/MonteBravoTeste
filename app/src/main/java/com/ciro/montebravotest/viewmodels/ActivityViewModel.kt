package com.ciro.montebravotest.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ciro.montebravotest.App
import com.ciro.montebravotest.models.Acao
import com.ciro.montebravotest.models.AcaoChartData
import com.ciro.montebravotest.models.ResponseData
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ActivityViewModel : ViewModel() {
    val acoes = MutableLiveData<List<Acao>>()
    val acoesDataChart = MutableLiveData<List<AcaoChartData>?>()


    fun newAcoes() {

        App.apiServices.getAcoes("XP",false, arrayOf("neutral", "sell", "buy")).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    acoes.postValue(response.body()?.message?.result)

                } else {
                    Log.d("failure", response.message().toString())
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.d("failure", t.message.toString())
            }

        })
    }

    fun getChartData(symbol: String, function: String) {
        val tempList = ArrayList<AcaoChartData>()
        var test : String = ""
        val tempSymbolChartData: AcaoChartData
        App.apiServiceChart.getChartData(symbol, function).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    response.body()?.string()?.let {
                        if(it.contains("Note")){
                            acoesDataChart.postValue(null)
                        }else
                        {
                            val jsonObj = JSONObject(it)
                            var time: JSONObject? = null
                            if(function.equals("TIME_SERIES_WEEKLY"))
                                time = jsonObj.getJSONObject("Weekly Time Series")
                            else if (function.equals("TIME_SERIES_MONTHLY"))
                                time = jsonObj.getJSONObject("Monthly Time Series")
                            else
                                time = jsonObj.getJSONObject("Time Series (Daily)")
                            val iterator = time.keys()
                            var count = 0;
                            while (iterator.hasNext()){
                                if(count > 30)
                                    break
                                val date = iterator.next().toString()
                                val dateJson = time.getJSONObject(date)
                                val close = dateJson.getString("4. close")
                                tempList.add(AcaoChartData(date,close.toFloat()))
                                count++
                            }
                        }
                        acoesDataChart.postValue(tempList)
                        }

                }else
                {
                    Log.d("problema", response.message().toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

        })
        Log.d("aqui", "aqqq")
    }
}