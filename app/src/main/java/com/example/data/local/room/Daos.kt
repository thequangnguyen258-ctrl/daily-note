package com.example.data.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAccountDao {
    @Query("SELECT * FROM user_accounts ORDER BY username ASC")
    fun getAllAccounts(): Flow<List<UserAccountEntity>>

    @Query("SELECT * FROM user_accounts")
    suspend fun getAllAccountsList(): List<UserAccountEntity>

    @Query("SELECT * FROM user_accounts WHERE LOWER(username) = LOWER(:username) LIMIT 1")
    suspend fun getAccountByUsername(username: String): UserAccountEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: UserAccountEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllAccounts(accounts: List<UserAccountEntity>)

    @Query("DELETE FROM user_accounts WHERE LOWER(username) = LOWER(:username)")
    suspend fun deleteAccountByUsername(username: String)
}

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_entries WHERE username = :username ORDER BY createdAt ASC")
    fun getJournalsForUser(username: String): Flow<List<JournalEntity>>

    @Query("SELECT * FROM journal_entries WHERE username = :username ORDER BY createdAt ASC")
    suspend fun getJournalsListForUser(username: String): List<JournalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalEntity)

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteJournalById(id: String)
}

@Dao
interface PrayerChatDao {
    @Query("SELECT * FROM prayer_chats WHERE username = :username ORDER BY createdAt ASC")
    fun getChatsForUser(username: String): Flow<List<PrayerChatEntity>>

    @Query("SELECT * FROM prayer_chats WHERE username = :username ORDER BY createdAt ASC")
    suspend fun getChatsListForUser(username: String): List<PrayerChatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: PrayerChatEntity)

    @Query("DELETE FROM prayer_chats WHERE username = :username")
    suspend fun clearChatsForUser(username: String)
}

@Dao
interface TreeStateDao {
    @Query("SELECT * FROM tree_states WHERE username = :username LIMIT 1")
    fun getTreeStateForUser(username: String): Flow<TreeStateEntity?>

    @Query("SELECT * FROM tree_states WHERE username = :username LIMIT 1")
    suspend fun getTreeStateDirect(username: String): TreeStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTreeState(state: TreeStateEntity)
}

@Dao
interface HeartFieldDao {
    @Query("SELECT * FROM heart_field_states WHERE username = :username LIMIT 1")
    fun getHeartFieldForUser(username: String): Flow<HeartFieldEntity?>

    @Query("SELECT * FROM heart_field_states WHERE username = :username LIMIT 1")
    suspend fun getHeartFieldDirect(username: String): HeartFieldEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHeartField(state: HeartFieldEntity)
}

@Dao
interface FruitMemoryDao {
    @Query("SELECT * FROM fruit_memories WHERE username = :username AND fruitId = :fruitId ORDER BY date ASC")
    fun getMemoriesForFruit(username: String, fruitId: String): Flow<List<FruitMemoryEntity>>

    @Query("SELECT * FROM fruit_memories WHERE username = :username AND fruitId = :fruitId ORDER BY date ASC")
    suspend fun getMemoriesListForFruit(username: String, fruitId: String): List<FruitMemoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: FruitMemoryEntity)
}

@Dao
interface HomeMessageDao {
    @Query("SELECT * FROM home_messages WHERE username = :username LIMIT 1")
    fun getHomeMessageForUser(username: String): Flow<HomeMessageEntity?>

    @Query("SELECT * FROM home_messages WHERE username = :username LIMIT 1")
    suspend fun getHomeMessageDirect(username: String): HomeMessageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHomeMessage(message: HomeMessageEntity)
}
