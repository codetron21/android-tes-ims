package com.codetron.imscodingtest

import com.codetron.imscodingtest.abstraction.AccountManager
import com.codetron.imscodingtest.abstraction.ActivityRouter
import com.codetron.imscodingtest.manager.AccountManagerImpl
import com.codetron.imscodingtest.router.ActivityRouterImpl
import org.koin.dsl.module

object AppModules {

    fun getModules() = listOf(routerModule, managerModule)

    private val routerModule = module {
        factory<ActivityRouter> { ActivityRouterImpl() }
    }

    private val managerModule = module {
        single<AccountManager> { AccountManagerImpl() }
    }

}