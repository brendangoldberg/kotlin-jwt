package com.brendangoldberg.kotlin_jwt

import com.brendangoldberg.kotlin_jwt.algorithms.HSAlgorithm
import com.brendangoldberg.kotlin_jwt.ext.toDate
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class KtJwtDecoderTest {

    private val secret = "supersecret"

    private lateinit var decoder: KtJwtDecoder

    @BeforeEach
    fun setup() {
        decoder = KtJwtDecoder()
    }

    @Test
    fun `should decode HS256 token`() {
        // Given
        val algorithm = HSAlgorithm.HS256(secret)

        val issuer = "issuer1"
        val subject = "somesubject"
        val audience = listOf("developer1")
        val expiresAt = LocalDateTime.now().plusDays(4)
        val notBefore = LocalDateTime.now().plusDays(3)
        val issuedAt = LocalDateTime.now()
        val jwtId = "somejwtid"
        val customClaim = CustomClaim("somevalue")

        // When
        val jwt = KtJwtCreator.init()
            .setIssuer(issuer)
            .setSubject(subject)
            .setAudience(*(audience.toTypedArray()))
            .setExpiresAt(expiresAt)
            .setNotBefore(notBefore)
            .setIssuedAt(issuedAt)
            .setJwtId(jwtId)
            .addClaim("custom_claim", customClaim, CustomClaim.serializer())
            .sign(algorithm)

        decoder.decode(jwt)

        // Then
        assertThat(decoder.issuer).isEqualTo(issuer)
        assertThat(decoder.subject).isEqualTo(subject)
        assertThat(decoder.audience).isEqualTo(audience)
        assertThat(decoder.expiresAt).isEqualTo(expiresAt.toDate())
        assertThat(decoder.notBefore).isEqualTo(notBefore.toDate())
        assertThat(decoder.issuedAt).isEqualTo(issuedAt.toDate())
        assertThat(decoder.jwtId).isEqualTo(jwtId)
        assertThat(decoder.getClaim("custom_claim", CustomClaim.serializer())).isEqualTo(customClaim)

    }

    @Test
    fun `should decode HS384 token`() {
        // Given
        val algorithm = HSAlgorithm.HS384(secret)

        val issuer = "issuer1"
        val subject = "somesubject"
        val audience = listOf("developer1")
        val expiresAt = LocalDateTime.now().plusDays(4)
        val notBefore = LocalDateTime.now().plusDays(3)
        val issuedAt = LocalDateTime.now()
        val jwtId = "somejwtid"
        val customClaim = CustomClaim("somevalue")

        // When
        val jwt = KtJwtCreator.init()
            .setIssuer(issuer)
            .setSubject(subject)
            .setAudience(*(audience.toTypedArray()))
            .setExpiresAt(expiresAt)
            .setNotBefore(notBefore)
            .setIssuedAt(issuedAt)
            .setJwtId(jwtId)
            .addClaim("custom_claim", customClaim, CustomClaim.serializer())
            .sign(algorithm)

        decoder.decode(jwt)

        // Then
        assertThat(decoder.issuer).isEqualTo(issuer)
        assertThat(decoder.subject).isEqualTo(subject)
        assertThat(decoder.audience).isEqualTo(audience)
        assertThat(decoder.expiresAt).isEqualTo(expiresAt.toDate())
        assertThat(decoder.notBefore).isEqualTo(notBefore.toDate())
        assertThat(decoder.issuedAt).isEqualTo(issuedAt.toDate())
        assertThat(decoder.jwtId).isEqualTo(jwtId)
        assertThat(decoder.getClaim("custom_claim", CustomClaim.serializer())).isEqualTo(customClaim)

    }

    @Test
    fun `should decode HS512 token`() {
        // Given
        val algorithm = HSAlgorithm.HS512(secret)

        val issuer = "issuer1"
        val subject = "somesubject"
        val audience = listOf("developer1")
        val expiresAt = LocalDateTime.now().plusDays(4)
        val notBefore = LocalDateTime.now().plusDays(3)
        val issuedAt = LocalDateTime.now()
        val jwtId = "somejwtid"
        val customClaim = CustomClaim("somevalue")

        // When
        val jwt = KtJwtCreator.init()
            .setIssuer(issuer)
            .setSubject(subject)
            .setAudience(*(audience.toTypedArray()))
            .setExpiresAt(expiresAt)
            .setNotBefore(notBefore)
            .setIssuedAt(issuedAt)
            .setJwtId(jwtId)
            .addClaim("custom_claim", customClaim, CustomClaim.serializer())
            .sign(algorithm)

        decoder.decode(jwt)

        // Then
        assertThat(decoder.issuer).isEqualTo(issuer)
        assertThat(decoder.subject).isEqualTo(subject)
        assertThat(decoder.audience).isEqualTo(audience)
        assertThat(decoder.expiresAt).isEqualTo(expiresAt.toDate())
        assertThat(decoder.notBefore).isEqualTo(notBefore.toDate())
        assertThat(decoder.issuedAt).isEqualTo(issuedAt.toDate())
        assertThat(decoder.jwtId).isEqualTo(jwtId)
        assertThat(decoder.getClaim("custom_claim", CustomClaim.serializer())).isEqualTo(customClaim)
    }

    @Serializable
    private data class CustomClaim(
        @SerialName("custom_value") val customValue: String
    )

}