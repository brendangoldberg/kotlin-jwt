package com.brendangoldberg.kotlin_jwt

internal object Constants {

    // Payload
    const val ISSUER = "iss"
    const val SUBJECT = "sub"
    const val AUDIENCE = "aud"
    const val EXPIRES_AT = "exp"
    const val NOT_BEFORE = "nbf"
    const val ISSUED_AT = "iat"
    const val JWT_ID = "jti"

    // Header
    const val ALGORITHM = "alg"
    const val TYPE = "typ"
    const val CONTENT_TYPE = "cty"
}