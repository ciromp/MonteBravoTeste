package com.ciro.montebravotest

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ciro.montebravotest.adapters.AcaoRecyclerAdapter
import com.ciro.montebravotest.adapters.SectionsAcoesRecyclerAdapter
import com.ciro.montebravotest.databinding.FragmentFirstBinding
import com.ciro.montebravotest.models.Acao
import com.ciro.montebravotest.viewmodels.ActivityViewModel

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    var adapter = AcaoRecyclerAdapter(null)
    val listSections = ArrayList<String>()


    private val model : ActivityViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var tempAcao: Acao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val searchIcon = binding.searchView.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.GRAY)
        val cancelIcon = binding.searchView.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.GRAY)
        val textView = binding.searchView.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)
        textView.setHintTextColor(Color.GRAY)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acaoDetailsModalFragment = AcaoDetailsModalFragment()

        binding.acoesProgress.visibility = View.VISIBLE

        createSections()
        val adapterSections = SectionsAcoesRecyclerAdapter(listSections)

        binding.sectionsRecyclerView.adapter = adapterSections
        val layoutManagerSections = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        binding.sectionsRecyclerView.layoutManager = layoutManagerSections

        binding.recyclerView.adapter = adapter
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager

        model.acoes.observe(viewLifecycleOwner, { acoes ->
            //update UI
            adapter.setResult(acoes)
            adapter.notifyDataSetChanged()
            binding.acoesProgress.visibility = View.GONE
        })

        model.newAcoes()



        adapter.onItemClick = { acao ->
            tempAcao = acao
            val bundle = Bundle()
            bundle.putParcelable("acao", tempAcao)
            acaoDetailsModalFragment.arguments = bundle
            acaoDetailsModalFragment.show(parentFragmentManager,"acaoDetailsModalFragment")
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    fun createSections(){
        listSections.add("Todas")
        listSections.add("Financeira")
        listSections.add("Petróleo & Gás")
        listSections.add("Energia")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}