package test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class TaskCountResponseResultHandler implements ResultHandler<Map<String, String>> {
  private final Map<Entry<String, String>, TaskCountResponse> map = new TreeMap<>((e1, e2) -> {
    int x = e1.getKey().compareTo(e2.getKey());
    return x != 0 ? x : e1.getValue().compareTo(e2.getValue());
  });
  private final List<String> years;
  private final List<String> months;

  public TaskCountResponseResultHandler(List<String> years, List<String> months) {
    super();
    this.years = years;
    this.months = months;
  }

  @Override
  public void handleResult(ResultContext<? extends Map<String, String>> resultContext) {
    // This method is called for each row and
    // this map contains [column label] vs. [column value]
    Map<String, String> row = resultContext.getResultObject();

    Entry<String, String> regionOfficeKey = Map.entry(row.get("REGION_NAME"), row.get("OFFICE_NAME"));
    String monthKey = "month_" + row.get("YEAR_MONTH");

    map.computeIfAbsent(regionOfficeKey, k -> {
      TaskCountResponse v = new TaskCountResponse();
      v.setRegion(k.getKey());
      v.setOffice(k.getValue());
      v.setMonthlyCounts(new TreeMap<>());
      return v;
    }).getMonthlyCounts().merge(monthKey, 1, Integer::sum);
  }

  public List<TaskCountResponse> getResult() {
    return map.values().stream().map(response -> {
      // Puts zero for months with no data
      Map<String, Integer> counts = response.getMonthlyCounts();
      for (String year : years) {
        for (String month : months) {
          counts.putIfAbsent("month_" + year + "_" + month, 0);
        }
      }
      return response;
    }).toList();
  }
}