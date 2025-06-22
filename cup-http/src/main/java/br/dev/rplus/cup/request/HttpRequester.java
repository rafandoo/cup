package br.dev.rplus.cup.request;

import br.dev.rplus.cup.log.Logger;
import br.dev.rplus.cup.utils.UniqueIdentifiers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to make HTTP requests.
 */
@SuppressWarnings("unused")
public class HttpRequester {

    private URL url;
    private HttpMethod method;
    private Map<String, String> headers;
    private String requestBody;
    private int connectionTimeout = 30000;
    private int readTimeout = 30000;
    private boolean followRedirects;
    private HttpURLConnection conn;

    /**
     * HTTP methods supported by the `RequestHttp` class.
     */
    public enum HttpMethod {
        /**
         * HTTP GET method.
         */
        GET,

        /**
         * HTTP POST method.
         */
        POST,

        /**
         * HTTP PUT method.
         */
        PUT,

        /**
         * HTTP DELETE method.
         */
        DELETE,

        /**
         * HTTP PATCH method.
         */
        PATCH,

        /**
         * HTTP HEAD method.
         */
        HEAD,

        /**
         * HTTP OPTIONS method.
         */
        OPTIONS
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private HttpRequester() {
        this.headers = this.getDefaultHeaders();
        this.method = HttpMethod.GET;
        this.followRedirects = false;
    }

    /**
     * Creates a new instance of the `RequestHttp` class.
     *
     * @return the created `RequestHttp` instance.
     */
    public static HttpRequester builder() {
        return new HttpRequester();
    }

    /**
     * Defines the URL for the HTTP request.
     *
     * @param url the URL to be set.
     * @return the updated RequestHttp object.
     */
    public HttpRequester url(String url) {
        try {
            this.url = URI.create(url).toURL();
        } catch (Exception e) {
            Logger.getInstance().error("Failed to define url for request: %s", url, e);
        }
        return this;
    }

    /**
     * Defines the URL for the HTTP request.
     *
     * @param uri the URL to be set.
     * @return the updated RequestHttp object.
     */
    public HttpRequester url(URI uri) {
        try {
            this.url = uri.toURL();
        } catch (Exception e) {
            Logger.getInstance().error("Failed to define url for request: %s", uri, e);
        }
        return this;
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
     * Sets headers for the HTTP request.
     *
     * @param headers a map of headers to set.
     * @return the updated RequestHttp object.
     */
    public HttpRequester headers(Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            this.headers = Map.copyOf(headers);
        }
        return this;
    }

    /**
     * Sets a header for the HTTP request.
     *
     * @param key   the header key to set.
     * @param value the header value to set.
     * @return the updated RequestHttp object.
     */
    public HttpRequester header(String key, String value) {
        this.headers.put(key, value);
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
     * Sets the body for the HTTP request, using multipart/form-data. Used mainly with POST, PUT, PATCH.
     *
     * @param fields a map of fields to set.
     * @param files  a map of files to set.
     * @return the updated RequestHttp object.
     */
    public HttpRequester multipartBody(Map<String, String> fields, Map<String, File> files) {
        String boundary = "----WebKitFormBoundary" + UniqueIdentifiers.getUUIDv4().toString();
        this.headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);

        StringBuilder bodyBuilder = new StringBuilder();
        try {
            if (fields != null) {
                for (Map.Entry<String, String> entry : fields.entrySet()) {
                    bodyBuilder.append("--")
                            .append(boundary)
                            .append("\r\n");
                    bodyBuilder.append("Content-Disposition: form-data; name=\"")
                            .append(entry.getKey())
                            .append("\"\r\n");
                    bodyBuilder.append("\r\n");
                    bodyBuilder.append(entry.getValue())
                            .append("\r\n");
                }
            }

            if (files != null) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    File file = entry.getValue();
                    String fileName = file.getName();
                    String mimeType = Files.probeContentType(file.toPath());

                    bodyBuilder.append("--")
                            .append(boundary)
                            .append("\r\n");
                    bodyBuilder.append("Content-Disposition: form-data; name=\"")
                            .append(entry.getKey())
                            .append("\"; filename=\"")
                            .append(fileName)
                            .append("\"\r\n");
                    bodyBuilder.append("Content-Type: ")
                            .append(mimeType != null ? mimeType : "application/octet-stream")
                            .append("\r\n");
                    bodyBuilder.append("\r\n");

                    try (InputStream is = new FileInputStream(file)) {
                        byte[] buffer = is.readAllBytes();
                        bodyBuilder.append(new String(buffer, StandardCharsets.UTF_8));
                    }
                    bodyBuilder.append("\r\n");
                }
            }

            bodyBuilder.append("--")
                    .append(boundary)
                    .append("--\r\n");
        } catch (IOException e) {
            Logger.getInstance().error("Failed to build multipart body for request.", e);
        }
        this.requestBody = bodyBuilder.toString();
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
     * Sets whether to follow redirects for the request.
     *
     * @param followRedirects a boolean value indicating whether to follow redirects.
     * @return the updated RequestHttp object.
     */
    public HttpRequester followRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    /**
     * Sends the HTTP request and retrieves the response.
     *
     * @return a StringBuilder object containing the response from the URL.
     */
    public StringBuilder send() {
        this.conn = null;
        StringBuilder response;
        try {
            this.prepareRequest();

            if (this.conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
                response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response;
            } else {
                Logger.getInstance().warn("Failed to retrieve data from URL: %s", this.url);
                Logger.getInstance().warn("Response code: %s.", this.conn.getResponseCode());
                Logger.getInstance().warn("Response message: %s.", this.conn.getResponseMessage());
            }
        } catch (IOException e) {
            Logger.getInstance().error("Failed to send request to url: %s", this.url, e);
            throw new RuntimeException(e);
        } finally {
            if (this.conn != null) {
                this.conn.disconnect();
            }
        }
        return null;
    }

    /**
     * Sends the HTTP request and retrieves the response.
     *
     * @return the HttpURLConnection object.
     */
    public HttpURLConnection sendRaw() {
        this.prepareRequest();
        return this.conn;
    }

    /**
     * Prepares the HTTP request by setting up the connection and headers.
     */
    private void prepareRequest() {
        try {
            this.conn = (HttpURLConnection) this.url.openConnection();
            this.conn.setRequestMethod(this.method.name());
            this.conn.setConnectTimeout(this.connectionTimeout);
            this.conn.setReadTimeout(this.readTimeout);
            this.conn.setInstanceFollowRedirects(this.followRedirects);

            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                this.conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            if (this.method == HttpMethod.POST || this.method == HttpMethod.PUT || this.method == HttpMethod.PATCH) {
                this.conn.setDoOutput(true);
                if (this.requestBody != null && !this.requestBody.isEmpty()) {
                    try (OutputStream os = this.conn.getOutputStream()) {
                        os.write(this.requestBody.getBytes());
                        os.flush();
                    }
                }
            }
        } catch (IOException e) {
            Logger.getInstance().error("Failed to prepare request to url: %s", this.url, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a map of default headers to be included in every request.
     *
     * @return a map containing default headers.
     */
    private Map<String, String> getDefaultHeaders() {
        return new HashMap<>() {{
            put("User-Agent", "CUP-RequestHttp/1.0");
        }};
    }
}
