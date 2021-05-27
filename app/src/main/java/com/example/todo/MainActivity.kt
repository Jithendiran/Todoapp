package com.example.todo

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.adapter.adapter
import com.example.todo.database.database
import com.example.todo.database.datalist
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.text.FieldPosition

class MainActivity : AppCompatActivity()
{

    lateinit var norecord:TextView
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        norecord = findViewById(R.id.tvNoRecordsAvailable)
        recyclerView = findViewById(R.id.recycler)

        var newbtn: View = findViewById(R.id.floating_action_button)

        newbtn.setOnClickListener{

           adddata()

        }
         setrecycler()

    }

    fun setrecycler() {
        if(viewdata().size > 0)
        {

            norecord.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true);

            recyclerView.adapter = adapter(this, viewdata())
        }
        else
        {

            norecord.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE

        }
    }

    fun viewdata():ArrayList<datalist>
    {

        var databasehandler:database = database(this)
        var datas:ArrayList<datalist> = databasehandler.viewdata() as ArrayList<datalist>

        return datas
    }


    fun adddata()
    {
        var intent:Intent = Intent(this,newtodo::class.java)

        startActivityForResult(intent,1014)
         setrecycler()
    }

    fun deletedata(position: Int)
    {
        var databasehandler:database = database(this)
        var status = databasehandler.deletedata(position)
        if (status > -1)
        {

            Toast.makeText(this, "Data deleted", Toast.LENGTH_SHORT).show()
        } else
        {
            Toast.makeText(this, "Data not deleted", Toast.LENGTH_SHORT).show()

        }
        setrecycler()

    }

    fun updatedata(data: datalist)
    {
        var databasehandler:database = database(this)


        var status = databasehandler.updatedata(data)
        if(status > -1)
        {
            Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this, "Data not updated", Toast.LENGTH_SHORT).show()
        }
        setrecycler()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 1014)
        {

           setrecycler()
        }
        else if(resultCode == Activity.RESULT_CANCELED && requestCode == 1014 )
        {
            Toast.makeText(this,"Data not Saved",Toast.LENGTH_SHORT).show()
            setrecycler()

        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 1015)
        {

            var data_txt:datalist? = data?.extras?.getParcelable("updated_data")
            if(data_txt != null)
            {

                updatedata(data_txt)
            }
            data_txt = data?.extras?.getParcelable("delete_data")
            if(data_txt != null)
            {

                deletedata(data_txt.id)
            }
        }
        else if(resultCode == Activity.RESULT_CANCELED && requestCode == 1015 )
        {
            Toast.makeText(this,"Data not Updated",Toast.LENGTH_SHORT).show()
            setrecycler()

        }
    }
}

