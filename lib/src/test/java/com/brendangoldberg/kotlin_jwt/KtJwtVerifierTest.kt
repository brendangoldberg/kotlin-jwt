package com.brendangoldberg.kotlin_jwt

import com.brendangoldberg.kotlin_jwt.algorithms.HSAlgorithm
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class KtJwtVerifierTest {

    private val secret = "supersecret"

    private lateinit var verifier: KtJwtVerifier

    /**
     * @see <a href="https://jwt.io/">jwt.io</a>
     */
    @Test
    fun `should verify HS256 predefined token`() {
        // Given
        val expected = true

        val secret = "your-256-bit-secret"

        val jwt =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

        val algorithm = HSAlgorithm.HS256(secret)

        verifier = KtJwtVerifier(algorithm)

        // When
        val actual = verifier.verify(jwt)

        // Then
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `should verify HS256 invalid token from different secret`() {
        // Given
        val secret1 = "supersecret"
        val secret2 = "notsosupersecret"

        val expected = false

        val algorithm1 = HSAlgorithm.HS256(secret1)
        val algorithm2 = HSAlgorithm.HS256(secret2)

        val expiresAt = LocalDateTime.now().plusMinutes(1)

        val jwt = KtJwtCreator.init()
            .setExpiresAt(expiresAt)
            .sign(algorithm1)

        verifier = KtJwtVerifier(algorithm2)

        // When
        val actual = verifier.verify(jwt)

        // Then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should verify valid token with HS256`() {
        // Given
        val expected = true

        val algorithm = HSAlgorithm.HS256(secret)

        val jwt = KtJwtCreator.init()
            .setExpiresAt(LocalDateTime.now().plusMinutes(1))
            .sign(algorithm)

        verifier = KtJwtVerifier(algorithm)

        // When
        val actual = verifier.verify(jwt)

        // Then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should verify HS256 multiple valid tokens`() {
        // Given
        val expected = true

        val algorithm = HSAlgorithm.HS256(secret)

        val jwt1 = KtJwtCreator.init()
            .setExpiresAt(LocalDateTime.now().plusMinutes(1))
            .setIssuer("developer1")
            .sign(algorithm)

        val jwt2 = KtJwtCreator.init()
            .setExpiresAt(LocalDateTime.now().plusMinutes(2))
            .setIssuer("developer2")
            .sign(algorithm)

        verifier = KtJwtVerifier(algorithm)

        // When
        val actual1 = verifier.verify(jwt1)
        val actual2 = verifier.verify(jwt2)

        // Then
        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

}