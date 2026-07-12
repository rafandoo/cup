package dev.rafandoo.cup.request;

import dev.rafandoo.cup.text.StringValidator;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A flexible and fluent utility class for building URLs dynamically.
 * <p>
 * Example usage:
 * <pre>./gradlew clean aggregateJavadoc
 *
 * String url = UrlBuilder.builder()
 *     .protocol("https")
 *     .host("api.example.com")
 *     .port(8080)
 *     .path("v1/users")
 *     .addParameter("page", "2")
 *     .addParameter("limit", "50")
 *     .fragment("profile")
 *     .toString();
 * // Result: https://api.example.com:8080/v1/users?page=2&limit=50#profile
 * </pre>
 * </p>
 */
public class UrlBuilder {

    private String scheme;
    private String host;
    private Integer port;
    private final List<String> pathSegments = new ArrayList<>();
    private final Map<String, List<String>> queryParams = new LinkedHashMap<>();
    private String fragment;

    /**
     * Private constructor to enforce the use of the static builder() method.
     */
    private UrlBuilder() {
    }

    /**
     * Creates a new instance of the {@link UrlBuilder}.
     *
     * @return a new UrlBuilder instance.
     */
    public static UrlBuilder builder() {
        return new UrlBuilder();
    }

    /**
     * Defines the protocol (scheme) of the URL, e.g., "http" or "https".
     *
     * @param scheme the protocol to use.
     * @return the current builder instance.
     */
    public UrlBuilder protocol(String scheme) {
        this.scheme = scheme;
        return this;
    }

    /**
     * Shortcut method to set the protocol to "http".
     *
     * @return the current builder instance.
     */
    public UrlBuilder http() {
        return this.protocol("http");
    }

    /**
     * Shortcut method to set the protocol to "https".
     *
     * @return the current builder instance.
     */
    public UrlBuilder https() {
        return this.protocol("https");
    }

    /**
     * Defines the host (domain) of the URL.
     *
     * @param host the host to use.
     * @return the current builder instance.
     */
    public UrlBuilder host(String host) {
        this.host = host;
        return this;
    }

    /**
     * Defines the port number to use.
     *
     * @param port the port number.
     * @return the current builder instance.
     */
    public UrlBuilder port(int port) {
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port: " + port + ". Port number must be between 1 and 65535");
        }
        this.port = port;
        return this;
    }

    /**
     * Defines the path (endpoint) to append to the URL using multiple segments.
     *
     * @param segments the path segments to join and append.
     * @return the current builder instance.
     */
    public UrlBuilder path(String... segments) {
        if (segments == null) {
            return this;
        }
        for (String segment : segments) {
            this.addPathSegment(segment);
        }

        return this;
    }

    /**
     * Adds an additional path segment to the existing path.
     *
     * @param segment the path segment to add.
     * @return the current builder instance.
     */
    public UrlBuilder addPathSegment(String segment) {
        if (StringValidator.isNullOrEmpty(segment)) {
            return this;
        }

        String cleaned = this.trimSlashes(segment);
        if (!cleaned.isEmpty()) {
            this.pathSegments.add(cleaned);
        }

        return this;
    }

    /**
     * Trims leading and trailing slashes from a path segment.
     *
     * @param value the path segment to clean.
     * @return the cleaned path segment without leading or trailing slashes.
     */
    private String trimSlashes(String value) {
        int start = 0;
        int end = value.length();

        while (start < end && value.charAt(start) == '/') {
            start++;
        }
        while (end > start && value.charAt(end - 1) == '/') {
            end--;
        }
        return value.substring(start, end);
    }

    /**
     * Adds a single query parameter.
     *
     * @param key   the parameter name.
     * @param value the parameter value (null values are ignored).
     * @return the current builder instance.
     */
    public UrlBuilder addParameter(String key, Object value) {
        if (StringValidator.isNullOrEmpty(key) || value == null) {
            return this;
        }

        this.queryParams.computeIfAbsent(key, k -> new ArrayList<>())
            .add(String.valueOf(value));

        return this;
    }

    /**
     * Adds multiple query parameters from a map.
     *
     * @param params a map of parameter names and values (null values are ignored).
     * @return the current builder instance.
     */
    public UrlBuilder addParameters(Map<String, ?> params) {
        if (params == null) {
            return this;
        }
        params.forEach(this::addParameter);
        return this;
    }

    /**
     * Clears all query parameters from the builder.
     *
     * @return the current builder instance.
     */
    public UrlBuilder clearParameters() {
        this.queryParams.clear();
        return this;
    }

    /**
     * Defines a URL fragment (the part after '#').
     *
     * @param fragment the fragment identifier.
     * @return the current builder instance.
     */
    public UrlBuilder fragment(String fragment) {
        this.fragment = fragment;
        return this;
    }

    /**
     * Encodes a query parameter value using UTF-8 encoding.
     *
     * @param value the value to encode.
     * @return the URL-encoded value.
     */
    private String encodeQuery(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    /**
     * Builds the path string by joining all path segments with slashes.
     *
     * @return the complete path string, or null if no segments are defined.
     */
    private String buildPath() {
        if (this.pathSegments.isEmpty()) {
            return null;
        }
        return "/" + String.join("/", this.pathSegments);
    }

    /**
     * Builds the query string by encoding all parameters and joining them with '&'.
     *
     * @return the complete query string, or null if no parameters are defined.
     */
    private String buildQuery() {
        if (this.queryParams.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();

        boolean first = true;
        for (Map.Entry<String, List<String>> entry : this.queryParams.entrySet()) {
            String key = this.encodeQuery(entry.getKey());
            for (String value : entry.getValue()) {
                if (!first) {
                    query.append("&");
                } else {
                    first = false;
                }
                query.append(key).append("=").append(encodeQuery(value));
            }
        }
        return query.toString();
    }

    /**
     * Builds and returns the URL as a {@link URI} object.
     *
     * @return the complete URL as a URI.
     * @throws IllegalStateException if the URI cannot be constructed from the provided components.
     */
    public URI toURI() {
        try {
            return new URI(
                this.scheme,
                null,
                this.host,
                this.port == null ? -1 : this.port,
                this.buildPath(),
                this.buildQuery(),
                this.fragment
            );
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Failed to build URI from the provided components", e);
        }
    }

    /**
     * Builds and returns the URL as a string.
     *
     * @return the complete URL as a string.
     */
    @Override
    public String toString() {
        return this.toURI().toString();
    }
}
