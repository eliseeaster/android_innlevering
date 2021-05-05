package no.kristiania.android_innlevering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
/*
SCREEN 6 - SELL CRYPTO CURRENCY
- Screen shows a header containing the icon, name, symbol, recent rate of the cryptocurrency.
- Users can enter cryptocurrency values they want to sell using a suitable number format input keyboard.
- Resultant value of the dollars will be automatically calculated using recent rates.
- Click action: On the Sell button click, the app stores the transaction into the database and updates the cryptocurrency account accordingly. If a user has less cryptocurrency than the entered amount, show error in Toast.
- After this app navigates back to screen 4. Note: Screen 4 must be refreshed to reflect the recents changes after the transaction.
- On back app goes to screen 4
*/

class SellCCActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sellcc)



    }
}