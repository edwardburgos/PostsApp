package com.example.postsapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.postsapp.database.CommentsDatabase
import com.example.postsapp.database.CommentsDatabaseDao
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
        val database = CommentsDatabase.getInstance(application).commentDatabaseDao

        val viewModelFactory = DetailViewModelFactory(postProperty, application, database)
        val detailViewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailViewModel::class.java)
        binding.viewModel = detailViewModel



        val adapter = CommentsAdapter()
        binding.commentsGrid.adapter = adapter

//        detailViewModel.commentsf.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                // This change the list
//                // Without DiffUtil
//                // adapter.data = it
//                // With DiffUtil
//                //adapter.submitList(it)
//
//                println("CAMBIÓ")
//                println(it)
//                adapter.submitList(it)
//                //detailViewModel.selectedProperty.value?.comments = it
//            }
//        })


//        binding.viewModel.commentsf.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                // This change the list
//                // Without DiffUtil
//                // adapter.data = it
//                // With DiffUtil
//                binding.commentsGrid.adapter.da
//            }
//        })
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