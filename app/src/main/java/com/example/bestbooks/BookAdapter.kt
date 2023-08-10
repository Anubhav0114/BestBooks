package com.example.bestbooks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init

class BookAdapter (private val books : List<BestSellerBook> , private val listener : mClickListener): RecyclerView.Adapter<BookAdapter.BookViewHolder>() {


    private var itemClickListener : ((position : Int, url : String) -> Unit)?  = null


    fun setOnClickListener (callback : (position : Int , url : String) -> Unit){
        itemClickListener = callback
    }



   inner class BookViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){

       val rank: TextView = itemView.findViewById(R.id.book_rank)
       val coverImg : ImageView = itemView.findViewById(R.id.book_cover)
       val title : TextView = itemView.findViewById(R.id.tv_title)
       val author : TextView = itemView.findViewById(R.id.tv_author)
       val description : TextView = itemView.findViewById(R.id.tv_description)
       val purchase : TextView = itemView.findViewById(R.id.purchase_btn)

       init {
           purchase.setOnClickListener {
               val position = adapterPosition
               if (position != RecyclerView.NO_POSITION){
//                   itemClickListener?.invoke(position , books[position].purchaseLink.toString())
                   listener.itemClick(books[position].purchaseLink.toString())
               }
           }
       }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent , false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
       return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.rank.text = book.rank
        Glide.with(holder.coverImg.context).load(book.coverImg).into(holder.coverImg)
        holder.title.text = book.title
        holder.author.text = book.author
        holder.description.text = book.description
    }
}
interface mClickListener {
    fun itemClick(url : String)
}