package com.elephant.searchspacepictures.main_screen

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.elephant.searchspacepictures.R
import com.elephant.searchspacepictures.adapter.GalleryPicturesAdapter
import com.elephant.searchspacepictures.base.BaseFragment
import com.elephant.searchspacepictures.databinding.FragmentMainScreenBinding
import com.elephant.searchspacepictures.models.ResponseUrlPictures
import com.elephant.searchspacepictures.utils.viewModelCreator


class MainScreenFragment :
    BaseFragment<FragmentMainScreenBinding>(FragmentMainScreenBinding::inflate) {

    private val viewModel by viewModelCreator { MainScreenViewModel() }
    private lateinit var adapter: GalleryPicturesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObserve()
        initClickButtonNextPage()
        initClickButtonPreviousPage()
        initClickSearch()
    }

    private fun initClickSearch() {
        binding.textField.setEndIconOnClickListener {
            if (binding.textInput.text!!.isEmpty()) return@setEndIconOnClickListener
            closeKeyboard(it)
            binding.progressBar.isVisible = true
            viewModel.getPictures(binding.textInput.text.toString(), 1)
        }
        binding!!.textInput.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View, p1: Int, p2: KeyEvent?): Boolean {
                if(p1 == KeyEvent.KEYCODE_ENTER){
                    closeKeyboard(p0)
                    binding!!.progressBar.isVisible = true
                    viewModel.getPictures(binding!!.textInput.text.toString(), 1)
                    return true
                }
                return false
            }

        })
    }

    private fun closeKeyboard(it: View) {
        val inputManager: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            it.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun initClickButtonPreviousPage() {
        if (viewModel.state.value!!.page <= 1) binding.previousPage.isVisible =
            false
        binding.previousPage.setOnClickListener {
            binding.progressBar.isVisible = true
            binding.scrollView.smoothScrollTo(0, 0)
            viewModel.downPage(binding.textInput.text.toString())
        }
    }

    private fun initClickButtonNextPage() {
        if ((viewModel.state.value!!.totalPictures / 100).toInt() == viewModel.state.value!!.page) binding.nextPage.isVisible =
            false
        binding.nextPage.setOnClickListener {
            binding.progressBar.isVisible = true
            binding.scrollView.smoothScrollTo(0, 0)
            viewModel.upPage(binding.textInput.text.toString())
        }
    }

    private fun initObserve() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.loaded) {
                binding.progressBar.isVisible = false
                binding.recyclerViewGroup.isVisible = true
                if (state.page == 1) {
                    binding.previousPage.isVisible = false
                }
            }

            if (state.notResponse) {
                Toast.makeText(
                    requireContext(),
                    R.string.no_data_available,
                    Toast.LENGTH_SHORT
                ).show()
                binding.progressBar.isVisible = false
                binding.recyclerViewGroup.isVisible = false
            }
            adapter.pictures = state.listResponsePicture
        }
    }

    private fun initRecyclerView() {
        adapter = GalleryPicturesAdapter {
            navigateTo(it)
        }
        binding.recyclerView.layoutManager =
            GridLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private fun navigateTo(listPictures: ResponseUrlPictures) {
        findNavController().navigate(
            MainScreenFragmentDirections.actionMainScreenFragmentToPictureScreenFragment(
                listPictures
            )
        )
    }
}