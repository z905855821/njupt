package com.njupt.ws_cxf_spring.ws.test;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.njupt.ws_cxf_spring.ws.bean.Device;
import com.njupt.ws_cxf_spring.ws.dao.Dao;
import com.njupt.ws_cxf_spring.ws.dao.Dao2;
import com.njupt.ws_cxf_spring.ws.dao.SendMessage;

public class WS_Test {
	Dao dao;
	Dao2 dao2;

	public WS_Test() {
		dao = new Dao();
		dao2 = new Dao2();
	}

	public String queryProjectsTest() {
		String res = dao.queryProjects("9de089c60f374fe5be7b9f0cf1206313");
		return res;
	}

	public String queryDevicesTest() {
		String res = dao.queryDevices(1000000);
		return res;
	}

	public String queryDataTypeTest() {
		String res = dao.queryDataType("93bfdc61ef56477d965cbe0a3a04f45d");
		return res;
	}

	public String getRecentValuesTest() {
		String res = dao.getRecentValues("temp", "b12c86db4a1f40db8595737fe3f47520");
		return res;
	}

	public String changeShowTypeTest() {
		String res = dao.changeShowType("humi", "eef8f40402e6437ca3d29282656763de", 2);
		return res;
	}

	public String queryDevicesNumTest() {
		String res = dao.queryDevicesNum(1000000);
		return res;
	}

	public String queryDeviceInfoTest() {
		String res = dao.queryDeviceInfo("eef8f40402e6437ca3d29282656763de");
		return res;
	}

	public String queryOneDataTypeTest() {
		String res = dao.queryOneDataType("eef8f40402e6437ca3d29282656763de", "humi");
		return res;
	}

	public int queryProjectIdByDevkeyTest() {
		int res = dao.queryProjectIdByDevkey("b12c86db4a1f40db8595737fe3f47520");
		return res;
	}

	public String queryUserKeyByProjectIdTest() {
		String res = dao.queryUserKeyByProjectId(1000000);
		return res;
	}

	public String addOpenAppTest() {
		String res = dao.addOpenApp("3d090918d4704a318054ca9157e8af75", "415a26bf5fb14a4bb8f6b2c02739eedd");
		return res;
	}

	public String addOpenDeviceTest() {
		String res = dao.addOpenDevice("415a26bf5fb14a4bb8f6b2c02739eeww", "415a26bf5fb14a4bb8f6b2c02739eeqq");
		return res;
	}

	public int updateApplyStateByAppkeyTest() {
		int res = dao.updateApplyStateByAppkey("415a26bf5fb14a4bb8f6b2c02739eegg", "审核通过");
		return res;
	}

	public int updateApplyStateByDevicekeyTest() {
		int res = dao.updateApplyStateByDevicekey("415a26bf5fb14a4bb8f6b2c02739eeqq", "审核通过");
		return res;
	}

	public String queryOpenAppByUserKeyTest() {
		String res = dao.queryOpenAppByUserKey("415a26bf5fb14a4bb8f6b2c02739eedd");
		return res;
	}

	public String queryOpenDeviceByDevicekeyTest() {
		String res = dao.queryOpenDeviceByDevicekey("415a26bf5fb14a4bb8f6b2c02739eeqq");
		return res;
	}

	public String queryAllOpenAppTest() {
		String res = dao.queryAllOpenApp();
		return res;
	}

	public String queryDeviceByAppkeyTest() {
		String res = dao.queryDeviceByAppkey("3d090918d4704a318054ca9157e8af75");
		return res;
	}

	public String queryUserNameByUserkeyTest() {
		String res = dao.queryUserNameByUserkey("c3682eaa73f54b96a3e3897682b56016");
		return res;
	}

	public String queryOpenAppByApplystateTest() {
		String res = dao.queryOpenAppByApplystate("申请中");
		return res;
	}

	public int queryCountByApplykeyandUserkeyTest() {
		int res = dao.queryCountByApplykeyandUserkey("6cca210be02b4804b65bd19ea2fda103",
				"9de089c60f374fe5be7b9f0cf1206313");
		return res;
	}

	public int queryCountByApplykeyandDevicekeyTest() {
		int res = dao.queryCountByApplykeyandDevicekey("6cca210be02b4804b65bd19ea2fda103",
				"a41e7aec6ac24d68b35ff621fa9c1689");
		return res;
	}

	public String addThirdUserTest() {
		String res = dao.addThirdUser("6cca210be02b4804b65bd19ea2fda103", "415a26bf5fb14a4bb8f6b2c02739eedd");
		return res;
	}

	public String queryThirdApplyAppByUserkeyTest() {
		String res = dao.queryThirdApplyAppByUserkey("1bcc391563034cd0adc929f0ecbd8b51");
		return res;
	}

	public int updateApplyStateByAppkeyandUserkeyTest() {
		int res = dao.updateApplyStateByAppkeyandUserkey("同意", "6cca210be02b4804b65bd19ea2fda103",
				"415a26bf5fb14a4bb8f6b2c02739eedd");
		return res;
	}

	public String queryThirdAppByUserkeyTest() {
		String res = dao.queryThirdAppByUserkey("9de089c60f374fe5be7b9f0cf1206313");
		return res;
	}

	public int queryCountByAppkeyandUserkeyTest() {
		int res = dao.queryCountByAppkeyandUserkey("6cca210be02b4804b65bd19ea2fda103",
				"415a26bf5fb14a4bb8f6b2c02739eedd");
		return res;
	}

	public String getGatewayCurrentValueTest() {
		String res = dao.getGatewayCurrentValue();
		return res;
	}

	public Device getDeviceByDevkeyTest() {
		Device res = dao.getDeviceByDevkey("3c65ba4a411b4d6197ee46fd3e8d045d");
		return res;
	}

	public String findDevicesByIpTest() {
		String res = dao.findDevicesByIp("192.168.1.108");
		return res;
	}

	public String getDeviceDataCurrentValueTest() {
		String res = dao.getDeviceDataCurrentValue("b7f3efdeea514d8a9454b510dd10a1fb");
		return res;
	}

	public String getLocationTest() {
		String res = dao.getLocation("25");
		return res;
	}

	public String getIpByDevkeyTest() {
		String res = dao.getIpByDevkey("3c65ba4a411b4d6197ee46fd3e8d045d");
		return res;
	}

	public String getRelationidByDevkeyTest() {
		String res = dao.getRelationidByDevkey("3c65ba4a411b4d6197ee46fd3e8d045d");
		return res;
	}

	public String addConfigTest() {
		String res = dao.addConfig("3c65ba4a411b4d6197ee46fd3e8d045d", "S:N=801002#NCTR@Period=10sE");
		return res;
	}

	public String getRecentConfigValuesByDevkeyTest() {
		String res = dao.getRecentConfigValuesByDevkey("3c65ba4a411b4d6197ee46fd3e8d045d");
		return res;
	}

	public String configLocalTest() {
		String res = dao.configLocal("10.10.25.89", "20000", "S:N=801002@Find=0E;");
		return res;
	}

	public String getDeviceByIpTest() {
		String res = dao.getDeviceByIp("10.10.25.89");
		return res;
	}

	public String getOldDataByRangeTest() {
		String res = dao.getOldDataByRange(326, 391);
		return res;
	}

	public String configTest() {
		String res = dao.controlHeartBeat("7e4e5ff11f014041a8975399ad08c02b", "S:N=801003#NCTR@Find=1E");
		return res;
	}

	public String getLastFreAndPowerBydevkeyTest() {
		String res = dao.getLastFreAndPowerBydevkey("835c80380a1d45668c41e20248f761d5");
		return res;
	}

	public String getFreAndPowerBydevkeyAndTimeTest() {
		String res = dao.getFreAndPowerBydevkeyAndTime("2017-4-9 20:45:00", "835c80380a1d45668c41e20248f761d5", "3",
				"33");
		return res;
	}

	public String getPowerAndTimeBydevkeyAndFreTest() {
		String res = dao.getPowerAndTimeBydevkeyAndFre("601001", "26.5");
		return res;
	}

	public String addDeviceTest() {
		String res = dao.addDevice("1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
		return res;
	}

	public String selectTimeFromFreTest() {
		String res = dao2.selectTimeFromFre("bbfe374c29d245a48920d99dfb27e8c1");
		return res;
	}

	public String getTenTimePowerAndTimeBydevkeyAndFreTest() {
		String res = dao2.getTenTimePowerAndTimeBydevkeyAndFre("bbfe374c29d245a48920d99dfb27e8c1");
		return res;
	}

	public String controlFrequencyPeriodTest() {
		String res = dao.controlFrequencyPeriod("S:N=835c80380a1d45668c41e20248f761d5@NCONF@Period=100sE;");
		return res;
	}

	public String getFrequencyDevice() {
		String res = dao2.getFrequencyDevice();
		return res;
	}

	public String selectLastTenData() {
		String res = dao2.selectLastTenData("835c80380a1d45668c41e20248f761d5");
		return res;
	}

	public String SelectAdapterRealstate() {
		String res = dao.SelectAdapterRealstate();
		return res;
	}

	public String getAdapterHistoryByReltionid() {
		String res = dao.getAdapterHistoryByReltionid("74");
		return res;
	}

	public String parkingInfo() {
		String res = dao.parkingInfo();
		return res;
	}

	public String SensorValueGet() {
		String res = dao.SensorValueGet("3c65ba4a411b4d6197ee46fd3e8d045d", "Temp");
		System.out.println(res);
		return res;
	}

	public String SensorControl() {
		String res = dao.SensorControl("21", "23", "Temp", "3c65ba4a411b4d6197ee46fd3e8d045d",
				"9c0b27c9e54b4db5aea08d889e38c346");
		System.out.println(res);
		return res;
	}
	public String Sendmessage() {
		String apikey = "5055392273789589e01dc3fb2c23217c";
		String text = "【云片网】您的验证码是12345678";
		// 修改为您要发送的手机号
		String mobile = "15005186909";
		String res = SendMessage.singleSend(apikey, text, mobile);
		System.out.println(res);
		return res;
	}
	public static void main(String[] args) {
		// new WS_Test().queryProjectsTest();
//		 new WS_Test().queryDevicesTest();
		// new WS_Test().queryDataTypeTest();
		// new WS_Test().getRecentValuesTest();
		// new WS_Test().changeShowTypeTest();
		// new WS_Test().queryDevicesNumTest();
		// new WS_Test().queryDeviceInfoTest();
		// new WS_Test().queryOneDataTypeTest();
		// new WS_Test().queryProjectIdByDevkeyTest();
		// new WS_Test().queryUserKeyByProjectIdTest();
		// new WS_Test().addOpenAppTest();
		// new WS_Test().addOpenDeviceTest();
		// new WS_Test().updateApplyStateByAppkeyTest();
		// new WS_Test().updateApplyStateByDevicekeyTest();
		// new WS_Test().queryOpenAppByUserKeyTest();
		// new WS_Test().queryOpenDeviceByDevicekeyTest();
		// new WS_Test().queryAllOpenAppTest();
		// new WS_Test().queryDeviceByAppkeyTest();
		// new WS_Test().queryUserNameByUserkeyTest();
		// new WS_Test().queryOpenAppByApplystateTest();
		// new WS_Test().queryCountByApplykeyandUserkeyTest();
		// new WS_Test().queryCountByApplykeyandDevicekeyTest();
		// new WS_Test().addThirdUserTest();
		// new WS_Test().queryThirdApplyAppByUserkeyTest();
		// new WS_Test().updateApplyStateByAppkeyandUserkeyTest();
		// new WS_Test().queryThirdAppByUserkeyTest();
		// new WS_Test().queryCountByAppkeyandUserkeyTest();
		// new WS_Test().getGatewayCurrentValueTest();
		// new WS_Test().getDeviceByDevkeyTest();
		 new WS_Test().findDevicesByIpTest();
//		 new WS_Test().getDeviceDataCurrentValueTest();
		// new WS_Test().getLocationTest();
		// new WS_Test().getIpByDevkeyTest();
		// new WS_Test().getRelationidByDevkeyTest();
//		 new WS_Test().addConfigTest();
		// new WS_Test().getRecentConfigValuesByDevkeyTest();
		// new WS_Test().configLocalTest();
		// new WS_Test().getDeviceByIpTest();
		// new WS_Test().getOldDataByRangeTest();
//		 new WS_Test().configTest();
		// new WS_Test().getLastFreAndPowerBydevkeyTest();
		// new WS_Test().getFreAndPowerBydevkeyAndTimeTest();
		// new WS_Test().getPowerAndTimeBydevkeyAndFreTest();
		// new WS_Test().addDeviceTest();
//		 new WS_Test().selectTimeFromFreTest();
//		 new WS_Test().getTenTimePowerAndTimeBydevkeyAndFreTest();
		// new WS_Test().controlFrequencyPeriodTest();
		// new WS_Test().getFrequencyDevice();
//		 new WS_Test().selectLastTenData();
		// new WS_Test().SelectAdapterRealstate();
		// new WS_Test().getAdapterHistoryByReltionid();
		// new WS_Test().parkingInfo();
//		 new WS_Test().SensorValueGet();
//		new WS_Test().SensorControl();
//		 new WS_Test().Sendmessage();
	}
}
