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
import kotlinx.android.synthetic.main.crypto_row.view.*
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



        // TRANSACTION-BUTTON (GOES TO PAGE 7)
        val btn_transactions = findViewById(R.id.btn_transactions) as Button
        // set on-click listener
        btn_transactions.setOnClickListener {
            val intent = Intent(this@PortfolioActivity, TransactionsActivity::class.java)
            startActivity(intent)
        }

        //fetchJSON()


    }


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