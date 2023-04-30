package com.playground.anditer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.playground.anditer.databinding.FragmentDynamicBinding
import com.playground.anditer.databinding.FragmentFridaBinding

class DynamicFragment : Fragment() {

    val distinction = "Dynamic"
    lateinit var mainActivity: Context
    var addData = arrayOf(
        "Bypass Dynamic Code",
        "Bypass Hide Code",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDynamicBinding.inflate(inflater, container, false)
        val datas = mutableListOf<String>()

        addData.forEach {
            datas.add(it)
        }
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = MyAdapter(datas, mainActivity, distinction)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context
    }
}