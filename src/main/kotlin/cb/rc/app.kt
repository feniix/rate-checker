package cb.rc

import com.evalab.core.cli.Command
import com.evalab.core.cli.exception.OptionException

import redis.clients.jedis.Jedis

import org.nield.kotlinstatistics.*
import java.util.stream.Collectors
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {
    val command = Command("check-rates", "command just for testing")

    command.addStringOption("currency", false, 'c', "Sets the currency: BTC or ETH. default: ETH")
    command.addIntegerOption("lbound", false, 'l', "Sets the USD that we are going to be checking for.")
    command.addLongOption("check-delay", false, 'd', "Sets the delay between checks in minutes. default: 5")

    try {
        command.parse(args)
    } catch (e: OptionException) {
        println(command.getHelp())
        System.exit(2)
    }

    val currency = command.getStringValue("currency", "ETH")
    val lbound = command.getIntegerValue("lbound", 50)
    val delay = command.getLongValue("check-delay", 5) as Long

    while (true) {
        println(coinbase().ethRate)
        TimeUnit.MINUTES.sleep(delay)
    }


}
