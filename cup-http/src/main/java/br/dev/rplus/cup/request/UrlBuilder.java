package br.dev.rplus.cup.request;

import br.dev.rplus.cup.log.Logger;
import br.dev.rplus.cup.utils.StringUtils;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for building URLs.
 */
@SuppressWarnings("unused")
public class UrlBuilder {

    private final StringBuilder urlBuilder;
    private final Map<String, String> params;

    private String protocol;
    private String url;
    private int port;
    private String path;

    private boolean built;

    /**
     * Private constructor to prevent instantiation.
     */
    private UrlBuilder() {
        this.urlBuilder = new StringBuilder();
        this.params = new HashMap<>();
        this.built = false;
    }

    /**
     * Creates a new instance of the UrlBuilder class.
     *
     * @return a new instance of the UrlBuilder class.
     */
    public static UrlBuilder builder() {
        return new UrlBuilder();
    }

    /**
     * Defines the protocol for the URLBuilder.
     *
     * @param protocol the protocol to be set.
     * @return the updated UrlBuilder object.
     */
    public UrlBuilder protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * Defines the URL for the URLBuilder.
     *
     * @param url the URL to be set.
     * @return the updated UrlBuilder object.
     */
    public UrlBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * Defines the port for the URLBuilder.
     *
     * @param port the port to be set.
     * @return the updated UrlBuilder object.
     */
    public UrlBuilder port(int port) {
        this.port = port;
        return this;
    }

    /**
     * Defines the path for the URLBuilder.
     *
     * @param path the path to be set.
     * @return the updated UrlBuilder object.
     */
    public UrlBuilder path(String path) {
        this.path = path;
        return this;
    }

    /**
     * Adds the given parameters to the URL.
     *
     * @param params a map of key-value pairs representing the parameters to add.
     * @return the updated UrlBuilder object.
     */
    public UrlBuilder addParameters(Map<String, String> params) {
        if (params != null) {
            this.params.putAll(params);
        }
        return this;
    }

    /**
     * Adds the given parameter to the URL.
     *
     * @param key   the key of the parameter to add.
     * @param value the value of the parameter to add.
     * @return the updated UrlBuilder object.
     */
    public UrlBuilder addParameter(String key, String value) {
        if (!StringUtils.isNullOrEmpty(key) && !StringUtils.isNullOrEmpty(value)) {
            this.params.put(key, value);
        }
        return this;
    }

    /**
     * Builds the final URL if it has not been built yet.
     */
    private void build() {
        if (this.built) return;

        this.appendProtocolIfNecessary();
        this.appendUrl();
        this.appendPortIfNecessary();
        this.appendPath();
        this.appendParameters();

        this.built = true;
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
            this.urlBuilder.append(this.url);
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
            boolean needsSlash = !this.path.startsWith("/") && (this.url == null || !this.url.endsWith("/"));
            if (needsSlash) {
                this.urlBuilder.append("/");
            }
            this.urlBuilder.append(this.path);
        }
    }

    /**
     * Appends query parameters to the URL in the format <code>?key=value&key2=value2...</code>.
     * Each key and value is URL-encoded using UTF-8.
     */
    private void appendParameters() {
        if (this.params.isEmpty()) return;

        // Append "?" if not present
        if (this.urlBuilder.indexOf("?") == -1) {
            this.urlBuilder.append("?");
        }

        boolean first = this.urlBuilder.charAt(this.urlBuilder.length() - 1) == '?';
        for (var entry : this.params.entrySet()) {
            if (!first) {
                this.urlBuilder.append("&");
            } else {
                first = false;
            }

            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
            this.urlBuilder.append(key).append("=").append(value);
        }
    }

    /**
     * Builds the URL and returns it as a string.
     *
     * @return the URL as a string.
     */
    @Override
    public String toString() {
        this.build();
        return this.urlBuilder.toString();
    }

    /**
     * Builds the URL and returns it as a URI.
     *
     * @return the URL as a URI.
     */
    public URI toURI() {
        try {
            this.build();
            return new URI(this.urlBuilder.toString());
        } catch (Exception e) {
            Logger.getInstance().error("Failed to build url: %s", this.urlBuilder.toString(), e);
            return null;
        }
    }
}
