package com.playground.anditer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.playground.anditer.databinding.FragmentIntegrityBinding
import com.playground.anditer.databinding.FragmentPinningBinding

class IntegrityFragment : Fragment() {
    val distinction = "Integrity"
    lateinit var mainActivity: Context
    var addData = arrayOf(
        "Bypass App Name",
        "Bypass Hash Key",
        "Bypass Installer",
        "Bypass CRC",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIntegrityBinding.inflate(inflater, container, false)
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