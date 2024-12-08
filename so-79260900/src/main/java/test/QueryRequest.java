package test;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequest implements Serializable {
  private List<String> years;
  private List<String> months;
  private List<String> region;
  private List<String> office;
}
