package com.example.postsapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.postsapp.databinding.FragmentDetailBinding
import com.example.postsapp.databinding.FragmentDetailBindingImpl
import com.example.postsapp.overview.OverviewFragmentDirections

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application

        binding.lifecycleOwner = viewLifecycleOwner

        val postProperty = DetailFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val viewModelFactory = DetailViewModelFactory(postProperty, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailViewModel::class.java)

        binding.commentsGrid.adapter = CommentsAdapter()
        return binding.root
    }

//
//    binding.viewModel = viewModel
//
//    binding.postsGrid.adapter = PostsAdapter(PostsAdapter.OnClickListener {
//        viewModel.displayPropertyDetails(it)
//    })
//
//    viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
//        if (null != it) {
//            Navigation.findNavController(requireView())
//                .navigate(OverviewFragmentDirections.actionShowDetail(it))
//            viewModel.displayPropertyDetailsComplete()
//        }
//    })
//    setHasOptionsMenu(true)
//    return binding.root
}