package no.kristiania.android_innlevering.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Currencies::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun CurrenciesDao(): CurrenciesDao

    companion object {
        @Volatile
        private var instance: CurrencyDatabase? = null

        operator fun invoke(context: Context): CurrencyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): CurrencyDatabase {
            return databaseBuilder(context, CurrencyDatabase::class.java, "currencies")
                .fallbackToDestructiveMigration().build()
        }
    }
}
