## Kotlin JWT Library

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This is a JSON Web Token (JWT) library written fully in Kotlin
utilizing [Kotlin Serializer](https://github.com/Kotlin/kotlinx.serialization).

For introduction to JWTs please visit [https://jwt.io/introduction/](https://jwt.io/introduction/).

### Features

- Create, decode and verify a JWT.
- Fully custom JWT claims using [Kotlin Serializer](https://github.com/Kotlin/kotlinx.serialization).

### Examples

```kotlin

import com.brendangoldberg.kotlin_jwt.KtJwtCreator
import com.brendangoldberg.kotlin_jwt.KtJwtDecoder
import com.brendangoldberg.kotlin_jwt.KtJwtVerifier
import com.brendangoldberg.kotlin_jwt.algorithms.HSAlgorithm
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomClaim(
    @SerialName("my_custom_value") val customValue: String
)

fun main() {
    // Declare which signing algorithm to use see import com.brendangoldberg.kotlin_jwt.algorithms.* for available algorithms.
    val algorithm = HSAlgorithm.HS256("my-super-secret")

    val customClaim = CustomClaim("myCustomClaim")

    // Create JWT
    val jwt = KtJwtCreator.init().addClaim("custom_claim", customClaim, CustomClaim.serializer()).sign(algorithm)

    // Verify JWT
    val verified = KtJwtVerifier(algorithm).verify(jwt)

    println("verified: $verified")

    // Decode JWT
    val decoded = KtJwtDecoder.decode(jwt)

    println("custom claim: ${decoded.getClaim("custom_claim", CustomClaim.serializer())}")
}
```

### Setup

#### Gradle

All versions can be found in the [maven repo](https://repo1.maven.org/maven2/com/brendangoldberg/kotlin-jwt/), or [maven search](https://search.maven.org/search?q=g:com.brendangoldberg%20AND%20a:kotlin-jwt).

```kotlin
repositories { 
  mavenCentral()
}

dependencies {
  implementation("com.brendangoldberg:kotlin-jwt:<latest-version>")
}
```
