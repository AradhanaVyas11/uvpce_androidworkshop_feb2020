package com.ads.contactlistapp.commonClass

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import com.ads.contactlistapp.R
import kotlinx.android.synthetic.main.dialog_custom.*

class CustomDialog : Dialog {

    var strMessage: String? = null
    var strNegativeBtn: String = Resources.getSystem().getString(android.R.string.cancel)
    var strPositiveBtn: String = Resources.getSystem().getString(android.R.string.ok)
//    var setCustomClick: SetCustomClick? = null
    var isNegativeBtnVisible: Boolean? = false
    var isOnlyMesgDisplay: Boolean? = false
    var setCustomClick: SetCustomClick?= null

    interface SetCustomClick {
        fun setClickListener()
    }

    constructor(context: Context, strMessage: String, isOnlyMesgDisplay : Boolean) : super(context) {
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.strMessage = strMessage
        this.isOnlyMesgDisplay = isOnlyMesgDisplay
    }

    constructor(context: Context, strMessage: String, setCustomClick: SetCustomClick? = null) : super(context) {
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.strMessage = strMessage
        this.setCustomClick = setCustomClick
    }

    constructor(context: Context, strMessage: String, isNegativeBtnVisible: Boolean? = false, setCustomClick: SetCustomClick? = null) : super(context) {
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.strMessage = strMessage
        this.setCustomClick = setCustomClick
        this.isNegativeBtnVisible = isNegativeBtnVisible
    }

    constructor(context: Context, strMessage: String, isNegativeBtnVisible: Boolean? = false, strNegativeBtn: String, strPositiveBtn: String, setCustomClick: SetCustomClick? = null) : super(context) {
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.strMessage = strMessage
        this.setCustomClick = setCustomClick
        this.isNegativeBtnVisible = isNegativeBtnVisible
        this.strNegativeBtn = strNegativeBtn
        this.strPositiveBtn = strPositiveBtn
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_custom)
        tvMessage.text = strMessage

        if (!isNegativeBtnVisible!!) btnNegative.visibility = View.GONE else btnNegative.visibility = View.VISIBLE
        if (isOnlyMesgDisplay!!) {
            llActionBtn.visibility = View.GONE
            tvTitle.visibility = View.GONE
            viewBlue.visibility = View.GONE
            tvTitle.setTypeface(tvTitle.getTypeface(), Typeface.BOLD);
        } else{
            llActionBtn.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            viewBlue.visibility = View.VISIBLE
            tvTitle.setTypeface(tvTitle.getTypeface(), Typeface.NORMAL);
        }

        if (strNegativeBtn!=null && !strNegativeBtn.trim().equals("")){
            btnNegative.setText(strNegativeBtn.trim())
        }

        if (strPositiveBtn!=null && !strPositiveBtn.trim().equals("")){
            btnPositive.setText(strPositiveBtn.trim())
        }

        btnPositive.setOnClickListener({
            dismiss()
            setCustomClick?.setClickListener()
        })

        btnNegative.setOnClickListener({
            dismiss()
        })
    }
}