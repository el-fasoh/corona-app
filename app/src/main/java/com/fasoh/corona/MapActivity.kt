package com.fasoh.corona

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import com.heetch.countrypicker.Utils
import kotlinx.android.synthetic.main.activity_map.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        webView.getSettings().setJavaScriptEnabled(true);
        // this function is used to access javascript from html page
       // webView.addJavascriptInterface(JavaScriptInterface(this), "AndroidNativeCode");
        // load file from assets folder

        webView.loadUrl("file:///android_asset/map.html");
    }

    inner class JavaScriptInterface internal constructor(//              // send value from java class to html javascript function
        var mContext: Context
    ) {
        //                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        view.loadUrl("javascript:setJson(" + jArray + ")");
//                    }
//                });

        //       }

        // method to send JsonArray to HTML
        @get:Throws(JSONException::class)
        @get:JavascriptInterface
        val valueJson: Unit
            get() {
                val jArray = JSONArray()
                var jObject = JSONObject()
                jObject.put("Country", "Germany")
                jObject.put("Popularity", "100")
                jObject.put("Temperature", "513")
                jArray.put(jObject)
                jObject = JSONObject()
                jObject.put("Country", "Brazil")
                jObject.put("Popularity", "200")
                jObject.put("Temperature", "112")
                jArray.put(jObject)
                jObject = JSONObject()
                jObject.put("Country", "India")
                jObject.put("Popularity", "300")
                jObject.put("Temperature", "417")
                jArray.put(jObject)
                jObject = JSONObject()
                jObject.put("Country", "Australia")
                jObject.put("Popularity", "400")
                jObject.put("Temperature", "819")
                jArray.put(jObject)

                jObject = JSONObject()
                jObject.put("Country", "Kenya")
                jObject.put("Popularity", "400")
                jObject.put("Temperature", "819")
                jArray.put(jObject)
                println(jArray.toString())
                //              // send value from java class to html javascript function
                this@MapActivity.runOnUiThread{
                        webView.loadUrl("javascript:setJson($jArray)");
                    }


                //       }
            }

    }
}
