package com.example.podcastsapp

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)












        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    //Toast.makeText(this, intent.getStringExtra(Intent.EXTRA_TEXT).toString(), Toast.LENGTH_SHORT).show()
                    var intent_received =   intent.getStringExtra(Intent.EXTRA_TEXT).toString()

                    var link = "https"+intent_received.split("https")[1]

                    var id = link.split("/v/")[1].split("?")[0]

                    Log.i("vieux_chat_errant", id)

                    var vod_link = "https://vod.544146.workers.dev/" + id

                    WebScratch(vod_link).execute()



                } else if (intent.type?.startsWith("image/") == true) {

                }
            }
            intent?.action == Intent.ACTION_SEND_MULTIPLE
                    && intent.type?.startsWith("image/") == true -> {

            }
            else -> {
                // Handle other intents, such as being started from the home screen
            }
        }








       //val mediaplayer = MediaPlayer()

        //mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        //mediaplayer.setDataSource("https://file10.ausha.co/wfeN0in1kEN0yBgbnh1DUv6qLpytxjS9cVJRWc5s.mp3?token=PP1evQhDv6k1Z0EH3_CIjw&expires=1654032304")
        //mediaplayer.prepare()
        //mediaplayer.start()
    }

    inner class WebScratch(vodlink: String) : AsyncTask<Void, Void, Void>() {
        private lateinit var words: String
        var linkk = vodlink

        override fun doInBackground(vararg params: Void): Void? {
            try {
                val document =  Jsoup.connect(linkk).get()
                words = document.text()
                var link_to_send =  words.split(" ")[0].toString()
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link_to_send))
                browserIntent.setPackage("com.kiwibrowser.browser");
                startActivity(browserIntent)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)

        }
    }
}