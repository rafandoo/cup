package br.dev.rplus.cup.utils;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.enums.UuidLocalDomain;
import com.github.f4b6a3.uuid.enums.UuidNamespace;
import com.github.f4b6a3.uuid.util.UuidValidator;

import java.time.Instant;
import java.util.UUID;

/**
 * The {@code UniqueIdentifiers} class provides a set of static methods for generating
 * various types of unique identifiers, including UUIDs (Versions 1 to 7) and ULIDs.
 */
public class UniqueIdentifiers {

    /**
     * Private constructor to prevent instantiation.
     */
    private UniqueIdentifiers() {}

    /**
     * Generates a time-based UUID (Version 1).
     * This UUID includes a timestamp and a machine identifier.
     *
     * @return a UUIDv1.
     */
    public static UUID getUUIDv1() {
        return UuidCreator.getTimeBased();
    }

    /**
     * Generates a DCE Security UUID (Version 2).
     * This UUID includes a timestamp, local domain, and local identifier.
     *
     * @param localIdentifier the local identifier to include in the UUID.
     * @return a UUIDv2.
     */
    public static UUID getUUIDv2(int localIdentifier) {
        return UuidCreator.getDceSecurity(UuidLocalDomain.LOCAL_DOMAIN_PERSON, localIdentifier);
    }

    /**
     * Generates a name-based UUID (Version 3) using MD5 hashing.
     * The generated UUID is based on the given name and the URL namespace.
     *
     * @param name the name to hash into a UUID.
     * @return a UUIDv3.
     */
    public static UUID getUUIDv3(String name) {
        return UuidCreator.getNameBasedMd5(UuidNamespace.NAMESPACE_URL, name);
    }

    /**
     * Generates a random-based UUID (Version 4).
     * This UUID is purely random and does not contain any timestamp or machine identifier.
     *
     * @return a UUIDv4.
     */
    public static UUID getUUIDv4() {
        return UuidCreator.getRandomBased();
    }

    /**
     * Generates a name-based UUID (Version 5) using SHA-1 hashing.
     * The generated UUID is based on the given name and the URL namespace.
     *
     * @param name the name to hash into a UUID.
     * @return a UUIDv5.
     */
    public static UUID getUUIDv5(String name) {
        return UuidCreator.getNameBasedSha1(UuidNamespace.NAMESPACE_URL, name);
    }

    /**
     * Generates a time-ordered UUID (Version 6).
     * This UUID includes a timestamp, and its ordering is optimized for database indexing.
     *
     * @return a UUIDv6.
     */
    public static UUID getUUIDv6() {
        return UuidCreator.getTimeOrdered();
    }

    /**
     * Generates a Unix Epoch time-based UUID (Version 7).
     * This UUID includes a timestamp with millisecond precision and random data.
     *
     * @return a UUIDv7.
     */
    public static UUID getUUIDv7() {
        return UuidCreator.getTimeOrderedEpoch();
    }

    /**
     * Generates a ULID (Universally Unique Lexicographically Sortable Identifier).
     * This identifier is based on the current time and random data.
     *
     * @return a ULID.
     */
    public static Ulid getULID() {
        return UlidCreator.getUlid();
    }

    /**
     * Generates a monotonic ULID, which guarantees that ULIDs are ordered
     * if generated within the same millisecond.
     *
     * @return a monotonic ULID.
     */
    public static Ulid getMonotonicUlid() {
        return UlidCreator.getMonotonicUlid();
    }

    /**
     * Generates a hash-based ULID using the specified timestamp and name.
     *
     * @param time the timestamp to include in the ULID.
     * @param name the name to hash into the ULID.
     * @return a hash-based ULID.
     */
    public static Ulid getHashUlid(long time, String name) {
        return UlidCreator.getHashUlid(time, name);
    }

    /**
     * Generates a fast ULID. This method is optimized for speed.
     *
     * @return a fast ULID.
     */
    public static Ulid getQuickUlid() {
        return Ulid.fast();
    }

    /**
     * Converts a ULID to a UUID (RFC-4122 format).
     *
     * @param ulid the ULID to convert.
     * @return the equivalent UUID.
     */
    public static UUID convertUlidToUUID(Ulid ulid) {
        return ulid.toUuid();
    }

    /**
     * Converts a ULID to a Version 4 UUID.
     *
     * @param ulid the ULID to convert.
     * @return the equivalent UUIDv4.
     */
    public static UUID convertUlidToUUIDv4(Ulid ulid) {
        return ulid.toRfc4122().toUuid();
    }

    /**
     * Extracts the timestamp from a ULID and returns it as an {@link Instant}.
     *
     * @param ulid the ULID to extract the timestamp from.
     * @return the timestamp as an {@link Instant}.
     */
    public static Instant getUlidInstant(Ulid ulid) {
        return ulid.getInstant();
    }

    /**
     * Checks if a ULID is valid.
     *
     * @param ulid the ULID to check.
     * @return {@code true} if the ULID is valid, {@code false} otherwise.
     */
    public static boolean isValidUlid(String ulid) {
        return Ulid.isValid(ulid);
    }

    /**
     * Checks if a UUID is valid.
     *
     * @param uuid the UUID to check.
     * @return {@code true} if the UUID is valid, {@code false} otherwise.
     */
    public static boolean isValidUUID(String uuid) {
        return UuidValidator.isValid(uuid);
    }

    /**
     * Converts a string into a UUID.
     *
     * @param uuid the string to convert.
     * @return the UUID.
     */
    public static UUID getUUIDFromString(String uuid) {
        return UuidCreator.fromString(uuid);
    }

    /**
     * Converts a string into a ULID.
     *
     * @param ulid the string to convert.
     * @return the ULID.
     */
    public static Ulid getUlidFromString(String ulid) {
        return Ulid.from(ulid);
    }
}
