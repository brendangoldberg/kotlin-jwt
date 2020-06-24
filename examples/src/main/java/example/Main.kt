package example

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
    val decoder = KtJwtDecoder()
    decoder.decode(jwt)

    println("custom claim: ${decoder.getClaim("custom_claim", CustomClaim.serializer())}")
}