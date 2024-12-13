package com.example.pruebatecnica.auth_feature.presenter.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pruebatecnica.auth_feature.data.model.RegisterUser
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.util.Utils.Companion.isValidEmail
import com.example.pruebatecnica.core_feature.util.Utils.Companion.isValidPassword
import com.example.pruebatecnica.core_feature.util.Utils.Companion.validateField
import com.example.pruebatecnica.core_feature.util.hide
import com.example.pruebatecnica.core_feature.util.invisible
import com.example.pruebatecnica.core_feature.util.show
import com.example.pruebatecnica.core_feature.util.snackBar
import com.example.pruebatecnica.core_feature.util.toast
import com.example.pruebatecnica.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding:FragmentRegisterBinding?=null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        binding.apply {
            btnRegister.setOnClickListener {
                val validEmail = validateField(tfEmailRegister, ::isValidEmail, "Set valid email")
                val validPassword = validateField(tfPasswordRegister, ::isValidPassword, "Password must be at least 8 characters long, contain one digit, and one uppercase letter.")
                if (!validEmail || !validPassword){
                    return@setOnClickListener
                }
                btnRegister.invisible()
                viewModel.register(
                    RegisterUser(
                        email = tfEmailRegister.editText!!.text.toString(),
                        pass = tfPasswordRegister.editText!!.text.toString()
                    )
                )
            }
        }
    }

    private fun observers() {
        viewModel.registerState.observe(viewLifecycleOwner){ response->
            when(response){
                is ResponseState.Success->{
                    toast("Register success")
                    findNavController().popBackStack()
                }
                is ResponseState.Error->{
                    binding.apply {
                        btnRegister.show()
                        progressView.invisible()
                    }

                    response.code?.let { code->
                        snackBar(response.msg)
                        when(code){
                            401->{

                            }

                        }
                    } ?: snackBar(response.msg)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}