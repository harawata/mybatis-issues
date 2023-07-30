package test;

import java.io.Serializable;

public class Mdl101Parameter implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long idProcesso;
  private OutText outText;

  public Mdl101Parameter(Long idProcesso, OutText outText) {
    this.idProcesso = idProcesso;
    this.outText = outText;
  }

  public Mdl101Parameter() {
  }

  public Long getIdProcesso() {
    return idProcesso;
  }

  public void setIdProcesso(Long idProcesso) {
    this.idProcesso = idProcesso;
  }

  public OutText getOutText() {
    return outText;
  }

  public void setOutText(OutText outText) {
    this.outText = outText;
  }

  @Override
  public String toString() {
    return "Mdl101Parameter [idProcesso=" + idProcesso + ", outText=" + outText + "]";
  }
}
