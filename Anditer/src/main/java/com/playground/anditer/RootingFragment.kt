package com.playground.anditer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.playground.anditer.databinding.FragmentRootingBinding


class RootingFragment : Fragment() {
    val distinction = "Rooting"
    lateinit var mainActivity: Context
    var addData = arrayOf(
        "Bypass Packages",
        "Bypass Binaries",
        "Bypass Command Execution",
        "Bypass Build-Tags",
        "Bypass Writeable",
        "Bypass System Property",
        "Bypass Check Process",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRootingBinding.inflate(inflater, container, false)
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