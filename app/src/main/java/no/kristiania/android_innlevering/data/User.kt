package no.kristiania.android_innlevering.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "user_points") val userPoints: Int?,
    @ColumnInfo(name = "user_owned_currency") val userOwnedCurrency: String?
)