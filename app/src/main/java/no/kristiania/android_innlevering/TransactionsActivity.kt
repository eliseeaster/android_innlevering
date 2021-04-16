package no.kristiania.android_innlevering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//PAGE 7 - TRANSACTIONS
/*
This screen shows the list of all buying and selling transactions executed by the user.
These transactions were stored in the database table when the user clicked the buy and sell buttons on screen 5 and screen 6 respectively.
Note: This screen will also show installation reward transaction.
*/
class TransactionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

    }
}