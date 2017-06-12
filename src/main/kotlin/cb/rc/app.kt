package cb.rc

import com.evalab.core.cli.Command
import com.evalab.core.cli.exception.OptionException
//import redis.clients.jedis.Jedis

fun main(args: Array<String>) {
    val command = Command("check-rates", "command just for testing")

    command.addStringOption("currency", false, 'c', "Sets the currency: BTC or ETH. default: ETH")
    command.addIntegerOption("lbound", false, 'l', "Sets the USD that we are going to be checking for.")

    try {
        command.parse(args)
    } catch (e: OptionException) {
        println(command.getHelp())
        System.exit(2)
    }

    val currency = command.getStringValue("currency", "ETH")
    val lbound = command.getIntegerValue("lbound")

    val r = khttp.get("https://api.coinbase.com/v2/exchange-rates?currency=$currency")
    val usd = r.jsonObject.getJSONObject("data").getJSONObject("rates").getString("USD")
    println(usd)
}
