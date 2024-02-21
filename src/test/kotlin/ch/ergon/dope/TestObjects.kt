package ch.ergon.dope

import ch.ergon.dope.resolvable.expression.unaliased.type.addField
import ch.ergon.dope.resolvable.fromable.Bucket
import ch.ergon.dope.resolvable.fromable.Collection
import ch.ergon.dope.validtype.BooleanType
import ch.ergon.dope.validtype.NumberType
import ch.ergon.dope.validtype.StringType

class Person : Collection("person") {
    var type = addField<StringType>("type")
    var title = addField<StringType>("title")
    var fname = addField<StringType>("fname")
    var lname = addField<StringType>("lname")
    var age = addField<NumberType>("age")
    var email = addField<StringType>("email")
    var relation = addField<StringType>("relation")
    var isMale = addField<BooleanType>("isMale")
}

class Customer : Collection("customer") {
    var firstName = addField<StringType>("firstname")
}

class Airline : Collection("airline") {
    val aname = addField<StringType>("name")
    val type = addField<StringType>("type")
    val callsign = addField<StringType>("callsign")
    val country = addField<StringType>("country")
    val iota = addField<StringType>("iota")
    val icao = addField<StringType>("icao")
    val id = addField<StringType>("id")
}

class Route : Collection("route") {
    val airline = addField<StringType>("airline")
    val airlineid = addField<StringType>("airlineid")
    val destinationairport = addField<StringType>("destinationairport")
    val distance = addField<StringType>("distance")
    val equipment = addField<StringType>("equipment")
    val id = addField<NumberType>("id")
    val schedule = addField<StringType>("schedule")
    val sourceairport = addField<StringType>("sourceairport")
    val stops = addField<NumberType>("stops")
    val type = addField<StringType>("type")
}

class Airport : Collection("airport") {
    val airportname = addField<StringType>("airportname")
    val city = addField<StringType>("city")
    val country = addField<StringType>("country")
    val faa = addField<StringType>("faa")
    val geo = addField<StringType>("geo")
    val alt = addField<StringType>("alt")
    val lat = addField<StringType>("lat")
    val lon = addField<StringType>("lon")
    val icao = addField<StringType>("icao")
    val id = addField<StringType>("id")
    val type = addField<StringType>("type")
    val tz = addField<StringType>("tz")
}

class Landmark : Collection("landmark") {
    val lname = addField<StringType>("lname")
    val city = addField<StringType>("city")
    val country = addField<StringType>("country")
}

// Fake Class for easy testing
class TestBucket : Bucket("TestBucket") {
    companion object {
        val Person = Person()
        val Customer = Customer()
        val Airline = Airline()
        val Airport = Airport()
        val Landmark = Landmark()
        val Route = Route()
    }
}
