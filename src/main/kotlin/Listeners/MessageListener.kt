package top.whcraft.MaraiPlugin.Listeners

import net.mamoe.mirai.contact.getMember
import net.mamoe.mirai.contact.mute
import net.mamoe.mirai.contact.recallMessage
import net.mamoe.mirai.event.Event
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent

class MessageListener {
    public fun init(event: EventChannel<Event>) {
        run(event)
    }

    private fun run(event: EventChannel<Event>) {
        event.subscribeAlways<GroupMessageEvent> {
            val reg = Regex("^//(.*)$")
            if (reg.matches(message.toString())) {
                val member = sender

                group.recallMessage(message)
                member.mute(1)
            }
        }
    }
}