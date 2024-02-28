package com.vaxapp.memorynotes.framework.di

import android.app.Application
import com.vaxapp.core.repository.NoteRepository
import com.vaxapp.memorynotes.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))
}
