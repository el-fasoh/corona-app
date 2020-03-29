package com.fasoh.corona.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fasoh.corona.R
import com.fasoh.corona.models.keepsafe.KeepSafe
import com.fasoh.corona.ui.keepsafe.KeepSafeAdapter
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_keep_safe.*

/**
 * A simple [Fragment] subclass.
 */
class KeepSafeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keep_safe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpScrollView()
    }

    private fun setUpScrollView(){
        val keepSafeAdapter = KeepSafeAdapter(getKeepSafeItems())
        scrollView.adapter = keepSafeAdapter
        scrollView.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.2f)
                .setMinScale(0.8f)
                .build())
    }

    private fun getKeepSafeItems() : List<KeepSafe>{
        val keepSafe1 = KeepSafe("wash_hands.json",resources.getString(R.string.one))
        val keepSafe2 = KeepSafe("social_distancing.json",resources.getString(R.string.three))
        val keepSafe3 = KeepSafe("stayathome.json",resources.getString(R.string.four))
        val keepSafe4 = KeepSafe("cough.json",resources.getString(R.string.five))
        val keepSafe5 = KeepSafe("clean.json",resources.getString(R.string.six))
        return listOf(keepSafe1, keepSafe2, keepSafe3, keepSafe4, keepSafe5)
    }

}
