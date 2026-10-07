package com.example.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserAccountEntity::class,
        JournalEntity::class,
        PrayerChatEntity::class,
        TreeStateEntity::class,
        HeartFieldEntity::class,
        FruitMemoryEntity::class,
        HomeMessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userAccountDao(): UserAccountDao
    abstract fun journalDao(): JournalDao
    abstract fun prayerChatDao(): PrayerChatDao
    abstract fun treeStateDao(): TreeStateDao
    abstract fun heartFieldDao(): HeartFieldDao
    abstract fun fruitMemoryDao(): FruitMemoryDao
    abstract fun homeMessageDao(): HomeMessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "caysusong_devotional.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
