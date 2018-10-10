package com.xxl.hellomeizi.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.xxl.hellomeizi.R
import kotlinx.android.synthetic.main.activity_meizi_detail.*

class MeiziDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meizi_detail)
        val url = intent.getStringExtra("url")
        Glide.with(this)
                .load(url)
                .into(ui_photoView)
    }
}
