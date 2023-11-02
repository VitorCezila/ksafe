package com.cezila.ksafe.ui.analyze_password_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cezila.ksafe.R
import com.cezila.ksafe.ui.utils.showBottomNavView

class AnalyzePasswordFragment : Fragment(R.layout.fragment_analyze_password) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showBottomNavView(R.id.bottom_navigation_view)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}