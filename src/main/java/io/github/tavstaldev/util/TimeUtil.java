package io.github.tavstaldev.util;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil
{
    private static final Pattern timePattern = Pattern.compile("(\\d+)([dhms])");

    public static String formatDuration(long seconds) {
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(GriefPrevention.instance.dataStore.getMessage(Messages.Day, String.valueOf(days))).append(" ");
        }
        if (hours > 0) {
            sb.append(GriefPrevention.instance.dataStore.getMessage(Messages.Hour, String.valueOf(hours))).append(" ");
        }
        if (minutes > 0) {
            sb.append(GriefPrevention.instance.dataStore.getMessage(Messages.Minute, String.valueOf(minutes))).append(" ");
        }
        if (secs > 0)
        {
            sb.append(GriefPrevention.instance.dataStore.getMessage(Messages.Second, String.valueOf(secs))).append(" ");
        }

        return sb.toString().trim();
    }

    public static Duration parseDuration(String humanReadable) {
        if (humanReadable == null || humanReadable.trim().isEmpty()) {
            return Duration.ZERO;
        }

        // Pattern to find numbers followed by time unit (d, h, m, s)
        final Matcher matcher = timePattern.matcher(humanReadable.toLowerCase());
        long totalSeconds = 0;

        while (matcher.find()) {
            long value = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "d":
                    totalSeconds += value * 24 * 60 * 60; // Days
                    break;
                case "h":
                    totalSeconds += value * 60 * 60;      // Hours
                    break;
                case "m":
                    totalSeconds += value * 60;           // Minutes
                    break;
                case "s":
                    totalSeconds += value;                // Seconds
                    break;
                default:
                    break;
            }
        }
        return Duration.ofSeconds(totalSeconds);
    }

    public static String durationToHumanReadable(Duration duration) {
        StringBuilder sb = new StringBuilder();
        final long seconds = duration.getSeconds();
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        if (days > 0) {
            sb.append(String.format("%sd ", days));
        }
        if (hours > 0) {
            sb.append(String.format("%sh ", hours));
        }
        if (minutes > 0) {
            sb.append(String.format("%sm ", minutes));
        }
        if (secs > 0)
        {
            sb.append(String.format("%ss ", secs));
        }

        return sb.toString().trim();
    }
}
