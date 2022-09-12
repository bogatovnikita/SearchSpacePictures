package com.elephant.searchspacepictures.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.elephant.searchspacepictures.base.BaseFragment
import com.elephant.searchspacepictures.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            delay(2000)
            navigateTo()
        }
    }

    private fun navigateTo() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainScreenFragment())
    }
}