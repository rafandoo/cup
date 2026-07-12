package dev.rafandoo.cup.request;

import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents an HTTP response returned from an {@link HttpRequester}.
 * <p>
 * This class encapsulates the response status code, headers, and body as an InputStream,
 * along with metadata such as Content-Type, content length, success flag, and cookies.
 * </p>
 */
@Getter
public class HttpResponse implements AutoCloseable {

    private final HttpURLConnection connection;

    private final int statusCode;
    private final InputStream body;
    private final Map<String, List<String>> headers;

    private final String contentType;
    private final long contentLength;
    private final boolean isSuccessful;
    private final Map<String, String> cookies;

    /**
     * Constructs a new HttpResponse object.
     *
     * @param conn the {@link HttpURLConnection} from which to extract the response details.
     */
    public HttpResponse(HttpURLConnection conn) throws IOException {
        this.connection = conn;
        this.statusCode = conn.getResponseCode();
        this.body = this.statusCode >= 400 ? conn.getErrorStream() : conn.getInputStream();
        this.headers = conn.getHeaderFields()
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey() != null)
            .collect(Collectors.toUnmodifiableMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));

        this.contentType = headers.getOrDefault("Content-Type", List.of("")).stream().findFirst().orElse("");
        this.contentLength = conn.getContentLength();
        this.isSuccessful = statusCode >= 200 && statusCode < 300;

        this.cookies = CookieParser.parse(headers);
    }

    /**
     * Reads the entire InputStream into a byte array.
     * <p>
     * Use with caution for large responses as it loads everything into memory.
     * </p>
     *
     * @return byte array of the response body
     * @throws IOException if an I/O error occurs
     */
    public byte[] getBodyAsBytes() throws IOException {
        try (InputStream is = this.body; ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
            return baos.toByteArray();
        }
    }

    /**
     * Returns the response body as a UTF-8 string.
     *
     * @return response body as string
     * @throws IOException if an I/O error occurs
     */
    public String getBodyAsString() throws IOException {
        return new String(this.getBodyAsBytes());
    }

    /**
     * Returns the response body as a string using the specified charset.
     *
     * @param charset charset to decode the body
     * @return response body as string
     * @throws IOException                  if an I/O error occurs
     * @throws UnsupportedEncodingException if charset is not supported
     */
    public String getBodyAsString(String charset) throws IOException, UnsupportedEncodingException {
        return new String(this.getBodyAsBytes(), charset);
    }

    /**
     * Returns the first value of a specific HTTP header.
     *
     * @param name the header name
     * @return the header value, or null if not present
     */
    public String getHeader(String name) {
        return this.headers.getOrDefault(name, List.of(""))
            .stream()
            .findFirst()
            .orElse(null);
    }

    /**
     * Checks if the response status code indicates a non-successful response (4xx or 5xx).
     *
     * @return {@code true} if the response is not successful, {@code false} otherwise
     */
    public boolean isNotSuccessful() {
        return !this.isSuccessful;
    }

    @Override
    public void close() {
        try {
            if (this.body != null) {
                this.body.close();
            }
        } catch (IOException ignored) {
        }
        if (this.connection != null) {
            this.connection.disconnect();
        }
    }
}
