package no.kristiania.android_innlevering

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import no.kristiania.android_innlevering.data.Homefeed
import kotlinx.android.synthetic.main.crypto_row.view.*
import no.kristiania.android_innlevering.data.Data

class HomeAdapter(private val context: Context, val homeFeed: Homefeed):
    RecyclerView.Adapter<CustomViewHolder>(){

    lateinit var data: Data


    //numberOfItem
    override fun getItemCount(): Int {
        return homeFeed.data.count()
    }


//GETS EACH ITEM IN THE RECYCLERVIEW, AND RETURNS THEM IN A CUSTOMVIEWHOLDER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val cellForRow = layoutInflater.inflate(R.layout.crypto_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    //
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val data = homeFeed.data?.get(position)

        holder?.view?.textView_crypto_title?.text = data.name
        holder?.view?.textView_crypto_symbol?.text = data.symbol
        holder?.view?.textView_crypto_value?.text = data.priceUsd.toString()
        holder?.view?.textView_crypto_percentage.text = ("" + data.changePercent24Hr + "%")

        val icon = holder?.view?.textView_crypto_icon
        val path = "https://static.coincap.io/assets/icons/" + data.symbol.toLowerCase() + "@2x.png"
        Picasso.get().load(path).into(icon);

        holder?.data = data

    }
}

class CustomViewHolder(val view: View, var data: Data? = null): RecyclerView.ViewHolder(view){

    companion object {
        val RECENT_RATES_KEY = "RECENT_RATES"

    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, CCInfoActivity::class.java)
            val stringPrice = data?.priceUsd.toString()
            intent.putExtra(RECENT_RATES_KEY, stringPrice)
            view.context.startActivity(intent)

            }
        }


    /* USER-POINTS-BUTTON (GOES TO PAGE XX)*/

/*
    init {
        btn_user_points.setOnClickListener {
            val intent = Intent(this, PortfolioActivity::class.java)
            Intent(this@HomeActivity, TransactionsActivity::class.java)
            startActivity(intent);*
        }
    }*/
    }