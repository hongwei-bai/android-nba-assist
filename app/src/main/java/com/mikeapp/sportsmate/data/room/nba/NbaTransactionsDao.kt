package com.mikeapp.sportsmate.data.room.nba

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mikeapp.sportsmate.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface NbaTransactionsDao {
    @Query("SELECT * FROM transactions WHERE apiVersion=$API_VERSION")
    fun getTransactions(): Flow<NbaTransactionsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(transactionsEntity: NbaTransactionsEntity)

    @Query("DELETE FROM transactions")
    suspend fun clear()

    @Query("SELECT * FROM transactions")
    fun getAllRecords(): List<NbaTransactionsEntity>
}