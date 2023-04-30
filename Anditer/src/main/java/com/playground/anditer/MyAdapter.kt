package com.playground.anditer

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.playground.anditer.databinding.ItemRecyclerviewBinding

class MyAdapter(val datas: MutableList<String>, var mainActivity: Context, var distinction: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var checkDetector: Boolean = false
    private val checkboxStatus = SparseBooleanArray()
    val RootingDetector = RootingDetector(mainActivity)
    val DebuggerDetector = DebuggerDetector(mainActivity)
    val EmulatorDetector = EmulatorDetector(mainActivity)
    val FridaDetector = FridaDetector(mainActivity)
    val PinningDetector = PinningDetector(mainActivity)
    val IntegrityDetector = IntegrityDetector(mainActivity)
    val DynamicDetector = DynamicDetector(mainActivity)

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = UserItemViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserItemViewHolder)
            holder.bind(datas[position])

    }

    private fun setImageChange(itemImage: ImageView) {
        val itemImage = itemImage
        when(distinction) {
            "Rooting" -> itemImage.setImageResource(R.drawable.image_rooting)
            "Debugger" -> itemImage.setImageResource(R.drawable.image_debugging)
            "Emulator" -> itemImage.setImageResource(R.drawable.image_emulator)
            "Frida" -> itemImage.setImageResource(R.drawable.image_frida)
            "Pinning" -> itemImage.setImageResource(R.drawable.image_pinning)
            "Integrity" -> itemImage.setImageResource(R.drawable.image_integrity)
            "Dynamic" -> itemImage.setImageResource(R.drawable.image_dynamic)
        }
    }

    private fun setCallBottomSheet(distinction: String, adapterPosition: Int, title: String) {
        (mainActivity as MainActivity).changeFragment(distinction, adapterPosition, title)
    }

    inner class UserItemViewHolder(private val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(title: String) = with(binding) {
            val anim = AnimationUtils.loadAnimation(mainActivity, R.anim.list_item_animation)
            setImageChange(itemImage)
            itemImage.clipToOutline = true
            itemRoot.animation = anim
            itemData.text = title

            confirmIcon.setOnClickListener {
                setCallBottomSheet(distinction, adapterPosition, title)
            }

            switchView.isChecked = checkboxStatus[adapterPosition]
            switchView.setOnCheckedChangeListener { compoundButton, check ->
                if (switchView.isChecked) {
                    checkboxStatus.put(adapterPosition, true)
                    if (distinction == "Rooting") {
                            checkDetector = when(adapterPosition) {
                            0 -> RootingDetector.isCheckRootingInstalled()
                            1 -> RootingDetector.isCheckRootingBinary()
                            2 -> RootingDetector.isCheckRootingExec()
                            3 -> RootingDetector.isCheckRootingKeys()
                            4 -> RootingDetector.isCheckRWFile()
                            5 -> RootingDetector.isCheckForProps()
                            6 -> RootingDetector.isCheckRootingProcesses()
                            else -> false
                        }
                    } else if(distinction == "Debugger") {
                        checkDetector = when(adapterPosition) {
                            0 -> DebuggerDetector.isCheckDebuggerState()
                            1 -> DebuggerDetector.isCheckDebuggable()
                            2 -> DebuggerDetector.isCheckCallPPID()
                            3 -> DebuggerDetector.isCheckDevelopMode()
                            4 -> DebuggerDetector.isCheckUSBDebuggingMode()
                            5 -> DebuggerDetector.isCheckConnectUSB()
                            else -> false
                        }
                    } else if(distinction == "Emulator") {
                        checkDetector = when(adapterPosition) {
                            0 -> EmulatorDetector.isCheckBuildSet()
                            1 -> EmulatorDetector.isCheckEmulatorFile()
                            2 -> EmulatorDetector.isCheckEumlatorPackages()
                            else -> false
                        }
                    } else if(distinction == "Frida") {
                        checkDetector = when(adapterPosition) {
                            0 -> FridaDetector.isCheckFridaBinary()
                            1 -> FridaDetector.isCheckFridaPort()
                            2 -> FridaDetector.isCheckFridaModule()
                            3 -> FridaDetector.isCheckFridaPipe()
                            else -> false
                        }
                    } else if(distinction == "Pinning") {
                        checkDetector = when(adapterPosition) {
                            0 -> PinningDetector.isCheckRootCA()
                            1 -> PinningDetector.isCheckAllowCA()
                            else -> false
                        }
                    } else if(distinction == "Integrity") {
                        checkDetector = when(adapterPosition) {
                            0 -> IntegrityDetector.isCheckAppName()
                            1 -> IntegrityDetector.isCheckHashKey()
                            2 -> IntegrityDetector.isCheckInstaller()
                            3 -> IntegrityDetector.isCheckCRC()
                            else -> false
                        }
                    } else if(distinction == "Dynamic") {
                        checkDetector = when(adapterPosition) {
                            0 -> DynamicDetector.isCheckDynamic(false)
                            1 -> DynamicDetector.isCheckDynamic(true)
                            else -> false
                        }
                    }

                    if(checkDetector) {
                        switchView.text = "Fail... "
                        switchView.setTextColor(Color.parseColor("#E91E63"))

                        switchView.setTrackResource(R.drawable.item_switch_fail_track_on)
                        switchView.setThumbResource(R.drawable.item_switch_fail_thumb_on)
                    } else {
                        switchView.text = "Success! "
                        switchView.setTextColor(Color.parseColor("#B2FF59"))

                        switchView.setTrackResource(R.drawable.item_switch_success_track_on)
                        switchView.setThumbResource(R.drawable.item_switch_success_thumb_on)
                    }
                } else {
                    checkboxStatus.put(adapterPosition, false)
                    switchView.text = "Check "
                    switchView.setTextColor(Color.parseColor("#ECECEC"))

                    switchView.setTrackResource(R.drawable.item_switch_track_off)
                    switchView.setThumbResource(R.drawable.item_switch_thumb_off)
                }
            }
        }
    }
}