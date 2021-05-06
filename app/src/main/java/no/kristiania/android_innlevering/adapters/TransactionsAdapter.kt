package no.kristiania.android_innlevering.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_transactions.*
import no.kristiania.android_innlevering.R
import no.kristiania.android_innlevering.data.Transactions

class TransactionsAdapter(private var transactionsList: List<Transactions>) :
RecyclerView.Adapter<TransactionViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.transactions_row, parent, false))
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {


        val listItem = transactionsList[position]

//MAKES TEXT GREEN/RED BASED ON PURCHASETYPE
        if (listItem.type == "PURCHASED") {
            holder.itemView.findViewById<TextView>(R.id.tv_bought_sold).apply {
                text = listItem.type
                //setting blue color
                setTextColor(Color.parseColor("##008000"))
            }
        } else if (listItem.type == "SOLD" || listItem.type == "Installation Reward") {
            holder.itemView.findViewById<TextView>(R.id.tv_bought_sold).apply {
                text = listItem.type
                //setting red color
                setTextColor(Color.parseColor("#ff0000"))
            }
        }

        //SETS STARTSUM TO 10.000$, MAKES TEXT GREEN
        if (listItem.details == "10000.00 $") {
            holder.itemView.findViewById<TextView>(R.id.tv_transaction_history_detail).apply {
                text = listItem.details
                setTextColor(Color.parseColor("#008000"))
            }
        } else {

            //!!!!MIGHT REMOVE THIS???
            holder.itemView.findViewById<TextView>(R.id.tv_transaction_history_detail).text = listItem.details
        }

        holder.itemView.findViewById<TextView>(R.id.tv_date_time).text = listItem.dateTime

        Picasso.get().load("https://static.coincap.io/assets/icons/${listItem.symbol.toLowerCase()}@2x.png")
            .into(holder.itemView.findViewById<ImageView>(R.id.transactions_img))

    }

    //RETURNS ENTIRE LIST OF TRANSACTIONHISTORY
    override fun getItemCount(): Int {
        return transactionsList.size
    }

}

class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}