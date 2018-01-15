package com.njupt.ws_cxf_spring.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ServiceInterface {

	/**
	 * 验证用户是否合法，合法则给句柄
	 * @param username
	 * @param passwords
	 * @return
	 */
	@WebMethod
	String getPermission(String username,String passwords)throws Exception;
	

	/**
	 * 执行注销操作
	 * @param handle
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	String logout(String handle) throws Exception;

	
	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	@WebMethod
	String userRegister(String handle,String username,String password);
	
	/**
	 * 用户登录，返回用户KEY
	 * @param username
	 * @param password
	 * @return
	 */
	@WebMethod
	String userLogin( String handle,String username,String password);
	
	/**
	 * 添加项目，返回项目ID
	 * @param handle
	 * @param username
	 * @param password
	 * @return
	 */
	@WebMethod
	int addProject(String handle,String projectname,String ownerapikey);
	
	/**
	 * 注册设备，返回设备KEY
	 * @param handle
	 * @param devname
	 * @param devtag
	 * @param devdesc
	 * @param devpicture
	 * @param protocol
	 * @param devauth
	 * @param location
	 * @param longitude
	 * @param latitude
	 * @param projectid
	 * @return
	 */
	@WebMethod
	String addDevice(String handle,String devname,String devtag,String devdesc,String devpicture,String protocol,
			String devauth,String location,String longitude,String latitude,String projectid);
	
	/**
	 * 注册数据类型，返回数据类型key
	 * @param handle
	 * @param type
	 * @param symbol
	 * @param showtype
	 * @return
	 */
	@WebMethod
	String addDataType(String handle,String type,String symbol,int showtype,String devKey);
	
	/**
	 * 根据设备key与类型id查询最新的一条数据
	 * @param handle
	 * @param typeid
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String getCurrentValue(String handle,String typeid,String devkey);
	
	/**
	 * 根据userkey查询用户创建的项目
	 * @param handle
	 * @param userkey
	 * @return
	 */
	@WebMethod
	String queryProjects(String handle,String userkey);
	
	/**
	 * 根据projectid查询该项目所关联的设备信息
	 * @param handle
	 * @param projectid
	 * @return
	 */
	@WebMethod
	String queryDevices(String handle,int projectid);
	
	/**
	 * 根据projectid查询该项目所关联的设备总数
	 * @param handle
	 * @param projectid
	 * @return
	 */
	@WebMethod
	String queryDevicesNum(String handle,int projectid);
	
	/**
	 * 根据deviceID查询该设备的数据类型信息
	 * @param handle
	 * @param deviceKey
	 * @return
	 */
	@WebMethod
	String queryDataType(String handle,String deviceKey);
	
	/**
	 * 根据设备key与类型获取最近8条数据及对应时间
	 * @param handle
	 * @param typeid
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String getRecentValues(String handle,String typeid,String devkey);
	
	/**
	 * 修改数据展现方式
	 * @param handle
	 * @param type
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String changeShowType(String handle,String type,String devkey,int showtype);

	/**
	 * 根据devkey查询该设备的信息
	 * @param handle
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String queryDeviceInfo(String handle,String devkey);
	
	/**
	 * 根据deviceKey与数据类型type查询该设备指定数据类型的信息
	 * @param handle
	 * @param deviceKey
	 * @param type
	 * @return
	 */
	@WebMethod
	String queryOneDataType(String handle,String deviceKey,String type);
	
	/**
	 * 在指定项目下添加应用
	 * @param handle
	 * @param appinfo
	 * @param projectid
	 * @return appkey
	 */
	@WebMethod
	String addApplication(String handle,String appname,String appinfo,int projectid);
	
	/**
	 * 根据projectid查询该项目下的所有应用，返回应用的key
	 * @param handle
	 * @param projectid
	 * @return appkeys
	 */
	@WebMethod
	String queryAppsByProjectID(String handle,int projectid);
	
	/**
	 * 根据APPKEY查询该应用的详细信息
	 * @param handle
	 * @param appKey
	 * @return
	 */
	@WebMethod
	String queryAppByAppKey(String handle,String appKey);
	
	/**
	 * 根据Devkey查找Userkey
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String queryUserKeyByDevkey(String handle,String devkey);
	/**
	 * 用户公开自己的应用
	 * @param handle
	 * @param appkey
	 * @param userkey
	 * @return
	 */
	@WebMethod
	String addOpenApp(String handle,String appkey, String userkey);
	/**
	 * 用户公开自己的设备
	 * @param handle
	 * @param appkey
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String addOpenDevice(String handle,String appkey, String devkey);
	/**
	 * 更新公开的应用表中的申请状态
	 * @param handle
	 * @param appkey
	 * @param applystate
	 * @return
	 */
	@WebMethod
	String updateApplyStateByAppkey(String handle,String appkey,String applystate);
	/**
	 * 更新公开的设备中的申请状态
	 * @param handle
	 * @param devkey
	 * @param applystate
	 * @return
	 */
	@WebMethod
	String updateApplyStateByDevicekey(String handle,String devkey,String applystate);
	/**
	 * 根据Userkey查询
	 * @param userkey
	 * @return
	 */
	@WebMethod
	String queryOpenAppByUserKey(String handle,String userkey);
	/**
	 * 根据Devicekey查询
	 * @param devicekey
	 * @return
	 */
	@WebMethod
	String queryOpenDeviceByDevicekey(String handle,String devicekey);
	/**
	 * 管理员查看所有的APP
	 * @param handle
	 * @return
	 */
	@WebMethod
	String queryAllOpenApp(String handle);
	/**
	 * 根据Userkey查找用户名
	 * @param userkey
	 * @return
	 */
	@WebMethod
	String queryUserNameByUserkey(String handle,String userkey);
	/**
	 * 根据applystate查询已经申请公开的app
	 * @param applystate
	 * @return
	 */
	@WebMethod
	String queryOpenAppByApplystate(String handle,String applystate);
	/**
	 * 根据appkey和Userkey插入第三方申请 表
	 * @param handle
	 * @param appkey
	 * @param userkey
	 * @return
	 */
	@WebMethod
	String addThirdUser(String handle,String appkey, String userkey);
	/**
	 * 根据Userkey查找第三方申请的信息
	 * @param handle
	 * @param userkey
	 * @return
	 */
	@WebMethod
	String queryThirdApplyAppByUserkey(String handle,String userkey);
	/**
	 * 根据第三方的userkey查询第三方的申请信息
	 * @param handle
	 * @param userkey
	 * @return
	 */
	@WebMethod
	String queryThirdAppByUserkey(String handle,String userkey);
	/**
	 * 根据第三方用户的Userkey和所要申请的app修改申请状态
	 * @param handle
	 * @param applystate
	 * @param appkey
	 * @param userkey
	 * @return
	 */
	@WebMethod
	String updateApplyStateByAppkeyandUserkey(String handle,String applystate,String appkey,String userkey);
	/**
	 * 获取网关的数据
	 * @param handle
	 * @return
	 */
	@WebMethod
	String getGatewayCurrentValue(String handle);
	/**
	 * 根据IP获取网关下的所有设备
	 * @param handle
	 * @param ip
	 * @return
	 */
	@WebMethod
	String findDevicesByIp(String handle,String ip);
	/**
	 * 根据Devkey查找当前值
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String getDeviceDataCurrentValue(String handle,String devkey);
	
	/**
	 * 根据locationID查找具体位置
	 * @param handle
	 * @param locationID
	 * @return
	 */
	@WebMethod
	String getLocation(String handle,String locationID);
	/**
	 * 配置网关或节点参数
	 * @param handle
	 * @param relationid
	 * @param order
	 * @return
	 */
	@WebMethod
	String config(String handle,String devkey,String order);
	/**
	 * 反向控制设备
	 * @param handle
	 * @param relationid
	 * @param order
	 * @return
	 */
	@WebMethod
	String control(String handle,String devkey,String order);
	
	/**
	 * 获取控制信息
	 * @param handle
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String getRecentConfigValuesByDevkey(String handle,String devkey);
	
	/**
	 * 本地控制
	 * @param handle
	 * @param ip
	 * @param port
	 * @param msg
	 * @return
	 */
	@WebMethod
	String configLocal(String handle,String ip,String port,String msg);
	
	/**
	 * 根据网关IP查找所属设备的heartbeat
	 * @param handle
	 * @param parentip
	 * @return
	 */
	@WebMethod
	String getDeviceByIp(String handle,String parentip);
	/**
	 * 根据范围取值
	 * @param handle
	 * @param min
	 * @param max
	 * @return
	 */
	@WebMethod
	String getOldDataByRange(String handle,int min,int max);
	
	@WebMethod
	String controlHeartBeat(String handle,String devkey,String order);
	
	/**
	 * 根据Devkey查找最新的频率数据
	 * @param handle
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String getLastFreAndPowerBydevkey(String handle,String devkey);
	
	/**
	 * 根据时间，Devkey和起始截止频率查找
	 * @param handle
	 * @param timestamp
	 * @param devkey
	 * @param begin
	 * @param end
	 * @return
	 */
	@WebMethod
	String getFreAndPowerBydevkeyAndTime(String handle,String timestamp ,String devkey,String begin,String end);
	
	
	/**
	 * 根据Devkey和频率返回时间和强度
	 * @param handle
	 * @param devkey
	 * @param fre
	 * @return
	 */
	@WebMethod
	String getPowerAndTimeBydevkeyAndFre(String handle,String devkey,String fre);
	
	/**
	 * 获取前十个时间点的数据
	 * @param handle
	 * @param devkey
	 * @return
	 */
	@WebMethod
	String getTenTimePowerAndTimeBydevkeyAndFre(String handle,String devkey);
	
	/**
	 * 配置频谱仪的上传周期
	 * @param handle
	 * @param order
	 * @return
	 */
	@WebMethod
	String controlFrequencyPeriod(String handle,String order);
	/**
	 * 获得所有频谱设备的信息
	 * @param handle
	 * @param order
	 * @return
	 */
	@WebMethod
	String getFrequencyDevice(String handle);
	/**
	 * 根据设备key选取最近十条频率信息
	 * @param handle
	 * @param order
	 * @return
	 */
	@WebMethod
	String selectLastTenData(String handle,String devkey);
	/**
	 * 获取所有的插座信息
	 * @param handle
	 * @param order
	 * @return
	 */
	@WebMethod
	String SelectAdapterRealstate(String handle);
	/**
	 *  获取指定插座的历史信息
	 * @param handle
	 * @param order
	 * @return
	 */
	@WebMethod
	String getAdapterHistoryByReltionid(String handle,String relationid);
	/**
	 *  获取所有车位锁信息
	 * @param handle
	 * @param order
	 * @return
	 */
	@WebMethod
	String parkingInfo(String handle);
	/**
	 *  获取所有传感器的值
	 * @param handle
	 * @param order
	 * @return
	 */
	@WebMethod
	public String SensorValueGet(String handle,String devkey,String typeid);
	/**
	 *  反向控制传感器
	 * @param handle
	 * @param order
	 * @return
	 */
	@WebMethod
	public String SensorControl(String handle,String userValueStart,String userValueEnd,String t,String d1,String d2);
	/**
	 *  发送短信
	 * @param handle
	 * @param order
	 * @return
	 */
	@WebMethod
	public String Sendmessage(String handle,String apikey, String text, String mobile);
}

