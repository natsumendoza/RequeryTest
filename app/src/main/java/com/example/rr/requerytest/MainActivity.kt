package com.example.rr.requerytest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rr.requerytest.model.Models
import com.example.rr.requerytest.model.Person
import com.example.rr.requerytest.model.PersonEntity
import io.requery.Persistable
import io.requery.android.sqlite.DatabaseSource
import io.requery.kotlin.eq
import io.requery.sql.EntityDataStore
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import java.net.URL
import java.util.*
import javax.sql.CommonDataSource

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model = Models.DEFAULT
        val context = applicationContext
        val dbName = "android.db"


        val dataSource = DatabaseSource(context, model, dbName, 1)
//        val data = EntityDataStore<Any>(dataSource.configuration)

//        val configuration = KotlinConfiguration(dataSource = dataSource)

        val data = KotlinEntityDataStore<Any>(dataSource.configuration)

        val person = randomPerson()
        val person1 = randomPerson()
        val person2 = randomPerson()

        data.invoke {
            insert(person)
            insert(person1)
            insert(person2)
            val result = select(Person::class) where (Person::id eq  person.id) limit 5
            result.get().forEach {
                println(it.name)
            }


        }

    }

    fun randomPerson(): Person {
        val random = Random()
        val person = PersonEntity()
        val firstNames = arrayOf("Alice", "Bob", "Carol")
        val lastNames = arrayOf("Smith", "Lee", "Jones")
        person.name = (firstNames[random.nextInt(firstNames.size)] + " " +
                lastNames[random.nextInt(lastNames.size)])
        person.email = (person.name.replace(" ".toRegex(), "").toLowerCase() + "@example.com")
        val calendar = Calendar.getInstance()
        calendar.set(1900 + random.nextInt(90), random.nextInt(12), random.nextInt(30))
        person.birthday = calendar.time
        return person
    }
}
