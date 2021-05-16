package com.example.final_app.dataGroup

import androidx.room.*



//indices = arrayOf(Index(value = ["groupName"], unique = true))

@Entity(tableName = "group_table")
data class Group(
    @PrimaryKey
    @ColumnInfo(name = "groupName")
    val groupName: String,
    )

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

@Entity(
    tableName = "flashcard_table",
    foreignKeys = [
        ForeignKey(
            entity = SubGroup::class,
            parentColumns = ["subGroupName"],
            childColumns = ["FCParentSubGroup"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )]
)
data class FlashCard(
    @PrimaryKey
    @ColumnInfo(name = "FC_Front")
    var front: String,
    @ColumnInfo(name = "FC_back")
    var back: String,
    @ColumnInfo(name = "FCParentSubGroup")
    var FCParentSubGroup: String
)