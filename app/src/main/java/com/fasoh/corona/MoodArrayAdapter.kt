package com.fasoh.corona

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.heetch.countrypicker.Country
import com.heetch.countrypicker.Utils
import kotlinx.android.synthetic.main.country_with_flag.view.*

class MoodArrayAdapter(
    ctx: Context,
    items: MutableList<Country>
) :
    ArrayAdapter<Country>(ctx, 0, items) {
    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.country_with_flag,
            parent,
            false
        )

        item?.let {
            val mipmapId: Int
            if(item.isoCode =="ALL"){
                mipmapId =R.mipmap.ic_globe
            }else{
                mipmapId = Utils.getMipmapResId(context,String.format("%s_flag",item.isoCode.toLowerCase()))
            }
            view.imageView.setImageResource(mipmapId)
            view.textView.text = item.isoCode
        }
        return view
    }
}