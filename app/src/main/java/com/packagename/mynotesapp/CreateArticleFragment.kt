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
import com.packagename.mynotesapp.databinding.FragmentCreateArticleBinding
import com.packagename.mynotesapp.models.ArticleDet
import com.packagename.mynotesapp.models.CreateArticleRequest
import com.packagename.mynotesapp.utilis.NetworkResult
import com.packagename.mynotesapp.viewmodels.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private var _binding: FragmentCreateArticleBinding ?= null

    private val binding get() = _binding!!

    private val articleViewModel by viewModels<ArticleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateArticleBinding.inflate(inflater,container,false)
//        binding.btnPublish.setOnClickListener {
//            articleViewModel.createArticle(CreateArticleRequest(ArticleDet("hijjji","hjjjfff","pkkkjjjk")))
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        bindObserver()
    }

    private fun setUpUi(){
        binding.txtBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
//        binding.btnPublish.setOnClickListener {
//            articleViewModel.createArticle(CreateArticleRequest(ArticleDet("hkkkjji","hjjjfff","ttttkkkjjjk")))
//        }

        binding.btnPublish.setOnClickListener {
//

            articleViewModel.createArticle(userRequest())
        }
    }

    private fun userRequest(): CreateArticleRequest{
        val title = binding.edtTitle.text
        val description = binding.edtDescription.text
        val article = binding.edtArticle.text
        return CreateArticleRequest(ArticleDet(article.toString(),description.toString(),title.toString()))

    }

    private fun validateUserInput() : String{
        val createArticleRequest = userRequest().article
        return articleViewModel.validatePage(createArticleRequest.title,createArticleRequest.description,createArticleRequest.body)
    }

    private fun bindObserver(){
        articleViewModel.statusArticleLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
            }
        })
    }



}