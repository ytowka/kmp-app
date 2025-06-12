package com.example.feature.users.ui.edit

import com.example.feature.users.ui.UserRoleModel

interface EditUserCallback {

    fun changeFullName(fullName: String)
    fun changeEmail(email: String)
    fun changePhoneNumber(phoneNumber: String)
    fun changeRole(role: UserRoleModel)
    fun changeBlocked(blocked: Boolean)
    fun onSave()
}