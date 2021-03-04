package com.danil.kleshchin.rss.screens.feed

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.FragmentFeedBinding
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.screens.imagezoom.FeedImageZoomFragment
import com.squareup.picasso.Picasso
import javax.inject.Inject


class FeedFragment : Fragment(), FeedContract.View, FeedNavigator {

    @Inject
    lateinit var feedPresenter: FeedContract.Presenter

    private var feed: FeedEntity? = null

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val KEY_FEED = "KEY_FEED"
        private val INSTANCE_STATE_PARAM_FEED = "STATE_PARAM_FEED"

        fun newInstance(feed: FeedEntity): FeedFragment {
            val feedFragment = FeedFragment()
            val args = Bundle()
            args.putSerializable(KEY_FEED, feed)
            feedFragment.arguments = args
            return feedFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeFragment(savedInstanceState)

        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedPresenter.setView(this)
        feedPresenter.onAttach()
        initPresenterForSection()

        binding.apply {
            pageUrl.setOnClickListener { feedPresenter.onReadFullArticleSelected(feed!!) }
            iconStar.setOnClickListener {
                //todo
                val url = feed!!.feedPageUrl
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
            iconShare.setOnClickListener {
                //todo
                ShareCompat.IntentBuilder.from(activity!!)
                    .setType("text/plain")
                    .setChooserTitle(getString(R.string.read_full_article))
                    .setText(feed!!.feedPageUrl)
                    .startChooser()
            }
            binding.backButton.setOnClickListener { finish() }
            image.setOnClickListener { initZoomImageView(activity!!, feed!!.iconUrl, feed!!.title) }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setBackPressedCallback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        feedPresenter.onDetach()
    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

    }

    override fun showRetry() {

    }

    override fun hideRetry() {

    }

    override fun showErrorMessage() {

    }

    override fun showFeed(feed: FeedEntity) {
        binding.apply {
            title.text = feed.title
            kicker.text = feed.kicker
            author.text = feed.author
            dateCreated.text = getString(R.string.date_created, feed.dateCreated)
            dateUpdated.text = getString(R.string.date_updated, feed.dateUpdated)
            imageCaption.text = feed.iconCaption
            imageCopyright.text = feed.iconCopyright
            description.text = feed.description

            if (feed.thumbUrl.isEmpty()) {
                image.setImageResource(R.drawable.ic_empty_feed_icon)
            } else {
                Picasso.get().load(feed.iconUrl).into(image)
            }
        }
    }

    override fun showWebPage(url: String) {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(INSTANCE_STATE_PARAM_FEED, feed)
        super.onSaveInstanceState(outState)
    }

    private fun setBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initializeFragment(savedInstanceState: Bundle?) {
        feed = if (savedInstanceState == null) {
            getFeed()
        } else {
            savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_FEED) as FeedEntity
        }
    }

    private fun initPresenterForSection() {
        val feed = getFeed()
        feedPresenter.initialize(feed)
    }

    private fun getFeed(): FeedEntity {
        return arguments?.getSerializable(KEY_FEED) as FeedEntity
    }

    private fun initZoomImageView(
        context: FragmentActivity,
        imageUrl: String,
        toolbarTitle: String
    ) {
        val zoomFragment = FeedImageZoomFragment.newInstance(imageUrl, toolbarTitle)
        context.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, zoomFragment)
            .commitNow()
    }

    private fun finish() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commitNow()
    }
}
