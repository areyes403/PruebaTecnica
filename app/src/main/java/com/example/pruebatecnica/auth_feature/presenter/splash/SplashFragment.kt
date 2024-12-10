package com.example.pruebatecnica.auth_feature.presenter.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pruebatecnica.R
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.util.takeIfError
import com.example.pruebatecnica.core_feature.util.takeIfSuccess
import com.example.pruebatecnica.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding:FragmentSplashBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
    }
    private fun observers(){
        viewModel.getData.observe(viewLifecycleOwner){response->
            when(response){
                is ResponseState.Error->{
                    Log.i("apiResponse",response.toString())
                }
                is ResponseState.Success->{
                    Log.i("apiResponse","Success")
                    findNavController().navigate(R.id.action_splashFragment_to_organizationsFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}