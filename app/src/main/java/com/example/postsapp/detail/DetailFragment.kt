package com.example.postsapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.postsapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)

        val application = requireNotNull(activity).application

        binding.lifecycleOwner = viewLifecycleOwner

        val postProperty = DetailFragmentArgs.fromBundle(requireArguments()).selectedPost

        val viewModelFactory = DetailViewModelFactory(postProperty, application )
        val detailViewModel = ViewModelProvider(this, viewModelFactory, )
            .get(DetailViewModel::class.java)
        binding.viewModel = detailViewModel

        val adapter = CommentsAdapter()
        binding.commentsGrid.adapter = adapter

        return binding.root
    }
}