package no.kristiania.android_innlevering.data;

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.time.OffsetDateTime

@Entity(tableName = "transactions_table")
data class Transactions (

        @ColumnInfo(name = "currency_symbol")
        val symbol: String,

        @ColumnInfo(name = "transactions_date")
        val dateTime: String

        @ColumnInfo(name = "transactions_type")
        val type: String,

        @ColumnInfo(name = "transactions_details")
        val details: String

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactions_id")
    val id: Int = 0
}