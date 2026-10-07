package com.example.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_accounts")
data class UserAccountEntity(
    @PrimaryKey val username: String,
    val password: String,
    val fullName: String,
    val isAdmin: Boolean = false
)

@Entity(tableName = "journal_entries")
data class JournalEntity(
    @PrimaryKey val id: String,
    val username: String,
    val title: String,
    val content: String,
    val moodsCsv: String,
    val category: String,
    val associatedFruit: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "prayer_chats")
data class PrayerChatEntity(
    @PrimaryKey val id: String,
    val username: String,
    val sender: String, // "user" or "god_ai"
    val text: String,
    val verse1925: String? = null,
    val reference: String? = null,
    val associatedFruit: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "tree_states")
data class TreeStateEntity(
    @PrimaryKey val username: String,
    val waterDrops: Int = 250,
    val rootsCount: Int = 3,
    val leavesCount: Int = 8,
    val branchesCount: Int = 2,
    val unlockedFruitsCsv: String = "love,peace,joy",
    val level: Int = 1,
    val isWilted: Boolean = false,
    val lastWatered: Long = System.currentTimeMillis()
)

@Entity(tableName = "heart_field_states")
data class HeartFieldEntity(
    @PrimaryKey val username: String,
    val isNegative: Boolean = false,
    val negativityType: String = "",
    val thornsLevel: Int = 0,
    val amenLeavesCsv: String = "",
    val amenFruitsCsv: String = "",
    val amenRootsCsv: String = "",
    val lastHealedAt: Long? = null
)

@Entity(tableName = "fruit_memories")
data class FruitMemoryEntity(
    @PrimaryKey val id: String,
    val username: String,
    val fruitId: String,
    val text: String,
    val date: Long = System.currentTimeMillis()
)

@Entity(tableName = "home_messages")
data class HomeMessageEntity(
    @PrimaryKey val username: String,
    val recentProblem: String,
    val verse1925: String,
    val reference: String,
    val blessing: String,
    val comfortText: String,
    val updatedAt: Long = System.currentTimeMillis()
)
