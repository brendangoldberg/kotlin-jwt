## Kotlin JWT Library
This is a library written fully in Kotlin utilizing [Kotlin Serializer](https://github.com/Kotlin/kotlinx.serialization)

### Features

- Create, decode and verify a JWT
- Custom claims using [Kotlin Serializer](https://github.com/Kotlin/kotlinx.serialization)

### Examples

```kotlin
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