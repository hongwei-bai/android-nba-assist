package com.hongwei.android_nba_assistant.compat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hongwei.android_nba_assistant.databinding.LayoutCalendarDayBinding

class CalendarListAdapter : RecyclerView.Adapter<CalendarListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutCalendarDayBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 14

    class MyViewHolder(binding: LayoutCalendarDayBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}