package com.fasoh.corona.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.fasoh.corona.R
import com.google.android.material.snackbar.Snackbar

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val button: Button = root.findViewById(R.id.button)

        button.setOnClickListener {
            Snackbar.make(it, "Coming soon", Snackbar.LENGTH_LONG).show()
        }
        return root
    }
}