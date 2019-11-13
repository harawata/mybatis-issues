package test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;

public interface Mapper {
  List<Map<String, ?>> getProductsForSequentialDatesInternal(@Param("rangeStart") Date rangeStart,
      @Param("rangeEnd") Date rangeEnd);

  @SuppressWarnings("unchecked")
  default Map<Date, List<Product>> getProductsForSequentialDates(Date rangeStart, Date rangeEnd) {
    return getProductsForSequentialDatesInternal(rangeStart, rangeEnd)
        .stream().collect(Collectors.toMap(
            e -> (Date) e.get("date"),
            e -> (List<Product>) e.get("products")));
  }

  List<Map<String, ?>> getProductsForRandomDatesInternal(@Param("dates") List<Date> dates);

  @SuppressWarnings("unchecked")
  default Map<Date, List<Product>> getProductsForRandomDates(List<Date> dates) {
    return getProductsForRandomDatesInternal(dates)
        .stream().collect(Collectors.toMap(
            e -> (Date) e.get("date"),
            e -> (List<Product>) e.get("products")));
  }
}
