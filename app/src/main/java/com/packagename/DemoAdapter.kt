package com.packagename

import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.packagename.mynotesapp.R
import com.packagename.mynotesapp.databinding.ItemArticelBinding
import com.packagename.mynotesapp.models.ArticleDto
import com.packagename.mynotesapp.models.CreateArticleResponse
import com.packagename.mynotesapp.models.ProgrammingItem

class DemoAdapter(private val onArticleClicked:(ArticleDto) -> Unit):
    ListAdapter<ArticleDto, DemoAdapter.ViewHolder>(Diffutil()) {

   inner class ViewHolder(private val binding : ItemArticelBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: ArticleDto){
            Log.d("author", item.author.toString())
            binding.txtUserName.text = item.author.username
            binding.date.text = item.createdAt
            binding.textViewArticleDescription.text = item.description
            binding.textViewArticleTitle.text = item.title
            binding.txtUserCircle.text = item.author.username[0].toString()
            binding.root.setOnClickListener {
                onArticleClicked(item)
            }

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_articel,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class Diffutil : DiffUtil.ItemCallback<ArticleDto>(){
        override fun areItemsTheSame(oldItem: ArticleDto, newItem: ArticleDto): Boolean {
           return oldItem.slug == newItem.slug
        }

        override fun areContentsTheSame(
            oldItem: ArticleDto,
            newItem: ArticleDto
        ): Boolean {
            return oldItem == newItem
        }

    }
}