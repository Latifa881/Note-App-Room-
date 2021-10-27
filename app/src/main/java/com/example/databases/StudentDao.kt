package com.example.databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//Database Access Object
@Dao
interface StudentDao {

    @Query("SELECT * FROM Student where Name=:name ORDER BY Name DESC")
    fun getAllUserInfo(name: String): List<Student>
    @Query("SELECT * FROM Student ")
    fun getAllUsersInfo(): List<Student>
    @Insert
    fun insertStudent(student: Student)
    @Query("DELETE FROM Student where Name=:name")
    fun deleteStudent(name: String)

    @Query("UPDATE Student SET Location=:location where Name=:name")
    fun updateStudent(name: String,location: String)

}