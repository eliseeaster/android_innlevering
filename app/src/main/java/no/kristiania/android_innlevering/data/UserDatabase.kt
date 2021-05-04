package no.kristiania.android_innlevering.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null

        operator fun invoke(context: Context): UserDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): UserDatabase {
            return databaseBuilder(context, UserDatabase::class.java, "user")
                .fallbackToDestructiveMigration().build()
        }
    }
}
