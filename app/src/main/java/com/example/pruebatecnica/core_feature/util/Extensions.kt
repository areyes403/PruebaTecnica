package com.example.pruebatecnica.core_feature.util

import android.app.Activity
import android.app.KeyguardManager
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pruebatecnica.core_feature.data.model.ResponseState
import com.google.android.material.snackbar.Snackbar


fun Activity.snackBar(msg:String?){
    this.window.decorView.rootView.let { rootView ->
        Snackbar.make(rootView, "Error: $msg", Snackbar.LENGTH_SHORT).show()
    }
}

fun Activity.toast(msg: String?){
    Toast.makeText(this,msg, Toast.LENGTH_LONG).show()
}

fun Fragment.snackBar(msg:String?){
    view?.let {
        if (msg.isNullOrBlank()){
            Snackbar.make(it, "Error desconocido", Snackbar.LENGTH_SHORT).show()
        }else{
            Snackbar.make(it, msg, Snackbar.LENGTH_SHORT).show()
        }
    }
}

fun Fragment.toast(msg: String?){
    Toast.makeText(requireContext(),msg, Toast.LENGTH_LONG).show()
}

fun View.hide(){
    visibility = View.GONE
}

fun View.invisible(){
    visibility= View.INVISIBLE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.disable(){
    isEnabled = false
}

fun View.enabled(){
    isEnabled = true
}

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
    return keymgr.isKeyguardSecure
}

fun Fragment.isBiometricHardWareAvailable():Boolean{
    var result = false
    val biometricManager = BiometricManager.from(requireContext())

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
fun Fragment.checkPermissionGranted(permission:String):Boolean{
    return ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
}
