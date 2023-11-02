package com.cezila.ksafe.ui.onboard_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cezila.ksafe.R

class OnBoardAdapter(
    private val onboardingItems: List<OnBoardingItem>
) : RecyclerView.Adapter<OnBoardAdapter.OnBoardingItemViewHolder>() {

    inner class OnBoardingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val onboardingImageView: ImageView = itemView.findViewById(R.id.iv_onboarding)
        val onboardingTitleView: TextView = itemView.findViewById(R.id.tv_title_onboarding)
        val onboardingDescriptionView: TextView =
            itemView.findViewById(R.id.tv_description_onboarding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_container_onboarding, parent, false)
        return OnBoardingItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        val onboardItem = onboardingItems[position]
        holder.onboardingImageView.setImageResource(onboardItem.imageId)
        holder.onboardingTitleView.text = onboardItem.title
        holder.onboardingDescriptionView.text = onboardItem.description
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }
}