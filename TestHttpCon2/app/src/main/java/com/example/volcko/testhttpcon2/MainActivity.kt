package com.example.volcko.testhttpcon2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import com.example.volcko.testhttpcon.JSONDownloader

class MainActivity : AppCompatActivity() {

    private var jsonURL = "https://safe-falls-78094.herokuapp.com/meal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gv = findViewById<GridView>(R.id.myGridView)

        val downloadBtn = findViewById<Button>(R.id.downloadBtn)
        downloadBtn.setOnClickListener { JSONDownloader(this, jsonURL, gv).execute() }

    }
}
