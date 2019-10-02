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

class ArticleFeedAdapter(private var list: List<ArticleContentParcelable>?,
                         private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ArticleFeedAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
    }

    fun setItems(items: List<ArticleContentParcelable>){
        list = items
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        list?.apply {
            holder.bindView(this[position], listener)
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val articleTitleTV = itemView.findViewById<TextView>(R.id.articleTitleTV)
        private val articleDateTimeTV = itemView.findViewById<TextView>(R.id.articleDateTimeTV)
        private val articleIngressTV = itemView.findViewById<TextView>(R.id.articleIngressTV)
        private val articleIV = itemView.findViewById<ImageView>(R.id.articleIV)

        fun bindView(articleContentParcelable: ArticleContentParcelable,
            listener: OnItemClickListener) {
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


