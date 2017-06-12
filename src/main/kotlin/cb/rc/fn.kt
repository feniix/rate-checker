package cb.rc

fun get_currency_value(currency: String): String {
    val r = khttp.get("https://api.coinbase.com/v2/exchange-rates?currency=$currency")
    when (r.statusCode) {
        200 -> return r.jsonObject.getJSONObject("data").getJSONObject("rates").getString("USD")
        else -> return ""
    }
}