package com.packagename.mynotesapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.packagename.DemoAdapter
import com.packagename.mynotesapp.databinding.FragmentMyarticleBinding
import com.packagename.mynotesapp.models.ArticleDto
import com.packagename.mynotesapp.models.CreateArticleResponse
import com.packagename.mynotesapp.models.User
import com.packagename.mynotesapp.models.UserResponse
import com.packagename.mynotesapp.repository.PreferenceManager
import com.packagename.mynotesapp.utilis.NetworkResult
import com.packagename.mynotesapp.utilis.Username
import com.packagename.mynotesapp.viewmodels.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyArticleFragment : Fragment() {

    private var _binding:FragmentMyarticleBinding? = null
    private val binding get() = _binding!!

    private val articleViewModel by viewModels<ArticleViewModel>()

    private lateinit var adapter: DemoAdapter

//    private lateinit var preferenceManager:PreferenceManager

    @Inject
    lateinit var username: Username

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyarticleBinding.inflate(inflater,container,false)
        adapter = DemoAdapter(::onArticleClick)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleViewModel.getArticleUsername(username.getUsername().toString())
        binding.rlArticle.adapter = adapter

        articleViewModel.articleByUsernameLivedata.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Error -> TODO()
                is NetworkResult.Loading -> TODO()
                is NetworkResult.Success -> {
                    adapter.submitList(it.data?.articles)
                    Log.d("my Article size",it.data?.articles?.size.toString() )
                }

                else -> {

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