package com.playground.anditer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CustomAlert(val context: Context) : AppCompatActivity() {

    fun smaliDialog(title: String, contents: String, isCall: String) {
        val layoutInflater = LayoutInflater.from(context as MainActivity)
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        val alertDialog = AlertDialog.Builder(context, R.style.CustomAlertDialog).setView(view).create()
        val textTitle = view.findViewById<TextView>(R.id.title)
        val textSubtitle =  view.findViewById<TextView>(R.id.contents)
        val buttonBack = view.findViewById<View>(R.id.back)

        textTitle.text = title
        textSubtitle.text = contents


        buttonBack.setOnClickListener {

            if(isCall == "Home") {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/naroSEC/Anditer"))
                context.startActivity(intent)
            }
            alertDialog.dismiss()

        }
        alertDialog.show()
    }
}