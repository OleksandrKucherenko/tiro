package com.tiro;

import javax.ws.rs.core.MediaType;

import static com.tiro.Services.Versions.V1;
import static com.tiro.Services.Versions.V2;

/** Constants for making managing of URL mapping easier. */
@SuppressWarnings({"unused"})
public interface Services {

  /** High level API versions. */
  interface Versions {
    /** First version. */
    String V1 = "/v1";
    /** Second version often propose more speed optimized API. */
    String V2 = "/v2";
  }

  /** Supported DATA protocols. */
  interface Protocols {
    /** Protocol buffer. */
    String PROTO = "application/x-protobuf";
    /** JSON. */
    String JSON = MediaType.APPLICATION_JSON;
    /** Xml. */
    String XML = MediaType.APPLICATION_XML;
  }

  /** Version 1 of security API. */
  String V1_SECURITY = V1 + "/security";
  /** Version 2 of the security API. */
  String V2_SECURITY = V2 + "/security";
}
