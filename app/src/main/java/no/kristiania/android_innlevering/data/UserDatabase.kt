import androidx.room.Database
import androidx.room.RoomDatabase
import no.kristiania.android_innlevering.data.User
import no.kristiania.android_innlevering.data.UserDao

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}