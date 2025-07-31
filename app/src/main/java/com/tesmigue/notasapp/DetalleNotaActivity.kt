package com.tesmigue.notasapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tesmigue.notasapp.data.NotesManager
import com.tesmigue.notasapp.databinding.ActivityDetalleNotaBinding
import com.tesmigue.notasapp.model.Note

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleNotaBinding
    private var note: Note? = null
    private var wasEmpty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra("note_id", -1)
        note = NotesManager.findById(id)

        note?.let {
            wasEmpty = it.title.isEmpty() && it.content.isEmpty()
            binding.etTitle.setText(it.title)
            binding.etContent.setText(it.content)
        } ?: finish()

        binding.btnSave.setOnClickListener {
            saveNote()
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnDelete.setOnClickListener {
            note?.let {
                NotesManager.delete(it.id)
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString().trim()
        val content = binding.etContent.text.toString().trim()

        note?.let {
            if (title.isNotEmpty() || content.isNotEmpty()) {
                it.title = title
                it.content = content
                NotesManager.update(it)
            } else if (wasEmpty) {
                NotesManager.delete(it.id)
            }
        }
    }
}
