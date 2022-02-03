package com.ads.contactlistapp.activity

import android.os.Bundle
import android.view.*
import com.ads.contactlistapp.R
import kotlinx.android.synthetic.main.activity_toolbar.*

abstract class ToolbarActivity : BaseActivity() {

    override fun getLayoutResource(): Int {
        return R.layout.activity_toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView: View = findViewById(R.id.flToolbarContentContainer)
        if (contentView is ViewGroup) {
            contentView.addView(LayoutInflater.from(this).inflate(getToolbarLayoutResource(), contentView, false))
        }
        setSupportActionBar(toolbar)
    }

    protected abstract fun getToolbarLayoutResource(): Int

    open fun showToolbar() {
        toolbar.setVisibility(View.VISIBLE)
    }

    open fun hideToolbar() {
        toolbar.setVisibility(View.GONE)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}