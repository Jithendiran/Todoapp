package com.example.todo.database


import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

@Parcelize
data class datalist(var id:Int, var data: String?,var status:String ) :Parcelable
