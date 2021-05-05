package com.example.final_app.dataGroup


import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize


//indices = arrayOf(Index(value = ["groupName"], unique = true))

@Parcelize
@Entity(tableName = "group_table")
data class Group(
    @PrimaryKey
    @ColumnInfo(name = "groupName")
    val groupName: String,
    ) : Parcelable

@Entity(
    tableName = "sub_group_table",
    foreignKeys = [
        ForeignKey(
            entity = Group::class,
            parentColumns = ["groupName"],
            childColumns = ["sgParentGroupName"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )]
)
data class SubGroup(
    @PrimaryKey
    @ColumnInfo(name = "subGroupName")
    val subGroupName: String,
    @ColumnInfo(name = "sgParentGroupName")
    val sgParentGroupName: String,

)