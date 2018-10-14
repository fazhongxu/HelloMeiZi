package com.xxl.hellomeizi.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.xxl.hellomeizi.R
import com.xxl.hellomeizi.adapter.MeiZiPagerAdapter
import com.xxl.netcore.bean.MenuBean
import com.xxl.netcore.jsoup.JsoupFactory
import com.xxl.netcore.jsoup.MenuSoup
import com.xxl.netcore.net.Callback
import com.xxl.netcore.net.HttpClient
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

@Route(path = "/test/MainActivity")
class MainActivity : AppCompatActivity() {
    val HOST_MOBILE_URL : String = "http://m.mzitu.com" //http://www.mzitu.com

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val httpClient = HttpClient()

            httpClient.get(HOST_MOBILE_URL, object : Callback {
                override fun onFailure(e: IOException?) {
                    //Log.e("aaa",e?.message)
                }

                override fun onResponse(response: String?) {

                    runOnUiThread {
                        kotlin.run {
                            val values = JsoupFactory.parseHtml(MenuSoup::class.java, response)
                            //Log.e("aaa", "" + values.size)

                            if (values != null) {
                                var localMenu: ArrayList<String>? = ArrayList()
                                var fragments: ArrayList<Fragment>? = ArrayList()
                                val menus = values.get(MenuSoup::class.java.simpleName)
                                if (menus != null) {
                                    val list = menus as List<MenuBean>
                                    for (menuBean in list) {
                                        localMenu?.add(menuBean.title)
                                        val meiZiFragment = MeiZiFragment.newInstance(menuBean.title, menuBean.url)  // 创建fragment
                                        fragments?.add(meiZiFragment)
                                        //fragments?.removeAt(fragments?.size-1)
                                    }
                                    var meiZiPagerAdapter = MeiZiPagerAdapter(supportFragmentManager, fragments, localMenu)
                                    activity_main_vp.adapter = meiZiPagerAdapter
                                    activity_main_tl.setupWithViewPager(activity_main_vp)
                                }
                            }
                        }
                    }
                }
            })

    }

}
