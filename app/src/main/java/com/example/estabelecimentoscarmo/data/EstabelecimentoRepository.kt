package com.example.estabelecimentoscarmo.data

import com.example.estabelecimentoscarmo.model.Estabelecimento

class EstabelecimentoRepository(private val dao: EstabelecimentoDao) {

    suspend fun listarTodos(): List<Estabelecimento> = dao.listarTodos()

    suspend fun inserir(estabelecimento: Estabelecimento): Long = dao.inserir(estabelecimento)

    suspend fun filtrarPorNome(filtro: String): List<Estabelecimento> = dao.filtrarPorNome("%$filtro%")
}
