package com.packagename.mynotesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.packagename.DemoAdapter
import com.packagename.mynotesapp.databinding.FragmentArticleDetailsBinding
import com.packagename.mynotesapp.databinding.FragmentMyfavouritearticleBinding
import com.packagename.mynotesapp.models.ArticleDto
import com.packagename.mynotesapp.utilis.NetworkResult
import com.packagename.mynotesapp.utilis.Username
import com.packagename.mynotesapp.viewmodels.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFavouriteArticle : Fragment() {

    private var _binding: FragmentMyfavouritearticleBinding? = null
    private val binding get() = _binding!!

    private val articleViewModel by viewModels<ArticleViewModel>()

    private lateinit var adapter: DemoAdapter

    @Inject
     lateinit var username: Username

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyfavouritearticleBinding.inflate(inflater,container,false)
        adapter = DemoAdapter(::onArticleClick)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleViewModel.getFavouriteArticleUsername(username.getUsername().toString())
        binding.rlFavourite.adapter = adapter

        articleViewModel.favouriteArticle.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> TODO()
                is NetworkResult.Success -> {
                    adapter.submitList(it.data?.articles)
                }
            }
        })


    }

    private fun onArticleClick(articleDto: ArticleDto){
        val bundle = Bundle()
        bundle.putString("article", Gson().toJson(articleDto))
        findNavController().navigate(R.id.action_profileFragment_to_articleDetailsFragment,bundle)

    }
}