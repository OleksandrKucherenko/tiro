package com.tiro;

import static com.tiro.Services.Versions.V1;
import static com.tiro.Services.Versions.V2;

/** Constants for making managing of URL mapping easier. */
public interface Services {

  interface Versions {
    String V1 = "/v1";
    String V2 = "/v2";
  }

  /** Version 1 of security API. */
  String V1_SECURITY = V1 + "/security";
  /** Version 2 of the security API. */
  String V2_SECURITY = V2 + "/security";
}
