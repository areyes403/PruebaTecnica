package com.example.pruebatecnica.organization_feature.presenter.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.pruebatecnica.R
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

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
        binding.apply {
            txtStatus.text="Downloading data..."
            animationView.apply {
                setAnimation(R.raw.dowloading_animation)
                repeatCount = LottieDrawable.INFINITE
                repeatMode = LottieDrawable.RESTART
                playAnimation()
            }
        }
        observers()
    }

    private fun observers(){
        viewModel.getData.observe(viewLifecycleOwner){response->
            when(response){
                is ResponseState.Error->{
                    Log.i("apiResponse",response.toString())
                }
                is ResponseState.Success->{
                    binding.txtStatus.text="Data downloaded"
                    binding.animationView.cancelAnimation()
                    binding.animationView.apply {
                        setAnimation(R.raw.animation_success)
                        repeatCount = 0
                        playAnimation()
                        addAnimatorListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationEnd(animation)
                                findNavController().navigate(R.id.action_splashFragment_to_organizationsFragment)
                                binding.animationView.removeAllAnimatorListeners()
                            }
                        })
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}