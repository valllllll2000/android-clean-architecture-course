package com.vaxapp.core.usecase

import com.vaxapp.core.data.Note

class GetWordCount {
    operator fun invoke(note: Note) = getCount(note.title) + getCount(note.content)

    private fun getCount(str: String) = str.split(" ", "\n").count {
        it.contains(Regex(".*[a-zA-Z].*"))
    }

}