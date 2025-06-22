package com.example.validation


object FieldValidation {

    val EMAIL_ADDRESS_PATTERN = Regex(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
            "@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    )

    fun isEmailValid(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }
}