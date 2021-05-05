package no.kristiania.android_innlevering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ccinfo.*
import kotlinx.android.synthetic.main.activity_ccinfo.view.*
import kotlinx.android.synthetic.main.crypto_row.view.*
import no.kristiania.android_innlevering.data.CurrencyDatabase


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
    private var isCurrencyExistAlready: Boolean = false
    private var symbol: String = ""
    private var name: String = ""
    private var priceUsd: String = ""
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ccinfo)

        val receivedIntent = intent
        name = receivedIntent.getStringExtra("name").toString()
        symbol = receivedIntent.getStringExtra("symbol").toString()
        priceUsd = receivedIntent.getStringExtra("priceUsd").toString()
        id = receivedIntent.getStringExtra("id").toString()
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol.toLowerCase()}@2x.png")
            .into(crypto_img)

               

        getDataFromDb(symbol)

        Thread.sleep(100)

        val textView_name: TextView = findViewById(R.id.crypto_name) as TextView
        textView_name.text = name;

        val textView_amount: TextView = findViewById(R.id.crypto_amount) as TextView
        textView_amount.text = priceUsd;

        val image: ImageView = findViewById(R.id.crypto_img) as ImageView
        image

        val btn_start_buy = findViewById(R.id.buy_btn) as Button
        btn_start_buy.setOnClickListener{
            val intent = Intent(this@CCInfoActivity, BuyCCActivity::class.java)
            startActivity(intent)
        }

        val btn_start_sell = findViewById(R.id.sell_btn) as Button
        btn_start_sell.setOnClickListener{
            val intent = Intent(this@CCInfoActivity, SellCCActivity::class.java)
            startActivity(intent);
        }
    }

    fun getDataFromDb(symbol: String, usdSymbol: String = "USD") {
        Thread {
            availableCurrency = CurrencyDatabase(applicationContext).CurrenciesDao().getVolume(symbol)
            availableUsd = CurrencyDatabase(applicationContext).CurrenciesDao().getVolume(usdSymbol)
            isCurrencyExistAlready = CurrencyDatabase(applicationContext).CurrenciesDao().isCurrencyExistAlready(symbol)
        }.start()
    }



    override fun onResume(){
        super.onResume()
        getDataFromDb(symbol)

        }

}