package com.example.feature.users.ui.edit

import com.example.feature.users.ui.UserModel
import com.example.feature.users.ui.UserRoleModel

sealed interface EditUserIntent {
    data class LoadUser(val userModel: UserModel, val isMe: Boolean) : EditUserIntent
    data class ChangeFullName(val fullName: String) : EditUserIntent
    data class ChangeEmail(val email: String) : EditUserIntent
    data class ChangePhoneNumber(val phoneNumber: String) : EditUserIntent
    data class ChangeRole(val role: UserRoleModel) : EditUserIntent
    data class ChangeBlocked(val blocked: Boolean) : EditUserIntent
    data object Save : EditUserIntent
}

sealed interface EditUserSideEffect {

    data object UserSaved : EditUserSideEffect
}