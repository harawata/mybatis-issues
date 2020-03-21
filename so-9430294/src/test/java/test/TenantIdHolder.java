/*
 * Copyright KEY Planning co.,ltd. All rights reserved.
 */

package test;

public class TenantIdHolder {
  private static ThreadLocal<String> value = new ThreadLocal<>();

  public static void setTenantId(String tenantId) {
    value.set(tenantId);
  }

  public static String getTenantId() {
    return value.get();
  }
}
