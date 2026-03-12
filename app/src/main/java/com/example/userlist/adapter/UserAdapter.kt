package com.example.userlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.features.User

class UserAdapter(
    private var userList: List<User>,
    private val onUserClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // ViewHolder: This holds references to the views in one row.
    // We "find" the views once here so we don't have to keep searching for them
    // every time the user scrolls (which saves battery and CPU).
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.tv_name)
        val emailText: TextView = itemView.findViewById(R.id.tv_email)
    }

    // This method only runs for NEW rows.
    // Once the RecyclerView has enough "boxes" to fill the screen, it stops calling this.
    // Instead of destroying old rows, it sends them to a "Recycler Pool" to be used again.
    // So the next rows that appear when scrolling are resued rows not new.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        // inflate: turn into a kotlin object you can interact with
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    fun updateData(newUsers: List<User>) {
        this.userList = newUsers
        notifyDataSetChanged()
    }

    // This is called every time a row scrolls into view.
    // It takes the "box" (holder) and fills it with data from a specific position.
    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {
        val currentUser = userList[position]
        // update the UI with the data
        holder.nameText.text = currentUser.name
        holder.emailText.text = currentUser.email

        // Set the click listener on the whole row
        holder.itemView.setOnClickListener {
            onUserClick(currentUser)
        }
    }

    // return the user list size
    override fun getItemCount(): Int {
        return userList.size
    }

}