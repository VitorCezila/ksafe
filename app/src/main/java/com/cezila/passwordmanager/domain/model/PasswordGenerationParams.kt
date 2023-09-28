package com.cezila.passwordmanager.domain.model

data class PasswordGenerationParams(
    val length: Int,
    val charGroups: List<PasswordCharGroup>
)
