package com.fasoh.corona.ui.globe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fasoh.corona.R
import com.fasoh.corona.models.timeline.TimelineDataItem
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*
import kotlin.collections.HashMap


class GeoChartFragment : Fragment() {

    private val viewModel: GeoChartViewModel by inject()
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.geo_chart_fragment, container, false)

        webView = view.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true;


        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Timber.e( consoleMessage.message())
                return true
            }
        }

        viewModel.setup(lifecycle)
        viewModel.getData()

        viewModel.data.observe(viewLifecycleOwner, Observer {
            Timber.e(it.size.toString())
            webView.addJavascriptInterface(JavaScriptInterface(it), "AndroidNativeCode")
            val extraHeaders: MutableMap<String, String> = HashMap()
            extraHeaders["Referer"] = "__file_url__//android_asset/map.html"
            webView.loadUrl("file:///android_asset/map.html",extraHeaders)
        })

        return view
    }

    inner class JavaScriptInterface internal constructor(val items: List<TimelineDataItem>) {
        @get:Throws(JSONException::class)
        @get:JavascriptInterface
        val valueJson: Unit
            get() {
                requireActivity().runOnUiThread {
                    val jArray = JSONArray()
                    items.forEach {
                        val jObject = JSONObject()
                        jObject.put("countryCode", Locale("",it.countryCode!!).displayName)
                        jObject.put("totalCases",it.totalCases)
                        jObject.put("totalDeaths", it.totalDeaths)
                        jArray.put(jObject)
                    }
                    webView.loadUrl("javascript:setJson($jArray)");
                }
            }
    }

}
