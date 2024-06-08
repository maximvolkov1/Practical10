package com.example.practical10

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class Activity1 : AppCompatActivity() {
    var colors = arrayOf(0x00BCD4, 0xEADC62, 0x59B65D)
    var sheetNumber = 0
    lateinit var text : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1)
        text = findViewById(R.id.text)
        val font_plus : Button = findViewById(R.id.font_plus)
        val font_minus : Button = findViewById(R.id.font_def)
        val clearBtn : Button = findViewById(R.id.clearBtn)
        sheetNumber = getIntent().getIntExtra("sheetNumber", 0)
        val  next : Button = findViewById(R.id.next)
        next.setOnClickListener() {
            if (sheetNumber < colors.size - 1) {
                val intent = Intent(this, this::class.java)
                intent.putExtra("sheetNumber", sheetNumber + 1)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, getString(R.string.lastSheep), Toast.LENGTH_SHORT).show()
            }
        }
        font_plus.setOnClickListener {
            text.textSize = 48f
        }
        font_minus.setOnClickListener {
            text.textSize = 18f
        }
        clearBtn.setOnClickListener {
            text.setText("")
        }
    }
    override fun onPause() {
        super.onPause()
        val prefs : SharedPreferences.Editor = getPreferences(Context.MODE_PRIVATE).edit()
        prefs.putString("note" + sheetNumber, text.editableText.toString())
        prefs.apply()
    }

    override fun onResume() {
        super.onResume()
        val sheet : ConstraintLayout = findViewById<ConstraintLayout>(R.id.sheet)
        sheet.setBackgroundColor(colors[sheetNumber].toInt())
        val saveState : String? = getPreferences(Context.MODE_PRIVATE).getString("note" + sheetNumber.toString(), null)
        if (saveState != null) {
            text.setText(saveState)
        }
    }
}