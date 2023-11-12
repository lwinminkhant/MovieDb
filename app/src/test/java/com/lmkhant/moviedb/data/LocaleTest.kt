package com.lmkhant.moviedb.data

import org.junit.Test
import java.util.Locale

class LocaleTest {
    @Test
    fun localeCodeToCountry(){
        println(Locale("en").displayLanguage)
    }
}