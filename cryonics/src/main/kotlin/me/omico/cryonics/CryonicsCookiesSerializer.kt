@file:Suppress("unused")

package me.omico.cryonics

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

object CryonicsCookiesSerializer : Serializer<CryonicsCookies> {

    override val defaultValue: CryonicsCookies
        get() = CryonicsCookies.getDefaultInstance()

    @Throws(CorruptionException::class)
    override suspend fun readFrom(input: InputStream): CryonicsCookies =
        runCatching { CryonicsCookies.parseFrom(input) }
            .getOrElse { throw CorruptionException("Cannot read CryonicsCookies.", it) }

    @Throws(CorruptionException::class)
    override suspend fun writeTo(t: CryonicsCookies, output: OutputStream) =
        runCatching { t.writeTo(output) }
            .getOrElse { throw CorruptionException("Cannot write CryonicsCookies.", it) }
}
