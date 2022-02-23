package com.app.myintentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MoveWithObjectActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_PERSON = "extra_person"
        const val EXTRA_MANY_PERSON = "extra_many_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_with_object)

        val tvObject: TextView = findViewById(R.id.tv_object_received)

        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON) as Person
        val manyPerson = intent.getParcelableArrayListExtra<Person>(EXTRA_MANY_PERSON) as ArrayList<Person>
//        val text = "Name : ${person.name}, \nEmail : ${person.email}, \nAge : ${person.age}, \nLocation : ${person.city}"

        var text = StringBuilder().apply {
            append("Name : ${person.name},")
            append("\nEmail : ${person.email},")
            append("\nAge : ${person.age},")
            append("\nLocation : ${person.city}")
        }
        if (manyPerson.size > 0){
            manyPerson.forEach {
                /*concate text*/
                text = StringBuilder().apply {
                    /*menggabungkan text yang sudah ada sebelumnya dengan text baru*/
                    append(text)
                    append("\nName : ${it.name},")
                    append("\nEmail : ${it.email},")
                    append("\nAge : ${it.age},")
                    append("\nLocation : ${it.city}")
                }
            }
        }
        tvObject.text = text
    }
}