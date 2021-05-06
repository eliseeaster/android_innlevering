package no.kristiania.android_innlevering.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Portfolio::class, Transactions::class], version = 1, exportSchema = false)
abstract class PortfolioDatabase : RoomDatabase() {

    abstract fun PortfolioDao(): PortfolioDao
    abstract fun getTransactionsDao(): TransactionsDao

    companion object {
        @Volatile
        private var instance: PortfolioDatabase? = null

        operator fun invoke(context: Context): PortfolioDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PortfolioDatabase {
            return databaseBuilder(context, PortfolioDatabase::class.java, "portfolio")
                .fallbackToDestructiveMigration().build()
        }
    }
}
