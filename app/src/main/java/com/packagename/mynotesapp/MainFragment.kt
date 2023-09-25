package com.packagename.mynotesapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.packagename.DemoAdapter
import com.packagename.mynotesapp.databinding.FragmentMainBinding
import com.packagename.mynotesapp.databinding.FragmentRegisterBinding
import com.packagename.mynotesapp.models.ArticleDto
import com.packagename.mynotesapp.models.CreateArticleResponse
import com.packagename.mynotesapp.models.ProgrammingItem
import com.packagename.mynotesapp.utilis.NetworkResult
import com.packagename.mynotesapp.viewmodels.ArticleViewModel
import com.packagename.mynotesapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding ? = null

    private val binding get() = _binding!!

    private val articleViewModel by viewModels<ArticleViewModel>()

    private lateinit var adapters: DemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
//        adapter = DemoAdapter()
        adapters = DemoAdapter(::onArticleClick)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel.getArticle()
        bindObserver()


        binding.rclProfile.adapter = adapters


        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_create_article -> {
                    findNavController().navigate(R.id.action_mainFragment_to_createArticleFragment)
                    true
                }

                else -> {
                     false
                }
            }
        }

    }

   private fun bindObserver(){
        articleViewModel.articleResponseLivedata.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(),"error", Toast.LENGTH_LONG)
                }
                is NetworkResult.Loading -> TODO()
                is NetworkResult.Success -> {
                    adapters.submitList(it.data?.articles)
                    Log.d("List", "${it.data?.articles?.size}")
                }
            }
        })
    }

    private fun onArticleClick(articleDto: ArticleDto){
        val bundle = Bundle()
        bundle.putString("article",Gson().toJson(articleDto))
        findNavController().navigate(R.id.action_mainFragment_to_articleDetailsFragment,bundle)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}