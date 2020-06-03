package test;

import java.util.ArrayList;
import java.util.List;

public class Addresses {
  List<Address> addresses;

  public List<Address> getAddresses() {
    if (addresses == null) {
      addresses = new ArrayList<Address>();
    }
    return this.addresses;
  }
}
