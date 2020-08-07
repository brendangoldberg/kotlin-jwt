package com.brendangoldberg.kotlin_jwt

import com.brendangoldberg.kotlin_jwt.algorithms.HSAlgorithm
import com.brendangoldberg.kotlin_jwt.ext.toDate
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class KtJwtDecoderTest {

    private val secret = "supersecret"

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
        val primitiveClaim = 1

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
            .addClaim("primitive_claim", primitiveClaim, Int.serializer())
            .sign(algorithm)

        val decoded = KtJwtDecoder.decode(jwt)

        // Then
        assertThat(decoded.issuer).isEqualTo(issuer)
        assertThat(decoded.subject).isEqualTo(subject)
        assertThat(decoded.audience).isEqualTo(audience)
        assertThat(decoded.expiresAt).isEqualTo(expiresAt.toDate())
        assertThat(decoded.notBefore).isEqualTo(notBefore.toDate())
        assertThat(decoded.issuedAt).isEqualTo(issuedAt.toDate())
        assertThat(decoded.jwtId).isEqualTo(jwtId)
        assertThat(decoded.getClaim("custom_claim", CustomClaim.serializer())).isEqualTo(customClaim)
        assertThat(decoded.getClaim("primitive_claim", Int.serializer())).isEqualTo(primitiveClaim)

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
        val primitiveClaim = 1

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
            .addClaim("primitive_claim", primitiveClaim, Int.serializer())
            .sign(algorithm)

        val decoded = KtJwtDecoder.decode(jwt)

        // Then
        assertThat(decoded.issuer).isEqualTo(issuer)
        assertThat(decoded.subject).isEqualTo(subject)
        assertThat(decoded.audience).isEqualTo(audience)
        assertThat(decoded.expiresAt).isEqualTo(expiresAt.toDate())
        assertThat(decoded.notBefore).isEqualTo(notBefore.toDate())
        assertThat(decoded.issuedAt).isEqualTo(issuedAt.toDate())
        assertThat(decoded.jwtId).isEqualTo(jwtId)
        assertThat(decoded.getClaim("custom_claim", CustomClaim.serializer())).isEqualTo(customClaim)
        assertThat(decoded.getClaim("primitive_claim", Int.serializer())).isEqualTo(primitiveClaim)

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
        val primitiveClaim = 1

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
            .addClaim("primitive_claim", primitiveClaim, Int.serializer())
            .sign(algorithm)

        val decoded = KtJwtDecoder.decode(jwt)

        // Then
        assertThat(decoded.issuer).isEqualTo(issuer)
        assertThat(decoded.subject).isEqualTo(subject)
        assertThat(decoded.audience).isEqualTo(audience)
        assertThat(decoded.expiresAt).isEqualTo(expiresAt.toDate())
        assertThat(decoded.notBefore).isEqualTo(notBefore.toDate())
        assertThat(decoded.issuedAt).isEqualTo(issuedAt.toDate())
        assertThat(decoded.jwtId).isEqualTo(jwtId)
        assertThat(decoded.getClaim("custom_claim", CustomClaim.serializer())).isEqualTo(customClaim)
        assertThat(decoded.getClaim("primitive_claim", Int.serializer())).isEqualTo(primitiveClaim)

    }

    @Serializable
    private data class CustomClaim(
        @SerialName("custom_value") val customValue: String
    )

}