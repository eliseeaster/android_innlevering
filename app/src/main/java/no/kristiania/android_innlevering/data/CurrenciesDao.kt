package no.kristiania.android_innlevering.data

import androidx.room.*

@Dao
interface CurrenciesDao {



    //DEMS FUNKSJONER

    @Update
    fun updateCurrencies(currencies: Currencies)

    @Query("SELECT count(*)!=0 FROM currency_table WHERE currency_symbol = :currencySymbol ")
    fun currencyExists(currencySymbol: String): Boolean

    @Query("SELECT currency_volume FROM currency_table WHERE currency_symbol = :currencySymbol")
    fun getVolume(currencySymbol: String): Double

    @Query("SELECT * FROM currency_table WHERE currency_symbol!=(SELECT currency_symbol FROM currency_table WHERE currency_symbol =:currencySymbol)")
    fun getAllCurrencies(currencySymbol: String): MutableList<Currencies>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCurrencies(currencies: Currencies)

    @Query("SELECT count(*)!=0 FROM currency_table WHERE currency_symbol = :currencySymbol ")
    fun isCurrencyExistAlready(currencySymbol: String): Boolean


    /*

    VÅRE GAMLE EKSEMPEL-FUNKSJONER

    @Query("SELECT * FROM currency_table")
    fun getAll(): List<User>

    @Query("SELECT * FROM currency_table where uid = :uid ")
    fun getOne(uid: Int): Currencies

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: Currencies)

    @Query("DELETE FROM currency_table where uid = :uid")
    fun delete(uid: Int)*/
}