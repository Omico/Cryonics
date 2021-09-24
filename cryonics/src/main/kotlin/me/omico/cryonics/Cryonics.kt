@file:Suppress("unused")

package me.omico.cryonics

import okhttp3.Cookie

fun CryonicsCookie(cookie: Cookie): CryonicsCookie =
    cryonicsCookie {
        name = cookie.name
        value = cookie.value
        expiresAt = cookie.expiresAt
        domain = cookie.domain
        path = cookie.path
        secure = cookie.secure
        httpOnly = cookie.httpOnly
        hostOnly = cookie.hostOnly
    }

fun Cookie(cookie: CryonicsCookie): Cookie =
    Cookie.Builder()
        .name(cookie.name)
        .value(cookie.value)
        .expiresAt(cookie.expiresAt)
        .domain(cookie.domain)
        .path(cookie.path)
        .apply { if (cookie.secure) secure() }
        .apply { if (cookie.httpOnly) httpOnly() }
        .build()
