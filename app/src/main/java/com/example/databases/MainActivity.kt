package com.example.databases

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {
    lateinit var btSave: Button
    lateinit var btGetLocation: Button
    lateinit var etName: EditText
    lateinit var etLocation: EditText
    lateinit var etNameGetLocation: EditText
    lateinit var tvGetLocation: TextView
    lateinit var etUpdateName: EditText
    lateinit var etUpdateLocation: EditText
    lateinit var btUpdate: Button
    lateinit var etDelete: EditText
    lateinit var btDelete: Button

    lateinit var students: List<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)
        btSave = findViewById(R.id.btSave)
        etNameGetLocation = findViewById(R.id.etNameGetLocation)
        btGetLocation = findViewById(R.id.btGetLocation)
        tvGetLocation = findViewById(R.id.tvGetLocation)
        etUpdateName = findViewById(R.id.etUpdateName)
        etUpdateLocation = findViewById(R.id.etUpdateLocation)
        btUpdate = findViewById(R.id.btUpdate)
        etDelete = findViewById(R.id.etDelete)
        btDelete = findViewById(R.id.btDelete)

        StudentDatabase.getInstance(applicationContext)
        students = arrayListOf()

        btSave.setOnClickListener {
            val name = etName.text.toString()
            val location = etLocation.text.toString()
            if (name.isNotEmpty() && location.isNotEmpty()) {
                val student = Student(0, name, location)
                CoroutineScope(IO).launch {
                    StudentDatabase.getInstance(applicationContext).StudentDao()
                        .insertStudent(student)
                }
                Toast.makeText(applicationContext, "Data saved successfully! ", Toast.LENGTH_SHORT)
                    .show()
                etName.setText("")
                etLocation.setText("")
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please enter a name and location ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        btGetLocation.setOnClickListener {
            var name = etNameGetLocation.text.toString()
            if (name.isNotEmpty()) {

                CoroutineScope(IO).launch {
                    var locationFound=async {
                        getLocation(name)
                    }.await()
                    if (locationFound.isNotEmpty()) {
                        withContext(Dispatchers.Main){

                            tvGetLocation.text=locationFound //
                        }
                    }
                    else{
                        withContext(Dispatchers.Main){
                            Toast.makeText(applicationContext, "Name doesn't exists ", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
//                  val  studentsArray = StudentDatabase.getInstance(applicationContext).StudentDao()
//                        .getAllUserInfo(name)
//                    tvGetLocation.text = studentsArray.get(0).location

                }

            } else {
                Toast.makeText(applicationContext, "Please enter a name ", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        btUpdate.setOnClickListener {
            val name = etUpdateName.text.toString()
            val location = etUpdateLocation.text.toString()
            if (name.isNotEmpty() && location.isNotEmpty()) {

                CoroutineScope(IO).launch {
                    StudentDatabase.getInstance(applicationContext).StudentDao()
                        .updateStudent(name, location)

                }
                Toast.makeText(
                    applicationContext,
                    "Updated Successfully ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please enter a name and location ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        }
        btDelete.setOnClickListener {
            val name = etDelete.text.toString()
            if (name.isNotEmpty()) {
                CoroutineScope(IO).launch {
                    StudentDatabase.getInstance(applicationContext).StudentDao()
                        .deleteStudent(name)

                }
                Toast.makeText(
                    applicationContext,
                    "Deleted Successfully ",
                    Toast.LENGTH_SHORT
                )
                    .show()

            } else {

                Toast.makeText(
                    applicationContext,
                    "Please enter a name",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }


    }

    private fun getLocation(name:String):String{
        val allStudents= StudentDatabase.getInstance(applicationContext).StudentDao().getAllUsersInfo()
        for(i in allStudents){
            if(i.name.equals(name))
            {
                return i.location.toString()

            }
        }
        return ""
    }


}