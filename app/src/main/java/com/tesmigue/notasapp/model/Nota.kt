package com.tesmigue.notasapp.model

data class Note(
    val id: Long,
    var title: String,
    var content: String,
    val createdAt: Long = System.currentTimeMillis()
)
