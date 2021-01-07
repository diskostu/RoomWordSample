package de.diskostu.android.roomwordsample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = true)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        // singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context): WordRoomDatabase {
            // if the INSTANCE is not null, the return it
            // if it is, then create the database
            return INSTANCE ?: createDatabase(context)
        }

        private fun createDatabase(context: Context): WordRoomDatabase {
            return synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance

                // return instance
                instance
            }
        }
    }
}