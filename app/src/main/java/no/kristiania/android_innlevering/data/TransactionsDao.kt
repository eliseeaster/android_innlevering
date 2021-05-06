package no.kristiania.android_innlevering.data;
import androidx.room.*

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(transactions: Transactions)

    @Query("SELECT * FROM transactions_table")
    fun fetchTransactions(): MutableList<Transactions>

}
