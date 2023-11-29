package me.marquez.upbit.entity.date;

import lombok.Builder;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
public record YearMonthDay(
        int year,
        int month,
        int day
) {
    private static final Pattern[] patterns = {
            Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})$"),
            Pattern.compile("^(\\d{4})(\\d{2})(\\d{2})$")
    };

    @Nullable
    public static YearMonthDay of(String text) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String year = matcher.group(1);
                String month = matcher.group(2);
                String day = matcher.group(3);
                return new YearMonthDay(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
