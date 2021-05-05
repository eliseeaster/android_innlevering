package no.kristiania.android_innlevering.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "currency_table")
data class Currencies(
    @PrimaryKey
    @ColumnInfo(name = "currency_symbol") val symbol: String,

    @ColumnInfo(name = "currency_volume") val volume: Double,

    @ColumnInfo(name = "currency_price") val priceUsd: Double


)