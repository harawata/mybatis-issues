/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package test;

import java.math.BigDecimal;
import java.util.Date;

public class PoolDoOutDetailModel {
  private String barcode;

  private Integer equipmentId;

  private String equipmentName;

  private Integer opType;

  private String opTypeName;

  private String doCode;

  private String batchNo;

  private String materialCode;

  private String materialName;

  private BigDecimal quantity = BigDecimal.ZERO;

  private Integer qualityType;

  private String qualityTypeName;

  private String shiftName;

  private String personName;

  private Date happenTime;

  private String moldCode;

  private String failReason;

  String operationId;

  String destSnapshotId;

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public Integer getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(Integer equipmentId) {
    this.equipmentId = equipmentId;
  }

  public String getEquipmentName() {
    return equipmentName;
  }

  public void setEquipmentName(String equipmentName) {
    this.equipmentName = equipmentName;
  }

  public Integer getOpType() {
    return opType;
  }

  public void setOpType(Integer opType) {
    this.opType = opType;
  }

  public String getOpTypeName() {
    return opTypeName;
  }

  public void setOpTypeName(String opTypeName) {
    this.opTypeName = opTypeName;
  }

  public String getDoCode() {
    return doCode;
  }

  public void setDoCode(String doCode) {
    this.doCode = doCode;
  }

  public String getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }

  public String getMaterialCode() {
    return materialCode;
  }

  public void setMaterialCode(String materialCode) {
    this.materialCode = materialCode;
  }

  public String getMaterialName() {
    return materialName;
  }

  public void setMaterialName(String materialName) {
    this.materialName = materialName;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public Integer getQualityType() {
    return qualityType;
  }

  public void setQualityType(Integer qualityType) {
    this.qualityType = qualityType;
  }

  public String getQualityTypeName() {
    return qualityTypeName;
  }

  public void setQualityTypeName(String qualityTypeName) {
    this.qualityTypeName = qualityTypeName;
  }

  public String getShiftName() {
    return shiftName;
  }

  public void setShiftName(String shiftName) {
    this.shiftName = shiftName;
  }

  public String getPersonName() {
    return personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  public Date getHappenTime() {
    return happenTime;
  }

  public void setHappenTime(Date happenTime) {
    this.happenTime = happenTime;
  }

  public String getMoldCode() {
    return moldCode;
  }

  public void setMoldCode(String moldCode) {
    this.moldCode = moldCode;
  }

  public String getFailReason() {
    return failReason;
  }

  public void setFailReason(String failReason) {
    this.failReason = failReason;
  }

  public String getOperationId() {
    return operationId;
  }

  public void setOperationId(String operationId) {
    this.operationId = operationId;
  }

  public String getDestSnapshotId() {
    return destSnapshotId;
  }

  public void setDestSnapshotId(String destSnapshotId) {
    this.destSnapshotId = destSnapshotId;
  }
}
