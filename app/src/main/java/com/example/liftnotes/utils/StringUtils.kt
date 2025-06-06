package com.example.liftnotes.utils

object StringUtils {
    fun trimUnnecessaryDecimals(input: String): String {
        var result = input
        if (result.endsWith(".")) {
            result = result.dropLast(1)
        } else if (result.contains(".")) {
            result = result.trimEnd('0').trimEnd('.')
        }
        return result
    }
}