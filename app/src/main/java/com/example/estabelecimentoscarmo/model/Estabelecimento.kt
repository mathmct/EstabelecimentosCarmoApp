package com.example.estabelecimentoscarmo.model

import java.io.Serializable

data class Estabelecimento(
    val foto: Int,
    val nome: String,
    val categoria: String,
    val endereco: String,
    val site: String,
    val telefone: String,
    val plusCode: String
) : Serializable
