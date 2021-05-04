
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import no.kristiania.android_innlevering.data.User
import no.kristiania.android_innlevering.data.UserDao


@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        @Volatile private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
    }

}
        private fun buildDatabase(context: Context): UserDatabase {
            return databaseBuilder(context, UserDatabase::class.java, "user").build()
        }
    }
}