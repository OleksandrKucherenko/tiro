package com.tiro.rest;


import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Singleton of the JSON mapping library, used for (de-)serialization of POJOs.
 * <p>
 * References:
 * - https://github.com/eishay/jvm-serializers/wiki
 * - https://github.com/FasterXML/jackson-databind
 * - https://github.com/FasterXML/jackson-databind/wiki/Databind-Annotations
 * - https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations
 * - https://github.com/FasterXML/jackson-jaxrs-providers
 * - http://www.baeldung.com/jackson-deserialize-json-unknown-properties
 */
@SuppressWarnings({"unused"})
public enum JsonBinding {
  INSTANCE;

  private static final Logger _log = LoggerFactory.getLogger(JsonBinding.class);

  public final ObjectMapper mapper;

  /* package */ JsonBinding() {
    mapper = new ObjectMapper();

    // optimize by speed
    final VisibilityChecker<?> vc = mapper.getSerializationConfig().getDefaultVisibilityChecker();

    mapper.setVisibility(vc
        .withFieldVisibility(Visibility.ANY)
        .withCreatorVisibility(Visibility.NONE)
        .withGetterVisibility(Visibility.NONE)
        .withSetterVisibility(Visibility.NONE)
        .withIsGetterVisibility(Visibility.NONE));

    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // pretty print
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  @Nonnull
  public static String toJson(@Nonnull final Object instance) {
    try {
      return JsonBinding.INSTANCE.mapper.writeValueAsString(instance);
    } catch (final JsonProcessingException ignored) {
      _log.error("Failed serialization of the instance of class: {} with exception: {}", instance.getClass(), ignored);
    }

    return "{ \"error\":\"JSON parser failure. Something is completely wrong.\" }";
  }

  @Nullable
  public static <T> T fromJson(@Nonnull final String json, @Nonnull final Class<T> clazz) {
    try {
      return JsonBinding.INSTANCE.mapper.readValue(json, clazz);
    } catch (final IOException ignored) {
      _log.error("Failed deserialization of class: {} with exception: {}, json: {}", clazz, ignored, json);
    }

    return null;
  }
}
