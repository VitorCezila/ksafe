package com.cezila.ksafe.domain.model

sealed class Errors {
    data object FieldEmpty: Errors()
    data object InputTooShort: Errors()
}
