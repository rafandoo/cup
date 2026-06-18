package dev.rafandoo.cup.request;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class provides a utility method to parse "Set-Cookie" headers from HTTP responses.
 * It extracts cookie names and their corresponding values into a map for easy access.
 */
@UtilityClass
public final class CookieParser {

    /**
     * Parses the "Set-Cookie" headers from the provided headers map and returns a map of cookie names to their values.
     *
     * @param headers the map of HTTP headers, where the key is the header name and the value is a list of header values.
     * @return a map of cookie names to their corresponding values, or an empty map if no "Set-Cookie" headers are present.
     */
    public static Map<String, String> parse(Map<String, List<String>> headers) {
        if (headers.containsKey("Set-Cookie")) {
            return headers.get("Set-Cookie")
                .stream()
                .map(cookie -> cookie.split("=", 2))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toUnmodifiableMap(
                    parts -> parts[0].trim(),
                    parts -> parts[1].trim(),
                    (a, b) -> a
                ));
        }
        return Map.of();
    }
}
