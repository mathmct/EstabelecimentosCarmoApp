package com.example.estabelecimentoscarmo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.estabelecimentoscarmo.model.Estabelecimento

@Dao
interface EstabelecimentoDao {
    @Insert
    suspend fun inserir(estabelecimento: Estabelecimento): Long

    @Insert
    suspend fun inserirTodos(estabelecimentos: List<Estabelecimento>)

    @Query("SELECT * FROM estabelecimento ORDER BY nome ASC")
    suspend fun listarTodos(): List<Estabelecimento>

    @Query("SELECT * FROM estabelecimento WHERE nome LIKE :filtro ORDER BY nome ASC")
    suspend fun filtrarPorNome(filtro: String): List<Estabelecimento>
}
