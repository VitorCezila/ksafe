package com.cezila.ksafe.domain.model

import com.cezila.ksafe.core.utils.SimpleResource

data class InsertPasswordResult(
    val titleError: Errors? = null,
    val passwordError: Errors? = null,
    val insertResult: SimpleResource? = null
)
