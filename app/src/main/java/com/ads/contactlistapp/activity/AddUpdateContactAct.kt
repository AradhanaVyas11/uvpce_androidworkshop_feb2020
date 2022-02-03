package com.ads.contactlistapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.FirebaseFirestoreKey.MAIN_FOLDER_NAME
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_CONTACT_INFO
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_FROM
import com.ads.auronia.kotlinlearn1.commonclass.AppConstants.IntentPassDataKey.INTENT_FROM_ADD
import com.ads.auronia.kotlinlearn1.commonclass.AppUtils
import com.ads.auronia.kotlinlearn1.commonclass.CustomLoader
import com.ads.auronia.kotlinlearn1.commonclass.CustomValidator
import com.ads.auronia.kotlinlearn1.model.contactListModel.ContactListBean
import com.ads.contactlistapp.R
import com.ads.contactlistapp.commonClass.CustomDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_update_contact.*
import kotlinx.android.synthetic.main.row_contact_list.view.*
import java.util.*

class AddUpdateContactAct : ToolbarActivity(), View.OnClickListener {

    var isFromAdd = false
    var userId = ""
    lateinit var customLoader : CustomLoader
    lateinit var contactListBean : ContactListBean
    private var database : FirebaseFirestore? = null

    override fun getToolbarLayoutResource(): Int {
        return R.layout.activity_add_update_contact
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showToolbar()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        if (intent != null) {
            if (intent.hasExtra(INTENT_FROM)) {
                if (intent.getStringExtra(INTENT_FROM).equals(INTENT_FROM_ADD))
                    isFromAdd = true
            }
            if (intent.hasExtra(INTENT_CONTACT_INFO)) {
                contactListBean = intent.getSerializableExtra(INTENT_CONTACT_INFO) as ContactListBean
                userId = contactListBean.contactId!!
            }
        }

        title = getString(R.string.add_contact)
        if (!isFromAdd) {
            title = getString(R.string.edit_contact)
        }

        database = FirebaseFirestore.getInstance()
        customLoader = CustomLoader(this)

        initView()
    }

    private fun initView() {
        crdNext.setOnClickListener(this)
        tvSaveUpdate.text = getString(R.string.str_save)

        if (!isFromAdd && contactListBean!=null) {
            tvSaveUpdate.text = getString(R.string.str_update)
            if (!contactListBean.name.isNullOrBlank()) {
                etName.setText(contactListBean.name)
            }
            if (!contactListBean.nickName.isNullOrBlank()) {
                etNickName.setText(contactListBean.nickName)
            }
            if (!contactListBean.email.isNullOrBlank()) {
                etEmailAddress.setText(contactListBean.email)
            }
            if (!contactListBean.phoneNo.isNullOrBlank()) {
                etPhoneNumber.setText(contactListBean.phoneNo)
            }
            if (!contactListBean.bloodGroup.isNullOrBlank()) {
                edtBloodGroup.setText(contactListBean.bloodGroup)
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.crdNext -> {
                checkData()
            }
        }
    }

    private fun checkData() {
        if (!AppUtils.isInternetAvailable(this)) {
            CustomDialog(this, getString(R.string.err_no_internet), object: CustomDialog.SetCustomClick{
                override fun setClickListener() {
                   //// For Student > You can do any action over here.
                }
            }).show()
        } else if (TextUtils.isEmpty(etName.text.trim())) {
            CustomValidator.setError(etName, getString(R.string.err_empty_field))
        } else if (TextUtils.isEmpty(etNickName.text.trim())) {
            CustomValidator.setError(etNickName, getString(R.string.err_empty_field))
        } else if (TextUtils.isEmpty(etEmailAddress.text.trim())) {
            CustomValidator.setError(etEmailAddress, getString(R.string.err_empty_field))
        } else if (!CustomValidator.isValidEmail(etEmailAddress.text.toString())) {
            CustomValidator.setError(etEmailAddress, getString(R.string.err_invalid_email))
        } else if (TextUtils.isEmpty(etPhoneNumber.text?.trim())) {
            CustomValidator.setError(etPhoneNumber, getString(R.string.err_empty_field))
        } else if (TextUtils.isEmpty(edtBloodGroup.text?.trim())) {
            CustomValidator.setError(edtBloodGroup, getString(R.string.err_empty_field))
        } else {
            updateData()
        }
    }

    fun updateData() {
        if (!AppUtils.isInternetAvailable(this)) {
//            CustomDialog(this, getString(R.string.err_no_internet)).show()
            return
        }
        customLoader.show()

        var data = hashMapOf<String, String>()
        if (isFromAdd) {
            userId = UUID.randomUUID().toString()
        }

        data = hashMapOf("contactId" to userId
            , "name" to etName.text.toString()
            , "nickName" to etNickName.text.toString()
            , "email" to etEmailAddress.text.toString()
            , "phoneNo" to etPhoneNumber.text.toString()
            , "bloodGroup" to edtBloodGroup.text.toString())

        database?.collection(MAIN_FOLDER_NAME)?.document(userId)?.set(data)

        database!!.collection(MAIN_FOLDER_NAME).document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("TAGA", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    Log.d("TAGA", "Current data: ")
                    val returnIntent = Intent()
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                } else {
                    Log.d("TAGA", "Current data: null")
                }
                customLoader.dismiss()
            }

        /*FirebaseFirestore.getInstance()!!.collection(MAIN_FOLDER_NAME)
            .document(userId).set(data)

        database!!.collection(MAIN_FOLDER_NAME).document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("TAGA", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    Log.d("TAGA", "Current data: ")
                    val returnIntent = Intent()
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                } else {
                    Log.d("TAGA", "Current data: null")
                }
                customLoader.dismiss()
            }*/
    }
}