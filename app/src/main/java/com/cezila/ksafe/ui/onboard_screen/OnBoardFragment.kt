package com.cezila.ksafe.ui.onboard_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.cezila.ksafe.R
import com.cezila.ksafe.databinding.FragmentOnBoardBinding
import com.cezila.ksafe.ui.utils.hideBottomNavView
import com.cezila.ksafe.ui.utils.navTo

class OnBoardFragment : Fragment(R.layout.fragment_on_board) {

    private lateinit var binding: FragmentOnBoardBinding
    private lateinit var adapter: OnBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater)
        hideBottomNavView(R.id.bottom_navigation_view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PreferenceManager.getDefaultSharedPreferences(requireContext()).apply {
            if (!getBoolean(COMPLETED_ONBOARDING_PREF_NAME, false)) {
                adapter = OnBoardAdapter(onboardingItems)
                setupUi()
            } else {
                navTo(R.id.action_onBoardFragment_to_homeFragment)
            }
        }
    }

    private fun setupUi() {
        with(binding) {
            btnSkip.setOnClickListener {
                if (vpOnboard.currentItem + 1 < adapter.itemCount) {
                    vpOnboard.currentItem = vpOnboard.currentItem + 1
                } else {
                    setAlreadyOnBoarded()
                    navTo(R.id.action_onBoardFragment_to_homeFragment)
                }
            }
            setupOnBoardingItems()
        }
    }

    private fun setupOnBoardingItems() {
        with(binding) {
            vpOnboard.adapter = adapter
            setupOnboardingIndicators()
            vpOnboard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    setCurrentOnboardingIndicator(position)
                }
            })
        }

    }

    private fun setupOnboardingIndicators() {
        val indicators = arrayOfNulls<ImageView>(adapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.indicator_onboarding_inactive
                )
            )
            indicators[i]?.layoutParams = layoutParams
            binding.llOnboardIndicators.addView(indicators[i])
        }
    }

    private fun setCurrentOnboardingIndicator(index: Int) {
        val childCount = binding.llOnboardIndicators.childCount
        for (i in 0..childCount) {
            val imageView = binding.llOnboardIndicators.getChildAt(i) as ImageView?
            if (i == index) {
                imageView?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.indicator_onboarding_active
                    )
                )
            } else {
                imageView?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.indicator_onboarding_inactive
                    )
                )
            }
        }
        if (index == adapter.itemCount - 1) {
            binding.btnSkip.text = getString(R.string.start)
        } else {
            binding.btnSkip.text = getString(R.string.next)
        }
    }

    private fun setAlreadyOnBoarded() {
        PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().apply {
            putBoolean(COMPLETED_ONBOARDING_PREF_NAME, true)
            apply()
        }
    }

    companion object {
        const val COMPLETED_ONBOARDING_PREF_NAME = "COMPLETED_ONBOARDING"
        val onboardingItems = listOf(
            OnBoardingItem(
                imageId = R.drawable.ic_password_security,
                title = "Password Storage Security",
                description = "Your passwords are stored and protected with state-of-the-art encryption."
            ),
            OnBoardingItem(
                imageId = R.drawable.ic_analyze_data,
                title = "Analyze Password",
                description = "Check the strength of your existing passwords."
            ),
            OnBoardingItem(
                imageId = R.drawable.ic_generate_password,
                title = "Generate Password",
                description = "Our password generation feature allows you to easily create strong and unique passwords"
            )
        )
    }

}