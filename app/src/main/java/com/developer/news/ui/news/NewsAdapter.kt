package com.developer.news.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.news.R
import com.developer.news.model.News
import com.developer.news.util.DateConverter
import kotlinx.android.synthetic.main.news_item.view.*
import java.util.*

class NewsAdapter(
    private val context: Context,
    private var newsClickListener: ItemNewsClickListener
) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var list: List<News.Datum> = ArrayList()

    fun setList(list: MutableList<News.Datum>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = list[position]
        holder.bind(dataModel, newsClickListener)
    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {

        fun bind(
            item: News.Datum,
            newsClickListener: ItemNewsClickListener
        ) {

            itemView.tvNewsTittle.text = item.title
            itemView.tvNewsAuthor.text = item.author

            if (item.publishedAt != null)
                itemView.tvNewsDate.text = DateConverter.parseDateToddMMyyyy(item.publishedAt)

            Glide.with(itemView.context).load(item.urlToImage).error(R.drawable.placeholder)
                .into(itemView.ivNewsImage)

            itemView.lyContainer.setOnClickListener { newsClickListener.onNewsItemClickListener(item) }
        }

    }


    interface ItemNewsClickListener {

        fun onNewsItemClickListener(
            item: News.Datum
        )
    }
}

