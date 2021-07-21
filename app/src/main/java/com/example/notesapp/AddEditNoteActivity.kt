package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.data.AddNoteResponse
import com.example.notesapp.data.NoteData
import com.example.notesapp.data.NoteDataRequest
import com.example.notesapp.remote.RetrofitService
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var etTitle: TextInputEditText
    private lateinit var etNote: TextInputEditText
    private lateinit var btnSave: MaterialButton
    private lateinit var btnDelete: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        etTitle = findViewById(R.id.et_title)
        etNote = findViewById(R.id.et_note)
        btnSave = findViewById(R.id.btn_save)
        btnDelete = findViewById(R.id.btn_delete)

        val noteData = intent.getParcelableExtra<NoteData>("note")
        if (noteData != null) {
            etTitle.setText(noteData.title)
            etNote.setText(noteData.note)
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val note = etNote.text.toString()

            if (noteData != null) {
                val data = NoteDataRequest(title, note)
                editNote(noteData.id, data)
            } else {
                val data = NoteDataRequest(title, note)
                addNote(data)
            }
        }

        btnDelete.setOnClickListener {
            noteData?.let { it1 -> deleteNote(it1.id) }
        }
    }

    private fun addNote(data: NoteDataRequest) {
        val call = RetrofitService.getApiClient().addNote(data)
        call.enqueue(object : Callback<AddNoteResponse> {
            override fun onResponse(
                call: Call<AddNoteResponse>,
                response: Response<AddNoteResponse>
            ) {
                if (response.code() == 201) {
                    val result = response.body()?.message
                    Toast.makeText(this@AddEditNoteActivity, result, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<AddNoteResponse>, t: Throwable) {
                Toast.makeText(this@AddEditNoteActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun editNote(id: String, data: NoteDataRequest) {
        val call = RetrofitService.getApiClient().updateNote(id, data)
        call.enqueue(object : Callback<AddNoteResponse> {
            override fun onResponse(
                call: Call<AddNoteResponse>,
                response: Response<AddNoteResponse>
            ) {
                if (response.code() == 200) {
                    val result = response.body()?.message
                    Toast.makeText(this@AddEditNoteActivity, result, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<AddNoteResponse>, t: Throwable) {
                Toast.makeText(this@AddEditNoteActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun deleteNote(id: String) {
        val call = RetrofitService.getApiClient().deleteNote(id)
        call.enqueue(object : Callback<AddNoteResponse> {
            override fun onResponse(
                call: Call<AddNoteResponse>,
                response: Response<AddNoteResponse>
            ) {
                if (response.code() == 200) {
                    val result = response.body()?.message
                    Toast.makeText(this@AddEditNoteActivity, result, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<AddNoteResponse>, t: Throwable) {
                Toast.makeText(this@AddEditNoteActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}