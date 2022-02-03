package com.ads.contactlistapp.adapter

import android.app.Activity
import android.content.Intent
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ads.activity.ContactListAct
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_CONTACT_INFO
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_FROM
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_FROM_EDIT
//import com.ads.auronia.kotlinlearn1.R
//import com.ads.auronia.kotlinlearn1.activity.AddUpdateContactAct
//import com.ads.auronia.kotlinlearn1.activity.ContactListAct
//import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_CONTACT_INFO
//import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_FROM
//import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_FROM_EDIT
import com.ads.auronia.kotlinlearn1.model.contactListModel.ContactListBean
import com.ads.contactlistapp.R
import com.ads.contactlistapp.activity.AddUpdateContactAct
import kotlinx.android.synthetic.main.row_contact_list.view.*

class ContactListAdapter: RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    val mContext: Activity
    private val list: ArrayList<ContactListBean>

    constructor(mContext: Activity, list: ArrayList<ContactListBean>) : super() {
        this.mContext = mContext
        this.list = list
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_contact_list, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!list.get(position).name.isNullOrBlank()) {
            holder.itemView.tvName.setText(list.get(position).name)
        }
        if (!list.get(position).nickName.isNullOrBlank()) {
            holder.itemView.tvNickName.setText(list.get(position).nickName)
        }
        if (!list.get(position).email.isNullOrBlank()) {
            holder.itemView.tvEmailAddress.setText(list.get(position).email)
        }
        if (!list.get(position).phoneNo.isNullOrBlank()) {
            holder.itemView.tvPhoneNumber.setText(list.get(position).phoneNo)
        }
        if (!list.get(position).bloodGroup.isNullOrBlank()) {
            holder.itemView.tvBloodGroup.setText(list.get(position).bloodGroup)
        }

        holder.itemView.ivEdit.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, AddUpdateContactAct::class.java)
            intent.putExtra(INTENT_CONTACT_INFO, list.get(position))
            intent.putExtra(INTENT_FROM, INTENT_FROM_EDIT)
            (holder.itemView.context as ContactListAct).startForResult.launch(intent)
        })

        holder.itemView.ivCancel.setOnClickListener(View.OnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,list.size);
        })
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}