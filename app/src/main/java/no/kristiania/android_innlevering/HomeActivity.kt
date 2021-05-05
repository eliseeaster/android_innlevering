package no.kristiania.android_innlevering

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import no.kristiania.android_innlevering.data.Portfolio
import no.kristiania.android_innlevering.data.PortfolioDatabase
import no.kristiania.android_innlevering.data.Currencies
import no.kristiania.android_innlevering.data.Data
import okhttp3.*
import java.io.IOException
import java.util.*


class HomeActivity : AppCompatActivity()

{
    private var currencies = mutableListOf<Portfolio>()

    companion object {
        var data = mutableListOf<Data>()
    }

    fun getAll(db: PortfolioDatabase){

        Thread {
            val allCurrencies = db.PortfolioDao().getAllCurrencies("BTC")
            allCurrencies.forEach(){
                currencies.add(it)
            }
        }.start()
        Thread.sleep(100)
    }


    //POPULER DB MED 10.000 SOM I DEMS BUY
    fun populateDatabase(db: PortfolioDatabase){
        val currency1 = Portfolio("BTC", 0.1, 1.4)
        val currency2 =Portfolio("Etherium", 50.2, 4.2)
        Thread {
            db.PortfolioDao().addCurrencies(currency1)
            db.PortfolioDao().addCurrencies(currency2)
        }.start()
        Thread.sleep(100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btn_user_points = findViewById(R.id.btn_user_points) as Button
        btn_user_points.setOnClickListener{
            val intent = Intent(this@HomeActivity, PortfolioActivity::class.java)
            startActivity(intent)
        }
        //      recyclerView_home.setBackgroundColor(Color.BLUE);

        recyclerView_home.layoutManager = LinearLayoutManager(this)
        //       recyclerView_home.adapter = MainAdapter()


        val db = PortfolioDatabase(this)


        populateDatabase(db)
        fetchJson()
        println("\n\n--------------ALL USERS IN DB-------------- \n\n")
        getAll(db)
        currencies.forEach{
            println("user outside of thread: $it")
        }
    }

    fun fetchJson() {
        val url =
            "https://api.coincap.io/v2/assets"

        val request = Request.Builder().url(url).build()

        var client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, Currencies::class.java)
                data = homeFeed?.data as MutableList<Data>


                data.forEach{
                    println("name: ${it.name}")
                    println("symbol: ${it.symbol}")
                    println("priceUsd: ${it.priceUsd}")
                    println("id: ${it.id}")

                }


                runOnUiThread(){

                    recyclerView_home.adapter = HomeAdapter(this@HomeActivity, homeFeed) {
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
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed")
            }
        })
    }
}