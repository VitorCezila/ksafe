package com.cezila.ksafe.domain.model

data class PasswordGenerationParams(
    val length: Int,
    val charGroups: List<PasswordCharGroup>
)
