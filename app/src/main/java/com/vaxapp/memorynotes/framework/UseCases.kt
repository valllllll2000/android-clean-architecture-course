package com.vaxapp.memorynotes.framework

import com.vaxapp.core.usecase.AddNote
import com.vaxapp.core.usecase.GetAllNotes
import com.vaxapp.core.usecase.GetNote
import com.vaxapp.core.usecase.GetWordCount
import com.vaxapp.core.usecase.RemoveNote

data class UseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote,
    val getWordCount: GetWordCount
)
