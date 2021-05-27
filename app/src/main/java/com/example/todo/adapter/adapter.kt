package com.example.todo.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.database.database
import com.example.todo.database.datalist

import com.example.todo.updateactivity

class adapter(var context: Context ,var datas: ArrayList<datalist>) : RecyclerView.Adapter<adapter.ViewHolder>()
{
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        // Holds the TextView that will add each item to
        val textdata :TextView= view.findViewById(R.id.textview)
        var delbtn :Button = view.findViewById(R.id.delete_btn)
        var update :TextView = view.findViewById(R.id.textview)
        var verify:CheckBox = view.findViewById(R.id.verified_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter, parent, false) )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        var text1 = datas.get(position)
        holder.textdata.text = text1.data

        holder.verify.isChecked = text1.status.toBooleanStrict()
        holder.delbtn.setOnClickListener {
            if(context is MainActivity)
            {

                (context as MainActivity).deletedata(text1.id)

            }
        }

        holder.update.setOnClickListener {
            //Toast.makeText(context,"in editable",Toast.LENGTH_SHORT).show()
            var intent:Intent = Intent(context,updateactivity::class.java)

            intent.putExtra("data",text1)

            ActivityCompat.startActivityForResult(context as MainActivity,intent,1015,null)
        }

        holder.verify.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
            {
                //buttonView.isChecked = true
                (context as MainActivity).updatedata(datalist(text1.id,text1.data,"true"))
                Toast.makeText(context,"Verified ",Toast.LENGTH_SHORT).show()

            }
            else
            {
               // buttonView.isChecked = false
                (context as MainActivity).updatedata(datalist(text1.id,text1.data,"false"))
                Toast.makeText(context,"Not Verified ",Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun getItemCount(): Int
    {
        return datas.size
    }




}