package no.kristiania.android_innlevering.data;
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.time.OffsetDateTime

@Entity(tableName = "transactions_table")
data class Transactions (

        @ColumnInfo(name = "currency_symbol")
        var symbol: String,

        @ColumnInfo(name = "transactions_date")
        var dateTime: String,

        @ColumnInfo(name = "transactions_type")
        var type: String,

        @ColumnInfo(name = "transactions_details")
        var details: String

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactions_id")
    var id: Int = 0
}