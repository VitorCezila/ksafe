package com.cezila.ksafe.domain.model

import com.cezila.ksafe.core.utils.SimpleResource

data class UpdatePasswordResult(
    val titleError: Errors? = null,
    val passwordError: Errors? = null,
    val updateResult: SimpleResource? = null
)