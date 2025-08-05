package com.example.randomnamesapp.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object CsvReader {
    fun <T> readCsvFromAssets(
        context: Context,
        fileName: String,
        skipHeader: Boolean = true,
        mapper: (List<String>) -> T
    ): List<T> {
        val inputStream = context.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val result = mutableListOf<T>()

        reader.useLines { lines ->
            lines.drop(if (skipHeader) 1 else 0).forEach { line ->
                val tokens = line.split(",").map { it.trim() }
                result.add(mapper(tokens))
            }
        }

        return result
    }
}