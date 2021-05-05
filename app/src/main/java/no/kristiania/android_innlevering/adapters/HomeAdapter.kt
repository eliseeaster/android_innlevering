package no.kristiania.android_innlevering

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.kristiania.android_innlevering.data.Homefeed
import no.kristiania.android_innlevering.R
import no.kristiania.android_innlevering.data.Data

class HomeAdapter(private val context: Context, private val homeFeed: Homefeed, private val clickListener: (Data) -> Unit):
    RecyclerView.Adapter<CustomViewHolder>(){
    lateinit var data: Data

    //numberOfItem
    override fun getItemCount(): Int {
        return homeFeed.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val cellForRow = layoutInflater.inflate(R.layout.crypto_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        data = homeFeed.data.get(position)
        holder.bindView(data, clickListener)
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.textView_crypto_title)
    private val symbol: TextView = view.findViewById(R.id.textView_crypto_symbol)
    private val priceUsd: TextView = view.findViewById(R.id.textView_crypto_value)
    private val changePercent24Hr: TextView = view.findViewById(R.id.textView_crypto_percentage)
    private val icon: ImageView = view.findViewById(R.id.textView_crypto_icon)

    @SuppressLint("SetTextI18n")
    fun bindView(data: Data, clickListener: (Data) -> Unit) {

        name.text = data.name
        symbol.text = data.symbol

        val price = data.priceUsd?.toDouble()?.toBigDecimal().toString()
        priceUsd.text = "$$price"

        val cryptoChangePercent24Hr = data.changePercent24Hr?.toDouble()?.toBigDecimal().toString()
        //setting the value to textview
        changePercent24Hr.text = "$cryptoChangePercent24Hr%"

        Picasso.get().load(
            "https://static.coincap.io/assets/icons/${
                data.symbol.toString().toLowerCase()
            }@2x.png"
        ).into(icon)

        itemView.setOnClickListener {
            clickListener(data)
        }
    }
}