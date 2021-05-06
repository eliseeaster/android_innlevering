package no.kristiania.android_innlevering

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_portfolio.*
import kotlinx.android.synthetic.main.portfolio_row.*
import kotlinx.android.synthetic.main.portfolio_row.view.*
import kotlinx.android.synthetic.main.crypto_row.view.*
import no.kristiania.android_innlevering.adapters.PortfolioAdapter
import no.kristiania.android_innlevering.data.Currencies
import no.kristiania.android_innlevering.data.Portfolio
import no.kristiania.android_innlevering.data.PortfolioDatabase
import okhttp3.*

//SCREEN 3

class PortfolioActivity : AppCompatActivity() {

    private var currenciesList = mutableListOf<Portfolio>()
    private var availableUSD: Double = 0.0
    private var priceUsdData = HashMap<String, Double>()
    private var totalCurrency: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)

        supportActionBar.apply { title = "Portfolio" }

        val receivedIntent = intent
        priceUsdData = receivedIntent.getSerializableExtra("map") as HashMap<String, Double>

        this.rvPortfolio.layoutManager = LinearLayoutManager(this)

        Thread.sleep(100)

        getAllCurrenciesVolume()
        getUsdVolume()

        for (i in 0 until currenciesList.size) {
            totalCurrency += (currenciesList[i].volume * currenciesList[i].priceUsd).round(4)
        }

        totalCurrency += availableUSD
        insertTotalCurrency(totalCurrency)

        Thread.sleep(100)

        btn_portfolio_user_points.text = "${totalCurrency.round(4)} USD$"
        rvPortfolio.adapter = PortfolioAdapter(this@PortfolioActivity, currenciesList, priceUsdData)


        // TRANSACTION-BUTTON (GOES TO PAGE 7)
        val btn_transactions = findViewById(R.id.btn_transactions) as Button
        // set on-click listener
        btn_transactions.setOnClickListener {
            val intent = Intent(this@PortfolioActivity, TransactionsActivity::class.java)
            startActivity(intent)
        }

    }

    fun getAllCurrenciesVolume() {

        Thread {
            PortfolioDatabase(applicationContext).PortfolioDao().getAllCurrencies("USD")
                .forEach(){
                    currenciesList.add(it)
                }
        }.start()
    }

    private fun getUsdVolume() {
        Thread {
            availableUSD = PortfolioDatabase(applicationContext).PortfolioDao().getVolume("USD")
        }.start()
    }

    private fun insertTotalCurrency(totalCurrencies: Double) {
        Thread {
            PortfolioDatabase(applicationContext).PortfolioDao().insertCurrencies(Portfolio("TOTAL", totalCurrencies, 1.0))
        }
    }


    private class PortfolioViewHolder(val customView: View): RecyclerView.ViewHolder(customView) {

    }

}