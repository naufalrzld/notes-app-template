package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.notesapp.data.NoteData
import com.example.notesapp.data.NoteResponse
import com.example.notesapp.remote.RetrofitService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener {

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var swRefresh: SwipeRefreshLayout
    private lateinit var rvNote: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swRefresh = findViewById(R.id.sw_refresh)
        rvNote = findViewById(R.id.rv_notes)
        fabAdd = findViewById(R.id.fab_add)

        fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditNoteActivity::class.java))
        }

        noteAdapter = NoteAdapter(this)
        rvNote.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }

        swRefresh.isRefreshing = true
        swRefresh.setOnRefreshListener {
            getNotes()
        }
    }

    private fun getNotes() {
        val call = RetrofitService.getApiClient().getNotes()
        call.enqueue(object : Callback<NoteResponse> {
            override fun onResponse(call: Call<NoteResponse>, response: Response<NoteResponse>) {
                swRefresh.isRefreshing = false
                if (response.code() == 200) {
                    val result = response.body()?.data?.notes
                    noteAdapter.notes = result ?: listOf()
                }
            }

            override fun onFailure(call: Call<NoteResponse>, t: Throwable) {
                swRefresh.isRefreshing = false
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        getNotes()
    }

    override fun onItemClick(data: NoteData) {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.putExtra("note", data)
        startActivity(intent)
    }
}