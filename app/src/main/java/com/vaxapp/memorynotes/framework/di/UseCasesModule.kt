package com.vaxapp.memorynotes.framework.di

import com.vaxapp.core.repository.NoteRepository
import com.vaxapp.core.usecase.AddNote
import com.vaxapp.core.usecase.GetAllNotes
import com.vaxapp.core.usecase.GetNote
import com.vaxapp.core.usecase.GetWordCount
import com.vaxapp.core.usecase.RemoveNote
import com.vaxapp.memorynotes.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun getUseCases(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordCount()
    )
}
