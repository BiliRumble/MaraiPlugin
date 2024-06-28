package top.whcraft.AutoMute

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.MemberPermission
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At

import top.whcraft.AutoMute.Tools.TimeTool
import java.util.*

object Main : KotlinPlugin(JvmPluginDescription(id = "top.rumble.AutoMute", name = "AutoMute", version = "1.0.1") { author("rumble/WhCraftMC") }) {
    private val map = Data.mutableMap

    override fun onEnable() {
        Config.reload()
        Data.reload()

        // 自检数据
        if (Config.TimeMin < 0 || Config.TimeMax < 0 || Config.Timer < 0 || Config.ClearTime < 0 || Config.MaxCount <= 0 || Config.TimeRange < 0) {
            throw IllegalArgumentException("配置文件中存在非法数值")
        } else {
            if (Config.ClearTime > -1 && Config.ClearTime < 24) {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR, Config.ClearTime)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                Timer().schedule(object : TimerTask() { override fun run() { map.clear() } }, Date(calendar.timeInMillis), 86400000)
            }

            for (_groupId in Config.groupList) {
                var num = 1
                var preMessage = ""
                var preSenderId: Long = 0
                GlobalEventChannel.filter { it is GroupMessageEvent && it.group.id == _groupId }.subscribeAlways<GroupMessageEvent> {
                    if (group.botPermission > MemberPermission.MEMBER) {
                        if (sender.id == preSenderId && message.serializeToMiraiCode() == preMessage) {
                            num++
                            if (num >= Config.MaxCount) {
                                if (group.botPermission > sender.permission) {
                                    if (map.containsKey(sender.id)) {
                                        if (map.getValue(sender.id) * Config.Timer > Config.TimeMax) {
                                            map[sender.id] = Config.TimeMax
                                        } else {
                                            map[sender.id] = map.getValue(sender.id) * Config.Timer
                                        }
                                    } else {
                                        map[sender.id] = Config.TimeMin
                                    }

                                    if (TimeTool().isNearTime(TimeTool().getCurrentTime(), Config.TimeRange)) {
                                        group.sendMessage(At(sender) + Config.Message.replace("[&t]", map.getValue(sender.id).toString()))
                                        sender.mute(map.getValue(sender.id))
                                    }
                                }
                                num = 0
                            }
                        } else {
                            preSenderId = sender.id
                            preMessage = message.serializeToMiraiCode()
                            num = 1
                        }
                    } else if (Config.Notify) {
                        bot.friends[Config.NotifyID]?.sendMessage(Config.NotifyMessage.replace("[&G]", group.id.toString()).replace("[&B]", bot.id.toString()))
                        logger.warning((Config.NotifyMessage.replace("[&G]", group.id.toString()).replace("[&B]", bot.id.toString())))
                    }
                }
            }
            logger.info("AutoMute 载入成功!")
        }

        //配置文件目录 "${dataFolder.absolutePath}/"
        super.onEnable()
    }

    override fun onDisable() {
        Data.mutableMap = map
        super.onDisable()
    }
    // endregion
}
