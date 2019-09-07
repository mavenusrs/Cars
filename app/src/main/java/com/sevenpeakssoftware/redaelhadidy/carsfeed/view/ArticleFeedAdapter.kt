package com.sevenpeakssoftware.redaelhadidy.carsfeed.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sevenpeakssoftware.redaelhadidy.carsfeed.R
import com.sevenpeakssoftware.redaelhadidy.carsfeed.common.DateHandler
import com.sevenpeakssoftware.redaelhadidy.carsfeed.model.ArticleContentParcelable
import com.squareup.picasso.Picasso

class ArticleFeedAdapter(private val list: List<ArticleContentParcelable>,
                         private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ArticleFeedAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articleItem = list[position]
        holder.bindView(articleItem, listener)
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(
            articleContentParcelable: ArticleContentParcelable,
            listener: OnItemClickListener
        ) {
            with(itemView) {
                val articleTitleTV = findViewById<TextView>(R.id.articleTitleTV)
                val articleDateTimeTV = findViewById<TextView>(R.id.articleDateTimeTV)
                val articleIngressTV = findViewById<TextView>(R.id.articleIngressTV)
                val articleIV = findViewById<ImageView>(R.id.articleIV)

                articleTitleTV.text = articleContentParcelable.title
                articleContentParcelable.dateTime?.also {
                    articleDateTimeTV.text =
                        DateHandler(itemView.context).toFormatedDateTime(it)
                }

                articleIngressTV.text = articleContentParcelable.ingress
                Picasso.get().load(articleContentParcelable.image)
                    .placeholder(R.drawable.place_holder)
                    .into(articleIV)
                itemView.setOnClickListener { listener.onItemClick(articleContentParcelable) }


            }


        }
    }
}


