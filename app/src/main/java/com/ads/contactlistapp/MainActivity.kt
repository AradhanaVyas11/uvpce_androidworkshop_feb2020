package com.ads.contactlistapp

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ads.auronia.kotlinlearn1.model.contactListModel.ContactListBean
import com.ads.contactlistapp.activity.ToolbarActivity
import com.ads.contactlistapp.adapter.ContactListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ToolbarActivity(), View.OnClickListener {

    var adapterPremisesImgAdapter: ContactListAdapter? = null
    val mainList = ArrayList<ContactListBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showToolbar()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        title = getString(R.string.contact_list)

//        database = FirebaseFirestore.getInstance()
//        customLoader = CustomLoader(this)

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

//        readChatData()
    }

    override fun getToolbarLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun onClick(view: View?) {
       when(view?.id) {
           R.id.fabAdd ->{

           }
        }
    }
}