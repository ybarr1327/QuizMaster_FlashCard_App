package com.example.final_app.dataGroup

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Constructor


@Parcelize
@Entity(tableName = "group_table", indices = arrayOf(Index(value = ["groupName"], unique = true)))
data class Group(
    @PrimaryKey(autoGenerate = true) // room database automatically generates the id
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "groupName")
    val groupName:String,

):Parcelable