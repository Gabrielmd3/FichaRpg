package com.example.ficharpg1.model.dao

import androidx.room.*
import com.example.ficharpg1.model.entity.Ficha


@Dao
interface FichaDao {

    @Insert
    suspend fun inserir(ficha: Ficha)

    @Query("SELECT * FROM ficha")
    suspend fun buscarTodos(): List<Ficha>

    @Delete
    suspend fun deletar(ficha: Ficha)

    @Update
    suspend fun atualizar(ficha: Ficha)
}

