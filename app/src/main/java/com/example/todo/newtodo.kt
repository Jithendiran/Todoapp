package com.example.todo

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.database.database
import com.example.todo.database.datalist

class newtodo:AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view)
        var edtxt: EditText = findViewById(R.id.edit_text)
        var finishbtn: Button = findViewById(R.id.finish)
        var backbtn:Button = findViewById(R.id.backbuttom)
        var delbtn:Button = findViewById(R.id.delete_btn)
        var alaram:Button = findViewById(R.id.alaram_btn)
        delbtn.visibility = View.INVISIBLE


        //alaram.visibility = View.INVISIBLE
        finishbtn.setOnClickListener {

            var data = edtxt.text.toString()
            var databasehandler: database = database(this)

            var success = databasehandler.adddata(datalist(0,data,"false"))
            if (success > -1)
            {

                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
            } else
            {
                Toast.makeText(this, "Data not saved", Toast.LENGTH_SHORT).show()

            }

            setResult(Activity.RESULT_OK)
            finish()
        }
        backbtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}