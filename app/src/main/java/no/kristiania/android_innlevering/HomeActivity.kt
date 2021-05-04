package no.kristiania.android_innlevering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.*
import java.io.IOException


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView_home.layoutManager = LinearLayoutManager(this)
 //       recyclerView_home.adapter = MainAdapter()

        fetchJson()


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
                    recyclerView_home.adapter = MainAdapter(homeFeed)
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed")
            }

        })
    }
}


