package com.isetr.tp5app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "etudiant_table")
data class Etudiant(
    @PrimaryKey val identifiant: Int,
    val classe: String,
    val email: String,

    )
