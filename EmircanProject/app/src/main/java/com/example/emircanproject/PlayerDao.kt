package com.example.emircanproject

import androidx.contentpager.content.Query
import androidx.room.Dao
import androidx.room.Insert

@Dao
interface PlayerDao {
    @Insert
    suspend fun insert(player: Player)

    @androidx.room.Query("SELECT * FROM players")
    suspend fun getAllPlayers(): List<Player>
}
