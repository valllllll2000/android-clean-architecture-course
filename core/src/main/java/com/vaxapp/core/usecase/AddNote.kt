package com.vaxapp.core.usecase

import com.vaxapp.core.data.Note
import com.vaxapp.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}