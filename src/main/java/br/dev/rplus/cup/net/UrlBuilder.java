package br.dev.rplus.cup.net;

import br.dev.rplus.cup.log.Logger;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Utility class for building URLs.
 */
public class UrlBuilder {

    private final StringBuilder url;

    private UrlBuilder(String url) {
        this.url = new StringBuilder(url);
    }

    /**
     * Creates a new instance of the UrlBuilder class from the given URL.
     *
     * @param url the URL string representing the initial state of the UrlBuilder
     * @return a new instance of the UrlBuilder class
     */
    public static UrlBuilder from(String url) {
        return new UrlBuilder(url);
    }

    /**
     * Adds the given parameters to the URL.
     *
     * @param params a map of key-value pairs representing the parameters to add
     * @return the updated UrlBuilder object
     */
    public UrlBuilder addParameter(Map<String, String> params) {
        if (url.indexOf("?") == -1) {
            url.append("?");
        }
        String key;
        String value;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (url.indexOf("?") != url.length() - 1) {
                url.append("&");
            }
            key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
            url.append(String.format("%s=%s", key, value));
        }
        return this;
    }

    /**
     * Builds the URL and returns it as a string.
     *
     * @return the URL as a string.
     */
    public String build() {
        return url.toString();
    }

    /**
     * Builds the URL and returns it as a URI.
     *
     * @return the URL as a URI.
     */
    public URI buildURI() {
        try {
            return new URI(this.url.toString());
        } catch (Exception e) {
            Logger.error("Failed to build url: %s", this.url.toString(), e);
            return null;
        }
    }
}