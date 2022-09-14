package com.elephant.searchspacepictures.pictureScreen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.elephant.searchspacepictures.R
import com.elephant.searchspacepictures.databinding.FragmentPictureScreenBinding
import com.elephant.searchspacepictures.utils.viewModelCreator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class PictureScreenFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentPictureScreenBinding? = null

    private val args by navArgs<PictureScreenFragmentArgs>()
    private val viewModel by viewModelCreator { PictureScreenViewModel(args.listPictures!!) }
    private lateinit var pictureBitMap: Bitmap
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(), ::onGotWriteStoragePermissionResult
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch { viewModel.loadingPictures() }
        initObserver()
    }

    private fun initToolbar() {
        binding.toolbarLayout.visibility = View.VISIBLE
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share_picture -> {
                    shareInPicture()
                    true
                }
                R.id.screensaver -> {
                    requestCameraPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    true
                }
                else -> false
            }
        }
    }

    private fun initObserver() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.pictureUrl.isNotEmpty()) {
                Glide.with(requireContext())
                    .asBitmap()
                    .load(state.pictureUrl)
                    .dontAnimate()
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            findNavController().popBackStack()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pictureBitMap = resource!!
                            binding.pictureUrl.setImageBitmap(resource)
                            binding.progressBar.isVisible = false
                            initToolbar()
                            return true
                        }
                    })
                    .into(binding.pictureUrl)
            }
        }
    }

    private fun shareInPicture() {
        val uri = viewModel.state.value!!.pictureUrl
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.look_at_this_beauty)
        intent.putExtra(Intent.EXTRA_TEXT, uri)
        startActivity(
            Intent.createChooser(
                intent,
                requireContext().resources.getText(R.string.share_url)
            )
        )
    }

    private fun onGotWriteStoragePermissionResult(granted: Boolean) {
        if (granted) {
            installPictureForWallpaper()
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                askUserForOpeningAppSettings()
            } else {
                Toast.makeText(requireActivity(), R.string.permission_denied, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun installPictureForWallpaper() {
        val uri = getImageUri(requireContext(), pictureBitMap)
        val intent = Intent(Intent.ACTION_ATTACH_DATA)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setDataAndType(uri, "image/jpeg")
        intent.putExtra("mimeType", "image/jpeg")
        startActivity(Intent.createChooser(intent, "Set as:"))
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun askUserForOpeningAppSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        )
        if (requireContext().packageManager.resolveActivity(
                appSettingsIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            ) == null
        ) {
            Toast.makeText(
                requireActivity(),
                R.string.permissions_denied_forever,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(R.string.open) { _, _ ->
                    startActivity(appSettingsIntent)
                }
                .create()
                .show()
        }
    }
}