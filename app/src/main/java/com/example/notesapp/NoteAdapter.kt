package com.example.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.data.NoteData

class NoteAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    var notes = listOf<NoteData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position], listener)
    }

    override fun getItemCount() = notes.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = itemView.findViewById<TextView>(R.id.tv_title)
        private val note = itemView.findViewById<TextView>(R.id.tv_note)

        fun bind(data: NoteData, listener: OnItemClickListener) {
            title.text = data.title
            note.text = data.note

            itemView.setOnClickListener {
                listener.onItemClick(data)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(data: NoteData)
    }
}