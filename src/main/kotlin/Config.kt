package top.whcraft.AutoMute

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object Config : AutoSavePluginConfig("config") {
    val TimeMin: Int by value(60)
    val TimeMax: Int by value(7200)
    val Timer: Int by value(2)
    val ClearTime: Int by value(4)
    val Message: String by value(" 你因为刷屏已被自动禁言 [&t]s")
    val TimeRange: Int by value(90)
    val MaxCount: Int by value(3)
    val groupList: List<Long> by value()
}