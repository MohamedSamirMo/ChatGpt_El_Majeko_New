package com.example.chatgptelmajeko.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.example.chatgptelmajeko.R
import com.example.chatgptelmajeko.models.messageModel

class MessageAdapter(var list:ArrayList<messageModel>): Adapter<MessageAdapter.MessageViewHolder>() {
    inner class MessageViewHolder(view: View): ViewHolder(view){

        val msgTxt=view.findViewById<TextView>(R.id.show_message)
        val imageContanter=view.findViewById<LinearLayout>(R.id.imageCard)
        val image=view.findViewById<ImageView>(R.id.image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        var view :View?=null
        var form=LayoutInflater.from(parent.context)
        if (viewType==0) {

         view=form.inflate(R.layout.chatrightitem,parent,false)}
           else{ view=form.inflate(R.layout.chatleftitem,parent,false)

        }
        return MessageViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val message=list[position]
        return if (message.isUser) 0 else 1


    }
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message=list[position]
        if (!message.isUser){
            holder.imageContanter.visibility= GONE
        }
        if (message.isImg){
            holder.imageContanter.visibility= VISIBLE
            Glide.with(holder.itemView.context).load(message.message).into(holder.image)

        }else holder.msgTxt.text=message.message
    }

    override fun getItemCount(): Int {
        return list.size
    }
}