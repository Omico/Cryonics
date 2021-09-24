package me.omico.cryonics

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class CryonicsCookiesDataStore(context: Context) {

    private val dataStore: DataStore<CryonicsCookies> = context.createCryonicsCookiesDataStore

    fun saveCookies(cookies: List<CryonicsCookie>) {
        updateCookies { cryonicsCookies ->
            cryonicsCookies.toBuilder()
                .clearCookie()
                .addAllCookie(cookies)
                .build()
        }
    }

    fun cookies(): List<CryonicsCookie> =
        runBlocking { dataStore.data.first().cookieList }

    fun cookies(predicate: (CryonicsCookie) -> Boolean): List<CryonicsCookie> =
        cookies().filter(predicate)

    fun clear() {
        updateCookies { cryonicsCookies ->
            cryonicsCookies.toBuilder().clearCookie().build()
        }
    }

    private fun updateCookies(
        transform: suspend (cookies: CryonicsCookies) -> CryonicsCookies,
    ): CryonicsCookies = runBlocking { dataStore.updateData(transform) }

    companion object {

        private var dataStore: CryonicsCookiesDataStore? = null

        fun getInstance(context: Context): CryonicsCookiesDataStore =
            dataStore ?: run {
                dataStore = CryonicsCookiesDataStore(context.applicationContext)
                dataStore!!
            }
    }
}

val Context.cryonicsCookiesDataStore
    get() = CryonicsCookiesDataStore.getInstance(this)

private val Context.createCryonicsCookiesDataStore by dataStore(
    fileName = "cryonics_cookies",
    serializer = CryonicsCookiesSerializer,
)
