package br.dev.rplus.cup.net;

import br.dev.rplus.cup.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to make HTTP requests.
 */
public class HttpRequester {

    private final URL url;
    private HttpMethod method;
    private Map<String, String> headers;
    private String requestBody;
    private int connectionTimeout = 30000;
    private int readTimeout = 30000;

    /**
     * HTTP methods supported by the `RequestHttp` class.
     */
    public enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE,
        PATCH,
        HEAD,
        OPTIONS
    }

    private HttpRequester(URL url) {
        this.url = url;
        this.headers = this.getDefaultHeaders();
        this.method = HttpMethod.GET;
    }

    /**
     * Creates a new instance of the `RequestHttp` class from a given URL string.
     *
     * @param url the URL string to create the `RequestHttp` instance from.
     * @return the created `RequestHttp` instance.
     */
    public static HttpRequester from(String url) {
        try {
            URI uri = new URI(url);
            return new HttpRequester(uri.toURL());
        } catch (Exception e) {
            Logger.error("Failed to create HttpRequester from url: %s", url, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the HTTP method for the request.
     *
     * @param method the HTTP method to be set.
     * @return the updated RequestHttp object.
     */
    public HttpRequester method(HttpMethod method) {
        this.method = method;
        return this;
    }

    /**
     * Sets or overrides headers for the HTTP request.
     *
     * @param headers a map of headers to set.
     * @return the updated RequestHttp object.
     */
    public HttpRequester headers(Map<String, String> headers) {
        if (headers != null) {
            this.headers = Map.copyOf(headers);
        }
        return this;
    }

    /**
     * Sets the body for the HTTP request. Used mainly with POST, PUT, PATCH.
     *
     * @param body the request body to set.
     * @return the updated RequestHttp object.
     */
    public HttpRequester body(String body) {
        this.requestBody = body;
        return this;
    }

    /**
     * Sets the connection timeout for the request.
     *
     * @param timeout the timeout in milliseconds.
     * @return the updated RequestHttp object.
     */
    public HttpRequester connectionTimeout(int timeout) {
        this.connectionTimeout = timeout;
        return this;
    }

    /**
     * Sets the read timeout for the request.
     *
     * @param timeout the timeout in milliseconds.
     * @return the updated RequestHttp object.
     */
    public HttpRequester readTimeout(int timeout) {
        this.readTimeout = timeout;
        return this;
    }

    /**
     * Sends the HTTP request and retrieves the response.
     *
     * @return a StringBuilder object containing the response from the URL.
     */
    public StringBuilder send() {
        HttpURLConnection conn = null;
        StringBuilder response;
        try {
            conn = (HttpURLConnection) this.url.openConnection();
            conn.setRequestMethod(this.method.name());
            conn.setConnectTimeout(this.connectionTimeout);
            conn.setReadTimeout(this.readTimeout);

            if (this.headers != null && !this.headers.isEmpty()) {
                for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (this.method == HttpMethod.POST || this.method == HttpMethod.PUT || this.method == HttpMethod.PATCH) {
                conn.setDoOutput(true);
                if (this.requestBody != null && !this.requestBody.isEmpty()) {
                    try (OutputStream os = conn.getOutputStream()) {
                        os.write(this.requestBody.getBytes());
                        os.flush();
                    }
                }
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response;
            } else {
                Logger.warn("Failed to retrieve data from URL: %s", this.url);
                Logger.warn("Response code: %s.", conn.getResponseCode());
                Logger.warn("Response message: %s.", conn.getResponseMessage());
            }
        } catch (IOException e) {
            Logger.error("Failed to send request to url: %s", this.url, e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    /**
     * Returns a map of default headers to be included in every request.
     *
     * @return a map containing default headers.
     */
    private Map<String, String> getDefaultHeaders() {
        Map<String, String> defaultHeaders = new HashMap<>();
        defaultHeaders.put("User-Agent", "RequestHttp/1.0");
        defaultHeaders.put("Accept", "*/*");
        defaultHeaders.put("Content-Type", "application/json");
        return defaultHeaders;
    }
}