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
import kotlinx.android.synthetic.main.activity_sellcc.*
import kotlinx.android.synthetic.main.activity_ccinfo.*
import kotlinx.android.synthetic.main.portfolio_row.*
import no.kristiania.android_innlevering.data.Currencies
import no.kristiania.android_innlevering.data.Portfolio
import no.kristiania.android_innlevering.data.PortfolioDatabase
import no.kristiania.android_innlevering.data.Transactions
import no.kristiania.android_innlevering.utils.Extentions
import java.util.*

class SellCCActivity : AppCompatActivity() {

    private var availableUsd: Double = 0.0
    private var inputUSDollar: Double = 0.0
    private var result: Double = 0.0
    private var availableCurrency: Double = 0.0
    private var currencyAlreadyExist: Boolean = false
    private var dateTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sellcc)

        val receivedIntent = intent

        val name = receivedIntent.getStringExtra("name").toString()
        val symbol = receivedIntent.getStringExtra("symbol").toString()
        val priceUsd = receivedIntent.getStringExtra("priceUsd")?.toDouble()
        Picasso.get().load("https://static.coincap.io/assets/icons/${symbol.toLowerCase()}@2x.png")
            .into(imageView2)


        Thread.sleep(100)



        val textView_name: TextView = findViewById(R.id.textView7) as TextView
        textView_name.text = name;

        val textView_amount: TextView = findViewById(R.id.editTextNumber3) as TextView
        textView_amount.text = "${priceUsd}";


        getUsdVolume()


        crypto_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputUSDollar = s.toString().toDouble().round(4)
                result = inputUSDollar * priceUsd?.round(4)!!
                result = result.round(4)
                usd_result.text = result.toString()
            }

        })

        sell_button.setOnClickListener {
            if (inputUSDollar > availableUsd) {
                Toast.makeText(this@SellCCActivity, "Too high amount", Toast.LENGTH_LONG).show()
            } else if (inputUSDollar == 0.0 || inputUSDollar.toString()
                    .isBlank() || inputUSDollar.toString().isEmpty()
            ) {
                Toast.makeText(this@SellCCActivity, "You cannot enter blanc", Toast.LENGTH_LONG)
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
                                    "SOLD",
                                    "$result $symbol for $inputUSDollar USD$",
                                    dateTime
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
                                    "SOLD",
                                    "$result $symbol for $inputUSDollar USD$",
                                    dateTime
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
                    this@SellCCActivity,
                    "Your sale was successfull. $result $symbol",
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