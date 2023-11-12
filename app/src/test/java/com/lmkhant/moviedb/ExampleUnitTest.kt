package com.lmkhant.moviedb

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun printApiKey() {
        println("Key: ${BuildConfig.API_KEY}")
    }

    @Test
    fun addition_isCorrect() {
        println(generatePrimaryKey("Popular", 0))
        println(generatePrimaryKey("Popular", 1))
        println(generatePrimaryKey("Popular", 2))
        println(generatePrimaryKey("UpComing", 0))
        println(generatePrimaryKey("UpComing", 1))
        println(generatePrimaryKey("UpComing", 2))
    }

    private fun generatePrimaryKey(source: String, number: Int): Int {
        val sourceHash = source.hashCode()
        return sourceHash xor number
    }

    @Test
    fun testNow() {
        val people = listOf(Person("aung", 13), Person("Shi", 33))
        var newPeople = listOf<Person>()
        newPeople = people.map { person ->
            person.copy(name = "Mr. ${person.name}")
        }
        println(newPeople)
    }

    data class Person(val name: String, val age: Int)

    @Test
    fun combineTwoList() {
        val listOld = listOf(Person("a", 1), Person("b", 1))
        val listNew = listOf(Person("newa", 3), Person("newb", 3))

        val ggp = listOld.zip(listNew) { a, b ->
            Person(a.name, b.age)
        }

        println(ggp)
    }

    fun formatExpression(input: String): String {
        var depth = 0
        val formatted = StringBuilder()

        for (char in input) {
            if (char == '(') {
                depth++
            } else if (char == ')') {
                depth--
            }

            formatted.append(char)

            if (depth == 0 && char in "+-*/") {
                formatted.insert(formatted.lastIndexOf(char), '(')
                formatted.append(')')
            }
        }

        return formatted.toString()
    }

    @Test
    fun RunThis(): Unit {
        print(formatExpression("9-5/(8-3)*2+6"))
    }

    data class IdName(val id: Int, val name: String)

    @Test
    fun MapTest() {
        val ids = listOf(1, 2)
        val idList = listOf(IdName(1, "One"), IdName(2, "Two"))

        println(ids.map { id ->
            idList.find { it.id == id }?.name ?: "Unknown"
        })

        val numbers = listOf("one", "two", "three")

    }

}