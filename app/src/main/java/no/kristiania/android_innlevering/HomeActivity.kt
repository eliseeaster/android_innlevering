package no.kristiania.android_innlevering

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import no.kristiania.android_innlevering.data.User
import no.kristiania.android_innlevering.data.UserDatabase
import okhttp3.*
import java.io.IOException


class HomeActivity : AppCompatActivity()
{
    private var users = mutableListOf<User>()

    fun getAll(db: UserDatabase){
        Thread {
            val allUsers = db.userDao().getAll()
            allUsers.forEach(){
                users.add(it)
            }
        }.start()
        Thread.sleep(100)
    }

    fun populateDatabase(db: UserDatabase){
        val user = User(1, 0, "Bitcoin")
        val user2 = User(2, 50, "Dogecoin")
        Thread {
            db.userDao().addUser(user)
            db.userDao().addUser(user2)
        }.start()
        Thread.sleep(100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //USER POINTS BTN - LEADS TO PORTFOLIO
        val btn_user_points = findViewById(R.id.btn_user_points) as Button
        btn_user_points.setOnClickListener{
            val intent = Intent(this@HomeActivity, PortfolioActivity::class.java)
            startActivity(intent);
        }

        recyclerView_home.layoutManager = LinearLayoutManager(this)

        val db = UserDatabase(this)


        populateDatabase(db)
        fetchJson()
        println("\n\n--------------ALL USERS IN DB-------------- \n\n")
        getAll(db)
        users.forEach{
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


