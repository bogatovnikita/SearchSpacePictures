package com.elephant.searchspacepictures.pictureScreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.elephant.searchspacepictures.base.BaseFragment
import com.elephant.searchspacepictures.databinding.FragmentPictureScreenBinding
import com.elephant.searchspacepictures.utils.viewModelCreator

class PictureScreenFragment :
    BaseFragment<FragmentPictureScreenBinding>(FragmentPictureScreenBinding::inflate) {

    private val args by navArgs<PictureScreenFragmentArgs>()
    private val viewModel by viewModelCreator { PictureScreenViewModel(args.listPictures!!) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}