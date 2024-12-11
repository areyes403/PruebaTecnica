package com.example.pruebatecnica.core_feature.util

import android.app.Activity
import android.app.KeyguardManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import com.example.pruebatecnica.core_feature.data.model.ResponseState

infix fun <T> ResponseState<T>.takeIfSuccess(onSuccess: ResponseState.Success<T>.() -> Unit): ResponseState<T> {
    return when (this) {
        is ResponseState.Success -> {
            onSuccess(this)
            this
        }
        else -> {
            this
        }
    }
}

infix fun <T> ResponseState<T>.takeIfError(onError: ResponseState.Error.() -> Unit): ResponseState<T> {
    return when (this) {
        is ResponseState.Error -> {
            onError(this)
            this
        }
        else -> {
            this
        }
    }
}

fun Activity.isBiometricHardWareAvailable():Boolean{
    var result = false
    val biometricManager = BiometricManager.from(this)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        when (biometricManager.canAuthenticate(DEVICE_CREDENTIAL or BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> result = true
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> result = false
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> result = false
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> result = false
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED ->
                result = true
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED ->
                result = true
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN ->
                result = false
        }
    } else {
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> result = true
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> result = false
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> result = false
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> result = false
        }
    }
    return result
}

fun Activity.deviceHasPasswordPinLock():Boolean{
    val keymgr = this.getSystemService(AppCompatActivity.KEYGUARD_SERVICE) as KeyguardManager
    if (keymgr.isKeyguardSecure)
        return true
    return false
}