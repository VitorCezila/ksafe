package com.cezila.ksafe.domain.util

import com.cezila.ksafe.domain.model.Errors

object ValidationUtil {

    fun basicValidation(data: String): Errors? {
        if (data.isEmpty()) {
            return Errors.FieldEmpty
        }
        return null
    }

}