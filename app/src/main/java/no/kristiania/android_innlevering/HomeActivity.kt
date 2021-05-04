package no.kristiania.android_innlevering

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import no.kristiania.android_innlevering.data.UserViewModel
import no.kristiania.android_innlevering.data.UserViewModelFactory
import okhttp3.*
import java.io.IOException


class HomeActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as UserApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //      recyclerView_home.setBackgroundColor(Color.BLUE);

        val adapter = HomeAdapter()
        recyclerView_home.layoutManager = LinearLayoutManager(this)
        //       recyclerView_home.adapter = MainAdapter()


        fetchJson()

        userViewModel.readAllData.observe(owner = this) {
                users -> users.let {adapter.submitList(it)}
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




