package com.example.pruebatecnica.core_feature.presenter.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pruebatecnica.R
import com.example.pruebatecnica.auth_feature.domain.model.AuthSession
import com.example.pruebatecnica.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.Executor

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var session:AuthSession

    private val viewModel by viewModels<MainActivityViewModel>()

    private val TAG="MAIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,callback)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val authFlag = DEVICE_CREDENTIAL or BIOMETRIC_STRONG
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authentication")
                .setAllowedAuthenticators(authFlag)
                .build()
        } else {
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setDeviceCredentialAllowed(true)
                .build()
        }

        val navHost= supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHost.navController

        if (savedInstanceState != null) {
            navController.setGraph(R.navigation.nav_graph, savedInstanceState)
        } else {
            navController.setGraph(R.navigation.nav_graph)
        }
        observers(firstSession = savedInstanceState == null)
    }

    private fun observers(firstSession: Boolean) {
        viewModel.sessionState.onEach { session->
            if (session==null){
                Log.i(TAG,"Not session")
            }else{
                this.session=session
                if (firstSession){
                    if (session.fingerprint==1){
                        biometricPrompt.authenticate(promptInfo)
                    }
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.token.onEach{ authToken->
            authToken?.let {
                println(authToken)
                if (firstSession){
                    navController.navigate(R.id.action_loginFragment_to_splashFragment)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private val callback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(
            errorCode: Int,
            errString: CharSequence
        ) {
            super.onAuthenticationError(errorCode, errString)
            Log.i(TAG,"onAuthenticationError")
        }

        override fun onAuthenticationSucceeded(
            result: BiometricPrompt.AuthenticationResult
        ) {
            super.onAuthenticationSucceeded(result)
            Log.i(TAG,"onAuthenticationSucceeded")
            viewModel.authenticated()

        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Log.i(TAG,"onAuthenticationFailed")
        }
    }

}