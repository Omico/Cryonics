@file:Suppress("unused")

package me.omico.cryonics

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CryonicsCookieJar(context: Context) : CookieJar {

    private val dataStore: CryonicsCookiesDataStore by lazy { context.cryonicsCookiesDataStore }

    override fun loadForRequest(url: HttpUrl): List<Cookie> =
        savedCookies().filter { it.matches(url) }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        dataStore.saveCookies(cookies.prune().map(::CryonicsCookie))
    }

    private fun savedCookies(): List<Cookie> =
        dataStore.cookies().map(::Cookie)

    private fun List<Cookie>.prune(): List<Cookie> =
        listOf(savedCookies(), filter { it.persistent })
            .asSequence()
            .flatten()
            .filter { it.expiresAt > System.currentTimeMillis() }
            .groupBy { it.value }
            .mapNotNull { (_, list) -> list.maxByOrNull { it.expiresAt } }
            .toList()
}
