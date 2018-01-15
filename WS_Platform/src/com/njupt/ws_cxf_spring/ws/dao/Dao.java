package com.njupt.ws_cxf_spring.ws.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.njupt.ws_cxf_spring.ws.bean.Adapter;
import com.njupt.ws_cxf_spring.ws.bean.App;
import com.njupt.ws_cxf_spring.ws.bean.AppOpen;
import com.njupt.ws_cxf_spring.ws.bean.Config;
import com.njupt.ws_cxf_spring.ws.bean.Data;
import com.njupt.ws_cxf_spring.ws.bean.DataNew;
import com.njupt.ws_cxf_spring.ws.bean.DataType;
import com.njupt.ws_cxf_spring.ws.bean.Device;
import com.njupt.ws_cxf_spring.ws.bean.DeviceOpen;
import com.njupt.ws_cxf_spring.ws.bean.FPT;
import com.njupt.ws_cxf_spring.ws.bean.FrequencyDevice;
import com.njupt.ws_cxf_spring.ws.bean.Gateway;
import com.njupt.ws_cxf_spring.ws.bean.HeartBean;
import com.njupt.ws_cxf_spring.ws.bean.OldData;
import com.njupt.ws_cxf_spring.ws.bean.Parking;
import com.njupt.ws_cxf_spring.ws.bean.Project;
import com.njupt.ws_cxf_spring.ws.bean.ThirdAdmin;
import com.njupt.ws_cxf_spring.ws.tools.Tools;
import com.sun.org.apache.regexp.internal.recompile;

import net.sf.json.JSONArray;

public class Dao {
	
	private static final String url = "jdbc:oracle:thin:@10.10.22.99:1521:iotdb";
	private static final String user = "scott";
	private static final String userpass = "system";
	private static ComboPooledDataSource ds;
	private ResultSet rs;
	private Connection conn;
	private PreparedStatement pstmt;
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	
	/**
     * 初始化连接池代码块,全局静态代码块
     */
	static{
		initDB();
	}
	
	/**
	 * 初始化数据库操作，建立连接池,使用的是C3PO数据源
	 * 
	 */
	private static final void initDB() {
		ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(driver);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		ds.setJdbcUrl(url);
		ds.setUser(user);
		ds.setPassword(userpass);
		ds.setMaxPoolSize(100);
		ds.setInitialPoolSize(10);
		ds.setMinPoolSize(5);
		ds.setMaxStatements(300);
		ds.setMaxIdleTime(10);
		ds.setMaxConnectionAge(100);
		//连接的最大空闲时间，如果超过这个时间，某个数据库连接还没有被使用，则会断开掉这个连接,单位秒  
		//ds.setMaxIdleTime(10);
		//配置连接的生存时间，超过这个时间的连接将由连接池自动断开丢弃掉。当然正在使用的连接不会马上断开，而是等待它close再断开。
		//ds.setMaxConnectionAge(50);
	}

	/**
	 * 向表w_athority中插入句柄，句柄生成时间以及结束时间
	 * 
	 * @param handle
	 * @return
	 * @throws Exception
	 */
	public int inserthandle(String handle, int userid) {
		
		String sql = "insert into w_authority values(?,sysdate,sysdate+1/24,?,1)";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, handle);
			pstmt.setInt(2, userid);
			flag = pstmt.executeUpdate();
			return flag;
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 判断用户是否合法
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @return
	 */
	public int islegalUser(String username, String password) {
		String sql = "select * from w_user where username=?";
		int c = 0;
		String b = Tools.getMD5Str(password);
		System.out.println("用户输入的密码："+b);
		String a = "";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				a = Tools.getMD5Str(rs.getString(3));
				System.out.println("用户注册在数据库中的密码："+a);
				if (a.equals(b)) {
					c = rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return c;
	}

	/**
	 * 判断句柄是否合法以及是否有效
	 * 
	 * @param handle
	 * @return
	 */
	public boolean islegalhandle(String handle) {
		String sql = "select * from w_authority where handle=?";
		try {
			conn = ds.getConnection();
			// ---------------------------------------------------
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, handle);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String a = rs.getString(3);

				String b = Tools.getCurrentTime();
				int c = Tools.timeCompare(b, a);
				if (c < 0) {
					return true;
				} else
					return false;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 更新句柄对应表的数据
	 * 
	 * @param handle
	 * @return
	 * @throws Exception
	 */
	public int updatehandle(String handle) {
		String sql = "update w_authority set endtime=sysdate,survivaltime=(sysdate-begintime)*24 where handle=?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, handle);
			flag = pstmt.executeUpdate();
			return flag;
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}

	
	
	
	
	/**
	 * 存储车位信息时用来判断车位信息是否存在
	 * @param deviceid
	 * @return
	 * @throws Exception
	 */
	public boolean findByDeviceID(int deviceid){
		boolean value=false;
		String sql = "select count(*) from w_parking_data where deviceid=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deviceid);
			rs = pstmt.executeQuery();
			System.out.println("findByDeviceID(int deviceid)");
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * 预约车位时用来判断用户记录是否存在
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public boolean findByUserID(int userid){
		boolean value=false;
		String sql = "select count(*) from w_reservation where userid=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);
			rs = pstmt.executeQuery();
			System.out.println("findByUserID(int userid)");
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * 根据userid判断用户是否登记过车牌
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public boolean findPropertyByUserID(int userid){
		boolean value=false;
		String sql = "select count(*) from w_user_property where userid=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userid);
			rs = pstmt.executeQuery();
			System.out.println("findPropertyByUserID(int userid)");
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	/**
	 * 根据用户名查找对应userid
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int getUserIDByUsername(String username){
		int userID=0;
		String sql = "select userid from w_user_info where username=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			System.out.println("getUserIDByUsername(String username)");
			while (rs.next()) {
				userID=rs.getInt(1);
			}
			return userID;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userID;
	}
	
	
	
	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @param telnum
	 * @return{res:}
	 * @throws Exception
	 */
	public String userRegister(String username, String password) {
		boolean exist=findByUsername(username);
		if(!exist){
			String userKey=Tools.createUUID();
			String sql = "insert into w_users values(usersid_increase.nextval,?,?,sysdate,3,?)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				pstmt.setString(3, userKey);
				int flag = pstmt.executeUpdate();
				if(flag==1){
					System.out.println("userRegister: success");
					return "{\"res\":\"success\"}";			
				}
			}catch(Exception e){			
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("userRegister: failed");
			return "{\"res\":\"failed\"}";
		}else{
			System.out.println("userRegister: 用户名已存在");
			return "{\"res\":\"用户名已存在\"}";
		}
	}
	
	
	/**
	 * 注册时用来判断用户名是否存在
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public boolean findByUsername(String username){
		boolean value=false;
		String sql = "select count(*) from w_users where username=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int res=rs.getInt(1);
				if(res>0){
					value=true;
				}
			}
			System.out.println("findByUsername: "+value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * 用户登录，成功则返回userKEY
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public String userLogin(String username, String password){
		String value="用户名或密码错误";
		String sql = "select userkey from w_users where username=? and password=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				value=rs.getString(1);
			}
			System.out.println("userLogin: "+value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * 添加项目，传入userKey，返回項目projectKey
	 * @param projectname
	 * @param ownerapikey
	 * @return
	 */
	public String addProject(String projectname, String ownerapikey) {
		String projectKey=Tools.createUUID();
		String sql = "insert into w_projects values(projectid_increase.nextval,?,?,?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectname);
			pstmt.setString(2, ownerapikey);
			pstmt.setString(3, projectKey);
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("addProject: "+projectKey);
				return projectKey;			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return projectKey;
	}
	
	/**
	 * 根据项目key获取项目ID
	 * @param projectKey
	 * @return
	 */
	public int getProjectIDByKey(String projectKey) {
		int projectID=0;
		String sql = "select id from w_projects where projectkey=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectKey);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				projectID=rs.getInt(1);
			}
			System.out.println("getProjectIDByKey: "+projectID);
			return projectID;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projectID;
	}
	
	/**
	 * 注册设备信息，返回设备key
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
	public String addDevice(String devname, String devtag, String devdesc, String devpicture, String protocol,
			String devauth, String location, String longitude, String latitude, String projectid) {
		String devKey=Tools.createUUID();
		String sql = "insert into w_device values(deviceid_increase.nextval,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, devname);
			pstmt.setString(2, devtag);
			pstmt.setString(3, devdesc);
			pstmt.setString(4, devpicture);
			pstmt.setString(5, protocol);
			pstmt.setString(6, devauth);
			pstmt.setString(7, location);
			pstmt.setString(8, longitude);
			pstmt.setString(9, latitude);
			pstmt.setString(10, projectid);
			pstmt.setString(11, devKey);
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("addDevice: "+devKey);
				return devKey;			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "failed";
	}

	/**
	 * 注册数据类型，返回数据类型ID
	 * @param type
	 * @param symbol
	 * @param showtype
	 * @return
	 */
	public String addDataType(String type, String symbol, int showtype,String devkey) {
//		String datatypeID=Tools.createUUID();
		String sql = "insert into w_datatype values(typeid_increase.nextval,?,?,?,sysdate,?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type);
			pstmt.setString(2, symbol);
			pstmt.setInt(3, showtype);
			pstmt.setString(4, devkey);
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("addDataType: "+"success");
				return "success";			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "failed";
	}

	public String getCurrentValue(String type, String devkey) {
		String result="";
		String sql="select value from(select * from w_devdata where typeid=? and devkey=? order by savetime desc) where rownum=1";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, type);
        	pstmt.setString(2, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	            result=rs.getString("value");	
	        }            		
	        System.out.println("getCurrentValue: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	
	public String queryProjects(String userkey) {
		String result="";
		Project project;
		List<Project> projectList;
		String sql="select * from W_PROJECTS where ownerapikey=?";		
        try{
        	projectList=new ArrayList<Project>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, userkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	project=new Project(rs.getInt("id"),rs.getString("projectname"),rs.getString("ownerapikey"),rs.getString("projectkey"));
	        	projectList.add(project);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(projectList);
	        result=jsonArray.toString();
	        System.out.println("queryProjects: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}

	public String queryDevices(int projectid) {
		String result="";
		Device device;
		List<Device> deviceList;
		String sql="select * from W_DEVICE where projectid=?";		
        try{
        	deviceList=new ArrayList<Device>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setInt(1, projectid);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	device=new Device(rs.getInt("id"),rs.getString("devname"),rs.getString("devtag"),rs.getString("devdesc"),rs.getString("protocol"),rs.getString("devauth"),rs.getString("location"),rs.getString("longitude"),rs.getString("latitude"),rs.getInt("projectid"),rs.getString("devicekey"));
	        	deviceList.add(device);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(deviceList);
	        result=jsonArray.toString();
	        System.out.println("queryDevices: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	
	public String queryDevicesNum(int projectid) {
		String result="";
		String sql="select count(*) from W_DEVICE where projectid=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setInt(1, projectid);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	result=rs.getInt(1)+"";
	        }     
	       
	        System.out.println("queryDevicesNum: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}

	public String queryDataType(String deviceKey) {
		String result="";
		DataType dataType;
		List<DataType> dataTypeList;
		String sql="select * from W_DATATYPE where devkey=?";		
        try{
        	dataTypeList=new ArrayList<DataType>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, deviceKey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	dataType=new DataType(rs.getInt("id"),rs.getString("type"),rs.getString("symbol"),rs.getInt("showtype"),rs.getTimestamp("createtime").toString(),rs.getString("devkey"));
	        	dataTypeList.add(dataType);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(dataTypeList);
	        result=jsonArray.toString();
	        System.out.println("queryDataType: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}

	public String getRecentValues(String type, String devkey) {
		String result="";
		Data data;
		List<Data> dataList;
		String sql="select * from(select * from w_devdata where typeid=? and devkey=? order by savetime desc) where rownum<9";		
        try{
        	dataList=new ArrayList<Data>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, type);
        	pstmt.setString(2, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	data=new Data(rs.getInt("id"),rs.getString("value"),rs.getString("typeid"),rs.getString("devkey"),rs.getTimestamp("savetime").toString());
	        	dataList.add(data);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(dataList);
	        result=jsonArray.toString();
	        System.out.println("getRecentValues: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}

	public String changeShowType(String type, String devkey, int showtype) {
		String sql = "update w_datatype set showtype=? where type=? and devkey=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, showtype);
			pstmt.setString(2, type);
			pstmt.setString(3, devkey);
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("changeShowType: success");
				return "{\"res\":\"success\"}";			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("changeShowType: failed");
		return "{\"res\":\"failed\"}";
	}

	public String queryDeviceInfo(String devkey) {
		String result="";
		Device device;
		List<Device> deviceList;
		String sql="select * from W_DEVICE where devicekey=?";		
        try{
        	deviceList=new ArrayList<Device>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	device=new Device(rs.getInt("id"),rs.getString("devname"),rs.getString("devtag"),rs.getString("devdesc"),rs.getString("protocol"),rs.getString("devauth"),rs.getString("location"),rs.getString("longitude"),rs.getString("latitude"),rs.getInt("projectid"),rs.getString("devicekey"));
	        	deviceList.add(device);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(deviceList);
	        result=jsonArray.toString();
	        System.out.println("queryDeviceInfo: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}

	public String queryOneDataType(String deviceKey, String type) {
		String result="";
		DataType dataType;
		List<DataType> dataTypeList;
		String sql="select * from W_DATATYPE where devkey=? and type=?";		
        try{
        	dataTypeList=new ArrayList<DataType>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, deviceKey);
        	pstmt.setString(2, type);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	dataType=new DataType(rs.getInt("id"),rs.getString("type"),rs.getString("symbol"),rs.getInt("showtype"),rs.getTimestamp("createtime").toString(),rs.getString("devkey"));
	        	dataTypeList.add(dataType);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(dataTypeList);
	        result=jsonArray.toString();
	        System.out.println("queryOneDataType: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}

	public String addApplication(String appname,String appinfo, int projectid) {
		String appKey=Tools.createUUID();
		String sql = "insert into w_app values(appid_increase.nextval,?,?,?,?,sysdate)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, appname);
			pstmt.setString(2, appinfo);
			pstmt.setString(3, appKey);
			pstmt.setInt(4, projectid);
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("addApplication: "+appKey);
				return appKey;			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "failed";
	}

	public String queryAppsByProjectID(int projectid) {
		String result="";
		String sql="select * from W_APP where projectid=?";		
		List<App> list;
		App app;
        try{
        	list=new ArrayList<App>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setInt(1, projectid);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	app=new App(rs.getInt("id"),rs.getString("appname"),rs.getString("appinfo"),rs.getString("appkey"),rs.getInt("projectid"),rs.getTimestamp("createtime").toString());
	        	list.add(app);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(list);
	        result=jsonArray.toString();
	        System.out.println("queryAppsByProjectID: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}

	public String queryAppByAppKey(String appKey) {
		String result="";
		String sql="select * from W_APP where appkey=?";		
		List<App> list;
		App app;
        try{
        	list=new ArrayList<App>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, appKey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	app=new App(rs.getInt("id"),rs.getString("appname"),rs.getString("appinfo"),rs.getString("appkey"),rs.getInt("projectid"),rs.getTimestamp("createtime").toString());
	        	list.add(app);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(list);
	        result=jsonArray.toString();
	        System.out.println("queryAppByAppKey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	
	public int queryProjectIdByDevkey(String devkey) {
		int projectid=-1;
		String sql="select projectid from W_DEVICE where devicekey=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, devkey);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	        	projectid=rs.getInt(1);
	        }     
	        
	        System.out.println("queryProjectIdByDevkey: "+projectid);
			return projectid;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return projectid;
	}

	public String queryUserKeyByProjectId(int projectid) {
		String result="";
		String sql="select ownerapikey from W_PROJECTS where id=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setInt(1, projectid);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	        	result=rs.getString(1);
	        }     
	        
	        System.out.println("queryUserKeyByProjectId: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	
	/**
	 * 用户申请公开自己的应用
	 * @param appkey
	 * @param userkey
	 * @return
	 */
	public String addOpenApp(String appkey, String userkey) {
		int count=queryCountByApplykeyandUserkey(appkey, userkey);
		if (count==1) {
			System.out.println("addOpenApp:已经申请过公开");
			return "已经申请过公开";
		}else if (count==0) {
			String json=queryDeviceByAppkey(appkey);
			ArrayList<String> list=Tools.jsonstring(json);
			for(int i=0,len=list.size();i<len;i++){
				addOpenDevice(appkey, list.get(i));
			}
			String result="failed";
			String sql = "insert into W_APP_GRANT values(deviceid_increase.nextval,?,?,?,sysdate)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, appkey);
				pstmt.setString(2, userkey);
				pstmt.setString(3, "申请中");
				int flag = pstmt.executeUpdate();
				if(flag==1){
					result="success";
					System.out.println("addOpenApp: "+result);
					return result;			
				}
			}catch(Exception e){			
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		return "failed";
	}
	
	
	/**
	 * 用户申请公开自己的设备
	 * @param appkey
	 * @param devkey
	 * @return
	 */
	public String addOpenDevice(String appkey, String devkey) {
		int count=queryCountByApplykeyandDevicekey(appkey, devkey);
		if (count==1) {
			System.out.println("addOpenDevice:已经申请过公开");
			return "已经申请过公开";
		}else if (count==0) {
			String result="failed";
			String sql = "insert into W_APP_OPEN values(deviceid_increase.nextval,?,?,?,sysdate)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, appkey);
				pstmt.setString(2, devkey);
				pstmt.setString(3, "申请中");
				int flag = pstmt.executeUpdate();
				if(flag==1){
					result="success";
					System.out.println("addOpenDevice: "+result);
					return result;			
				}
			}catch(Exception e){			
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		return "failed";
	}
	
	/**
	 * 根据appkey修改申请状态
	 * @param appkey
	 * @return
	 */
	public int updateApplyStateByAppkey(String appkey,String applystate) {
		String sql = "update W_APP_GRANT set apply_state=? where appkey=?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, applystate);
			pstmt.setString(2, appkey);
			flag = pstmt.executeUpdate();
			System.out.println("updateApplyStateByAppkey:"+flag);
			return flag;
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 根据appkey修改申请状态
	 * @param appkey
	 * @return
	 */
	public int updateApplyStateByDevicekey(String devkey,String applystate) {
		String sql = "update W_APP_OPEN set apply_state=? where devicekey=?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, applystate);
			pstmt.setString(2, devkey);
			flag = pstmt.executeUpdate();
			System.out.println("updateApplyStateByDevicekey:"+flag);
			return flag;
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	/**
	 * 根据userkey返回所查数据项的信息
	 * @param userkey
	 * @return
	 */
	public String queryOpenAppByUserKey(String userkey) {
		String result="";
		AppOpen appOpen;
		List<AppOpen> appOpens;
		String sql="select * from W_APP_GRANT where userkey=?";		
        try{
        	appOpens=new ArrayList<AppOpen>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, userkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	appOpen=new AppOpen(rs.getInt("id"), rs.getString("appkey"), rs.getString("userkey"), rs.getString("apply_state"), rs.getTimestamp("savetime").toString());
	        	appOpens.add(appOpen);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(appOpens);
	        result=jsonArray.toString();
	        System.out.println("queryOpenAppByUserKey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	
	/**
	 * 根据devicekey返回所查数据项的信息
	 * @param userkey
	 * @return
	 */
	public String queryOpenDeviceByDevicekey(String devicekey) {
		String result="";
		DeviceOpen deviceOpen;
		List<DeviceOpen> deviceOpens;
		String sql="select * from W_APP_OPEN where devicekey=?";		
        try{
        	deviceOpens=new ArrayList<DeviceOpen>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, devicekey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	deviceOpen=new DeviceOpen(rs.getInt("id"), rs.getString("appkey"), rs.getString("devicekey"), rs.getString("apply_state"), rs.getTimestamp("savetime").toString());
	        	deviceOpens.add(deviceOpen);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(deviceOpens);
	        result=jsonArray.toString();
	        System.out.println("queryOpenDeviceByDevicekey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	
	/**
	 * 管理员查看所有的APP
	 * @return
	 */
	public String queryAllOpenApp() {
		String result="";
		AppOpen appOpen;
		List<AppOpen> appOpens;
		String sql="select * from W_APP_GRANT";		
        try{
        	appOpens=new ArrayList<AppOpen>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	appOpen=new AppOpen(rs.getInt("id"), rs.getString("appkey"), rs.getString("userkey"), rs.getString("apply_state"), rs.getTimestamp("savetime").toString());
	        	appOpens.add(appOpen);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(appOpens);
	        result=jsonArray.toString();
	        System.out.println("queryAllOpenApp: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	
	/**
	 * 用户根据APPkey申请公开自己的设备
	 * @param appkey
	 * @return
	 */
	public String queryDeviceByAppkey(String appkey) {
		String result="";
		String sql = "select appinfo from w_app where appkey=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, appkey);
			rs = pstmt.executeQuery();
			while(rs.next()){
				result=rs.getString(1);
				System.out.println("queryDeviceByAppkey:"+result);
			}
			
			return result;
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 根据Userkey查找用户名
	 * @param userkey
	 * @return
	 */
	public String queryUserNameByUserkey(String userkey) {
		String result="";
		String sql = "select username from w_users where userkey=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userkey);
			rs = pstmt.executeQuery();
			while(rs.next()){
				result=rs.getString(1);
				System.out.println("queryUserNameByUserkey:"+result);
			}
			
			return result;
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 根据申请状态查看所有的APP信息
	 * @return
	 */
	public String queryOpenAppByApplystate(String applystate) {
		String result="";
		AppOpen appOpen;
		List<AppOpen> appOpens;
		String sql="select * from W_APP_GRANT where apply_state=?";		
        try{
        	appOpens=new ArrayList<AppOpen>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  
        	pstmt.setString(1, applystate);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	appOpen=new AppOpen(rs.getInt("id"), rs.getString("appkey"), rs.getString("userkey"), rs.getString("apply_state"), rs.getTimestamp("savetime").toString());
	        	appOpens.add(appOpen);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(appOpens);
	        result=jsonArray.toString();
	        System.out.println("queryOpenAppByApplystate: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	/**
	 * 根据appkey和Userkey判断有没有重复插入
	 * @param appkey
	 * @param userkey
	 * @return
	 */
	
	public int queryCountByApplykeyandUserkey(String appkey,String userkey) {
		int result=0;
		String sql="select count(*) from W_APP_GRANT where appkey=? and userkey=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  
        	pstmt.setString(1, appkey);
        	pstmt.setString(2, userkey);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	        	result=rs.getInt(1);
	        }     
	        System.out.println("queryCountByApplykeyandUserkey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	/**
	 * 根据appkey和devkey判断有没有重复插入
	 * @param appkey
	 * @param devkey
	 * @return
	 */
	
	public int queryCountByApplykeyandDevicekey(String appkey,String devkey) {
		int result=0;
		String sql="select count(*) from W_APP_OPEN where appkey=? and devicekey=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  
        	pstmt.setString(1, appkey);
        	pstmt.setString(2, devkey);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	        	result=rs.getInt(1);
	        }     
	        System.out.println("queryCountByApplykeyandDevicekey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	/**
	 * 根据appkey和Userkey插入数据
	 * @param appkey
	 * @param userkey
	 * @return
	 */
	
	public String addThirdUser(String appkey, String userkey) {
		int count=queryCountByAppkeyandUserkey(appkey, userkey);
		if (count==1) {
			System.out.println("addThirdUser:已经申请过公开");
			return "已经申请过公开";
		}else if (count==0) {
			String result="failed";
			String sql = "insert into W_APP_ADMIN values(deviceid_increase.nextval,?,?,?,sysdate)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, appkey);
				pstmt.setString(2, userkey);
				pstmt.setString(3, "申请中");
				int flag = pstmt.executeUpdate();
				if(flag==1){
					result="success";
					System.out.println("addThirdUser: "+result);
					return result;			
				}
			}catch(Exception e){			
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		return "failed";
	}
	
	/**
	 * 根据自身的Userkey查询第三方的申请信息
	 * @param userkey
	 * @return
	 */
	public String queryThirdApplyAppByUserkey(String userkey) {
		String result="";
		String sql="select appkey from W_APP_GRANT where userkey=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  
        	pstmt.setString(1, userkey);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	        	String appkey=rs.getString(1);
	        	System.out.println("根据"+userkey+"查到的appkey为"+appkey);
	        	result=queryThirdAppByAppkey(appkey);
	        }     

	        System.out.println("queryThirdApplyAppByUserkey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	
	/**
	 * 根据appkey查询第三方的申请信息
	 * @param userkey
	 * @return
	 */
	public String queryThirdAppByAppkey(String appkey) {
		String result="";
		ThirdAdmin thirdAdmin;
		List<ThirdAdmin> thirdAdmins;
		String sql="select * from W_APP_ADMIN where appkey=?";		
        try{
        	thirdAdmins=new ArrayList<ThirdAdmin>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  
        	pstmt.setString(1, appkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	thirdAdmin=new ThirdAdmin(rs.getInt("id"), rs.getString("appkey"), rs.getString("third_userkey"), rs.getString("apply_state"), rs.getTimestamp("savetime").toString());
	        	thirdAdmins.add(thirdAdmin);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(thirdAdmins);
	        result=jsonArray.toString();
	       // System.out.println("queryThirdAppByAppkey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	
	/**
	 * 根据第三方用户的Userkey和所要申请的app修改申请状态
	 * @param applystate
	 * @param appkey
	 * @param userkey
	 * @return
	 */
	public int updateApplyStateByAppkeyandUserkey(String applystate,String appkey,String userkey) {
		String sql = "update W_APP_ADMIN set apply_state=? where appkey=? and third_userkey=?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, applystate);
			pstmt.setString(2, appkey);
			pstmt.setString(3, userkey);
			flag = pstmt.executeUpdate();
			System.out.println("updateApplyStateByAppkeyandUserkey:"+flag);
			return flag;
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 根据第三方的userkey查询第三方的申请信息
	 * @param userkey
	 * @return
	 */
	public String queryThirdAppByUserkey(String userkey) {
		String result="";
		ThirdAdmin thirdAdmin;
		List<ThirdAdmin> thirdAdmins;
		String sql="select * from W_APP_ADMIN where third_userkey=?";		
        try{
        	thirdAdmins=new ArrayList<ThirdAdmin>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  
        	pstmt.setString(1, userkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	thirdAdmin=new ThirdAdmin(rs.getInt("id"), rs.getString("appkey"), rs.getString("third_userkey"), rs.getString("apply_state"), rs.getTimestamp("savetime").toString());
	        	thirdAdmins.add(thirdAdmin);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(thirdAdmins);
	        result=jsonArray.toString();
	       System.out.println("queryThirdAppByAppkey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	/**
	 * 根据appkey和devkey判断有没有重复插入
	 * @param appkey
	 * @param devkey
	 * @return
	 */
	
	public int queryCountByAppkeyandUserkey(String appkey,String userkey) {
		int result=0;
		String sql="select count(*) from W_APP_ADMIN where appkey=? and third_userkey=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  
        	pstmt.setString(1, appkey);
        	pstmt.setString(2, userkey);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	        	result=rs.getInt(1);
	        }     
	        System.out.println("queryCountByAppkeyandUserkey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	/**
	 * 根据IP获取网关的数据信息
	 * @param ip
	 * @return
	 */
	public String getGatewayCurrentValue() {
		String result="";
		Gateway gateway;
		List<Gateway> list=new ArrayList<>();
		String sql="select * from w_gatewayonly";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	           gateway=new Gateway(rs.getString("ip"), rs.getString("mac"),rs.getString("cpu_usr") , rs.getString("cpu_sys"), rs.getString("memory_total"), rs.getString("memory_used"), rs.getString("downlink"), rs.getString("uplink"),rs.getString("savetime"));
	           list.add(gateway);
	        }       
	        JSONArray jsonArray = JSONArray.fromObject(list);
	        result=jsonArray.toString();
	        System.out.println("getGatewayCurrentValue: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	
	/**
	 * 根据父级网关查找设备秘钥
	 * @param ip
	 * @return
	 */
	public ArrayList<String> findDeviceKeyByIp(String ip) {
		ArrayList<String> result=new ArrayList<>();
		String sql="select distinct devkey from w_devdata where parentip=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql); 
        	pstmt.setString(1, ip);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	String devkey=rs.getString("devkey");
	        	if (!result.contains(devkey)) {
					result.add(devkey);
				}
	        }       
	        System.out.println("findDeviceKeyByIp: "+result.toString());
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	/**
	 * 根据Devkey查找设备
	 * @param devkey
	 * @return
	 */
	public Device getDeviceByDevkey(String devkey) {
		Device result=null;
		String sql="select * from w_device where devicekey=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql); 
        	pstmt.setString(1, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	result=new Device(rs.getInt("id"), rs.getString("devname"), rs.getString("devtag"), rs.getString("devdesc"), rs.getString("protocol"), rs.getString("devauth"), rs.getString("location"), rs.getString("longitude"), rs.getString("latitude"), rs.getInt("projectid"), rs.getString("devicekey"));
	        }       
	        System.out.println("getDeviceByDevkey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	/**
	 * 根据IP查找网关下的所有设备
	 * @param ip
	 * @return
	 */
	public String findDevicesByIp(String ip){
		ArrayList<String> devkeylist=findDeviceKeyByIp(ip);
		ArrayList<Device> list=new ArrayList<>();
		String result=null;
		if (devkeylist==null||devkeylist.size()==0) {
			return result;
		}
		for(int i=0;i<devkeylist.size();i++){
			Device device=getDeviceByDevkey(devkeylist.get(i));
			if (device!=null) {
				list.add(device);
			}
		}
		JSONArray jsonArray = JSONArray.fromObject(list);
        result=jsonArray.toString();
        System.out.println("findDevicesByIp:"+result);
		return result;
	}
	
	/**
	 * 根据Devkey查找最新的数据
	 * @param devkey
	 * @return
	 */
	public String getDeviceDataCurrentValue(String devkey) {
		String result="";
		DataNew dataNew=null;
		List<DataNew> list=new ArrayList<>();
		String sql="select * from(select * from w_devdata where devkey=? order by savetime desc) where rownum=1";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	           dataNew=new DataNew(rs.getInt("id"), rs.getString("value"), rs.getString("typeid"), rs.getString("devkey"), rs.getTimestamp("savetime").toString(),rs.getString("location") , rs.getString("parentip"), rs.getString("mac"),rs.getString("type"))	;
	           list.add(dataNew);
	        }  
	        JSONArray jsonArray = JSONArray.fromObject(list);
	        result=jsonArray.toString();
	        System.out.println("getDeviceDataCurrentValue: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	/**
	 * 根据locationID查找具体位置
	 * @param locationID
	 * @return
	 */
	public  String getLocation(String locationID){
		String result=null;
		String sql="select location from w_location where locationid=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql); 
        	pstmt.setString(1, locationID);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	result=rs.getString("location");
	        }       
	        System.out.println("getLocation: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;	
	}
	
	/**
	 * 配置网关或节点参数
	 * @param relationid 网关与平台连接ID
	 * @param order 指令
	 * @return
	 */
	public String config(String devkey,String order) {
		String result="success";
		 try {
		 WebSocketContainer container = ContainerProvider.getWebSocketContainer(); 
		Session session=null;
		 String uri ="ws://10.10.23.10:8080/WebSocketUser/websocket.ws/relationId/1234567"; 
		 System.out.println("Connecting to:"+ uri); 
		 try { 
			  session = container.connectToServer(MyClient.class, URI.create(uri)); 
		 } catch (DeploymentException e) { 
			 e.printStackTrace();
			 result= "failed";
		 } catch (IOException e) { 
		e.printStackTrace();
		result= "failed";
		}
	        //session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=1E;");//开灯
	       // session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=0E;");//关灯
	     //   session.getBasicRemote().sendText("123456_"+"S:N=801002@Find=1E;");//开蜂鸣
		 String relationid=getRelationidByDevkey(devkey);
			session.getBasicRemote().sendText(relationid+"_"+order+";");//关蜂鸣
			addConfig(devkey, order);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="failed";
		}//关蜂鸣器
		return result;
	}
	
	/**
	 * 反向控制设备
	 * @param relationid 网关与平台连接ID
	 * @param order 指令
	 * @return
	 */
	public String control(String devkey,String order) {
		String result="success";
		 try {
		 WebSocketContainer container = ContainerProvider.getWebSocketContainer(); 
		Session session=null;
		 String uri ="ws://10.10.23.10:8080/WebSocketUser/websocket.ws/relationId/1234567"; 
		 System.out.println("Connecting to:"+ uri); 
		 try { 
			  session = container.connectToServer(MyClient.class, URI.create(uri)); 
		 } catch (DeploymentException e) { 
			 e.printStackTrace();
			 result= "failed";
		 } catch (IOException e) { 
		e.printStackTrace();
		result= "failed";
		}
	        //session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=1E;");//开灯
	       // session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=0E;");//关灯
	     //   session.getBasicRemote().sendText("123456_"+"S:N=801002@Find=1E;");//开蜂鸣
		 String relationid=getRelationidByDevkey(devkey);
			session.getBasicRemote().sendText(relationid+"_"+order+";");//关蜂鸣
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="failed";
		}//关蜂鸣器
		return result;
	}
	
	
	/**
	 * 根据Devkey获取IP
	 * @param devkey
	 * @return
	 */
	public String getIpByDevkey(String devkey){
		String result="";
		String sql="select parentip from w_devdata where devkey=? order by savetime desc";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql); 
        	pstmt.setString(1, devkey);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	        	result=rs.getString("parentip");
	        }       
	        System.out.println("getIpByDevkey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;	
	}
	
	public String getRelationidByDevkey(String devkey){
		String result="";
		String ip=getIpByDevkey(devkey);
		String sql="select relationid from w_relationid where ip=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql); 
        	pstmt.setString(1, ip);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	        	result=rs.getString("relationid");
	        }       
	        System.out.println("getRelationidByIp: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;	
	}
	/**
	 * 添加配置记录
	 * @param devkey
	 * @param order
	 * @return
	 */
	public String addConfig(String devkey, String order) {
			String result="failed";
			String sql = "insert into W_CONFIG values(configid_increase.nextval,?,sysdate,?)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, devkey);
				pstmt.setString(2, order);
				int flag = pstmt.executeUpdate();
				if(flag==1){
					result="success";
					System.out.println("addConfig: "+result);
					return result;			
				}
			}catch(Exception e){			
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result;
	}
	
	
	/**
	 * 根据Devkey查最近的9条数据
	 * @param devkey
	 * @return
	 */
	public String getRecentConfigValuesByDevkey(String devkey) {
		String result="";
		Config config;
		List<Config> configs;
		String sql="select * from(select * from w_config where devkey=? order by configtime desc) where rownum<9";		
        try{
        	configs=new ArrayList<>();
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	config=new Config(rs.getInt("id"),rs.getString("devkey"),rs.getTimestamp("configtime").toString(),rs.getString("content"));
	        	configs.add(config);
	        }     
	        JSONArray jsonArray = JSONArray.fromObject(configs);
	        result=jsonArray.toString();
	        System.out.println("getRecentConfigValuesByDevkey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;
	}
	/**
	 * 本地控制
	 * @param ip
	 * @param port
	 * @param msg
	 * @return
	 */
	public String configLocal(String ip,String port,String msg) {
		String result="success";
		try {
			boolean flag=Tools.tcpSendToServer(msg, ip, Integer.parseInt(port));
			if (flag==false) {
				result="failed";
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("configLocal:"+result);
		return result;
	}
	/**
	 * 根据网关IP查找目前的设备heartbeat
	 * @param parentip
	 * @return
	 */
	public String getDeviceByIp(String parentip) {
		String result="";
		HeartBean heartBean=null;
		List<HeartBean> list=new ArrayList<>();
		String sql="select * from w_heartbeat where parentip=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, parentip);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	           heartBean=new HeartBean(rs.getInt("id"), rs.getString("devkey"), rs.getString("heartbeat"), rs.getString("parentip"), rs.getString("savetime"));
	           list.add(heartBean);
	        }  
	        JSONArray jsonArray = JSONArray.fromObject(list);
	        result=jsonArray.toString();
	        System.out.println("getDeviceByIp: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	
	/**
	 * 根据范围取值
	 * @param min
	 * @param max
	 * @return
	 */
	public String getOldDataByRange(int min,int max) {
		String result="";
		OldData oldData=null;
		List<OldData> list=new ArrayList<>();
		String sql="select dd.deviceid,dd.value,dd.savetime,dd.name,lo.location from (select cc.deviceid,cc.value,cc.savetime,e.name,e.locationid from (select dt.deviceid,dt.value,dt.savetime from w_data dt,(select deviceid,max(savetime) savetime from W_data group by deviceid order by deviceid) gr where dt.savetime=gr.savetime and dt.deviceid=gr.deviceid order by dt.deviceid) cc, w_deviceinstance e where cc.deviceid=e.deviceid and cc.deviceid<=? and cc.deviceid>=?) dd,w_location lo where dd.locationid=lo.locationid";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setInt(1, max);
        	pstmt.setInt(2, min);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	           oldData=new OldData(rs.getInt("deviceid"), rs.getString("name"), rs.getString("value"), rs.getString("location"), rs.getString("savetime"));
	           //System.out.println(oldData);
	           list.add(oldData);
	        }  
	        JSONArray jsonArray = JSONArray.fromObject(list);
	        result=jsonArray.toString();
	        System.out.println("getOldDataByRange: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		 
        return result;			
	}
	
	public String getHeartRelationidBydevkey(String devkey) {
		String result="";
		String sql="select relationid from w_relationid where ip=(select parentip from w_heartbeat where devkey=?)";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	          result=rs.getString("relationid");
	        }  
	        
	        System.out.println("getHeartRelationidBydevkey:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	
	public String controlHeartBeat(String devkey,String order) {
		String result="success";
		 try {
		 WebSocketContainer container = ContainerProvider.getWebSocketContainer(); 
		Session session=null;
		 String uri ="ws://10.10.23.10:8080/WebSocketUser/websocket.ws/relationId/1234567"; 
		 System.out.println("Connecting to:"+ uri); 
		 try { 
			  session = container.connectToServer(MyClient.class, URI.create(uri)); 
		 } catch (DeploymentException e) { 
			 e.printStackTrace();
			 result= "failed";
		 } catch (IOException e) { 
		e.printStackTrace();
		result= "failed";
		}
	        //session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=1E;");//开灯
	       // session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=0E;");//关灯
	     //   session.getBasicRemote().sendText("123456_"+"S:N=801002@Find=1E;");//开蜂鸣
		 String relationid=getHeartRelationidBydevkey(devkey);
			session.getBasicRemote().sendText(relationid+"_"+order+";");//关蜂鸣
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="failed";
		}//关蜂鸣器
		return result;
	}
	
	/**
	 * 根据Devkey的值查找最近的设备数据
	 * @param devkey
	 * @return
	 */
	public String getLastFreAndPowerBydevkey(String devkey) {
		String result="";
		FPT fpt=null;
		List<FPT> list=new ArrayList<>();
		String sql="select frequency,dbm,savetime from w_frequency where savetime=(select max(savetime) from w_frequency where devkey=?) and devkey=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, devkey);
        	pstmt.setString(2, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	           fpt=new FPT(rs.getString("frequency"), rs.getString("dbm"), rs.getTimestamp("savetime").toString());
	           //System.out.println(oldData);
	           list.add(fpt);
	        }  
	        JSONArray jsonArray = JSONArray.fromObject(list);
	        result=jsonArray.toString();
	        System.out.println("getLastFreAndPowerBydevkey: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		 
        return result;			
	}
	
	/**
	 * 根据时间查找指定频率段内的值
	 * @param timestamp
	 * @param devkey
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getFreAndPowerBydevkeyAndTime(String timestamp ,String devkey,String begin,String end) {
		String result="";
		FPT fpt=null;
		List<FPT> list=new ArrayList<>();
		String sql="select frequency,dbm,savetime from w_frequency where savetime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')+1/(24*60) and devkey=? and frequency>=to_number(?) and frequency<=to_number(?)";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, timestamp);
        	pstmt.setString(2, timestamp);
//        	pstmt.setDate(1, date);
//        	pstmt.setDate(2, date);
        	pstmt.setString(3, devkey);
        	pstmt.setString(4, begin);
        	pstmt.setString(5, end);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	           fpt=new FPT(rs.getString("frequency"), rs.getString("dbm"), rs.getTimestamp("savetime").toString());
	           //System.out.println(oldData);
	           list.add(fpt);
	        }  
	        JSONArray jsonArray = JSONArray.fromObject(list);
	        result=jsonArray.toString();
	        System.out.println("getFreAndPowerBydevkeyAndTime: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		 
        return result;			
	}
	
	/**
	 * 根据Devkey和频率查找功率值和时间
	 * @param devkey
	 * @param fre
	 * @return
	 */
	public String getPowerAndTimeBydevkeyAndFre(String devkey,String fre) {
		String result="";
		FPT fpt=null;
		List<FPT> list=new ArrayList<>();
		String sql="select dbm,savetime from w_frequency where frequency=? and devkey=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, fre);
        	pstmt.setString(2, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	           fpt=new FPT(fre, rs.getString("dbm"),rs.getTimestamp("savetime").toString());
	           //System.out.println(oldData);
	           list.add(fpt);
	        }  
	        JSONArray jsonArray = JSONArray.fromObject(list);
	        result=jsonArray.toString();
	        System.out.println("getPowerAndTimeBydevkeyAndFre: "+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		 
        return result;			
	}
	/**
	 * 找到指定设备的保存时间
	 * @return
	 */
	public String selectTimeFromFre(String devkey) {
		String result="";
		String sql="select * from(select savetime from w_frequency where devkey=? group by savetime order by savetime desc) where rownum<11";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);
        	pstmt.setString(1, devkey);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	          result=result+rs.getString("savetime")+"=";
	        }  
	        if (result.length()>1) {
				result=result.substring(0, result.length()-1);
			}
	        System.out.println("selectTimeFromFre:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	
	
	public String getTenTimePowerAndTimeBydevkeyAndFre(String devkey) {
		String time=selectTimeFromFre(devkey);
		if (time==null||"".equals(time)) {
			return "";
		}else {
			String result="";
			FPT fpt=null;
			List<FPT> list=new ArrayList<>();
			String[] strings=time.split("=");
			for(int i=0;i<strings.length;i++){
				System.out.println(strings[i]);
				String sql="select frequency,dbm from w_frequency where savetime=?";		
		        try{
		        	conn=ds.getConnection();
		        	pstmt=conn.prepareStatement(sql);  	         
		        	pstmt.setTimestamp(1, Timestamp.valueOf(strings[i]));
		        	rs=pstmt.executeQuery();       
			        while(rs.next())
			        {
			           fpt=new FPT(rs.getString("frequency"), rs.getString("dbm"),strings[i]);
			           //System.out.println(oldData);
			           list.add(fpt);
			        }  
		        }catch(Exception e){
		        	e.printStackTrace();
		        }finally{
		        	try{
			        	if(rs!=null)
			        		rs.close();
			        	if(pstmt!=null)
			        		pstmt.close();
			        	if(conn!=null)
			        		conn.close();
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		        }
			}
			 JSONArray jsonArray = JSONArray.fromObject(list);
		      result=jsonArray.toString();
		     System.out.println("getTenTimePowerAndTimeBydevkeyAndFre: "+result);
				return result;	
		}  	
	}
	
	/**
	 * 配置频谱仪的上传周期
	 * @param order
	 * @return
	 */
	public String controlFrequencyPeriod(String order) {
		String result="success";
		 try {
		 WebSocketContainer container = ContainerProvider.getWebSocketContainer(); 
		Session session=null;
		 String uri ="ws://10.10.23.10:8080//FrequencyWebSocketServer/websocket.ws/relationId/1234567"; 
		 System.out.println("Connecting to:"+ uri); 
		 try { 
			  session = container.connectToServer(MyClient.class, URI.create(uri)); 
		 } catch (DeploymentException e) { 
			 e.printStackTrace();
			 result= "failed";
		 } catch (IOException e) { 
		e.printStackTrace();
		result= "failed";
		}
			session.getBasicRemote().sendText(order);//关蜂鸣
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="failed";
		}//关蜂鸣器
		return result;
	}
	/**
	 *  获取所有的插座信息
	 * @return
	 */
	public String SelectAdapterRealstate() {
		String result="";
		Adapter adapter;
		List<Adapter> adapters = new ArrayList<>();
		String sql="select * from w_adapter_realtime";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	adapter = new Adapter(rs.getInt("id"), rs.getString("relationid"), rs.getString("openstate"), rs.getString("savetime"));
				adapters.add(adapter);
	        }  
	        JSONArray jsonArray = JSONArray.fromObject(adapters);
			result = jsonArray.toString();
	        System.out.println("SelectAdapterRealstate:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	//select * from(select * from w_adapter_history t where  relationid='74'order by savetime desc)where rownum <=20 
	/**
	 *  获取指定插座的历史信息
	 * @return
	 */
	public String getAdapterHistoryByReltionid(String relationid) {
		String result="";
		Adapter adapter;
		List<Adapter> adapters = new ArrayList<>();
		String sql="select * from(select * from w_adapter_history t where  relationid=? order by savetime desc)where rownum <=20 ";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);
        	pstmt.setString(1, relationid);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	adapter = new Adapter(rs.getInt("id"), rs.getString("relationid"), rs.getString("openstate"), rs.getString("savetime"));
				adapters.add(adapter);
	        }  
	        JSONArray jsonArray = JSONArray.fromObject(adapters);
			result = jsonArray.toString();
	        System.out.println("getAdapterHistoryByReltionid:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;			
	}
	/**
	 * 获取所有的车位锁信息
	 * @return
	 */
	public String parkingInfo() {
		String  result="";
		Parking parking;
		List<Parking> parkings = new ArrayList<>();
		String sql = "select * from w_parking_resv_data t order by dataid";
		try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	        	parking= new Parking(rs.getInt("dataid"), rs.getInt("deviceid"), rs.getString("value"), rs.getString("savetime"));
				parkings.add(parking);
	        }  
	        JSONArray jsonArray = JSONArray.fromObject(parkings);
			result = jsonArray.toString();
	        System.out.println("parkingInfo:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
		return result;		
	}
	/**
	 * 获取所有传感器的值
	 * @param devkey
	 * @param typeid
	 * @return
	 */
	public String SensorValueGet(String devkey,String typeid) {
		String value = "";
		String sql = "select avg(value) from(select value from w_devdata t where devkey=? and typeid=? order by savetime desc)where rownum<6";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, devkey);
			pstmt.setString(2, typeid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				 value = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	/**
	 * 反向控制传感器
	 * @param userValueStart
	 * @param userValueEnd
	 * @param t
	 * @param d1
	 * @param d2
	 * @return
	 */
	public String SensorControl(String userValueStart,String userValueEnd,String t,String d1,String d2){
		//3c65ba4a411b4d6197ee46fd3e8d045d
		//9c0b27c9e54b4db5aea08d889e38c346
		double value1=Double .parseDouble(SensorValueGet(d1, t));
		double value2=Double .parseDouble(SensorValueGet(d2, t));
		double value=(value1+value2)/2;
		String openOrder ="S:N=801003#NCTR@Find=1E";
		String closeOrder="S:N=801003#NCTR@Find=0E";
		String result="success";

		if (Integer.parseInt(userValueEnd)<value) {
			controlHeartBeat("7e4e5ff11f014041a8975399ad08c02b", openOrder);
			return result;
		}
		if (Integer.parseInt(userValueStart)>value) {
			controlHeartBeat("7e4e5ff11f014041a8975399ad08c02b", closeOrder);
			return result;
		}
		if (Integer.parseInt(userValueEnd)<value&&Integer.parseInt(userValueStart)>value) {
			result="Room "+t+"  is within your range";
		}
		return result;
	
	}
}
