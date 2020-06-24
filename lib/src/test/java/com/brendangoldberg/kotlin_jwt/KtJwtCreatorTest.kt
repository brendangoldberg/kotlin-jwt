package com.brendangoldberg.kotlin_jwt

import com.brendangoldberg.kotlin_jwt.algorithms.HSAlgorithm
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class KtJwtCreatorTest {

    /**
     * @see <a href="https://jwt.io/">jwt.io</a>
     */
    @Test
    fun `should match predefined HS256 token`() {
        // Given
        val expected =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

        val secret = "your-256-bit-secret"

        val algorithm = HSAlgorithm.HS256(secret)

        val alg = algorithm.id
        val typ = "JWT"

        val sub = "1234567890"
        val name = "John Doe"
        val iat = 1516239022L

        // When
        val actual = KtJwtCreator.init()
            .setAlgorithm(alg) // order from predefined JWT need to set before "typ"
            .setType(typ)
            .setSubject(sub)
            .addClaim("name", name)
            .setIssuedAt(iat)
            .sign(algorithm)

        // Then
        assertThat(expected).isEqualTo(actual)
    }

    /**
     * @see <a href="https://jwt.io/">jwt.io</a>
     */
    @Test
    fun `should match predefined HS384 token`() {
        // Given
        val expected =
            "eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.bQTnz6AuMJvmXXQsVPrxeQNvzDkimo7VNXxHeSBfClLufmCVZRUuyTwJF311JHuh"

        val secret = "your-384-bit-secret"

        val algorithm = HSAlgorithm.HS384(secret)

        val alg = algorithm.id
        val typ = "JWT"

        val sub = "1234567890"
        val name = "John Doe"
        val admin = true
        val iat = 1516239022L

        // When
        val actual = KtJwtCreator.init()
            .setAlgorithm(alg) // order from predefined JWT need to set before "typ"
            .setType(typ)
            .setSubject(sub)
            .addClaim("name", name)
            .addClaim("admin", admin)
            .setIssuedAt(iat)
            .sign(algorithm)

        // Then
        assertThat(expected).isEqualTo(actual)
    }

    /**
     * @see <a href="https://jwt.io/">jwt.io</a>
     */
    @Test
    fun `should match predefined HS512 token`() {
        // Given
        val expected =
            "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.VFb0qJ1LRg_4ujbZoRMXnVkUgiuKq5KxWqNdbKq_G9Vvz-S1zZa9LPxtHWKa64zDl2ofkT8F6jBt_K4riU-fPg"

        val secret = "your-512-bit-secret"

        val algorithm = HSAlgorithm.HS512(secret)

        val alg = algorithm.id
        val typ = "JWT"

        val sub = "1234567890"
        val name = "John Doe"
        val admin = true
        val iat = 1516239022L

        // When
        val actual = KtJwtCreator.init()
            .setAlgorithm(alg) // order from predefined JWT need to set before "typ"
            .setType(typ)
            .setSubject(sub)
            .addClaim("name", name)
            .addClaim("admin", admin)
            .setIssuedAt(iat)
            .sign(algorithm)

        // Then
        assertThat(expected).isEqualTo(actual)
    }

}