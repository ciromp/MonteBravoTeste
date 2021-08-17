package com.ciro.montebravotest.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ciro.montebravotest.App
import com.ciro.montebravotest.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class ActivityViewModel : ViewModel() {
    val acoes = MutableLiveData<List<Acao>>()
    val acoesDataChart = MutableLiveData<List<AcaoChartData>?>()


    fun newAcoes() {

        App.apiServices.getAcoes("XP", false, arrayOf("neutral", "sell", "buy"))
            .enqueue(object : Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
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
        if(function.equals("TIME_SERIES_MONTHLY")) {
            App.apiServiceChart.getChartDataMonthly(symbol, function)
                .enqueue(object : Callback<AcaoResponseDataMonthly> {
                    override fun onResponse(
                        call: Call<AcaoResponseDataMonthly>,
                        responseDaily: Response<AcaoResponseDataMonthly>
                    ) {
                        if (responseDaily.isSuccessful) {
                            val tempList: ArrayList<AcaoChartData> = ArrayList<AcaoChartData>()
                            responseDaily.body()!!.data_chart_resuts.forEach { (key, value) ->
                                val temChartData: AcaoChartData = AcaoChartData(key, value.close)
                                tempList.add(temChartData)
                            }
                            acoesDataChart.postValue(tempList)
                        }
                    }

                    override fun onFailure(call: Call<AcaoResponseDataMonthly>, t: Throwable) {
                        Log.d("vendoError", t.message.toString())
                    }

                })
        }else if(function.equals("TIME_SERIES_WEEKLY")) {
            App.apiServiceChart.getChartDataWeekly(symbol, function)
                .enqueue(object : Callback<AcaoResponseDataWeekly> {
                    override fun onResponse(
                        call: Call<AcaoResponseDataWeekly>,
                        responseDaily: Response<AcaoResponseDataWeekly>
                    ) {
                        if (responseDaily.isSuccessful) {
                            val tempList: ArrayList<AcaoChartData> = ArrayList<AcaoChartData>()
                            responseDaily.body()!!.data_chart_resuts.forEach { (key, value) ->
                                val temChartData: AcaoChartData = AcaoChartData(key, value.close)
                                tempList.add(temChartData)
                            }
                            acoesDataChart.postValue(tempList)
                        }
                    }

                    override fun onFailure(call: Call<AcaoResponseDataWeekly>, t: Throwable) {
                        Log.d("vendoError", t.message.toString())
                    }

                })
        }else {
            App.apiServiceChart.getChartDataDaily(symbol, function)
                .enqueue(object : Callback<AcaoResponseDataDaily> {
                    override fun onResponse(
                        call: Call<AcaoResponseDataDaily>,
                        responseDaily: Response<AcaoResponseDataDaily>
                    ) {
                        if (responseDaily.isSuccessful) {
                            val tempList: ArrayList<AcaoChartData> = ArrayList<AcaoChartData>()
                            responseDaily.body()!!.data_chart_resuts.forEach { (key, value) ->
                                val temChartData: AcaoChartData = AcaoChartData(key, value.close)
                                tempList.add(temChartData)
                            }
                            acoesDataChart.postValue(tempList)
                        }
                    }

                    override fun onFailure(call: Call<AcaoResponseDataDaily>, t: Throwable) {
                        Log.d("vendoError", t.message.toString())
                    }

                })
        }

    }
}