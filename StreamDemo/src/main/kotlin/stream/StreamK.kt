package stream

/**
 * Kotlin Streaming
 */
import com.github.salomonbrys.kotson.*
import com.google.gson.Gson
import domain.Person
import java.io.InputStreamReader
import java.io.Reader
import kotlin.comparisons.compareBy

fun main(args: Array<String>) {
    val people = loadPeople()
    println("All People: $people")

    // Filter to just locate the Male records
    val males = people.filter { it.gender == "Male" }
    println("Males: $males")

    // Sorted by Last Name and then First Name
    people.sortedWith(compareBy({it.lastName}, {it.firstName}))

    // Extract the full name all of the people
    val names = people.map { "${it.firstName} ${it.lastName}"}
    println(names.joinToString(", ", prefix="Everyones Names: "))

    // Partitioned by Gender creates result of Map<String, List<Person>>
    val partitioned = people.partition { it.gender == "Male"}
    println("\nPartitioned People by Gender")
    println(partitioned)

    // Grouped by Gender creates result of Map<String, Pair<String,String>>
    val groups = people.groupBy { it.gender }.mapValues { it.value.map {Pair(it.firstName, it.lastName)} }
    println("\nGroup People by Gender")
    println(groups)

    // Count the number of people with 'e' in their first name
    val count = people.count { it.firstName.contains('e') }
    println("\nNumber of people with 'e' in their name: $count")

    // Find Person with shortest last name
    val shortest = people.minBy{ it.lastName.length }
    println("\nPerson with Shortest Name: ${shortest!!.lastName}")

    val shortestFemale = people.filter { it.gender == "Female" }.minBy{ it.lastName.length }
    println("\nPerson with Shortest Name: ${shortestFemale!!.lastName}")



}


/**
 * Load People from a JSON File
 */
fun loadPeople(): List<Person> {
    val gson = Gson()
    val reader: Reader = InputStreamReader(Gson::class.java.getResourceAsStream("/people.json"))
    val people = gson.fromJson<List<Person>>(reader)
    return people
}

