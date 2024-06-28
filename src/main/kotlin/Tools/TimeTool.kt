package top.whcraft.AutoMute.Tools

class TimeTool {
    public fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }

    public fun FormatTime(time: Long): String {
        try {
            var timestr = ""
            timestr = time.toString()
            return timestr
        } catch (e: Exception) {
            throw e
        }
    }

    public fun isNearTime(time: Long, TimeRangeSecond: Int): Boolean {
        val now = getCurrentTime()

        // 转化秒为TimeRange
        val TimeRange = TimeRangeSecond * 1000

        if (now - time < TimeRange && time - now < TimeRange) {
            return true
        }

        return false
    }
}