package com.elephant.searchspacepictures.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elephant.searchspacepictures.R
import com.elephant.searchspacepictures.databinding.ItemPictureBinding
import com.elephant.searchspacepictures.models.ResponseImages

class PicturesDiffCallback(
    private val oldList: List<ResponseImages>,
    private val newList: List<ResponseImages>
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

class GalleryPicturesAdapter(private val onPictureClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<GalleryPicturesAdapter.PicturesViewHolder>() {

    var pictures: List<ResponseImages> = mutableListOf()
        set(value) {
            val diffCallback = PicturesDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPictureBinding.inflate(inflater, parent, false)
        return PicturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val picture = pictures[position].previewImage
        Glide.with(holder.itemView.context)
            .load(picture)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.binding.picture)
    }

    override fun getItemCount(): Int = pictures.size

    class PicturesViewHolder(val binding: ItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root)
}