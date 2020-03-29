package com.fasoh.corona.ui.keepsafe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fasoh.corona.R
import com.fasoh.corona.models.keepsafe.KeepSafe
import kotlinx.android.synthetic.main.keep_safe_item.view.*

class KeepSafeAdapter (private val keepSafeList : List<KeepSafe>) : RecyclerView.Adapter<KeepSafeAdapter.KeepSafeViewHolder>() {

    inner class KeepSafeViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(keepSafe: KeepSafe){
            itemView.textViewDescription.text = keepSafe.keepSafeDescription
            itemView.lottieAnimationView.setAnimation(keepSafe.lottieAnimationPath)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeepSafeViewHolder {
        return KeepSafeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.keep_safe_item, null, false))
    }

    override fun getItemCount(): Int = keepSafeList.size

    override fun onBindViewHolder(holder: KeepSafeViewHolder, position: Int) = holder.bind(keepSafeList[position])
}