package com.vaxapp.memorynotes.framework.di

import com.vaxapp.memorynotes.framework.ListViewModel
import com.vaxapp.memorynotes.framework.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}
