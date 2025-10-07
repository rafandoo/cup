package dev.rafandoo.cup.request;

import dev.rafandoo.cup.utils.UniqueIdentifiers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to make HTTP requests.
 */
public class HttpRequester {

    private URL url;
    private HttpMethod method;
    private final Map<String, Object> headers = new HashMap<>();
    private byte[] requestBody;
    private int connectionTimeout = 30000;
    private int readTimeout = 30000;
    private boolean followRedirects = false;
    private HttpURLConnection conn;

    /**
     * HTTP methods supported by the `RequestHttp` class.
     */
    private enum HttpMethod {

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
        this.headers.put("User-Agent", "CUP-RequestHttp/1.0");
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
     * @throws MalformedURLException if the provided URL is invalid.
     */
    public HttpRequester url(String url) throws MalformedURLException {
        this.url = URI.create(url).toURL();
        return this;
    }

    /**
     * Defines the URL for the HTTP request.
     *
     * @param uri the URL to be set.
     * @return the updated RequestHttp object.
     * @throws MalformedURLException if the provided URL is invalid.
     */
    public HttpRequester url(URI uri) throws MalformedURLException {
        this.url = uri.toURL();
        return this;
    }

    /**
     * Sets headers for the HTTP request.
     *
     * @param headers a map of headers to set.
     * @return the updated RequestHttp object.
     */
    public HttpRequester headers(Map<String, Object> headers) {
        if (headers != null) {
            this.headers.putAll(headers);
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
    public HttpRequester header(String key, Object value) {
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
        this.requestBody = body.getBytes();
        return this;
    }

    /**
     * Sets the body for the HTTP request, using multipart/form-data. Used mainly with POST, PUT, PATCH.
     *
     * @param parts a map of parts to set.
     * @return the updated RequestHttp object.
     * @throws IOException if an I/O error occurs while reading files.
     */
    public HttpRequester multipartBodyParts(Map<String, Object> parts) throws IOException {
        return this.multipartBody(parts, null);
    }

    /**
     * Sets the body for the HTTP request, using multipart/form-data. Used mainly with POST, PUT, PATCH.
     *
     * @param files a map of files to set.
     * @return the updated RequestHttp object.
     * @throws IOException if an I/O error occurs while reading files.
     */
    public HttpRequester multipartBodyFiles(Map<String, File> files) throws IOException {
        return this.multipartBody(null, files);
    }

    /**
     * Sets the body for the HTTP request, using multipart/form-data. Used mainly with POST, PUT, PATCH.
     *
     * @param parts a map of fields to set.
     * @param files a map of files to set.
     * @return the updated RequestHttp object.
     * @throws IOException if an I/O error occurs while reading files.
     */
    public HttpRequester multipartBody(Map<String, Object> parts, Map<String, File> files) throws IOException {
        String boundary = "----WebKitFormBoundary" + UniqueIdentifiers.getUUIDv4().toString();
        this.headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (parts != null) {
                for (Map.Entry<String, Object> entry : parts.entrySet()) {
                    baos.write(("--" + boundary + "\r\n").getBytes());
                    baos.write(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n").getBytes());
                    baos.write(entry.getValue().toString().getBytes());
                    baos.write("\r\n".getBytes());
                }
            }
            if (files != null) {
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    File file = entry.getValue();
                    String fileName = file.getName();
                    String mimeType = Files.probeContentType(file.toPath());

                    baos.write(("--" + boundary + "\r\n").getBytes());
                    baos.write(("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\"" + fileName + "\"\r\n").getBytes());
                    baos.write(("Content-Type: " + (mimeType != null ? mimeType : "application/octet-stream") + "\r\n\r\n").getBytes());
                    Files.copy(file.toPath(), baos);
                    baos.write("\r\n".getBytes());
                }
            }

            baos.write(("--" + boundary + "--\r\n").getBytes());
            this.requestBody = baos.toByteArray();
        }
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
     * Executes an HTTP GET request.
     *
     * @return the HttpResponse object containing the response details.
     * @throws IOException if an I/O error occurs during the request.
     */
    public HttpResponse get() throws IOException {
        return this.execute(HttpMethod.GET);
    }

    /**
     * Executes an HTTP POST request.
     *
     * @return the HttpResponse object containing the response details.
     * @throws IOException if an I/O error occurs during the request.
     */
    public HttpResponse post() throws IOException {
        return this.execute(HttpMethod.POST);
    }

    /**
     * Executes an HTTP PUT request.
     *
     * @return the HttpResponse object containing the response details.
     * @throws IOException if an I/O error occurs during the request.
     */
    public HttpResponse put() throws IOException {
        return this.execute(HttpMethod.PUT);
    }

    /**
     * Executes an HTTP DELETE request.
     *
     * @return the HttpResponse object containing the response details.
     * @throws IOException if an I/O error occurs during the request.
     */
    public HttpResponse delete() throws IOException {
        return this.execute(HttpMethod.DELETE);
    }

    /**
     * Executes an HTTP PATCH request.
     *
     * @return the HttpResponse object containing the response details.
     * @throws IOException if an I/O error occurs during the request.
     */
    public HttpResponse patch() throws IOException {
        return this.execute(HttpMethod.PATCH);
    }

    /**
     * Executes an HTTP HEAD request.
     *
     * @return the HttpResponse object containing the response details.
     * @throws IOException if an I/O error occurs during the request.
     */
    public HttpResponse head() throws IOException {
        return this.execute(HttpMethod.HEAD);
    }

    /**
     * Executes an HTTP OPTIONS request.
     *
     * @return the HttpResponse object containing the response details.
     * @throws IOException if an I/O error occurs during the request.
     */
    public HttpResponse options() throws IOException {
        return this.execute(HttpMethod.OPTIONS);
    }

    /**
     * Executes the HTTP request with the specified method.
     *
     * @param method the HTTP method to use for the request.
     * @return the HttpResponse object containing the response details.
     * @throws IOException if an I/O error occurs during the request.
     */
    private HttpResponse execute(HttpMethod method) throws IOException {
        try {
            this.method = method;
            prepareRequest();
            int status = conn.getResponseCode();
            InputStream is = status >= 400 ? conn.getErrorStream() : conn.getInputStream();
            return new HttpResponse(status, is, conn.getHeaderFields(), conn.getContentLength());
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Prepares the HTTP request by setting up the connection and headers.
     *
     * @throws IOException if an I/O error occurs while preparing the request.
     */
    private void prepareRequest() throws IOException {
        this.conn = (HttpURLConnection) this.url.openConnection();
        this.conn.setRequestMethod(this.method.name());
        this.conn.setConnectTimeout(this.connectionTimeout);
        this.conn.setReadTimeout(this.readTimeout);
        this.conn.setInstanceFollowRedirects(this.followRedirects);

        for (Map.Entry<String, Object> entry : this.headers.entrySet()) {
            this.conn.setRequestProperty(entry.getKey(), entry.getValue().toString());
        }

        if (this.method == HttpMethod.POST || this.method == HttpMethod.PUT || this.method == HttpMethod.PATCH) {
            this.conn.setDoOutput(true);
            if (this.requestBody != null) {
                try (OutputStream os = this.conn.getOutputStream()) {
                    os.write(this.requestBody);
                    os.flush();
                }
            }
        }
    }
}
