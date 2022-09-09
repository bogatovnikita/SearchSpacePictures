package com.elephant.searchspacepictures.main_screen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.elephant.searchspacepictures.adapter.GalleryPicturesAdapter
import com.elephant.searchspacepictures.base.BaseFragment
import com.elephant.searchspacepictures.databinding.FragmentMainScreenBinding
import com.elephant.searchspacepictures.utils.viewModelCreator

class MainScreenFragment :
    BaseFragment<FragmentMainScreenBinding>(FragmentMainScreenBinding::inflate) {

    private val viewModel by viewModelCreator { MainScreenViewModel() }
    private lateinit var adapter: GalleryPicturesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
        initClickPage()
        viewModel.getPictures("moon")
    }

    private fun initClickPage() {

    }

    private fun initObserve() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.loaded) {
                binding.progressBar.isVisible = false
                binding.containerRecyclerView.isVisible = true
            }
            adapter.pictures = state.listResponseImages
        }
    }

    private fun initRecyclerView() {
        adapter = GalleryPicturesAdapter { }
        binding.recyclerView.layoutManager =
            GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

}