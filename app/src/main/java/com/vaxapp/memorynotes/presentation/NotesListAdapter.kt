package com.vaxapp.memorynotes.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.vaxapp.core.data.Note
import com.vaxapp.memorynotes.R
import java.text.SimpleDateFormat
import java.util.Date


class NotesListAdapter(var notes: ArrayList<Note>, val action: ListAction) :
    RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    )

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val layout = view.findViewById<CardView>(R.id.noteLayout)
        private val noteTitle = view.findViewById<TextView>(R.id.title)
        private val noteContent = view.findViewById<TextView>(R.id.content)
        private val noteDate = view.findViewById<TextView>(R.id.date)
        private val noteWords = view.findViewById<TextView>(R.id.wordCount)

        fun bind(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content
            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss")
            val result = sdf.format(Date(note.updateTime))
            noteDate.text = "Last updated: $result"

            layout.setOnClickListener {
                action.onClick(note.id)
            }

            noteWords.text = "Words: ${note.wordCount}"
        }
    }
}
