package no.kristiania.android_innlevering

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

//SCREEN 3

class PortfolioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)

        // get reference to button
        val btn_click_me = findViewById(R.id.btn_transactions) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@PortfolioActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }

    }

}