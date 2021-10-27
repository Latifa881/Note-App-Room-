package com.example.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Student")
data class Student (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Int = 0, // this is how can include id if needed
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Location") val location: String?
)