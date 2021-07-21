package com.example.notesapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class NoteResponse(
    val success: Boolean,
    val data: Note
)

data class Note(
    val notes: List<NoteData> = listOf()
)

@Parcelize
data class NoteData(
    val id: String,
    val title: String,
    val note: String
): Parcelable

data class NoteDataRequest(
    var title: String,
    var note: String
)
