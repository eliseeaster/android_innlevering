package no.kristiania.android_innlevering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ccinfo.*
import kotlinx.android.synthetic.main.activity_ccinfo.view.*
import kotlinx.android.synthetic.main.crypto_row.view.*
import no.kristiania.android_innlevering.data.PortfolioDatabase


//PAGE 4 - CRYPTO CURRENCY INFORMATION
/*
- Screen shows a header containing the icon, name, symbol, recent rate of the cryptocurrency whose row was clicked on screen 2.
- Screen shows how much this cryptocurrency user owns and its value in dollars.
- Users can sell or buy this currency. Sell button should only be enabled if the user owns this cryptocurrency. Buy button should only be enabled if the user's dollar account is not empty.
Hint button.isEnabled = false | true
- Graph (BONUS TASK): Screen also shows a graph indicating fluctuations in the value of cryprocurrency over an interval
*/


class CCInfoActivity() : AppCompatActivity() {

    private var availableCurrency: Double = 0.0
    private var availableUsd: Double = 0.0
    private var result: Double = 0.0
    private var currencyAlreadyExist: Boolean = false
    private var symbol: String = ""
    private var name: String = ""
    private var priceUsd: String = ""
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ccinfo)

        val receivedIntent = intent
        name = receivedIntent.getStringExtra("name").toString()
        priceUsd = receivedIntent.getStringExtra("priceUsd").toString()
        id = receivedIntent.getStringExtra("id").toString()
        symbol = receivedIntent.getStringExtra("symbol").toString()

        //Gets icon from database
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol.toLowerCase()}@2x.png")
            .into(crypto_img)

        getDataFromDb(symbol)

        //Making sure we get to fetch the data from db
        Thread.sleep(100)

        //Trying to get the data to replace the value the layout
        val cryptoTitle: TextView = findViewById<TextView>(R.id.crypto_title)
        cryptoTitle.text = name;

        val cryptoAmount: TextView = findViewById<TextView>(R.id.crypto_amount)
        cryptoAmount.text = priceUsd;


        val btn_start_buy = findViewById<Button>(R.id.buy_btn)
        btn_start_buy.setOnClickListener{
            val intent = Intent(this@CCInfoActivity, BuyCCActivity::class.java)
            startActivity(intent)
        }


        val btn_start_sell = findViewById<Button>(R.id.sell_btn)
        btn_start_sell.setOnClickListener{
            val intent = Intent(this@CCInfoActivity, SellCCActivity::class.java)
            startActivity(intent)
        }
    }

    fun getDataFromDb(symbol: String, usdSymbol: String = "USD") {
        Thread {
            availableCurrency = PortfolioDatabase(applicationContext).PortfolioDao().getVolume(symbol)
            availableUsd = PortfolioDatabase(applicationContext).PortfolioDao().getVolume(usdSymbol)
            currencyAlreadyExist = PortfolioDatabase(applicationContext).PortfolioDao().currencyAlreadyExist(symbol)
        }.start()
    }



    override fun onResume(){
        super.onResume()
        getDataFromDb(symbol)

        }

}