package no.kristiania.android_innlevering

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*

class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>(){

    override fun getItemCount(): Int {
        return homeFeed.data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
 //       val videoTitle = videoTitles.get(position)
        val data = homeFeed.data.get(position)

        holder?.view?.textView_video_title?.text = data.name
        holder?.view?.textView_symbol?.text = data.symbol
        holder?.view?.textView_value?.text = data.priceUsd.toString()
        holder?.view?.textView_percentage.text = ("" + data.changePercent24Hr + "%")

        val icon = holder?.view?.textView_icon
        val path = "https://static.coincap.io/assets/icons/" + data.symbol.toLowerCase() + "@2x.png"
        Picasso.get().load(path).into(icon);

    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, PortfolioActivity::class.java)
            view.context.startActivity(intent)
        }
        }
    }