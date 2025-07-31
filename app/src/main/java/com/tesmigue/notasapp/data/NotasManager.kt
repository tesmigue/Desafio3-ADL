package com.tesmigue.notasapp.data

import com.tesmigue.notasapp.model.Note

object NotesManager {
    private val notes = mutableListOf<Note>()

    fun getAll(): List<Note> = notes.sortedByDescending { it.createdAt }

    fun add(note: Note) {
        notes.add(note)
    }

    fun update(updatedNote: Note) {
        val index = notes.indexOfFirst { it.id == updatedNote.id }
        if (index != -1) {
            notes[index] = updatedNote
        }
    }

    fun delete(id: Long) {
        notes.removeIf { it.id == id }
    }

    fun findById(id: Long): Note? = notes.find { it.id == id }

    fun search(query: String): List<Note> {
        return notes.filter {
            it.title.contains(query, ignoreCase = true)
        }.sortedByDescending { it.createdAt }
    }
}
