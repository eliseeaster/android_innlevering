package no.kristiania.android_innlevering

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_buycc.*
import kotlinx.android.synthetic.main.activity_ccinfo.*
import no.kristiania.android_innlevering.data.PortfolioDatabase

//PAGE 5 - BUY CRYPTO CURRENCY
/*
- Screen shows a header containing the icon, name, symbol, recent rate of the cryptocurrency.
- User can enter USD values he wants to spend using a suitable number format input keyboard.
- Resultant value of the cryptocurrency currency will be automatically calculated using the recent rate for the cryptocurrency.
- Click action: On the Buy button click, the app stores the transaction into the database and updates the cryptocurrency account accordingly. If a user has less dollars than the entered amount, show error in Toast.
- After this app navigates back to screen 4. Note: Screen 4 must be refreshed to reflect the recents changes after the transaction.
*/


class BuyCCActivity : AppCompatActivity() {

    private var availableUsd: Double = 0.0
    private var inputUSD: Double = 0.0
    private var inputCrypto: Double = 0.0
    private var availableCurrency: Double = 0.0
    private var currencyAlreadyExist: Boolean = false
    private var dateTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buycc)

        val receivedIntent = intent
        val name = receivedIntent.getStringExtra("name").toString()
        val symbol = receivedIntent.getStringExtra("symbol").toString()
        val priceUsd = receivedIntent.getStringExtra("priceUsd").toString()
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol.toLowerCase()}@2x.png")
            .into(imageView2)

        getDataFromDb(symbol)

        Thread.sleep(100)

        val textView_name: TextView = findViewById(R.id.textView7) as TextView
        textView_name.text = name;

        val textView_amount: TextView = findViewById(R.id.editTextNumber3) as TextView
        textView_amount.text = priceUsd;

/*
            input_USD.addTextChangeListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    input_USD = s.toString().toDouble().round(4)
                    inputCrypto = inputUsd / priceUsd?.toDouble()?.round(4)!!
                    inputCrypto = result.round(4)
                    binding.tvCurrency.text = result.toString()
                }

            })*/


    }


    fun getDataFromDb(symbol: String, usdSymbol: String = "USD") {
        Thread {
            availableCurrency = PortfolioDatabase(applicationContext).PortfolioDao().getVolume(symbol)
            availableUsd = PortfolioDatabase(applicationContext).PortfolioDao().getVolume(usdSymbol)
            currencyAlreadyExist = PortfolioDatabase(applicationContext).PortfolioDao().currencyAlreadyExist(symbol)
        }.start()
    }

    private fun getUsdVolume() {
        Thread {
            availableUsd = PortfolioDatabase(applicationContext).get.getVolume("USD")
            tvUsd.text = buildSpannedString {
                append("You have ")
                bold { append("$availableUsd") }
                append(" USD")
            }
        }.start()
    }

    private fun getCurrencyVolume(symbol: String) {
        Thread {
            availableCurrency = PortfolioDatabase(applicationContext).getCurrencyDao().getVolume(symbol)
        }.start()
    }

    private fun isCurrencyExistAlready(symbol: String) {
        Thread {
            currencyAlreadyExist = PortfolioDatabase(applicationContext).getCurrencyDao().isCurrencyExistAlready(symbol)
        }.start()


    }
}