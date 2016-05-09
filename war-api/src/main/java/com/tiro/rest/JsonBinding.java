package com.tiro.rest;


import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
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
public enum JsonBinding {
  INSTANCE;

  public final ObjectMapper mapper;

  /* package */ JsonBinding() {
    mapper = new ObjectMapper();

    final VisibilityChecker<?> vc = mapper.getSerializationConfig().getDefaultVisibilityChecker();

    mapper.setVisibility(vc
        .withFieldVisibility(Visibility.ANY)
        .withCreatorVisibility(Visibility.NONE)
        .withGetterVisibility(Visibility.NONE)
        .withSetterVisibility(Visibility.NONE)
        .withIsGetterVisibility(Visibility.NONE));

    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @NotNull
  public static String toJson(@NotNull final Object instance) {
    try {
      return JsonBinding.INSTANCE.mapper.writeValueAsString(instance);
    } catch (final JsonProcessingException ignored) {
    }

    return "{ \"error\":\"JSON parser failure. Something is completely wrong.\" }";
  }

  @Null
  public static <T> T fromJson(@NotNull final String json, @NotNull final Class<T> clazz) {
    try {
      return JsonBinding.INSTANCE.mapper.readValue(json, clazz);
    } catch (final IOException ignored) {
    }

    return null;
  }
}
