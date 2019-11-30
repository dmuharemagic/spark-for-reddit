package com.dmuharemagic.sparkforreddit

import android.content.Context
import androidx.room.*
import com.dmuharemagic.sparkforreddit.model.NestedDataTypeConverter
import com.dmuharemagic.sparkforreddit.model.RedditNews
import com.dmuharemagic.sparkforreddit.model.RedditNewsDataResponse

@Database(entities = arrayOf(RedditNews::class), version = 1, exportSchema = false)
@TypeConverters(value = [(NestedDataTypeConverter::class)])
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(
            context: Context
        ): AppDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "reddit.db"
            ).build()
        }
    }
}