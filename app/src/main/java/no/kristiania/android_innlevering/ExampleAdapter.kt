package no.kristiania.android_innlevering

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
/*
//ViewHolder representerer en enkelt rad i listen. Den refererer til ImageView og TextViews ved hjelp av ID.

class ExampleAdapter(private val exampleList: List<ExampleItem>) : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {

        //.inflate er metoden som gjør layout-filen til et view-object
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.example_item,
        parent, false)

        return ExampleViewHolder(itemView)
    }
//Denne funksjonen blir kalt igjen og igjen mens man scroller eller et item blir oppdatert med ny data.
    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = exampleList[position]

    holder.imageView.setImageResource((currentItem.imageResource))
    holder.textView1.text = currentItem.text1
    holder.textView2.text = currentItem.text2

    }

    //Hvor mange items det skal være i listen vår. Det skal være like mange som vi har i exampleList.
    override fun getItemCount() = exampleList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val textView1: TextView = itemView.findViewById(R.id.text_view_1)
        val textView2: TextView = itemView.findViewById(R.id.text_view_2)
    }
}
*/