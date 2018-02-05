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

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleTest {

  private static SqlSessionFactory sqlSessionFactory;

  List<String> params = Arrays.asList("F86EC61B-4BC6-48A5-BE69-E89407481625", "A017DED3-FF52-41C6-BA81-29E42AF6011A",
      "883DDA21-C6E5-4492-99AB-7F36CE216B92", "DB77210F-7A93-44B6-83F2-220AAEFE730E",
      "CF48C192-8F91-4238-BE45-046471B48F09", "313D32C6-180B-4094-B12A-692E1AEEAABB",
      "164CE2AD-8DAE-425C-9F73-AE74DD728B04", "71206AAC-EEBF-4B09-8E5C-C4397D48CD74",
      "78E7C0F7-1B86-4A74-9682-79019AC288E7", "ECB86354-F8F1-4C3C-94F4-A354DE1D1F4F",
      "89A891E3-829F-4CCC-B643-47E53F441613", "66AB13D5-09B4-4535-AEDE-DFC801753021",
      "49043691-6C7A-4317-9231-1994F485C5D4", "E40BC73A-2A70-4DC3-98A0-0A04551ADB16",
      "6EEB39E9-F2CE-441F-AC84-6DFBAC5E904B", "6F54BE14-1AA0-448B-BFBD-7D6EB2388C7F",
      "96A19C4F-AF0C-4052-9C42-9AD829BAD171", "31F07BD0-76F8-435C-BA97-5DC6CBE9468D",
      "2AD014DC-45BA-4B1F-AF5C-07A4E1039CD9", "A6CAD892-8F00-41F5-B5DD-0D9577A8CA59",
      "5609EA14-1489-4780-831B-E83AE1C7DD22", "2DB06DF1-AEFF-43AE-B1B1-6BD599DB08FD",
      "C356C9E2-8FD9-46D5-B627-5D14D59340A5", "C24F43F4-FF32-473F-BEC6-9EDF56A9778F",
      "647A3916-C466-47B9-9571-6A583A21480B", "7CD5101D-CCA4-4A4F-B134-E005F9F6DB97",
      "34D4B261-BF91-4A71-ABB3-1BF15B8A9FCD", "E7F81BF5-178D-4B93-B38F-9BB526E16FD5",
      "A190EC79-4365-4493-AEB5-E0BBFC3F360E", "3464259F-2C53-494D-A7B0-2DB8C0D0AD84",
      "09BB8B7E-F971-4F13-A770-ED7CE9E01555", "C46E63D5-C622-4471-8738-F6D8A3CBECE4",
      "89B779CD-CC08-40EC-B369-9CDB774765B0", "E1D7098C-ED21-4CBE-8139-1C53886A2A8B",
      "9152D7B3-E601-40B8-8387-0E1A01746579", "2B71AF6C-A505-414F-BA5B-63CD7A0556FB",
      "A5EAF9BE-44E8-49DA-ACD2-271728720F35", "31DD5B66-EB1D-439F-841F-EFC4E86CFB23",
      "002E58E7-FE39-47DF-9CAD-32EDE1D9454C", "98F4B7DA-369B-4BC7-82F7-0FA45516E536",
      "BEAE319D-6588-4E10-BCC7-B3D84EB79433", "5AF4490A-228B-4328-B9FB-CBB48D6A5898",
      "96D9154A-C3F9-46F4-9240-CA097FE67033", "3414019F-2A50-4BE4-AFBD-111303C0FD17",
      "92D00E83-ECF3-4794-87DA-4CF4E5E0280D", "389BDA99-7676-4419-980F-FBEE44F71007",
      "23C08BF8-F954-4061-A86B-C225046A72AA", "1B7DD22D-0A0D-491C-8383-16B7026907BA",
      "7EC2F968-6952-48AC-B628-B179C2D7E20A", "F291F389-E0FC-4A05-86B3-FEAFE0EEB949",
      "D674F88F-6BA6-46F3-9A9D-0B8BBDC5FD64", "5387B831-FC43-407A-A41C-E0A0A5A9759A",
      "B1562267-2075-456A-A985-B704499E3732", "BDFF93B9-8ECA-4F1A-B428-AD3C3A243369",
      "D8680DF0-3DBA-47FC-9E77-B40022A74333", "81DD571F-4768-4557-A77D-EC79DE600741",
      "7B0D12C4-9F79-4FB7-B149-BEC40F949B30", "42874649-D07C-4F43-B053-4756CA399389",
      "DC505D8B-957B-497A-A55C-09111C654881", "889CA877-2EAC-4948-A341-D1890B1B3EFF",
      "9D5B40BA-C268-4E88-8777-39D7DAEADD69", "8D09C707-3733-4E15-8AE4-8046AEA4E3D2",
      "F8217553-9C97-48C3-9135-24331DD005D2", "DE0D59E8-DEB6-4433-A092-DA6A0DE3AF23",
      "4895EAAF-9467-4F01-8AA1-756C15F45BE4", "2C7B7AED-8728-4F27-858A-7CB4A8BB7E39",
      "DCF2A5A6-0E5A-45A4-9F89-94C8236822E5", "9C00E14C-2C69-4A57-B220-BB37E0EAD005",
      "A0B13828-225B-4A65-BB4A-BE80E30F6937", "DB90B0DD-3CCE-48C6-8F9F-9DC7E644F14A",
      "A77689B5-A504-4E82-9DA3-0156F950B10C", "82AD3624-50E4-413D-A048-7F5D73CA701B",
      "F4F17233-3B24-4190-9438-CD88683853F7", "6E282529-E59F-453A-866A-AC25895A14EF",
      "FFD08A43-FDCC-482C-A479-A033E331D013", "B86552B5-0099-4738-A147-4ECCBDC00AA3",
      "3706AF90-24C9-4F8D-82F1-8054F723BBE0", "9389207B-6E63-4B47-8DF7-13A3543258F6",
      "CB7F829A-473B-4E31-917C-4BB335E15411", "27455220-5C26-4635-84DC-E5D3E9D3E3B3",
      "18BDFA51-F294-4E77-80AF-A7F6E4462970", "CBA44EDB-AFDD-4E62-9A05-49A0D96B87FC",
      "767987C1-ABFA-4E45-9EF9-684055E80B38", "61E514F2-20FA-40E2-B03F-696747A4CAB3",
      "509E4431-A9F0-449B-97A2-10F45F350082", "42E42B55-0D55-46E8-A51B-A49B7A8CBEE3",
      "DB4E6C7B-04CD-407D-932B-B0C7AC75D768", "B252988C-580C-4BD6-87E8-1A7748CC1CE1",
      "79507CE1-8436-4A6B-9E0E-41CF4A648621", "37C004E4-A342-472F-9884-A32D9203389A",
      "5904C6E2-A329-4CC0-AA44-B6F72BC2641C", "45543ED6-9BC6-4FE2-B534-C3D592917095",
      "803A3F33-B2B0-4D81-B64F-D22DC4DC9EE5", "4EA0C6B7-CB32-48A1-87AB-F6AC74E7BF0B",
      "E9D95DD9-9E5A-4204-9A94-7F528DA93CE1", "B97A738A-F5DC-4708-BDFB-BB8242BFBF7B",
      "E170E616-C9CF-4F03-85EC-0567F831E75E", "C45ADB6D-01FD-4AD4-8722-463AD2D93CDC",
      "A160885D-CC87-483E-89AD-356E60EE3650", "5D084824-822D-4A7D-B0A6-7BEDFA26DB41",
      "036FC5C0-5895-4B30-BA7F-F4781A4B7C2B", "4A7301C0-A3F1-4465-BF6A-447681328DB3",
      "4B1D36F8-EE34-4EDC-A51A-2B62CAE10772", "6CC57E5F-4CE4-41B4-9A23-41B6461E2881",
      "DAF53A81-6D65-4222-B3EE-81D59694C983", "834C2A4C-BF2B-42ED-9771-AD527577EA3B",
      "D96D562B-9AB3-4664-A316-E34976DBE117", "0C1885EE-36A1-482C-BBBC-BF343ADC93F8",
      "63CBFFAB-DF25-4C36-A5BD-E16FFCCA6109");

  @BeforeClass
  public static void setUp() throws Exception {
    // create an SqlSessionFactory
    Reader reader = Resources.getResourceAsReader("test/mybatis-config.xml");
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    reader.close();

    // populate in-memory database
    SqlSession session = sqlSessionFactory.openSession();
    Connection conn = session.getConnection();
    reader = Resources.getResourceAsReader("test/CreateDB.sql");
    ScriptRunner runner = new ScriptRunner(conn);
    runner.setLogWriter(null);
    runner.runScript(reader);
    reader.close();
    session.close();
  }

  private static void insertBulkDummyData(SqlSession session) throws SQLException {
    Connection con = session.getConnection();
    try {
      int totalRowCount = 200000;
      String sql = "INSERT INTO fact_pool_do_out_test (_update_time, _source_id, _source_name, do_out_id, do_code, process_id, process_code, process_name, process_seq, manu_process_code, parent_do_id, parent_do_code, parent_process_id, parent_process_code, parent_process_name, parent_process_seq, material_code, material_name, material_spec, material_unit, batch_no, barcode, quantity, equipment_id, equipment_code, equipment_name, model_id, model_name, location_id, location_name, failreason_id, failreason_name, person_code, person_name, happen_time, shift_date, shift_name, shift_start_time, shift_end_time, type, iokey, bucket_no, mold_code, process_uid, packet_barcode, pallet_barcode, equip_container, quality_type, parent_barcode, op_type, op_type_name, material_status, material_status_name, operation_id, timestamp) "
          +
          "VALUES (getdate(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NEWID(), '')";
      PreparedStatement statement = con.prepareStatement(sql);

      // fill up rows up to totalRowCount
      for (int i = 0; i < totalRowCount; i++) {
        // there're 52 params, set them all to NULL
        for (int j = 1; j <= 52; j++) {
          statement.setObject(j, null);
        }
        statement.addBatch();
      }

      statement.executeBatch();

      System.out.println("insert bulk dummy data success");
    } catch (Exception ex) {
      System.out.println("exception: " + ex.getMessage());
    } finally {
      con.close();
    }
  }

  @Test
  public void shouldGetAUser() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      List<PoolDoOutDetailModel> records = sqlSession.selectList("getOutDetailRecords", params);
      assertEquals(109, records.size());
    } finally {
      sqlSession.close();
    }
  }

}
