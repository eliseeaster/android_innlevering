package no.kristiania.android_innlevering

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import no.kristiania.android_innlevering.data.Currencies
import no.kristiania.android_innlevering.data.CurrencyDatabase
import okhttp3.*
import java.io.IOException
import java.util.*


class HomeActivity : AppCompatActivity()
{
    private var currencies = mutableListOf<Currencies>()

    fun getAll(db: CurrencyDatabase){
        Thread {
            val allCurrencies = db.CurrenciesDao().getAllCurrencies("BTC")
            allCurrencies.forEach(){
                currencies.add(it)
            }
        }.start()
        Thread.sleep(100)
    }

    fun populateDatabase(db: CurrencyDatabase){
        val currency1 = Currencies("BTC", 0.1, 1.4)
        val currency2 =Currencies("Etherium", 50.2, 4.2)
        Thread {
            db.CurrenciesDao().addCurrencies(currency1)
            db.CurrenciesDao().addCurrencies(currency2)
        }.start()
        Thread.sleep(100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btn_user_points = findViewById(R.id.btn_user_points) as Button
        btn_user_points.setOnClickListener{
            val intent = Intent(this@HomeActivity, PortfolioActivity::class.java)
        }
        //      recyclerView_home.setBackgroundColor(Color.BLUE);

        recyclerView_home.layoutManager = LinearLayoutManager(this)
        //       recyclerView_home.adapter = MainAdapter()


        val db = CurrencyDatabase(this)


        populateDatabase(db)
        fetchJson()
        println("\n\n--------------ALL USERS IN DB-------------- \n\n")
        println("\n\n--------------ALL USERS IN DB-------------- \n\n")
        println("\n\n--------------ALL USERS IN DB-------------- \n\n")
        println("\n\n--------------ALL USERS IN DB-------------- \n\n")
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

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                runOnUiThread{
                    recyclerView_home.adapter = HomeAdapter(homeFeed)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed")
            }
        })
    }
}


