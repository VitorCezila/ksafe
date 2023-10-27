package com.cezila.ksafe.ui.home_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cezila.ksafe.R
import com.cezila.ksafe.domain.model.Password

class HomeAdapter(
    private val onItemClicked: (password: Password) -> Unit,
    private val onCopyClicked: (encryptedPassword: String) -> Unit
) : ListAdapter<Password, HomeAdapter.HomeViewHolder>(DiffCallback()) {

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconTextView: TextView = itemView.findViewById(R.id.tv_icon)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val loginTextView: TextView = itemView.findViewById(R.id.tv_login)
        private val copyButton: ImageView = itemView.findViewById(R.id.btn_copy)

        fun bind(password: Password) {
            iconTextView.text = extractInitials(password.title)
            titleTextView.text = password.title
            loginTextView.text = password.login
            copyButton.setOnClickListener {
                onCopyClicked(password.encryptedPassword)
            }
            itemView.setOnClickListener {
                onItemClicked(password)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.password_item, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val password = getItem(position)
        holder.bind(password)
    }

    private fun extractInitials(text: String): String {
        val words = text.split(" ")
        val initials = StringBuilder()
        for (word in words) {
            if (word.isNotEmpty()) {
                initials.append(word[0])
            }
        }
        return initials.toString().toUpperCase()
    }

    class DiffCallback : DiffUtil.ItemCallback<Password>() {
        override fun areItemsTheSame(oldItem: Password, newItem: Password) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Password, newItem: Password) =
            oldItem == newItem
    }
}