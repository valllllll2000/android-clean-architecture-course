package com.vaxapp.memorynotes.framework

import android.content.Context
import com.vaxapp.core.data.Note
import com.vaxapp.core.repository.NoteDataSource
import com.vaxapp.memorynotes.framework.db.DatabaseService
import com.vaxapp.memorynotes.framework.db.NoteEntity

class RoomNoteDataSource(context: Context) : NoteDataSource {

    private val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long): Note? = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll(): List<Note> =
        noteDao.getAllNoteEntities().map { noteEntity -> noteEntity.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}
