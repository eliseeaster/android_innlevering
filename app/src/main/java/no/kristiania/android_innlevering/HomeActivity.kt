package no.kristiania.android_innlevering

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.*
import java.io.IOException


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

  //      recyclerView_home.setBackgroundColor(Color.BLUE);

        recyclerView_home.layoutManager = LinearLayoutManager(this)
 //       recyclerView_home.adapter = MainAdapter()

        fetchJson()

        // get reference to button
        val btn_click_me = findViewById(R.id.button_home) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intent = Intent(this@HomeActivity, PortfolioActivity::class.java)
            startActivity(intent)
        }
    }


    fun fetchJson() {
        println("HALLO KAN DU HØRE MEG")

        val url =
            "https://api.coincap.io/v2/assets"

        val request = Request.Builder().url(url).build()

        var client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                runOnUiThread{
                    recyclerView_home.adapter = MainAdapter(homeFeed)
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed")
            }

        })
    }
}

class HomeFeed(val data: List<Data>)

class Data(val id: String, val name: String, val symbol: String, val priceUsd: Double, val changePercent24Hr: Double)

//name, symbol, recent value in dollars and negative or positive percentage change in the last 24 hours.



        /*

        val exampleList = generateDummyList(500)

        //brukt plugin under gradle module filen for å få dette til å funke
        recycler_view.adapter = ExampleAdapter(exampleList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

    }

    private fun generateDummyList(size: Int): List<ExampleItem> {

        val list = ArrayList<ExampleItem>()
        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_android
                1 -> R.drawable.mane
                else -> R.drawable.star
            }
            val item = ExampleItem(drawable, "Item $i", "Line 2")
            list += item
        }
        return list
    }
}*/