package com.example.notesapp.remote

import com.example.notesapp.data.AddNoteResponse
import com.example.notesapp.data.NoteDataRequest
import com.example.notesapp.data.NoteResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("notes")
    fun getNotes(): Call<NoteResponse>

    @POST("notes")
    fun addNote(@Body data: NoteDataRequest): Call<AddNoteResponse>

    @PUT("notes/{id}")
    fun updateNote(@Path("id") id: String, @Body data: NoteDataRequest): Call<AddNoteResponse>

    @DELETE("notes/{id}")
    fun deleteNote(@Path("id") id: String): Call<AddNoteResponse>
}