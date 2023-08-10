package com.example.bestbooks

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import com.example.bestbooks.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject

class MainActivity : AppCompatActivity() , mClickListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var rv = binding.recyclerView
        val layoutManager = GridLayoutManager(this , 2)
        rv.layoutManager = layoutManager

        updateView(rv)



        Log.i("MainActivity" ,"The code before the API response")

    }

    private fun updateView(rv: RecyclerView) {
        val client = AsyncHttpClient()
        val params = RequestParams()

        var adapter : BookAdapter

        client["https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=UrxZJAjcBifkOnU1aCV2xHCCTWLHA4xw" ,
                params , object : JsonHttpResponseHandler(){
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.i("MainActivity" , "error while fetching data : $response")
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                        Log.i("MainActivity" , "on response success : ${json?.jsonObject.toString()}")
                        val resultJSON : JSONObject = json?.jsonObject?.get("results") as JSONObject
                        val bookRawJSON : String = resultJSON.get("books").toString()
                        Log.i("MainActivity" , "books : $bookRawJSON")
                        val gson = Gson()
                        val arrayBookType = object : TypeToken<List<BestSellerBook>>(){}.type
                        val list : List<BestSellerBook> = gson.fromJson(bookRawJSON , arrayBookType)

                        adapter = BookAdapter(list , this@MainActivity )
                        rv.adapter = adapter

                    }

                }
        ]


    }

    override fun itemClick(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW , Uri.parse(url))
        startActivity(browserIntent)
    }
}