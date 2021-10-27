package com.example.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class],version = 1,exportSchema = false)
abstract class StudentDatabase: RoomDatabase() {

    companion object{
        var instance:StudentDatabase?=null;
        fun getInstance(ctx: Context):StudentDatabase
        {
            if(instance!=null)
            {
                return  instance as StudentDatabase;
            }
            instance= Room.databaseBuilder(ctx,StudentDatabase::class.java,"myDB").run { allowMainThreadQueries() }.build()
            return instance as StudentDatabase;
        }
    }
    abstract fun StudentDao():StudentDao;
}