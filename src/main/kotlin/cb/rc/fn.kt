package cb.rc

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun getCurrencyValue(currency: String? = "ETH"): String? {
    return try {
        val r = khttp.get("https://api.coinbase.com/v2/exchange-rates?currency=$currency")
        logger.debug { r.statusCode }
        when (r.statusCode) {
            200 -> r.jsonObject.getJSONObject("data").getJSONObject("rates").getString("USD")
            else -> null
        }
    } catch (e: Exception) {
        logger.error { e }
        null
    }
}