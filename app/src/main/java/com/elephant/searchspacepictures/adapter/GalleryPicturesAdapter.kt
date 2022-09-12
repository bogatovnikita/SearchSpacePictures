package com.elephant.searchspacepictures.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.elephant.searchspacepictures.R
import com.elephant.searchspacepictures.databinding.ItemPictureBinding
import com.elephant.searchspacepictures.models.ResponseUrlPictures

class PicturesDiffCallback(
    private val oldList: List<ResponseUrlPictures>,
    private val newList: List<ResponseUrlPictures>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldString = oldList[oldItemPosition]
        val newString = newList[newItemPosition]
        return oldString.previewImage == newString.previewImage
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldString = oldList[oldItemPosition]
        val newString = newList[newItemPosition]
        return oldString == newString
    }
}

class GalleryPicturesAdapter(private val onPictureClickListener: (ResponseUrlPictures) -> Unit) :
    RecyclerView.Adapter<GalleryPicturesAdapter.PicturesViewHolder>() {

    var pictures: List<ResponseUrlPictures> = mutableListOf()
        set(value) {
            val diffCallback = PicturesDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder =
        PicturesViewHolder(parent) {
            onPictureClickListener(pictures[it])
        }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val item = pictures[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = pictures.size

    class PicturesViewHolder(parent: ViewGroup, private val clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_picture, parent, false)
        ) {
        private val binding = ItemPictureBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }

        fun bind(item: ResponseUrlPictures) {
            Glide.with(itemView.context)
                .load(item.previewImage)
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(binding.picture)
        }
    }


}