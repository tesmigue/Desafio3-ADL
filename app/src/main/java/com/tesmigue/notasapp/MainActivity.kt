package com.tesmigue.notasapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tesmigue.notasapp.adapter.NotesAdapter
import com.tesmigue.notasapp.data.NotesManager
import com.tesmigue.notasapp.databinding.ActivityMainBinding
import com.tesmigue.notasapp.model.Note

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NotesAdapter { note ->
            val intent = Intent(this, NoteDetailActivity::class.java)
            intent.putExtra("note_id", note.id)
            startActivity(intent)
        }

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.adapter = adapter

        binding.fabAddNote.setOnClickListener {
            val newNote = Note(id = System.currentTimeMillis(), title = "", content = "")
            NotesManager.add(newNote)
            val intent = Intent(this, NoteDetailActivity::class.java)
            intent.putExtra("note_id", newNote.id)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val filtered = NotesManager.search(newText.orEmpty())
                adapter.updateNotes(filtered)
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        adapter.updateNotes(NotesManager.getAll())
    }
}
