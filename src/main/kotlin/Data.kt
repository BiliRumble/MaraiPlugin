package top.whcraft.AutoMute

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object Data : AutoSavePluginData("data") {
    var mutableMap: MutableMap<Long, Int> by value();
}