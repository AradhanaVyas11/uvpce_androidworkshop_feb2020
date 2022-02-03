package com.ads.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.FirebaseFirestoreKey.MAIN_FOLDER_NAME
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_FROM
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_FROM_ADD
import com.ads.auronia.kotlinlearn1.commonclass.AppUtils.toast
import com.ads.auronia.kotlinlearn1.commonclass.CustomLoader
import com.ads.auronia.kotlinlearn1.model.contactListModel.ContactListBean
import com.ads.contactlistapp.R
import com.ads.contactlistapp.activity.AddUpdateContactAct
import com.ads.contactlistapp.activity.ToolbarActivity
import com.ads.contactlistapp.adapter.ContactListAdapter
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_contact_list.*

class ContactListAct: ToolbarActivity(), View.OnClickListener {

    var adapterPremisesImgAdapter: ContactListAdapter? = null
    val mainList = ArrayList<ContactListBean>()

    lateinit var customLoader : CustomLoader
    private var database : FirebaseFirestore? = null


    override fun getToolbarLayoutResource(): Int {
        return R.layout.activity_contact_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showToolbar()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        title = getString(R.string.contact_list)

        customLoader = CustomLoader(this)
        database = FirebaseFirestore.getInstance()


        initView()
    }

    private fun initView() {
        fabAdd.setOnClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapterPremisesImgAdapter = ContactListAdapter(this as Activity, mainList)
        recyclerView.adapter = adapterPremisesImgAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    fabAdd.visibility = View.GONE
                } else {
                    fabAdd.visibility = View.VISIBLE
                }
            }
        })

        readChatData()
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            readChatData()
        }

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.fabAdd -> {
                val intent = Intent(this, AddUpdateContactAct::class.java)
                intent.putExtra(INTENT_FROM, INTENT_FROM_ADD)
                startForResult.launch(intent)
            }
        }
    }

    fun readChatData() {
        mainList.clear()
        customLoader.show()


        database!!.collection("/${MAIN_FOLDER_NAME}").get()
            .addOnSuccessListener(OnSuccessListener<QuerySnapshot> { queryDocumentSnapshots ->
                mainList.clear()
                if (!queryDocumentSnapshots.isEmpty) {
                    val list = queryDocumentSnapshots.documents
                    for (d in list) {
                        Log.w("TAGA","DocumentSnapshot added with ID:${d.id}")
                        Log.w("TAGA","DocumentSnapshot added with ID:${d.data}")
                        val c: ContactListBean? = d.toObject(ContactListBean::class.java)
                        if (c != null) {
                            mainList.add(c)
                        }
                    }
                    recyclerView?.adapter!!.notifyDataSetChanged()
                } else {
                    recyclerView?.adapter!!.notifyDataSetChanged()
                    // if the snapshot is empty we are displaying a toast message.
                    toast(resources.getString(R.string.toast_no_data))
                }
                customLoader.dismiss()
            })
            .addOnFailureListener(OnFailureListener {
                recyclerView?.adapter!!.notifyDataSetChanged()
                toast(resources.getString(R.string.toast_fail_to_get_data))
                customLoader.dismiss()
            })
    }
}