package com.packagename.mynotesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.packagename.mynotesapp.databinding.FragmentArticleDetailsBinding
import com.packagename.mynotesapp.models.ArticleDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private var _binding: FragmentArticleDetailsBinding ?= null

    private val binding get() = _binding!!

    var article:ArticleDto? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleDetailsBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()

    }

    fun setUpUi(){
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        val jsonArticle = arguments?.getString("article")
        if(jsonArticle != null){
            article = Gson().fromJson(jsonArticle,ArticleDto::class.java)
//             article = Gson().fromJson(jsonArticle,ArticleDto::class.java)
            article.let {
                binding.txtArticleTitle.text = it?.title
                binding.txtDescriptions.text = it?.description
                binding.txtUserCircle.text = it?.author?.username?.get(0).toString()
                binding.txtUserName.text = it?.author?.username
            }
        }else{
            Toast.makeText(requireContext(),"Something get wrong",Toast.LENGTH_LONG).show()
        }
    }
}