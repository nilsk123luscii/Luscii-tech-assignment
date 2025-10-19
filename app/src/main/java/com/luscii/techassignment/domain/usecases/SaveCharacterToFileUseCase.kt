package com.luscii.techassignment.domain.usecases

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.luscii.techassignment.R
import com.luscii.techassignment.domain.models.Character
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SaveCharacterToFileUseCase @Inject constructor(@ApplicationContext val context: Context) {

    operator fun invoke(character: Character) {
        val fileName = "${character.name}.csv"
        val text = characterToCsv(character)

        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "text/plain")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
        uri?.let { uri ->
            context.contentResolver.openOutputStream(uri)?.use {
                it.write(text.toByteArray())
            }
        }

        Toast.makeText(
            context,
            context.getString(R.string.misc_export_succeeded), Toast.LENGTH_SHORT
        ).show()
    }

    fun characterToCsv(character: Character): String {
        val header = "Name,Status,Species,Origin,Episodes"
        val line = listOf(
            character.name,
            character.status,
            character.species,
            character.origin.name,
            character.episode.size.toString()
        ).joinToString(separator = ",") { escapeCsv(it) }

        return "$header\n$line"
    }

    fun escapeCsv(value: String): String {
        var escaped = value.replace("\"", "\"\"") // escape quotes
        if (escaped.contains(",") || escaped.contains("\n") || escaped.contains("\"")) {
            escaped = "\"$escaped\""
        }
        return escaped
    }
}
