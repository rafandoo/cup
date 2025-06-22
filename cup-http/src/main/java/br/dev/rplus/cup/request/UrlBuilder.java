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
     * Builds the URL by appending the protocol, URL, port, path, and parameters.
     */
    private void build() {
        if (this.built) return;

        if (!StringUtils.isNullOrEmpty(this.protocol) && (this.url == null || !this.url.contains("://"))) {
            this.urlBuilder.append(this.protocol).append("://");
        }

        if (!StringUtils.isNullOrEmpty(this.url)) {
            this.urlBuilder.append(this.url);
        }

        if (this.port > 0 && (this.url == null || !this.url.contains(":"))) {
            this.urlBuilder.append(":").append(this.port);
        }

        if (!StringUtils.isNullOrEmpty(this.path)) {
            if (!this.path.startsWith("/") && (this.url == null || !this.url.endsWith("/"))) {
                this.urlBuilder.append("/");
            }
            this.urlBuilder.append(this.path);
        }

        if (!params.isEmpty()) {
            this.urlBuilder.append(this.urlBuilder.indexOf("?") == -1 ? "?" : "");

            boolean first = this.urlBuilder.charAt(this.urlBuilder.length() - 1) == '?';
            for (var entry : this.params.entrySet()) {
                if (!first) {
                    this.urlBuilder.append("&");
                } else {
                    first = false;
                }
                var key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
                var value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
                this.urlBuilder.append(key).append("=").append(value);
            }
        }

        this.built = true;
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
