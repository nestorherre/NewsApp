package com.example.newsapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.data.Article
import com.example.newsapp.utils.Formatter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import java.text.Format

class RecyclerViewAdapter(private val listener: (Article) -> Unit): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private var myList = emptyList<Article>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myList.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myList[position]
        setBottomMarginOnLastItem(holder, position)
        holder.itemView.tvTime.text = Formatter.date(currentItem.publishedAt)
        holder.itemView.tvSource.text = currentItem.source.name
        holder.itemView.tvTitle.text = currentItem.title
        holder.itemView.tvAuthor.text = Formatter.stringLength(currentItem.author,22)
        Picasso.get().load(currentItem.urlToImage).into(holder.itemView.ivNews)

        holder.itemView.setOnClickListener { listener(currentItem) }
    }

    private fun setBottomMarginOnLastItem(holder: MyViewHolder, position: Int) {
        if (position == myList.size - 1){
            val param = holder.itemView.cardView.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(param.leftMargin, param.topMargin,param.rightMargin,20)
            holder.itemView.cardView.layoutParams = param
        }
    }

    fun setData(newList: List<Article>){
        myList = newList
        notifyDataSetChanged()
    }

}