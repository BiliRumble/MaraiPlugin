package top.whcraft.MaraiPlugin

import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader

suspend fun main() {
    if (true) {

        error("""
            DEPRECATED:
            此启动方法已经被弃用, 请使用 ./gradlew runConsole 启动测试环境
            
            详见:
            https://docs.mirai.mamoe.net/console/plugin/JVMPlugin.html#%E8%B0%83%E8%AF%95
            https://github.com/mamoe/mirai/blob/dev/mirai-console/docs/plugin/JVMPlugin.md#%E8%B0%83%E8%AF%95
            """.trimIndent())
    }

    MiraiConsoleTerminalLoader.startAsDaemon()

    //如果是Kotlin
    Main.load()
    Main.enable()

    val bot = MiraiConsole.addBot(3814106534, "pq2951327332") {
        fileBasedDeviceInfo()
    }.alsoLogin()

    MiraiConsole.job.join()
}