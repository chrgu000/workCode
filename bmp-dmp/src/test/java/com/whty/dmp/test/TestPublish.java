package com.whty.dmp.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whty.dmp.utils.HttpUtils;
import com.whty.dmp.utils.JsonUtils;

/**
 * Created by tytx02 on 2016/9/18.
 */
public class TestPublish {
    //orcl为oracle数据库中的数据库名，localhost表示连接本机的oracle数据库
    //1521为连接的端口号
    private static String url="jdbc:oracle:thin:@//127.0.6.142:1522/edu";
    //system为登陆oracle数据库的用户名
    private static String user="demo_aam_dspuser";
    //manager为用户名system的密码
    private static String password="pass4aam_dspuser";
    public static Connection conn;
    public static PreparedStatement ps;
    public static ResultSet rs;
    public static Statement st ;
    //连接数据库的方法

    @org.junit.Test
    public void publish() {
        String url = "http://116.211.105.42:18015/dataCenterApi/api/publish";

        getConnection();
        try {
            String sql = "select * from t_organization where rownum < 2";     // 查询数据的sql语句
            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量

            ResultSet rs = st.executeQuery(sql);    //执行sql查询语句，返回查询数据的结果集
            System.out.println("最后的查询结果为：");
            while (rs.next()) { // 判断是否还有下一个数据

                // 根据字段名获取相应的值
                String id = rs.getString("id");
                String orgaCode = rs.getString("ORGA_CODE");
                String orgaName = rs.getString("ORGA_NAME");
                String orgaCage = rs.getString("ORGA_CAGE");
                String orgaType = rs.getString("ORGA_TYPE");
                String parentOrgaId = rs.getString("PARENT_ORGA_ID");
                String provinceCode = rs.getString("PROVINCE_CODE");

                String areaCode = rs.getString("AREA_CODE");
                String cityCode = rs.getString("CITY_CODE");
                String status = rs.getString("STATUS");
                Organization organization = new Organization();
                organization.setId(id);
                organization.setOrgaCode(orgaCode);
                organization.setOrgaName(orgaName);
                try {
                    organization.setOrgaCage(Integer.parseInt(orgaCage));
                } catch (Exception e) {
                }
                try {
                    organization.setOrgaType(Integer.parseInt(orgaType));
                } catch (Exception e) {
                }
                organization.setParentOrgaId(parentOrgaId);
                organization.setProvinceCode(provinceCode);
                organization.setAreaCode(areaCode);
                organization.setCityCode(cityCode);
                try {
                    organization.setStatus(Integer.parseInt(status));
                } catch (Exception e) {
                }
                Map<String, Object> map = new HashMap<>();
                map.put("obj", organization);
                map.put("operatorType", 1);
                map.put("platCode", "xxx");


                List< Map<String, Object>> result = new ArrayList<>();
                result.add(map);
                System.out.println(JsonUtils.ojbTojson(result));

                Map tempMap = new HashMap<String, String>();
                tempMap.put("serviceCode", "b62251d3a3024ebfbf1fb1c035ad7b91");
                tempMap.put("messageList", JsonUtils.ojbTojson(result));
                try {
                    String content = HttpUtils.getInstance().httpPost(url, tempMap);
                    System.out.println(content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            conn.close();   //关闭数据库连接

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询数据失败");
        }


    }



    //连接数据库的方法
    public void getConnection(){
        try {
            //初始化驱动包
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //根据数据库连接字符，名称，密码给conn赋值
            conn=DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}


class Organization {
    private String id;//
    private String orgaCode;
    private String orgaName;
    private Integer orgaCage;
    private Integer orgaType;
    private String parentOrgaId;
    private String provinceCode; //省
    private String cityCode;//市
    private String areaCode;//县/区
    private Integer status;

    private String platCode;

    private java.util.Date createTime;
    private java.util.Date updateTime;

    //-------------------bussiness params for ServiceQueueRecord---------------------------
    private int operatorType;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgaCode() {
        return orgaCode;
    }

    public void setOrgaCode(String orgaCode) {
        this.orgaCode = orgaCode;
    }

    public String getOrgaName() {
        return orgaName;
    }

    public void setOrgaName(String orgaName) {
        this.orgaName = orgaName;
    }



    public String getParentOrgaId() {
        return parentOrgaId;
    }

    public void setParentOrgaId(String parentOrgaId) {
        this.parentOrgaId = parentOrgaId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }


    public Integer getOrgaCage() {
        return orgaCage;
    }

    public void setOrgaCage(Integer orgaCage) {
        this.orgaCage = orgaCage;
    }

    public Integer getOrgaType() {
        return orgaType;
    }

    public void setOrgaType(Integer orgaType) {
        this.orgaType = orgaType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPlatCode() {
        return platCode;
    }

    public void setPlatCode(String platCode) {
        this.platCode = platCode;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(int operatorType) {
        this.operatorType = operatorType;
    }


}
