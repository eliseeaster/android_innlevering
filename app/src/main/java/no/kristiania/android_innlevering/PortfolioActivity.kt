package no.kristiania.android_innlevering

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_portfolio.*

//SCREEN 3

class PortfolioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)

        rvPortfolio.layoutManager = LinearLayoutManager(this)
        rvPortfolio.adapter = PortfolioAdapter()

        // TRANSACTION-BUTTON (GOES TO PAGE 7)
        val btn_click_me = findViewById(R.id.btn_transactions) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            val intent = Intent(this@PortfolioActivity, TransactionsActivity::class.java)
            startActivity(intent)
        }
    }

    private class PortfolioAdapter: RecyclerView.Adapter<PortfolioViewHolder>() {
        //HOW MANY ITEMS IN OUR LIST
        override fun getItemCount(): Int {
            return 5
        }
        //WHAT THE VIEW LOOKS LIKE
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val customView = layoutInflater.inflate(R.layout.portfolio_row, parent, false)

            /*val blueView = View(parent?.context)
            blueView.setBackgroundColor(Color.BLUE)
            blueView.minimumHeight = 50*/
            return PortfolioViewHolder(customView)
        }

        override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {

        }
    }

    private class PortfolioViewHolder(val customView: View): RecyclerView.ViewHolder(customView) {

    }

}