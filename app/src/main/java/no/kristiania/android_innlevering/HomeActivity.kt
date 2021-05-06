package no.kristiania.android_innlevering

import android.content.Intent
import android.os.Bundle
import android.provider.Contacts
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import no.kristiania.android_innlevering.data.*
import no.kristiania.android_innlevering.utils.Extentions
import no.kristiania.android_innlevering.utils.LocalPreferences
import okhttp3.*
import java.io.IOException
import java.lang.Thread.sleep
import java.util.*


class HomeActivity : AppCompatActivity() {

    private var loggedIn: Boolean = false

    private var priceUsdData = HashMap<String, Double>()
    private var currenciesList = mutableListOf<Portfolio>()
    private var availableUSD: Double = 0.0
    private var totalSum: Double = 0.0


    private var currencies = mutableListOf<Portfolio>()

    companion object {
        var data = mutableListOf<Data>()
    }

    fun getAll(db: PortfolioDatabase) {

        Thread {
            val allCurrencies = db.PortfolioDao().getAllCurrencies("BTC")
            allCurrencies.forEach() {
                currencies.add(it)
            }
        }.start()
        Thread.sleep(100)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btn_user_points = findViewById(R.id.btn_user_points) as Button
        btn_user_points.setOnClickListener {
            val intent = Intent(this@HomeActivity, PortfolioActivity::class.java)
            startActivity(intent)
        }

        supportActionBar.apply { title = "Home" }


        if (LocalPreferences.getInstance(this).getSaveStringValue("loggedIn") != null) {
            loggedIn = LocalPreferences.getInstance(this).getSaveStringValue("loggedIn")!!.toBoolean()
        }

        btn_user_points.text = "10000.00$"

        if(!loggedIn) {
            Thread {
                PortfolioDatabase(applicationContext).PortfolioDao()
                    .insertCurrencies(Portfolio("USD", 10000.00, 1.0))

                PortfolioDatabase(applicationContext).getTransactionsDao().insertTransactions(
                    Transactions(
                        "USD",
                        Extentions.dateTime(Date().time, "dd-MM-YYY HH:mm:ss"),
                        "Welcome Bonus",
                        "10000.00$"

                    )
                )
            }.start()
            sleep(100)
            LocalPreferences.getInstance(this).saveStringValue("loggedIn", "true")
            println("performed login")
        } else{
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
        }

        recyclerView_home.layoutManager = LinearLayoutManager(this)

        fetchJson()
        getCurrenciesVolume()
        getUsdVolume()
        getTotalSum()

        Thread.sleep(100)

        var saldo = 0.0
        for (i in 0 until currenciesList.size) {
            saldo += (currenciesList[i].volume * currenciesList[i].priceUsd.round(4))
        }

        saldo += availableUSD
        btn_user_points.text = "${saldo.round(4)}"
        btn_user_points.setOnClickListener {
            val intent = Intent(this, PortfolioActivity::class.java)
            intent.putExtra("map", priceUsdData)
            startActivity(intent)
        }
    }


    fun fetchJson() {
        val url =
            "https://api.coincap.io/v2/assets"

        val request = Request.Builder().url(url).build()

        var client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                val gson = GsonBuilder().create()

                val crypto = gson.fromJson(body, Currencies::class.java)
                data = crypto?.data as MutableList<Data>

                runOnUiThread() {

                    recyclerView_home.adapter = HomeAdapter(this@HomeActivity, crypto) {
                        val intent = Intent(this@HomeActivity, CCInfoActivity::class.java)
                        intent.putExtra("name", it.name)
                        intent.putExtra("symbol", it.symbol)
                        intent.putExtra("id", it.id)
                        intent.putExtra(
                            "priceUsd",
                            it.priceUsd?.toDouble()?.toBigDecimal().toString()
                        )



                        startActivity(intent)


                    }
                }
                for (elements in data) {
                    elements.priceUsd?.toDouble()?.round(2)
                        ?.let { priceUsdData.put(elements.symbol.toString(), it) }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed")
            }
        })
    }

    private fun getCurrenciesVolume(){
        Thread{
            PortfolioDatabase(applicationContext).PortfolioDao().getAllCurrencies("USD")
                .forEach(){
                    currenciesList.add(it)
                }
        }.start()
    }

    private fun getUsdVolume(){
        Thread {
            availableUSD = PortfolioDatabase(applicationContext).PortfolioDao().getVolume("USD")
        }.start()
    }

    private fun getTotalSum(){
        Thread {
            totalSum = PortfolioDatabase(applicationContext).PortfolioDao().getVolume("SALDO")
            btn_user_points.text = "${totalSum.round(4)} USD"
        }
    }

    override fun onStart() {
        super.onStart()

        getTotalSum()

        Thread.sleep(100)

        var saldo = 0.0
        for(i in 0 until currenciesList.size){
            saldo += (currenciesList[i].volume * currenciesList[i].priceUsd.round(4))
        }

        saldo += availableUSD

        btn_user_points.text = "${saldo.round(4)} USD"
    }

    override fun onResume() {
        super.onResume()

        getTotalSum()

        Thread.sleep(100)

        var saldo = 0.0
        for(i in 0 until currenciesList.size){
            saldo += (currenciesList[i].volume * currenciesList[i].priceUsd.round(4))
        }

        saldo += availableUSD

        btn_user_points.text = "${saldo.round(4)} USD"
    }
}


