package dev.rafandoo.cup.request;

import dev.rafandoo.cup.utils.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A flexible and fluent utility class for building URLs dynamically.
 * <p>
 * Example usage:
 * <pre>./gradlew clean aggregateJavadoc
 *
 * String url = UrlBuilder.builder()
 *     .protocol("https")
 *     .url("api.example.com")
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

    private final StringBuilder urlBuilder;
    private final Map<String, Object> params;
    private String protocol;
    private String url;
    private int port;
    private String path;
    private String fragment;

    private boolean built;

    /**
     * Private constructor to enforce the use of the static builder() method.
     */
    private UrlBuilder() {
        this.urlBuilder = new StringBuilder();
        this.params = new LinkedHashMap<>();
        this.built = false;
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
     * @param protocol the protocol to use.
     * @return the current builder instance.
     */
    public UrlBuilder protocol(String protocol) {
        this.ensureNotBuilt();
        this.protocol = protocol;
        return this;
    }

    /**
     * Defines the host or base URL.
     *
     * @param url the host or base URL.
     * @return the current builder instance.
     */
    public UrlBuilder url(String url) {
        this.ensureNotBuilt();
        this.url = url;
        return this;
    }

    /**
     * Defines the port number to use.
     *
     * @param port the port number.
     * @return the current builder instance.
     */
    public UrlBuilder port(int port) {
        this.ensureNotBuilt();
        this.port = port;
        return this;
    }

    /**
     * Defines the path (endpoint) to append to the URL.
     *
     * @param path the path to append.
     * @return the current builder instance.
     */
    public UrlBuilder path(String path) {
        this.ensureNotBuilt();
        this.path = path;
        return this;
    }

    /**
     * Adds multiple query parameters.
     *
     * @param params map of key-value query parameters.
     * @return the current builder instance.
     */
    public UrlBuilder addParameters(Map<String, Object> params) {
        this.ensureNotBuilt();
        if (params != null) {
            this.params.putAll(params);
        }
        return this;
    }

    /**
     * Adds a single query parameter.
     *
     * @param key   the parameter name.
     * @param value the parameter value (null values are ignored).
     * @return the current builder instance.
     */
    public UrlBuilder addParameter(String key, Object value) {
        this.ensureNotBuilt();
        if (!StringUtils.isNullOrEmpty(key) && value != null && !StringUtils.isNullOrEmpty(value.toString())) {
            this.params.put(key, value);
        }
        return this;
    }

    /**
     * Defines a URL fragment (the part after '#').
     *
     * @param fragment the fragment identifier.
     * @return the current builder instance.
     */
    public UrlBuilder fragment(String fragment) {
        this.ensureNotBuilt();
        this.fragment = fragment;
        return this;
    }

    /**
     * Builds the final URL if not built yet.
     *
     * @return the current builder instance with the built URL.
     */
    private UrlBuilder build() {
        if (this.built) return this;

        this.appendProtocolIfNecessary();
        this.appendUrl();
        this.appendPortIfNecessary();
        this.appendPath();
        this.appendParameters();
        this.appendFragment();

        this.built = true;
        return this;
    }

    /**
     * Appends the protocol to the URL if it's defined and not already present in the URL.
     */
    private void appendProtocolIfNecessary() {
        if (!StringUtils.isNullOrEmpty(this.protocol)
            && (this.url == null || !this.url.contains("://"))) {
            this.urlBuilder.append(this.protocol).append("://");
        }
    }

    /**
     * Appends the main URL (host/domain) if defined.
     */
    private void appendUrl() {
        if (!StringUtils.isNullOrEmpty(this.url)) {
            this.urlBuilder.append(this.url.replaceAll("/+$", ""));
        }
    }

    /**
     * Appends the port to the URL if it is greater than 0 and not already included.
     */
    private void appendPortIfNecessary() {
        if (this.port > 0 && (this.url == null || !this.url.contains(":"))) {
            this.urlBuilder.append(":").append(this.port);
        }
    }

    /**
     * Appends the path to the URL, ensuring that slashes are correctly positioned.
     */
    private void appendPath() {
        if (!StringUtils.isNullOrEmpty(this.path)) {
            boolean needsSlash = !this.path.startsWith("/") && !this.urlBuilder.isEmpty()
                && this.urlBuilder.charAt(this.urlBuilder.length() - 1) != '/';
            if (needsSlash) {
                this.urlBuilder.append("/");
            }
            this.urlBuilder.append(this.path.replaceAll("^/+", ""));
        }
    }

    /**
     * Appends query parameters to the URL in the format <code>?key=value&key2=value2...</code>.
     * Each key and value is URL-encoded using UTF-8.
     */
    private void appendParameters() {
        if (this.params.isEmpty()) return;

        if (this.urlBuilder.indexOf("?") == -1) {
            this.urlBuilder.append("?");
        } else if (!this.urlBuilder.toString().endsWith("?") && !this.urlBuilder.toString().endsWith("&")) {
            this.urlBuilder.append("&");
        }

        boolean first = this.urlBuilder.charAt(this.urlBuilder.length() - 1) == '?';
        for (var entry : this.params.entrySet()) {
            if (!first) this.urlBuilder.append("&");
            else first = false;

            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String value = URLEncoder.encode(String.valueOf(entry.getValue()), StandardCharsets.UTF_8);
            this.urlBuilder.append(key).append("=").append(value);
        }
    }

    /**
     * Appends the fragment identifier to the URL if defined.
     */
    private void appendFragment() {
        if (!StringUtils.isNullOrEmpty(this.fragment)) {
            this.urlBuilder.append("#").append(URLEncoder.encode(this.fragment, StandardCharsets.UTF_8));
        }
    }

    /**
     * Ensures that the URL has not been built yet.
     *
     * @throws IllegalStateException if the URL has already been built.
     */
    private void ensureNotBuilt() {
        if (this.built)
            throw new IllegalStateException("UrlBuilder cannot be modified after build()");
    }

    /**
     * Builds and returns the URL as a string.
     *
     * @return the complete URL as a string.
     */
    @Override
    public String toString() {
        this.build();
        return this.urlBuilder.toString();
    }

    /**
     * Builds and returns the URL as a {@link URI}.
     *
     * @return the complete URL as a URI.
     * @throws URISyntaxException if the resulting URL is invalid.
     */
    public URI toURI() throws URISyntaxException {
        this.build();
        return new URI(this.urlBuilder.toString());
    }
}
