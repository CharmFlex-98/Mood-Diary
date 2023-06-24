package com.mooddiary.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mooddiary.databinding.MoodDiaryItemBinding
import com.mooddiary.model.MoodDiary

class MoodDiaryListAdapter(private val _onItemClicked: (MoodDiaryListAdapter, Int) -> Unit): ListAdapter<MoodDiary, MoodDiaryListAdapter.MoodDiaryViewHolder>(
    diffUtilCB
) {


    companion object {
        val diffUtilCB = object: DiffUtil.ItemCallback<MoodDiary>() {
            override fun areItemsTheSame(oldItem: MoodDiary, newItem: MoodDiary): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: MoodDiary, newItem: MoodDiary): Boolean {
                return newItem.id == oldItem.id
            }

        }
    }
    class MoodDiaryViewHolder(private val _binding: MoodDiaryItemBinding): ViewHolder(_binding.root) {
        fun bind(moodDiary: MoodDiary) {
            _binding.date.text = moodDiary.date.toString()
            _binding.title.text = moodDiary.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodDiaryViewHolder {
        val binding: MoodDiaryItemBinding = MoodDiaryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MoodDiaryViewHolder(binding)
        binding.root.setOnClickListener {
            _onItemClicked(this, holder.adapterPosition)
        }

        return holder
    }

    override fun onBindViewHolder(holder: MoodDiaryViewHolder, position: Int) {
        holder.adapterPosition
        holder.bind(currentList[position])
    }
}