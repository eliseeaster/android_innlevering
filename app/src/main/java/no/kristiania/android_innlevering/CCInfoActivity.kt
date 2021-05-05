package no.kristiania.android_innlevering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button


//PAGE 4 - CRYPTO CURRENCY INFORMATION
/*
- Screen shows a header containing the icon, name, symbol, recent rate of the cryptocurrency whose row was clicked on screen 2.
- Screen shows how much this cryptocurrency user owns and its value in dollars.
- Users can sell or buy this currency. Sell button should only be enabled if the user owns this cryptocurrency. Buy button should only be enabled if the user's dollar account is not empty.
Hint button.isEnabled = false | true
- Graph (BONUS TASK): Screen also shows a graph indicating fluctuations in the value of cryprocurrency over an interval
*/


class CCInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ccinfo)

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
}