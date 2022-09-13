package com.elephant.searchspacepictures.splash

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.elephant.searchspacepictures.R
import com.elephant.searchspacepictures.base.BaseFragment
import com.elephant.searchspacepictures.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            binding.lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    navigateTo()
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }
            })

        }
    }

    private fun navigateTo() {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainScreenFragment())
    }
}