package com.developer.news.ui.news_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.developer.news.R
import com.developer.news.databinding.ActivityNewsDetailsBinding
import com.developer.news.model.News
import com.developer.news.ui.base.activity.BaseActivity
import com.developer.news.util.DateConverter
import com.developer.news.util.MainConstants
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class NewsDetailsActivity : BaseActivity(), View.OnClickListener {

    lateinit var news: News.Datum
    private lateinit var binding: ActivityNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_details)

        news = intent.extras?.get(MainConstants.INTENT_INFO_1) as News.Datum

        setDataBinding()

        setonClickListener()

    }

    private fun setDataBinding() {
        binding.news = news
        binding.urlToImage = news.urlToImage

        if (news.publishedAt != null)
            binding.tvNewsDate.text = DateConverter.parseDateToddMMyyyy(news.publishedAt)

    }

    private fun setonClickListener() {
        ivActionBack.setOnClickListener(this)
        btOpenWebsite.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.ivActionBack -> finish()
            R.id.btOpenWebsite -> goToWebSite()

        }

    }

    private fun goToWebSite() {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(news.url)
        startActivity(i)
    }
}