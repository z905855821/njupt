package com.njupt.ws_cxf_spring.ws;

import javax.jws.WebService;

import com.njupt.ws_cxf_spring.ws.dao.Dao;
import com.njupt.ws_cxf_spring.ws.dao.Dao2;
import com.njupt.ws_cxf_spring.ws.dao.SendMessage;
import com.njupt.ws_cxf_spring.ws.tools.Tools;

@WebService
public class ServiceInterfaceImpl implements ServiceInterface {

	Dao db;
	Dao2 db2;

	public ServiceInterfaceImpl()throws Exception {
		db = new Dao();
		db2=new Dao2();
	}

	/*
	 * 判断用户是否合法
	 * 
	 */
	public String getPermission(String username, String passwords)
			throws Exception {
		System.out.println("********************************begin*******************************");
		int a = db.islegalUser(username, passwords);
		System.out.println("用户id:"+a);
		if (a > 0) {
			String b;
			b = Tools.GetRandomNumber();
			System.out.println("生成的句柄："+b);
			db.inserthandle(b, a);
			System.out.println("句柄插入成功");
			System.out.println("*********************************end*******************************");
			return b;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";			
		}
	}
	
	/*
	 * 执行注销操作 (non-Javadoc)
	 * 
	 */
	@Override
	public String logout(String handle) throws Exception {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			db.updatehandle(handle);
			System.out.println("*********************************end*******************************");
			return "成功注销！";
		} else{
				System.out.println("*********************************end*******************************");
				return "未能成功注销！";
			}
	}
	
	@Override
	public String userRegister(String handle,String username, String password) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.userRegister(username, password);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到注册授权";
		}
	}

	@Override
	public String userLogin(String handle,String username, String password) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.userLogin(username, password);
			System.out.println("*********************************end*******************************");
			return value;
		}  else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}
	
	@Override
	public int addProject(String handle, String projectname, String ownerapikey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String projectKey = db.addProject(projectname, ownerapikey);
			int projectID=db.getProjectIDByKey(projectKey);
			System.out.println("*********************************end*******************************");
			return projectID;
		} else{
			System.out.println("*********************************end*******************************");
			return 00000000;
		}
	}
	
	@Override
	public String addDevice(String handle, String devname, String devtag, String devdesc, String devpicture,
			String protocol, String devauth, String location, String longitude, String latitude, String projectid) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.addDevice(devname,devtag,devdesc,devpicture,protocol,devauth,location,longitude,latitude,projectid);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");			
			return "你未得到授权";
		}
	}

	@Override
	public String addDataType(String handle, String type, String symbol, int showtype,String devkey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.addDataType(type, symbol,showtype,devkey);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getCurrentValue(String handle, String typeid, String devkey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.getCurrentValue(typeid, devkey);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryProjects(String handle, String userkey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.queryProjects(userkey);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryDevices(String handle, int projectid) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.queryDevices(projectid);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}
	
	@Override
	public String queryDevicesNum(String handle, int projectid) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.queryDevicesNum(projectid);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryDataType(String handle, String deviceKey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.queryDataType(deviceKey);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getRecentValues(String handle, String typeid, String devkey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.getRecentValues(typeid,devkey);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String changeShowType(String handle, String type, String devkey, int showtype) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.changeShowType(type,devkey,showtype);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryDeviceInfo(String handle, String devkey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.queryDeviceInfo(devkey);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryOneDataType(String handle, String deviceKey, String type) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.queryOneDataType(deviceKey,type);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String addApplication(String handle, String appname,String appinfo, int projectid) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.addApplication(appname,appinfo,projectid);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryAppsByProjectID(String handle, int projectid) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.queryAppsByProjectID(projectid);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryAppByAppKey(String handle, String appKey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String value = db.queryAppByAppKey(appKey);
			System.out.println("*********************************end*******************************");
			return value;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryUserKeyByDevkey(String handle,String devkey) {
		String userkey="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			int projectid=db.queryProjectIdByDevkey(devkey);
			if (projectid<0) {
				return "对不起，该设备没有关联到项目中";
			}else {
				userkey=db.queryUserKeyByProjectId(projectid);
			}
			System.out.println("*********************************end*******************************");
			return userkey;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String addOpenApp(String handle, String appkey, String userkey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.addOpenApp(appkey, userkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String addOpenDevice(String handle, String appkey, String devkey) {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.addOpenDevice(appkey, devkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String updateApplyStateByAppkey(String handle, String appkey, String applystate) {
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			int resultnumber=db.updateApplyStateByAppkey(appkey, applystate);
			if (resultnumber>0) {
				result="更改成功";
			}else if (resultnumber==0) {
				result="更改失败";
			}
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String updateApplyStateByDevicekey(String handle, String devkey, String applystate) {
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			int resultnumber=db.updateApplyStateByDevicekey(devkey, applystate);
			if (resultnumber>0) {
				result="更改成功";
			}else if (resultnumber==0) {
				result="更改失败";
			}
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryOpenAppByUserKey(String handle,String userkey) {
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			result=db.queryOpenAppByUserKey(userkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryOpenDeviceByDevicekey(String handle,String devicekey) {
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			result=db.queryOpenDeviceByDevicekey(devicekey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryAllOpenApp(String handle) {
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			result=db.queryAllOpenApp();
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryUserNameByUserkey(String handle, String userkey) {
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			result=db.queryUserNameByUserkey(userkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryOpenAppByApplystate(String handle,String applystate) {
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			result=db.queryOpenAppByApplystate(applystate);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String addThirdUser(String handle, String appkey, String userkey) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			result=db.addThirdUser(appkey, userkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryThirdApplyAppByUserkey(String handle, String userkey) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			System.out.println("************queryThirdApplyAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.queryThirdApplyAppByUserkey(userkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String queryThirdAppByUserkey(String handle, String userkey) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.queryThirdAppByUserkey(userkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String updateApplyStateByAppkeyandUserkey(String handle, String applystate, String appkey, String userkey) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			int resultnumber=db.updateApplyStateByAppkeyandUserkey(applystate, appkey, userkey);
			if (resultnumber>0) {
				result="更改成功";
			}else if (resultnumber==0) {
				result="更改失败";
			}
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getGatewayCurrentValue(String handle) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.getGatewayCurrentValue();
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String findDevicesByIp(String handle, String ip) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.findDevicesByIp(ip);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getDeviceDataCurrentValue(String handle, String devkey) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.getDeviceDataCurrentValue(devkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getLocation(String handle, String locationID) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.getLocation(locationID);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String config(String handle, String devkey, String order) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.config(devkey, order);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String control(String handle, String devkey, String order) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.config(devkey, order);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getRecentConfigValuesByDevkey(String handle, String devkey) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.getRecentConfigValuesByDevkey(devkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String configLocal(String handle, String ip, String port, String msg) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.configLocal(ip, port, msg);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getDeviceByIp(String handle, String parentip) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.getDeviceByIp(parentip);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getOldDataByRange(String handle, int min, int max) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.getOldDataByRange(min, max);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String controlHeartBeat(String handle, String devkey, String order) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.controlHeartBeat(devkey, order);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getLastFreAndPowerBydevkey(String handle, String devkey) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db2.getLastFreAndPowerBydevkey(devkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getFreAndPowerBydevkeyAndTime(String handle, String timestamp, String devkey, String begin,
			String end) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db2.getFreAndPowerBydevkeyAndTime(timestamp, devkey, begin, end);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getPowerAndTimeBydevkeyAndFre(String handle, String devkey, String fre) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db2.getPowerAndTimeBydevkeyAndFre(devkey, fre);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getTenTimePowerAndTimeBydevkeyAndFre(String handle, String devkey) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db2.getTenTimePowerAndTimeBydevkeyAndFre(devkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String controlFrequencyPeriod(String handle, String order) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.controlFrequencyPeriod(order);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getFrequencyDevice(String handle) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db2.getFrequencyDevice();
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String selectLastTenData(String handle, String devkey) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db2.selectLastTenData(devkey);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String SelectAdapterRealstate(String handle) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.SelectAdapterRealstate();
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String getAdapterHistoryByReltionid(String handle, String relationid) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.getAdapterHistoryByReltionid( relationid);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String parkingInfo(String handle) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.parkingInfo();
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String SensorValueGet(String handle, String devkey, String typeid) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.SensorValueGet( devkey, typeid);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String SensorControl(String handle, String userValueStart, String userValueEnd, String t, String d1,
			String d2) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=db.SensorControl( userValueStart, userValueEnd, t, d1, d2);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	@Override
	public String Sendmessage(String handle,String apikey, String text, String mobile) {
		// TODO Auto-generated method stub
		String result="";
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			//System.out.println("************queryThirdAppByUserkey方法的Userkey:"+userkey+"*******");
			result=SendMessage.singleSend(apikey, text, mobile);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你未得到授权";
		}
	}

	

	
	
	
	
	
	
	

	

}
