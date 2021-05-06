package no.kristiania.android_innlevering

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_buycc.*
import kotlinx.android.synthetic.main.activity_ccinfo.*
import kotlinx.android.synthetic.main.portfolio_row.*
import no.kristiania.android_innlevering.data.Currencies
import no.kristiania.android_innlevering.data.Portfolio
import no.kristiania.android_innlevering.data.PortfolioDatabase
import no.kristiania.android_innlevering.data.Transactions
import no.kristiania.android_innlevering.utils.Extentions
import java.util.*

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
    private var inputUSDollar: Double = 0.0
    private var result: Double = 0.0
    private var availableCurrency: Double = 0.0
    private var currencyAlreadyExist: Boolean = false
    private var dateTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buycc)

        val receivedIntent = intent

        val name = receivedIntent.getStringExtra("name").toString()
        val symbol = receivedIntent.getStringExtra("symbol").toString()
        val priceUsd = receivedIntent.getStringExtra("priceUsd")?.toDouble()
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol.toLowerCase()}@2x.png")
            .into(imageView2)

        //GET DATA FROM DATABASE

        Thread.sleep(100)

        val textView_name: TextView = findViewById(R.id.textView7) as TextView
        textView_name.text = name;

        val textView_amount: TextView = findViewById(R.id.editTextNumber3) as TextView
        textView_amount.text = "${priceUsd}";

        getUsdVolume()


        input_USD.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputUSDollar = s.toString().toDouble().round(4)
                result = inputUSDollar / priceUsd?.round(4)!!
                result = result.round(4)
                inputCrypto.text = result.toString();
            }

        })

        buy_button.setOnClickListener {
            if (inputUSDollar > availableUsd) {
                Toast.makeText(this@BuyCCActivity, "Too high amount", Toast.LENGTH_LONG).show()
            } else if (inputUSDollar == 0.0 || inputUSDollar.toString()
                    .isBlank() || inputUSDollar.toString().isEmpty()
            ) {
                Toast.makeText(this@BuyCCActivity, "You cannot enter blanc", Toast.LENGTH_LONG)
                    .show()
            } else {
                currencyAlreadyExist(symbol)

                getCurrencyVolume(symbol)

                Thread.sleep(100)

                dateTime = Extentions.dateTime(Date().time, "DD-MM-YYY HH:mm:ss")

                if (!currencyAlreadyExist) {
                    Thread {
                        PortfolioDatabase(applicationContext).PortfolioDao()
                            .insertCurrencies(Portfolio(symbol, result, priceUsd!!))

                        PortfolioDatabase(applicationContext).getTransactionsDao()
                            .insertTransactions(
                                Transactions(
                                    symbol,
                                    dateTime,
                                    "Purchased",
                                    "$result for $inputUSDollar USD$"

                                )
                            )

                        PortfolioDatabase(applicationContext).PortfolioDao()
                            .updateCurrencies(
                                Portfolio(
                                    "USD$",
                                    availableUsd - inputUSDollar,
                                    priceUsd!!
                                )
                            )
                    }.start()

                } else {
                    Thread {

                        PortfolioDatabase(applicationContext).PortfolioDao()
                            .updateCurrencies(
                                Portfolio(
                                    symbol,
                                    availableCurrency + result,
                                    priceUsd!!
                                )
                            )

                        PortfolioDatabase(applicationContext).getTransactionsDao()
                            .insertTransactions(
                                Transactions(
                                    symbol,
                                    dateTime,
                                    "Purchased",
                                    "$result for $inputUSDollar USD$"
                                )
                            )

                        PortfolioDatabase(applicationContext).PortfolioDao()
                            .updateCurrencies(
                                Portfolio(
                                    "USD",
                                    availableUsd - inputUSDollar,
                                    priceUsd!!
                                )
                            )
                    }.start()
                }
                Toast.makeText(
                    this@BuyCCActivity,
                    "Your purchase was successful. $result $symbol",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getUsdVolume() {
        Thread {
            availableUsd = PortfolioDatabase(applicationContext).PortfolioDao().getVolume("USD")
            tv_your_saldo.text = buildSpannedString {
                append("You have ")
                bold { append("$availableUsd") }
                append(" USD$")
            }
        }.start()
    }

    private fun getCurrencyVolume(symbol: String) {
        Thread {
            availableCurrency =
                PortfolioDatabase(applicationContext).PortfolioDao().getVolume(symbol)
        }.start()
    }

    private fun currencyAlreadyExist(symbol: String) {
        Thread {
            currencyAlreadyExist =
                PortfolioDatabase(applicationContext).PortfolioDao().currencyAlreadyExist(symbol)
        }.start()
    }
}