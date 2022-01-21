package com.example.postsapp.overview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.postsapp.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {

    lateinit var binding: FragmentOverviewBinding

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private val delay = 900000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOverviewBinding.inflate(inflater)

        val application = requireNotNull(activity).application

        binding.lifecycleOwner = viewLifecycleOwner

        val viewModelFactory = OverviewViewModelFactory(application)
        val overviewViewModel = ViewModelProvider(this, viewModelFactory)
            .get(OverviewViewModel::class.java)
        binding.viewModel = overviewViewModel


        binding.postsGrid.adapter = PostsAdapter(PostsAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedPost.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                Navigation.findNavController(requireView())
                    .navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPosts(false)
            binding.swipeRefreshLayout.setRefreshing(false);
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable, delay.toLong())
            viewModel.getPosts(false)
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        handler.removeCallbacks(runnable!!)
        super.onPause()
    }
}
