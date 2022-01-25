package com.mogga.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listner:NewsItemClicked):RecyclerView.Adapter<MyViewHolder>() {
    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.itemview,parent,false)
        val viewHolder =   MyViewHolder(itemView)
        itemView.setOnClickListener {
            listner.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleText.text=items[position].title
        holder.nameText.text=items[position].name
        holder.urlText.text = items[position].url
       Glide.with(holder.itemView.context).load(items[position].imageUrl).into(holder.newsImage)

    }

    override fun getItemCount(): Int {
        return items.size

    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateNews(updateNews: ArrayList<News>){
        items.clear()
        items.addAll(updateNews)

        notifyDataSetChanged()

    }

}
class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    val titleText:TextView=itemView.findViewById(R.id.titleText)
    val nameText:TextView=itemView.findViewById(R.id.nameText)
val newsImage:ImageView = itemView.findViewById(R.id.newsImage)
    val urlText:TextView = itemView.findViewById(R.id.urlText)

}
interface NewsItemClicked{
    fun onItemClicked(item:News)
}