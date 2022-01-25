package com.mogga.news

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.mogga.news.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsListAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.myRecyclerView.layoutManager=LinearLayoutManager(this)
        mAdapter= NewsListAdapter(this)
        binding.myRecyclerView.adapter=mAdapter
        featchData()
       binding.swipeRefresh.setOnRefreshListener {
           featchData()
           binding.swipeRefresh.isRefreshing = false

       }



    }
   private fun featchData() {

       val url = "https://gnews.io/api/v4/top-headlines?token=0ed31fb6561a90797b5aa63f79e91303"
       val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null, {
           response ->



                 val newsJSONArray = response.getJSONArray("articles")
          val newsArray =ArrayList<News>()
          for (i in 0 until newsJSONArray.length()){
              val newsJsonObject = newsJSONArray.getJSONObject(i)
              val newsObject = newsJsonObject.getJSONObject("source")

              val news = News(
                  newsJsonObject.getString("title"),
                  newsObject.getString("name"),
                  newsJsonObject.getString("url"),
                  newsJsonObject.getString("image"),

              )
              newsArray.add(news)
          }
          mAdapter.updateNews(newsArray)
       },

           {
              Toast.makeText(this,"Error pls on Internet",Toast.LENGTH_LONG).show()

          }


      )
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)




   }



    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent=builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }




}