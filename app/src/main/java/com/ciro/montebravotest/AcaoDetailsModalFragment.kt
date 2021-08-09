package com.ciro.montebravotest

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.red
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ciro.montebravotest.databinding.AcaoDetailsModalFragmentBinding
import com.ciro.montebravotest.models.Acao
import com.ciro.montebravotest.viewmodels.ActivityViewModel
import com.github.mikephil.charting.data.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class AcaoDetailsModalFragment : BottomSheetDialogFragment() {

    private var _binding: AcaoDetailsModalFragmentBinding? = null
    private val viewModel: ActivityViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var acao : Acao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AcaoDetailsModalFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        acao = arguments?.getParcelable<Acao>("acao")!!
        binding.symbolTxtView.text = acao.symbol
        binding.companyNameTxtView.text = acao.company_name
        binding.priceTxtView.text = acao.curent_price.toString()
        binding.brTxtView.text = "BR"
        binding.currentPriceTxtView.text = acao.curent_price.toString().replace(".",",")
        binding.targetPriceTxtView.text = acao.target_price.toString().replace(".",",")
        binding.potentialTxtView.text = acao.potential_price.toString().replace(".",",") + "%"
        if(acao.recomendation.equals("buy")){
            binding.recomendationTxtView.text =  "Compra"
            binding.recomendationTxtView.setBackgroundResource(R.drawable.rounded_corner_green_tag)
        }else if (acao.recomendation.equals("sell")){
            binding.recomendationTxtView.text =  "Venda"
        }else
        {
            binding.recomendationTxtView.text =  "Neutro"
            binding.recomendationTxtView.setBackgroundResource(R.drawable.rounded_corner_gray_tag)
        }

        if(acao.is_source_favorite_recomendation)
            binding.favXpTxtView.text = "SIM"
        else
            binding.favXpTxtView.text = "NÃO"
        Picasso.get().load(acao.symbol_image_url).into(binding.symbolImage)

        binding.saibamaisButton.setOnClickListener {
            val intent = Intent(this.activity, SaibaMaisActivity::class.java).apply {
                putExtra("url", acao.link)
            }

            startActivity(intent)
        }
        viewModel.acoesDataChart.observe(viewLifecycleOwner, {dataChart ->
            if(dataChart.isNullOrEmpty()){
                Toast.makeText(context, "Limit de requisição atingido, tente novamente em 1min.", Toast.LENGTH_LONG).show()
            }else
            {
                acao.chartData = dataChart
                createDataChart()
                binding.lineChart.notifyDataSetChanged();
                binding.lineChart.invalidate();
                binding.chartProgress.visibility = View.GONE
                if(binding.lineChart.visibility == View.INVISIBLE)
                    binding.lineChart.visibility = View.VISIBLE
            }

        })
        binding.dayButton.setOnClickListener{
            binding.chartProgress.visibility = View.VISIBLE
            changeBtnBg(0)
            viewModel.getChartData("${acao.symbol}.SA", "TIME_SERIES_DAILY")
        }
        binding.weekButton.setOnClickListener{
            changeBtnBg(1)
            binding.chartProgress.visibility = View.VISIBLE
            viewModel.getChartData("${acao.symbol}.SA", "TIME_SERIES_WEEKLY")
        }
        binding.monthButton.setOnClickListener{
            changeBtnBg(2)
            binding.chartProgress.visibility = View.VISIBLE
            viewModel.getChartData("${acao.symbol}.SA", "TIME_SERIES_MONTHLY")
        }
        binding.chartProgress.visibility = View.VISIBLE
        viewModel.getChartData("${acao.symbol}.SA", "TIME_SERIES_DAILY")
        binding.lineChart.visibility = View.INVISIBLE
        changeBtnBg(0)

        binding.closeModal.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   fun createDataChart(){
       val entries = ArrayList<Entry>()
       var count : Int = 0
       for(row in acao.chartData?.reversed()!!){
           entries.add(Entry(count.toFloat(), row.yAxis))
           count++
       }
       val vl = LineDataSet(entries, "")

       vl.setDrawValues(false)
       vl.setDrawFilled(true)
       vl.setDrawCircles(false)
       vl.lineWidth = 1f
       vl.fillDrawable = context?.getDrawable(R.drawable.chart_gradient_fill)
       vl.fillAlpha = 100
       vl.color = Color.parseColor("#27b743")

       binding.lineChart.legend.isEnabled = false
       binding.lineChart.xAxis.labelRotationAngle = 0f
       binding.lineChart.data = LineData(vl)
       binding.lineChart.axisRight.isEnabled = false
       binding.lineChart.setTouchEnabled(true)
       binding.lineChart.setPinchZoom(true)
       binding.lineChart.setDrawGridBackground(false)
       binding.lineChart.description.isEnabled = false
       binding.lineChart.xAxis.isEnabled = false
       binding.lineChart.axisLeft.isEnabled = false
       binding.lineChart.axisRight.textColor = Color.WHITE
       binding.lineChart.axisRight.isEnabled = true

   }

    fun changeBtnBg(btn : Int){
    if(btn == 0){
        binding.dayButton.setBackgroundResource(R.drawable.button_checked)
        binding.monthButton.setBackgroundResource(R.drawable.button_press)
        binding.weekButton.setBackgroundResource(R.drawable.button_press)
    }else if(btn == 1){
        binding.dayButton.setBackgroundResource(R.drawable.button_press)
        binding.weekButton.setBackgroundResource(R.drawable.button_checked)
        binding.monthButton.setBackgroundResource(R.drawable.button_press)
    }else if(btn == 2){
        binding.dayButton.setBackgroundResource(R.drawable.button_press)
        binding.weekButton.setBackgroundResource(R.drawable.button_press)
        binding.monthButton.setBackgroundResource(R.drawable.button_checked)
    }

    }
}