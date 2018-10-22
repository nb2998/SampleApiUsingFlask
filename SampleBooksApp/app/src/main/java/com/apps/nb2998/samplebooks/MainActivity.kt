package com.apps.nb2998.samplebooks

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var booksList: MutableList<Book>
    lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        booksList = mutableListOf<Book>()
        adapter = BookAdapter(this, booksList)
        recViewBooks.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recViewBooks.adapter = adapter

        addBooksToList()
        adapter.notifyDataSetChanged()
    }

    private fun addBooksToList() {
        val okhttpClient = OkHttpClient()
//        val request = Request.Builder().url("http://127.0.0.1:5000/api/v1/books/all").build()
        val request = Request.Builder().url("http://192.168.1.6:80/api/v1/books/all").build()
        okhttpClient.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("TAG", e.localizedMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                val gson = Gson()
                val list: MutableList<Book> = gson.fromJson(response.body()?.string(), Array<Book>::class.java).toMutableList()
                booksList.addAll(list)
                this@MainActivity.runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
                Log.d("TAG", booksList.size.toString())
            }

        })

    }
}