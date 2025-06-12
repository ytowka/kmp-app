package com.example.feature.users.ui.edit

import com.example.feature.users.ui.UserModel
import com.example.feature.users.ui.UserRoleModel

data class EditUserState(
    val initalUser: UserModel? = null,
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val role: UserRoleModel = UserRoleModel.USER,
    val isBlocked: Boolean = false,
    val userSaved: Boolean = false,
    val isMe: Boolean = true,
){

    val isChanged by lazy {
        initalUser?.fullName != fullName
                || initalUser.email != email
                || initalUser.phone != phone
                || initalUser.role != role
                || initalUser.isBlocked != isBlocked
    }
}