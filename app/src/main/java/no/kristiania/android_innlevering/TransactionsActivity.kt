package no.kristiania.android_innlevering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_transactions.*
import no.kristiania.android_innlevering.adapters.TransactionsAdapter
import no.kristiania.android_innlevering.data.PortfolioDatabase
import no.kristiania.android_innlevering.data.Transactions

//PAGE 7 - TRANSACTIONS
/*
This screen shows the list of all buying and selling transactions executed by the user.
These transactions were stored in the database table when the user clicked the buy and sell buttons on screen 5 and screen 6 respectively.
Note: This screen will also show installation reward transaction.
*/
class TransactionsActivity : AppCompatActivity() {

    var transactionsList = mutableListOf<Transactions>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Android_innlevering)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

    supportActionBar.apply {
        title = "Transactions"
    }

        addData()
        rv_history_transactions.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        rv_history_transactions.adapter = TransactionsAdapter(transactionsList)

    }

    fun addData() {
        Thread {
            PortfolioDatabase(applicationContext).getTransactionsDao().fetchTransactions()
                .forEach(){
                    transactionsList.add(it)
                }
        }.start()
    }
}