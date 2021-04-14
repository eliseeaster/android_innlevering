package no.kristiania.android_innlevering

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*




class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

  //      recyclerView_home.setBackgroundColor(Color.BLUE);

        recyclerView_home.layoutManager = LinearLayoutManager(this)
        recyclerView_home.adapter = MainAdapter()

        fetchJson()
    }

    fun fetchJson() {
        println("HALLO KAN DU HØRE MEG")

        val url =
            "https://api.letsbuildthatapp.com/youtube/home_feed?fbclid=IwAR1n92330MN9bpRG_y2n00wQ7ZOtGB58PectDv9xrZMUpS_WnHbfJvc5738"

        var client = OkH
    }
}



        /*

        val exampleList = generateDummyList(500)

        //brukt plugin under gradle module filen for å få dette til å funke
        recycler_view.adapter = ExampleAdapter(exampleList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

    }

    private fun generateDummyList(size: Int): List<ExampleItem> {

        val list = ArrayList<ExampleItem>()
        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_android
                1 -> R.drawable.mane
                else -> R.drawable.star
            }
            val item = ExampleItem(drawable, "Item $i", "Line 2")
            list += item
        }
        return list
    }
}*/