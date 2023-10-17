package com.cezila.ksafe.ui.home_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cezila.ksafe.R
import com.cezila.ksafe.domain.model.Password

class HomeAdapter(
    private val passwords: List<Password?>,
    private val onItemClicked: (password: Password) -> Unit,
    private val onCopyClicked: (encryptedPassword: String) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconTextView: TextView = itemView.findViewById(R.id.tv_icon)
        val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        val loginTextView: TextView = itemView.findViewById(R.id.tv_login)
        val copyButton: ImageView = itemView.findViewById(R.id.btn_copy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.password_item, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val password = passwords[position]

        holder.iconTextView.text = extractInitials(password?.title ?: "")
        holder.titleTextView.text = password?.title
        holder.loginTextView.text = password?.login
        holder.copyButton.setOnClickListener {
            if (password != null) {
                onCopyClicked(password.encryptedPassword)
            }
        }
        holder.itemView.setOnClickListener {
            if (password != null) {
                onItemClicked(password)
            }
        }
    }

    override fun getItemCount(): Int {
        return passwords.size
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
}