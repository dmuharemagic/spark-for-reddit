package com.dmuharemagic.sparkforreddit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmuharemagic.sparkforreddit.model.RedditNewsDataResponse
import kotlinx.android.synthetic.main.post_item.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostsHolder>() {

    private val posts: MutableList<RedditNewsDataResponse> = mutableListOf()

    override fun getItemCount(): Int = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsHolder
            = PostsHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false))

    override fun onBindViewHolder(holder: PostsHolder, position: Int) = holder.bind(posts[position])

    fun update(items: List<RedditNewsDataResponse>) {
        val diffCallback = DiffCallback(posts, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        posts.clear()
        posts.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PostsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: RedditNewsDataResponse) {
            itemView.post_title.text = post.title
        }
    }
}

class DiffCallback(val oldList: List<RedditNewsDataResponse>,
                   val newList: List<RedditNewsDataResponse>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].title == newList[newItemPosition].title
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id
}