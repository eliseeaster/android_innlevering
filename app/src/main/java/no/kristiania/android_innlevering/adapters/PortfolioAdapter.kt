package no.kristiania.android_innlevering.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.kristiania.android_innlevering.R
import no.kristiania.android_innlevering.data.Portfolio
import no.kristiania.android_innlevering.round

class PortfolioAdapter(private var context: Context, private var portfolioList: List<Portfolio>, private var priceUsdData: HashMap<String,Double>) :
RecyclerView.Adapter<PortfolioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        return PortfolioViewHolder(LayoutInflater.from(context).inflate(R.layout.portfolio_row, parent, false))

    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {

        val item = portfolioList[position]

        val priceUsd = priceUsdData[item.symbol]
        val result = (item.volume * priceUsd!!).round(4)

        holder.itemView.findViewById<TextView>(R.id.volume_rates).text = "${item.volume.round(4)} x $priceUsd"
        holder.itemView.findViewById<TextView>(R.id.result).text = "$result USD$"
        Picasso.get().load("https://static.coincap.io/assets/icons/${item.symbol.toLowerCase()}@2x.png")
            .into(holder.itemView.findViewById<ImageView>(R.id.crypto_img))
    }

    override fun getItemCount(): Int {
        return portfolioList.size
    }

}


class PortfolioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}