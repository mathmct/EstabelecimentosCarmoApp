package com.example.estabelecimentoscarmo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "estabelecimento")
data class Estabelecimento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fotoUri: String,
    val nome: String,
    val categoria: String,
    val endereco: String,
    val site: String,
    val telefone: String,
    val plusCode: String
) : Serializable
