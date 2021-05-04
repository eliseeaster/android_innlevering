package no.kristiania.android_innlevering

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_portfolio.*
import kotlinx.android.synthetic.main.portfolio_row.*
import kotlinx.android.synthetic.main.portfolio_row.view.*
import kotlinx.android.synthetic.main.video_row.view.*
import okhttp3.*

//SCREEN 3

class PortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)

        rvPortfolio.layoutManager = LinearLayoutManager(this)


        //TRYING TO CHANGE THE VALUE-TXT...
        val valueRecentRates = intent.getStringExtra("RECENT_RATES")

        if (valueRecentRates != null) {
          rvPortfolio.adapter = PortfolioAdapter(valueRecentRates)
        }


        val textView5 = findViewById<TextView>(R.id.textView)
        textView5.text = valueRecentRates

        //TRYING TO GET DATABASE
        /*val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "user_database_name"
        ).build()*/
/*
        val userDao = db.userDao()
        val users: List<User> = userDao.getAll()
        println(users)*/



        // TRANSACTION-BUTTON (GOES TO PAGE 7)
        val btn_click_me = findViewById(R.id.btn_transactions) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            val intent = Intent(this@PortfolioActivity, TransactionsActivity::class.java)
            startActivity(intent)
        }

        //fetchJSON()


    }
/*
    private fun fetchJSON() {
        val videoId = intent.getIntExtra(CustomViewHolder.RECENT_RATES_KEY, -1)
        val url = ""
        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()
        client.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Failed")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val currencies = gson.fromJson(body, Array<Data>::class.java)
            }
        })

    }

 */

        private class PortfolioAdapter(val rates: String): RecyclerView.Adapter<PortfolioViewHolder>() {
        //HOW MANY ITEMS IN OUR LIST
        override fun getItemCount(): Int {
            return 3
        }
        //WHAT THE VIEW LOOKS LIKE
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
                // how do we even create a view

            val layoutInflater = LayoutInflater.from(parent.context)
            val customView = layoutInflater.inflate(R.layout.portfolio_row, parent, false)
            return PortfolioViewHolder(customView)
        }

        override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
            println(rates)
            holder.customView.textView5?.text = rates
        }
    }

    private class PortfolioViewHolder(val customView: View): RecyclerView.ViewHolder(customView) {

    }

}