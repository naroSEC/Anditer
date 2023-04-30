package com.playground.anditer

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.playground.anditer.databinding.ActivityAppPassWordBinding

class AppPassWordActivity : AppCompatActivity(){
    lateinit var binding : ActivityAppPassWordBinding
    private var oldPwd =""
    private var changePwdUnlock = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppPassWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
            val buttonArray = arrayListOf<Button>(binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9, binding.btnClear, binding.btnErase)

            for (button in buttonArray){
            button.setOnClickListener(btnListener)
        }
    }

    // 버튼 클릭 했을때
    private val btnListener = View.OnClickListener { view ->
        var currentValue = -1

        when(view.id){
            R.id.btn0 -> currentValue = 0
            R.id.btn1 -> currentValue = 1
            R.id.btn2 -> currentValue = 2
            R.id.btn3 -> currentValue = 3
            R.id.btn4 -> currentValue = 4
            R.id.btn5 -> currentValue = 5
            R.id.btn6 -> currentValue = 6
            R.id.btn7 -> currentValue = 7
            R.id.btn8 -> currentValue = 8
            R.id.btn9 -> currentValue = 9
            R.id.btnClear -> onClear()
            R.id.btnErase -> onDeleteKey()
        }

        val strCurrentValue = currentValue.toString()
        if (currentValue != -1){
            when {
                binding.etPasscode1.isFocused -> {
                    setEditText(binding.etPasscode1, binding.etPasscode2, strCurrentValue)
                }
                binding.etPasscode2.isFocused -> {
                    setEditText(binding.etPasscode2, binding.etPasscode3, strCurrentValue)
                }
                binding.etPasscode3.isFocused -> {
                    setEditText(binding.etPasscode3, binding.etPasscode4, strCurrentValue)
                }
                binding.etPasscode4.isFocused -> {
                    binding.etPasscode4.setText(strCurrentValue)
                }
            }
        }

        if (binding.etPasscode4.text.isNotEmpty() && binding.etPasscode3.text.isNotEmpty() && binding.etPasscode2.text.isNotEmpty() && binding.etPasscode1.text.isNotEmpty()) {
            inputType(intent.getIntExtra("type", 0))
        }
    }

    private fun onDeleteKey() {
        when {
            binding.etPasscode1.isFocused -> {
                binding.etPasscode1.setText("")
            }
            binding.etPasscode2.isFocused -> {
                binding.etPasscode1.setText("")
                binding.etPasscode1.requestFocus()
            }
            binding.etPasscode3.isFocused -> {
                binding.etPasscode2.setText("")
                binding.etPasscode2.requestFocus()
            }
            binding.etPasscode4.isFocused -> {
                binding.etPasscode3.setText("")
                binding.etPasscode3.requestFocus()
            }
        }
    }

    private fun onClear(){
        binding.etPasscode1.setText("")
        binding.etPasscode2.setText("")
        binding.etPasscode3.setText("")
        binding.etPasscode4.setText("")
        binding.etPasscode1.requestFocus()
    }

    private fun inputedPassword():String {
        return "${binding.etPasscode1.text}${binding.etPasscode2.text}${binding.etPasscode3.text}${binding.etPasscode4.text}"
    }

    private fun setEditText(currentEditText : EditText, nextEditText: EditText, strCurrentValue : String){
        //currentEditText.setText(strCurrentValue)
        currentEditText.setText("*")
        nextEditText.requestFocus()
        nextEditText.setText("")
    }

    private fun inputType(type : Int){
        when(type){
            AppLockConst.ENABLE_PASSLOCK ->{
                if(oldPwd.isEmpty()){
                    oldPwd = inputedPassword()
                    onClear()
                    binding.etInputInfo.text = "한 번 더 입력해주세요."
                }
                else{
                    if(oldPwd == inputedPassword()){
                        AppLock(this).setPassLock(inputedPassword())
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                    else{
                        onClear()
                        oldPwd = ""
                        binding.etInputInfo.text = "입력한 비밀번호가 틀립니다.\n다시 입력해주세요."
                    }
                }
            }

            AppLockConst.DISABLE_PASSLOCK ->{
                if(AppLock(this).isPassLockSet()){
                    if(AppLock(this).checkPassLock(inputedPassword())) {
                        AppLock(this).removePassLock()
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                    else {
                        binding.etInputInfo.text = "비밀번호가 틀립니다."
                        onClear()
                    }
                }
                else{
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
            }

            AppLockConst.UNLOCK_PASSWORD ->
                if(AppLock(this).checkPassLock(inputedPassword())) {
                    setResult(Activity.RESULT_OK)
                    finish()
                }else{
                    binding.etInputInfo.text = "비밀번호가 틀립니다."
                    onClear()
                }

            AppLockConst.CHANGE_PASSWORD -> {
                if (AppLock(this).checkPassLock(inputedPassword()) && !changePwdUnlock) {
                    onClear()
                    changePwdUnlock = true
                    binding.etInputInfo.text = "새로운 비밀번호를 입력해주세요."
                }
                else if (changePwdUnlock) {
                    if (oldPwd.isEmpty()) {
                        oldPwd = inputedPassword()
                        onClear()
                        binding.etInputInfo.text = "한 번 더 입력해주세요."
                    } else {
                        if (oldPwd == inputedPassword()) {
                            AppLock(this).setPassLock(inputedPassword())
                            setResult(Activity.RESULT_OK)
                            finish()
                        } else {
                            onClear()
                            oldPwd = ""
                            binding.etInputInfo.text = "현재 비밀번호를 다시 입력해주세요."
                            changePwdUnlock = false
                        }
                    }
                } else {
                    binding.etInputInfo.text = "비밀번호가 틀립니다."
                    changePwdUnlock = false
                    onClear()
                }
            }
        }
    }
}