
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import no.kristiania.android_innlevering.data.User
import no.kristiania.android_innlevering.data.UserDao


@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        @Volatile private var instance: UserDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): UserDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context, scope).also { instance = it }
    }

}
        private fun buildDatabase(context: Context, scope: CoroutineScope): UserDatabase {
            return databaseBuilder(context, UserDatabase::class.java, "user")
                .fallbackToDestructiveMigration().addCallback(UserDBCallback(scope)).build()
        }
    }

    private class UserDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
        }
    }
}