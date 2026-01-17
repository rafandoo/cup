package dev.rafandoo.cup.request;

import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an HTTP response returned from an {@link HttpRequester}.
 * <p>
 * This class encapsulates the response status code, headers, and body as an InputStream,
 * along with metadata such as Content-Type, content length, success flag, and cookies.
 * </p>
 */
@Getter
public class HttpResponse {

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
     * @param statusCode    the HTTP status code.
     * @param body          InputStream of the response body.
     * @param headers       the response headers.
     * @param contentLength length of the body if known, otherwise -1.
     */
    public HttpResponse(int statusCode, InputStream body, Map<String, java.util.List<String>> headers, long contentLength) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = Map.copyOf(headers);

        this.contentType = headers.getOrDefault("Content-Type", java.util.List.of("")).stream().findFirst().orElse("");
        this.contentLength = contentLength;
        this.isSuccessful = statusCode >= 200 && statusCode < 300;

        this.cookies = new HashMap<>();
        if (headers.containsKey("Set-Cookie")) {
            for (String cookie : headers.get("Set-Cookie")) {
                String[] parts = cookie.split(";", 2);
                String[] keyValue = parts[0].split("=", 2);
                if (keyValue.length == 2) this.cookies.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
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
        return this.headers.getOrDefault(name, List.of("")).stream().findFirst().orElse(null);
    }
}
