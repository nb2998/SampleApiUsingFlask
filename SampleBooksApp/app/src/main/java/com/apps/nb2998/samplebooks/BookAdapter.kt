package com.apps.nb2998.samplebooks

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.single_row_book.view.*

class BookAdapter(val ctx: Context, val booksList: List<Book>): Adapter<BookAdapter.BookHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookHolder = BookHolder(LayoutInflater.from(ctx).inflate(R.layout.single_row_book, p0, false))

    override fun getItemCount(): Int = booksList.size

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.tvTitle.text = booksList[position].title
        holder.tvAuthor.text = booksList[position].author
        holder.tvDate.text = booksList[position].published.toString()
    }

    class BookHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView= itemView.tvTitle
        val tvAuthor: TextView= itemView.tvAuthor
        val tvDate: TextView= itemView.tvDate
    }
}