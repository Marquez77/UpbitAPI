package me.marquez.upbit.entity.date;

import lombok.Builder;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
public record HourMinuteSecond(
        int hour,
        int minute,
        int second
) {
    private static final Pattern[] patterns = {
            Pattern.compile("^(\\d{2}):(\\d{2}):(\\d{2})$"),
            Pattern.compile("^(\\d{2})(\\d{2})(\\d{2})$")
    };

    @Nullable
    public static HourMinuteSecond of(String text) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String hour = matcher.group(1);
                String minute = matcher.group(2);
                String second = matcher.group(3);
                return new HourMinuteSecond(Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return hour + ":" + minute + ":" + second;
    }
}
