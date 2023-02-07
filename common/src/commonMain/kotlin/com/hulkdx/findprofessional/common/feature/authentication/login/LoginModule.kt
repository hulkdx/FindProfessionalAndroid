package com.hulkdx.findprofessional.common.feature.authentication.login

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val loginModule: Module
    get() = module {
        factoryOf(::LoginUseCase)
    }