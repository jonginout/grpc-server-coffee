package site.jongin.coffeegrpc.domain

data class CoffeeRequestBody(
    val status: String,
    val price: Int,
    val menu: String
)
