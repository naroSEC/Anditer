package com.playground.anditer

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.playground.anditer.databinding.BottomSheetDialogBinding
import org.w3c.dom.Text

class BottomSheetFagment(context: Context, _distinction: String, _adapterPosition: Int, _title: String) : BottomSheetDialogFragment() {

    private val mContext: Context
    val distinction: String
    val adapterPosition: Int
    val title: String

    init {
        mContext = context
        distinction = _distinction
        adapterPosition = _adapterPosition
        title = _title
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = BottomSheetDialogBinding.inflate(inflater, container, false)
        when(distinction) {
            "Rooting" -> {
                binding.title.text = getString(R.string.bottom_sheet_rooting_title)
                when(adapterPosition) {
                    0 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_rooting_content)[adapterPosition]
                    1 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_rooting_content)[adapterPosition]
                    2 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_rooting_content)[adapterPosition]
                    3 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_rooting_content)[adapterPosition]
                    4 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_rooting_content)[adapterPosition]
                    5 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_rooting_content)[adapterPosition]
                    6 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_rooting_content)[adapterPosition]
                }
            }
            "Emulator" -> {
                binding.title.text = getString(R.string.bottom_sheet_debugger_title)
                when(adapterPosition) {
                    0 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_emulator_content)[adapterPosition]
                    1 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_emulator_content)[adapterPosition]
                    2 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_emulator_content)[adapterPosition]
                }
            }
            "Debugger" -> {
                binding.title.text = getString(R.string.bottom_sheet_debugger_title)
                when(adapterPosition) {
                    0 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_debugging_content)[adapterPosition]
                    1 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_debugging_content)[adapterPosition]
                    2 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_debugging_content)[adapterPosition]
                    3 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_debugging_content)[adapterPosition]
                    4 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_debugging_content)[adapterPosition]
                    5 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_debugging_content)[adapterPosition]
                }
            }
            "Frida" -> {
                binding.title.text = getString(R.string.bottom_sheet_frida_title)
                when(adapterPosition) {
                    0 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_frida_content)[adapterPosition]
                    1 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_frida_content)[adapterPosition]
                    2 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_frida_content)[adapterPosition]
                    3 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_frida_content)[adapterPosition]
                    4 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_frida_content)[adapterPosition]
                    5 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_frida_content)[adapterPosition]
                }
            }
            "Pinning" -> {
                binding.title.text = getString(R.string.bottom_sheet_pinning_title)
                when(adapterPosition) {
                    0 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_pinning_content)[adapterPosition]
                    1 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_pinning_content)[adapterPosition]
                }
            }
            "Integrity" -> {
                binding.title.text = getString(R.string.bottom_sheet_integrity_title)
                when(adapterPosition) {
                    0 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_integrity_content)[adapterPosition]
                    1 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_integrity_content)[adapterPosition]
                    2 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_integrity_content)[adapterPosition]
                    3 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_integrity_content)[adapterPosition]
                }
            }
            "Dynamic" -> {
                binding.title.text = getString(R.string.bottom_sheet_dynamic_title)
                when(adapterPosition) {
                    0 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_dynamic_content)[adapterPosition]
                    1 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_dynamic_content)[adapterPosition]
                }
            }
            "Native" -> {
                binding.title.text = getString(R.string.bottom_sheet_native_title)
                when(adapterPosition) {
                    0 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_native_content)[adapterPosition]
                    1 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_native_content)[adapterPosition]
                    2 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_native_content)[adapterPosition]
                    3 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_native_content)[adapterPosition]
                    4 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_native_content)[adapterPosition]
                    5 -> binding.content.text = resources.getStringArray(R.array.bottom_sheet_native_content)[adapterPosition]
                }
            }

        }


        binding.bdr.setOnClickListener {
            dialog?.cancel()
        }

        binding.clickbutton.setOnClickListener {
            dialog?.cancel()
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet!!.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        layoutParams.width = getBottomSheetDialogDefaultWidth()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 80 / 100
    }

    private fun getBottomSheetDialogDefaultWidth(): Int {
        return getWindowWidth() * 96 / 100
    }

    private fun getWindowHeight(): Int {
        return resources.displayMetrics.heightPixels
    }

    private fun getWindowWidth(): Int {
        return resources.displayMetrics.widthPixels
    }
}