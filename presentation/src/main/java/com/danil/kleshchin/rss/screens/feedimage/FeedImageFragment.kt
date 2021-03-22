package com.danil.kleshchin.rss.screens.feedimage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.FragmentFeedImageBinding
import com.danil.kleshchin.rss.widgets.ZoomableImageView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FeedImageFragment : Fragment() {

    private val REQUEST_CODE_WRITE_PERMISSIONS = 10001

    private val viewModel: FeedImageViewModel by viewModels()

    private var _binding: FragmentFeedImageBinding? = null
    private val binding get() = _binding!!
    private val args: FeedImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN //hide status bar

        _binding = FragmentFeedImageBinding.inflate(inflater, container, false).also {
            it.imageUrl = args.imageUrlArg
            it.toolbarTitle = args.toolbarTitleArg
            it.setClickListener { navigateBack() }
            it.image.setOnSingleTapConfirmedListener(object :
                ZoomableImageView.OnSingleTapConfirmedListener {
                override fun onSingleTapConfirmed() {
                    setToolbarVisibility()
                }
            })
        }
        setBackPressedCallback()

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_VISIBLE //show status bar
        (activity as AppCompatActivity).setSupportActionBar(null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.feed_image_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                createShareIntent()
                true
            }
            R.id.save_to_gallery -> {
                saveToGallerySelected()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setToolbarVisibility() {
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN //hide status bar
        binding.feedToolbar.isVisible = !binding.feedToolbar.isVisible
    }

    //TODO create base fragment for this?
    private fun createShareIntent() {
        ShareCompat.IntentBuilder.from(requireActivity())
            .setType("text/plain")
            .setChooserTitle(getString(R.string.share))
            .setText(args.imageUrlArg)
            .startChooser()
    }

    private fun saveToGallerySelected() {
        if (!isStoragePermissionGranted()) {
            requestWritePermissions()
            return
        }
        saveToGallery()
    }

    private fun saveToGallery() {
        var saved: Boolean
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                saved = viewModel.saveImageToGallery(
                    Glide.with(requireActivity())
                        .asBitmap()
                        .load(args.imageUrlArg)
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .error(android.R.drawable.stat_notify_error)
                        .submit()
                        .get(),
                    requireActivity().contentResolver
                )
                val message = if (saved) {
                    getString(R.string.image_saved)
                } else {
                    getString(R.string.unable_to_save)
                }
                Snackbar.make(binding.root, message, BaseTransientBottomBar.LENGTH_LONG).show()
            }
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestWritePermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE_WRITE_PERMISSIONS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveToGallery()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }
}
