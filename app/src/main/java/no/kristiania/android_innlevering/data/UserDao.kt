package no.kristiania.android_innlevering.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user where uid = :uid ")
    fun getOne(uid: Int): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Query("DELETE FROM user where uid = :uid")
    fun delete(uid: Int)
}