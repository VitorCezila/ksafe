package com.cezila.passwordmanager.ui.password_detail_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cezila.passwordmanager.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class PasswordDetailFragment : Fragment(R.layout.fragment_password_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).visibility =
            View.GONE
    }
}