package com.example.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.database.datalist

class updateactivity:AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view)

        var edtxt:EditText = findViewById(R.id.edit_text)
        var ok_btn:Button = findViewById(R.id.finish)
        var del_btn:Button = findViewById(R.id.delete_btn)


        var data_txt:datalist? = intent.extras?.getParcelable("data")


        var data = data_txt?.data
        var id = data_txt?.id!!.toInt()
        var sts = data_txt?.status

        var back:Button = findViewById(R.id.backbuttom)
        back.setOnClickListener {
            finish()
        }

        del_btn.setOnClickListener {
            intent.putExtra("delete_data",datalist(id,data,sts))
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

        edtxt.setText(data_txt?.data)
        ok_btn.setOnClickListener {
            data = edtxt.text.toString()
            var intent = Intent()

            intent.putExtra("updated_data",datalist(id,data,sts))
            setResult(Activity.RESULT_OK,intent)
            finish()

        }

    }
}