package com.example.rr.requerytest.model

import android.os.Parcelable
import io.requery.*
import java.util.*

@Entity
interface Person : Parcelable, Persistable {
    @get:Key
    @get:Generated
    val id: Int

    var name: String

    var email: String

    var birthday: Date

    var age: Int

    @get:ForeignKey
    @get:OneToOne
    var address: Address?

    @get:OneToMany(mappedBy = "owner")
    val phoneNumbers: MutableSet<Phone>
}