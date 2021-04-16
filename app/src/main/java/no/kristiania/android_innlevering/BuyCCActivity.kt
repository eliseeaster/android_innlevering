package no.kristiania.android_innlevering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//PAGE 5 - BUY CRYPTO CURRENCY
/*
- Screen shows a header containing the icon, name, symbol, recent rate of the cryptocurrency.
- User can enter USD values he wants to spend using a suitable number format input keyboard.
- Resultant value of the cryptocurrency currency will be automatically calculated using the recent rate for the cryptocurrency.
- Click action: On the Buy button click, the app stores the transaction into the database and updates the cryptocurrency account accordingly. If a user has less dollars than the entered amount, show error in Toast.
- After this app navigates back to screen 4. Note: Screen 4 must be refreshed to reflect the recents changes after the transaction.
*/


class BuyCCActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buycc)

    }
}