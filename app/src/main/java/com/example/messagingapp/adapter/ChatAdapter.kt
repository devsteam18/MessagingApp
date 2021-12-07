package com.example.messagingapp.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.R
import com.example.messagingapp.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatAdapter(private val context: Context, private val chatList: ArrayList<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val MESSAGE_TYPE_LEFT = 0
    private val MESSAGE_TYPE_RIGHT = 1
    var firebaseUser: FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == MESSAGE_TYPE_RIGHT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.right_message, parent, false)
            return ViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.left_message, parent, false)
            return ViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chatList[position]

        //getting date and displaying as today and yesterday
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE,0)

        val Today = dateFormat.format(cal.time)

        cal.add(Calendar.DATE,-1)

        val yesterday = dateFormat.format(cal.time)

        if(chat.date == Today){
            holder.tvDate.setText("Today")
        }else if (chat.date == yesterday){
            holder.tvDate.setText("Yesterday")

        }else{
            holder.tvDate.setText(chatList[position].date)

        }

          //comparing two dates and displaying it

        if (position > 0) {
            if (chatList[position].date.equals(chatList[position-1].date)) {
                holder.tvDate.setVisibility(View.GONE);
            } else {
                holder.tvDate.setVisibility(View.VISIBLE);
            }
        } else {
            holder.tvDate.setVisibility(View.VISIBLE);
        }

        if (position == MESSAGE_TYPE_RIGHT) {
            holder.txtUserName.text = chat.message
            holder.tvTime.text = chat.time
        } else {

            holder.txtUserName.text = chat.message
            holder.tvTime.text = chat.time
        }

        //Glide.with(context).load(user.profileImage).placeholder(R.drawable.profile_image).into(holder.imgUser)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtUserName: TextView = view.findViewById(R.id.tvMessage)
        val tvTime: TextView  = view.findViewById(R.id.time)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val imgUser: CircleImageView = view.findViewById(R.id.userImage)
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (chatList[position].senderId == firebaseUser!!.uid) {
            return MESSAGE_TYPE_RIGHT
        } else {
            return MESSAGE_TYPE_LEFT
        }

    }
}
