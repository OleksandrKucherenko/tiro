package com.tiro.rest;

import static com.tiro.rest.Services.Versions.V1;

/** Constants for making managing of URL mapping easier. */
public interface Services {

  interface Versions {
    String V1 = "/v1";
    String V2 = "/v2";
  }

  /** Version 1 of security API. */
  String V1_SECURITY = V1 + "/security";
}
