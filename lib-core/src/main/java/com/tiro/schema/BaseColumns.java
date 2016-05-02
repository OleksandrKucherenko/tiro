package com.tiro.schema;

/** List of common columns. */
public interface BaseColumns {
  /** Creation time column name. */
  String C_TIME = "_create";
  /** Modification time column name. */
  String U_TIME = "_modify";
  /** Delete time column name. */
  String D_TIME = "_delete";
  /** Entity version column name. */
  String VERSION = "_version";
}
