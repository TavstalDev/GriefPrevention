package io.github.tavstaldev.util;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;

public class TimeUtil
{
    public static String formatDuration(long seconds) {
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(GriefPrevention.instance.dataStore.getMessage(Messages.Day, String.valueOf(days))).append(" ");
        }
        if (hours > 0 || days > 0) {
            sb.append(GriefPrevention.instance.dataStore.getMessage(Messages.Hour, String.valueOf(hours))).append(" ");
        }
        if (minutes > 0 || hours > 0 || days > 0) {
            sb.append(GriefPrevention.instance.dataStore.getMessage(Messages.Minute, String.valueOf(minutes))).append(" ");
        }
        sb.append(GriefPrevention.instance.dataStore.getMessage(Messages.Second, String.valueOf(secs))).append(" ");

        return sb.toString().trim();
    }
}
