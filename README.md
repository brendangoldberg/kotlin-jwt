## Kotlin JWT Library

[ ![Download](https://api.bintray.com/packages/brendangoldberg/com.brendangoldberg/kotlin-jwt/images/download.svg) ](https://bintray.com/brendangoldberg/com.brendangoldberg/kotlin-jwt/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This is a JSON Web Token (JWT) library written fully in Kotlin utilizing [Kotlin Serializer](https://github.com/Kotlin/kotlinx.serialization).

For introduction to JWTs please visit [https://jwt.io/introduction/](https://jwt.io/introduction/).

### Features

- Create, decode and verify a JWT.
- Fully custom JWT claims using [Kotlin Serializer](https://github.com/Kotlin/kotlinx.serialization).

### Examples

```kotlin

@Serializable
data class CustomClaim(
    @SerialName("my_custom_value") val customValue: String
)

fun main() {
    // Declare which signing algorithm to use see com.brendangoldberg.kotlin_jwt.algorithms.* for available algorithms.
    val algorithm = HSAlgorithm.HS256("my-super-secret")

    val customClaim = CustomClaim("myCustomClaim")

    // Create JWT
    val jwt = KtJwtCreator.init().addClaim("custom_claim", customClaim, CustomClaim.serializer()).sign(algorithm)

    // Verify JWT
    val verified = KtJwtVerifier(algorithm).verify(jwt)

    println("verified: $verified")

    // Decode JWT
    val decoder = KtJwtDecoder()
    decoder.decode(jwt)

    println("custom claim: ${decoder.getClaim("custom_claim", CustomClaim.serializer())}")
}
```

### Setup

#### Gradle

```
repositories {
    // published to JCenter
    jcenter()
}

dependencies {
    implementation "com.brendangoldberg.kotlin-jwt:<latest-version>"
}
```

### Important Notes

- This library is still in pre-release mode, and should not be used in production until release tag's are not marked as "pre-release".
