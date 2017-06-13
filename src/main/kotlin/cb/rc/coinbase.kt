package cb.rc

import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import mu.KotlinLogging
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.google.gson.JsonParser
import java.math.BigDecimal

private val logger = KotlinLogging.logger {}

class Coinbase() {
    private val now = LocalDateTime.now()
    private val payload  = JsonParser().parse(getCurrencyValue())

    val fullDate: String?
        get() = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now)
    val date: String?
        get() = DateTimeFormatter.ofPattern("yyyyMMdd").format(now)
    val month: String?
        get() = DateTimeFormatter.ofPattern("yyyyMMM").format(now)
    val time: String?
        get() = DateTimeFormatter.ofPattern("HHmm").format(now)
    val ethRate: BigDecimal?
        get() = calcRate(payload, "ETH")
    val btcRate: BigDecimal?
        get() = calcRate(payload, "BTC")
    val ltcRate: BigDecimal?
        get() = calcRate(payload, "LTC")

    fun calcRate(payload: JsonElement, currency: String): BigDecimal? {
        return BigDecimal.ONE.setScale(2,BigDecimal.ROUND_HALF_UP) / payload["data"]["rates"][currency].asBigDecimal
    }

    fun getCurrencyValue(): String? {
        return try {
            val r = khttp.get("https://api.coinbase.com/v2/exchange-rates")
            logger.debug { r.statusCode }
            when (r.statusCode) {
                200 -> r.text
                else -> null
            }
        } catch (e: Exception) {
            logger.error { e }
            null
        }
    }
}