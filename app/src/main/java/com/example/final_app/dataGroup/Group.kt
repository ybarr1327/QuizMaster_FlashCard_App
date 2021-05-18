package com.example.final_app.dataGroup

import androidx.room.*



//this file includes the table definitions for the room database

//group table
@Entity(tableName = "group_table")
data class Group(
    @PrimaryKey
    @ColumnInfo(name = "groupName")
    val groupName: String, //the only key is the group name as a string
    )


//subgroup table
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
data class SubGroup( // contains the subgroup name as the primary key and sgParentGroupName is the foreign key to the group table
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
data class FlashCard( // this table contains the front as the primary key, the back and the FCParentSubGroup as a foreign key to the subgroup table
    @PrimaryKey
    @ColumnInfo(name = "FC_Front")
    var front: String,
    @ColumnInfo(name = "FC_back")
    var back: String,
    @ColumnInfo(name = "FCParentSubGroup")
    var FCParentSubGroup: String
)