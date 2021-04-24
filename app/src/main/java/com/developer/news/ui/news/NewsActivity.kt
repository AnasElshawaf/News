package com.developer.news.ui.news

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.news.R
import com.developer.news.model.News
import com.developer.news.ui.base.activity.BaseActivity
import com.developer.news.ui.news_details.NewsDetailsActivity
import com.developer.news.util.MainConstants.Companion.LANGUAGE_ARABIC
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.layout_fragment_container.*
import kotlinx.android.synthetic.main.layout_toolbar_home.*
import kotlinx.android.synthetic.main.side_menu_layout.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class NewsActivity : BaseActivity(), DrawerLayout.DrawerListener,
    NewsAdapter.ItemNewsClickListener {

    private lateinit var adapter: NewsAdapter
    private var res: Resources? = null
    private var dm: DisplayMetrics? = null
    private var conf: Configuration? = null

    private val isMenuButtonEnabled: Boolean
        get() = true

    private fun initActivityWindow() {
        res = resources
        dm = res?.displayMetrics
        conf = res?.configuration
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initActivityWindow()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        drawerLayout.addDrawerListener(this)
        if (isMenuButtonEnabled) {
            ivActionMenu?.setOnClickListener { handleMenuAction() }
        }

        initNavigationDrawerClicks()

        initNewsList()

        obServableViewModel()
    }

    private fun obServableViewModel() {
        var newsList: ArrayList<News.Datum> = ArrayList()
        val viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewModel.getNews()
        viewModel.newsMutableLiveData.observe(this, {
            if (it != null) {
              if (it.status=="error"){
                  showErrors(this,it.message)
              }else{
                  newsList.addAll(it.articles)
                  adapter.setList(newsList)
                  rvNews.hideShimmerAdapter()
              }
            }
        })

    }

    private fun initNewsList() {
        rvNews.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = NewsAdapter(this, this)
        rvNews.adapter = adapter
        rvNews.showShimmerAdapter()
    }

    private fun initNavigationDrawerClicks() {

        try {
            llDrawerExplore.setOnClickListener {
//                toggleDrawer(true)
                setSelectedItemDrawer(ivExploreSelected, "Explore")

            }

            llDrawerE_magazine.setOnClickListener {
//                toggleDrawer(true)
                setSelectedItemDrawer(ivE_magazineSelected, "E_magazine")

            }

            llDrawerGallery.setOnClickListener {
//                toggleDrawer(true)
                setSelectedItemDrawer(ivGallerySelected, "Gallery")

            }

            llDrawerLiveChat.setOnClickListener {
//                toggleDrawer(true)
                setSelectedItemDrawer(ivLiveChatSelected, "LiveChat")

            }

            llDrawerWishList.setOnClickListener {
//                toggleDrawer(true)
                setSelectedItemDrawer(ivWishListSelected, "WishList")

            }


        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun setSelectedItemDrawer(ivSelected: ImageView?, s: String) {
        when (ivSelected?.id) {

            R.id.ivExploreSelected -> {
                ivExploreSelected.visibility = View.VISIBLE
                ivLiveChatSelected.visibility = View.INVISIBLE
                ivE_magazineSelected.visibility = View.INVISIBLE
                ivGallerySelected.visibility = View.INVISIBLE
                ivWishListSelected.visibility = View.INVISIBLE
            }

            R.id.ivLiveChatSelected -> {
                ivExploreSelected.visibility = View.INVISIBLE
                ivLiveChatSelected.visibility = View.VISIBLE
                ivE_magazineSelected.visibility = View.INVISIBLE
                ivGallerySelected.visibility = View.INVISIBLE
                ivWishListSelected.visibility = View.INVISIBLE
            }

            R.id.ivE_magazineSelected -> {
                ivExploreSelected.visibility = View.INVISIBLE
                ivLiveChatSelected.visibility = View.INVISIBLE
                ivE_magazineSelected.visibility = View.VISIBLE
                ivGallerySelected.visibility = View.INVISIBLE
                ivWishListSelected.visibility = View.INVISIBLE
            }

            R.id.ivGallerySelected -> {
                ivExploreSelected.visibility = View.INVISIBLE
                ivLiveChatSelected.visibility = View.INVISIBLE
                ivE_magazineSelected.visibility = View.INVISIBLE
                ivGallerySelected.visibility = View.VISIBLE
                ivWishListSelected.visibility = View.INVISIBLE
            }

            R.id.ivWishListSelected -> {
                ivExploreSelected.visibility = View.INVISIBLE
                ivLiveChatSelected.visibility = View.INVISIBLE
                ivE_magazineSelected.visibility = View.INVISIBLE
                ivGallerySelected.visibility = View.INVISIBLE
                ivWishListSelected.visibility = View.VISIBLE
            }

        }
        showShortToast(s)
    }


    private fun handleMenuAction() {
        toggleDrawer(false)
    }

    @SuppressLint("WrongConstant")
    private fun toggleDrawer(isCloseDelayed: Boolean) {
        try {
            if (drawerLayout.isDrawerOpen(Gravity.START)) {
                if (!isCloseDelayed) {
                    drawerLayout.closeDrawer(Gravity.START)
                } else {
                    runOnUi(
                        runnable = { drawerLayout.closeDrawer(Gravity.START) },
                        delay = 30
                    )
                }

            } else {
                drawerLayout.openDrawer(Gravity.START)
            }
        } catch (e: java.lang.Exception) {
            Timber.e(e)
        }

    }


    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        if (conf?.locale?.displayLanguage.equals(
                Locale(LANGUAGE_ARABIC).displayLanguage,
                ignoreCase = true
            )
        ) {
            rlContainerView.translationX = -slideOffset * drawerView.width
        } else {
            rlContainerView.translationX = slideOffset * drawerView.width
        }
    }


    override fun onDrawerOpened(drawerView: View) {
        ivActionMenu?.setImageResource(R.drawable.ic_back)
    }

    override fun onDrawerClosed(drawerView: View) {
        ivActionMenu?.setImageResource(R.drawable.ic_menu)
    }

    override fun onDrawerStateChanged(newState: Int) {
    }

    override fun onNewsItemClickListener(item: News.Datum) {
        openActivityWithSlidingUpAndInfoInt(NewsDetailsActivity::class.java, item)
    }

}