package com.packagename.mynotesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.packagename.DemoAdapter
import com.packagename.ViewPagerAdapter
import com.packagename.mynotesapp.databinding.FragmentProfileBinding
import com.packagename.mynotesapp.utilis.NetworkResult
import com.packagename.mynotesapp.utilis.Username
import com.packagename.mynotesapp.viewmodels.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private val articleViewModel by viewModels<ArticleViewModel>()

    @Inject
    lateinit var username: Username


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(MyArticleFragment(),"My Article")
        adapter.addFragment(MyFavouriteArticle(),"Favourite Article")
        binding.viewpager.adapter = adapter
        binding.tablayout.setupWithViewPager(binding.viewpager)

        setUpUi()
        observer()



    }

    private fun setUpUi(){
        articleViewModel.getProfile(username.getUsername().toString())

        binding.imgBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun observer(){
        articleViewModel.profileLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    binding.txtProfileName.text = it.data?.profile?.username?.get(0).toString()
                    binding.txtBio.text = it.data?.profile?.bio.toString()
                    binding.txtNameUser.text = it.data?.profile?.username.toString()
                }
            }
        })
    }
}