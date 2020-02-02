package com.roland.androidpokedexkotlin

object Utils {
    /**
     * Capitalize the first letter of input and return the new string
     * @param lower
     * @return
     */
    fun toUpper(lower: String): String {
        return lower.substring(0, 1).toUpperCase() + lower.substring(1)
    }
}