package com.example.pruebatecnica.auth_feature.presenter.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pruebatecnica.R
import com.example.pruebatecnica.auth_feature.domain.model.AuthCredentials
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.example.pruebatecnica.core_feature.util.Utils.Companion.isValidEmail
import com.example.pruebatecnica.core_feature.util.Utils.Companion.isValidPassword
import com.example.pruebatecnica.core_feature.util.Utils.Companion.validateField
import com.example.pruebatecnica.core_feature.util.disable
import com.example.pruebatecnica.core_feature.util.invisible
import com.example.pruebatecnica.core_feature.util.isBiometricHardWareAvailable
import com.example.pruebatecnica.core_feature.util.show
import com.example.pruebatecnica.core_feature.util.snackBar
import com.example.pruebatecnica.core_feature.util.takeIfError
import com.example.pruebatecnica.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
            if(!isBiometricHardWareAvailable()){
                cbFingerprint.disable()
            }
            txtRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                val validEmail = validateField(tfEmailLogin,::isValidEmail, "Set valid email")
                val validPassword = validateField(tfPasswordLogin,::isValidPassword, "Password must be at least 8 characters long, contain one digit, and one uppercase letter.")
                if (!validEmail || !validPassword){
                    return@setOnClickListener
                }
                btnLogin.invisible()
                val credentialsData=AuthCredentials(
                    email = tfEmailLogin.editText!!.text.toString(),
                    password = tfPasswordLogin.editText!!.text.toString(),
                    fcmToken = null,
                    fingerprint = if (binding.cbFingerprint.isChecked) 1 else 0
                )
                viewModel.login(credentials = credentialsData)
            }
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
                    binding.apply {
                        btnLogin.show()
                    }
                    snackBar(response.msg)
                }
            }
        }
    }

    private fun signInGoogle(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
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
                            fcmToken = it,
                            fingerprint = 0
                        )
                        if(isBiometricHardWareAvailable()){
                            showDialog(credentials = credentials)
                        }
                    } ?: snackBar( "Error al obtener sus credenciales")
                }
            } catch (e: ApiException) {
                println(e.localizedMessage)
                e.printStackTrace()
                snackBar("Error al obtener sus credenciales")
            }
        }
    }

    private fun showDialog(credentials:AuthCredentials){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Enable fingerprint")
            .setMessage("Do you want to enable fingerprint?")
            .setIcon(R.drawable.ic_fingerprint_24)
            .setNeutralButton("Cancel") { dialog, which ->
                viewModel.login(credentials = credentials)
            }
            .setNegativeButton("Decline") { dialog, which ->
                viewModel.login(credentials = credentials)
            }
            .setPositiveButton("Accept") { dialog, which ->
                credentials.fingerprint=1
                viewModel.login(credentials = credentials)
            }.setOnCancelListener {
                viewModel.login(credentials = credentials)
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}