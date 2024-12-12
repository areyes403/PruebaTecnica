package com.example.pruebatecnica.auth_feature.presenter.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pruebatecnica.R
import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.util.snackBar
import com.example.pruebatecnica.core_feature.util.takeIfError
import com.example.pruebatecnica.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private var _binding:FragmentLoginBinding?=null
    private val binding get() = _binding!!

    private val RC_SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cardGoogleRegister.setOnClickListener {
                signInGoogle()
            }
        }
        observers()
    }
    private fun observers(){
        viewModel.response.observe(viewLifecycleOwner){ response->
            when(response){
                is ResponseState.Success->{

                }
                is ResponseState.Error->{
                    snackBar(response.msg)
                }
            }
        }
    }
    private fun signInGoogle(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val idToken = account.idToken
                    idToken?.let {
                        val credentials=AuthCredentials(
                            email = "",
                            password = "",
                            fcmToken = it
                        )
                        viewModel.login(credentials = credentials)
                    } ?: snackBar("Error al obtener sus credenciales")
                }
            } catch (e: ApiException) {
                println(e.localizedMessage)
                e.printStackTrace()
                snackBar("Error al obtener sus credenciales")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}