package com.example.feature.auth.ui

interface LoginFormCallback{
    fun onUsernameChange(username:String)
    fun onPasswordChange(password:String)
    fun onLogin()
    fun onCreateAccount()
}

interface RegisterFormCallback{
    fun onRegister()
    fun returnToSignIn()
    fun onUsernameChange(username:String)
    fun onPasswordChange(password:String)
    fun onFullNameChange(fullName:String)
    fun onEmailChange(email:String)
    fun onPhoneChange(phone:String)
    fun onImagePicked(uri: String?)
    fun onPasswordConfirmationChange(passwordConfirmation:String)
}