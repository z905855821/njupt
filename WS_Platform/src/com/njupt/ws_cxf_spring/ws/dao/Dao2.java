package com.njupt.ws_cxf_spring.ws.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.njupt.ws_cxf_spring.ws.bean.FPT;
import com.njupt.ws_cxf_spring.ws.bean.FrequencyDevice;


import net.sf.json.JSONArray;

public class Dao2 {
	Dao dao = new Dao();
	private static final String url = "jdbc:oracle:thin:@10.10.23.10:1521:iotdb";
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
	static {
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
		// 连接的最大空闲时间，如果超过这个时间，某个数据库连接还没有被使用，则会断开掉这个连接,单位秒
		// ds.setMaxIdleTime(10);
		// 配置连接的生存时间，超过这个时间的连接将由连接池自动断开丢弃掉。当然正在使用的连接不会马上断开，而是等待它close再断开。
		// ds.setMaxConnectionAge(50);
	}

	/**
	 * 通过设备key获得心跳设备的relationid
	 * 
	 * @param devkey
	 * @return
	 */
	public String getHeartRelationidBydevkey(String devkey) {
		String result = "";
		String sql = "select relationid from w_relationid where ip=(select parentip from w_heartbeat where devkey=?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, devkey);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("relationid");
			}

			System.out.println("getHeartRelationidBydevkey:" + result);
			return result;
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
		return result;
	}

	/**
	 * 控制心跳设备
	 * 
	 * @param devkey
	 * @param order
	 * @return
	 */
	public String controlHeartBeat(String devkey, String order) {
		String result = "success";
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			Session session = null;
			String uri = "ws://202.119.234.4/WebSocketUser/websocket.ws/relationId/1234567";
			System.out.println("Connecting to:" + uri);
			try {
				session = container.connectToServer(MyClient.class, URI.create(uri));
			} catch (DeploymentException e) {
				e.printStackTrace();
				result = "failed";
			} catch (IOException e) {
				e.printStackTrace();
				result = "failed";
			}
			// session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=1E;");//开灯
			// session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=0E;");//关灯
			// session.getBasicRemote().sendText("123456_"+"S:N=801002@Find=1E;");//开蜂鸣
			String relationid = getHeartRelationidBydevkey(devkey);
			session.getBasicRemote().sendText(relationid + "_" + order + ";");// 关蜂鸣
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "failed";
		} // 关蜂鸣器
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
	/**
	 * 根据Devkey的值查找最近的设备数据
	 * 
	 * @param devkey
	 * @return
	 */
	public String getLastFreAndPowerBydevkey(String devkey) {
		String result = "";
		FPT fpt = null;
		List<FPT> list = new ArrayList<>();
		String sql = "select frequency,dbm,savetime from w_frequency where savetime=(select max(savetime) from w_frequency where devkey=?) and devkey=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, devkey);
			pstmt.setString(2, devkey);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				fpt = new FPT(rs.getString("frequency"), rs.getString("dbm"), rs.getTimestamp("savetime").toString());
				// System.out.println(oldData);
				list.add(fpt);
			}
			JSONArray jsonArray = JSONArray.fromObject(list);
			result = jsonArray.toString();
			System.out.println("getLastFreAndPowerBydevkey: " + result);
			return result;
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

		return result;
	}

	/**
	 * 根据时间查找指定频率段内的值
	 * 
	 * @param timestamp
	 * @param devkey
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getFreAndPowerBydevkeyAndTime(String timestamp, String devkey, String begin, String end) {
		String result = "";
		FPT fpt = null;
		List<FPT> list = new ArrayList<>();
		String sql = "select frequency,dbm,savetime from w_frequency where savetime between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss')+1/(24*60) and devkey=? and frequency>=to_number(?) and frequency<=to_number(?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, timestamp);
			pstmt.setString(2, timestamp);
			// pstmt.setDate(1, date);
			// pstmt.setDate(2, date);
			pstmt.setString(3, devkey);
			pstmt.setString(4, begin);
			pstmt.setString(5, end);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				fpt = new FPT(rs.getString("frequency"), rs.getString("dbm"), rs.getTimestamp("savetime").toString());
				// System.out.println(oldData);
				list.add(fpt);
			}
			JSONArray jsonArray = JSONArray.fromObject(list);
			result = jsonArray.toString();
			System.out.println("getFreAndPowerBydevkeyAndTime: " + result);
			return result;
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

		return result;
	}

	/**
	 * 根据Devkey和频率查找功率值和时间
	 * 
	 * @param devkey
	 * @param fre
	 * @return
	 */
	public String getPowerAndTimeBydevkeyAndFre(String devkey, String fre) {
		String result = "";
		FPT fpt = null;
		List<FPT> list = new ArrayList<>();
		String sql = "select dbm,savetime from w_frequency where frequency=? and devkey=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fre);
			pstmt.setString(2, devkey);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				fpt = new FPT(fre, rs.getString("dbm"), rs.getTimestamp("savetime").toString());
				// System.out.println(oldData);
				list.add(fpt);
			}
			JSONArray jsonArray = JSONArray.fromObject(list);
			result = jsonArray.toString();
			System.out.println("getPowerAndTimeBydevkeyAndFre: " + result);
			return result;
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

		return result;
	}

	/**
	 * 根据设备key选取最近十条频率信息
	 * 
	 * @return
	 */
	public String selectLastTenData(String devkey) {
		String result = "";
		FrequencyDevice frequencyDevice;
		List<FrequencyDevice> list = new ArrayList<>();
		String sql = "select * from(select * from w_frequency where  devkey= ? order by savetime desc) where rownum<11";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, devkey);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String locationjuti = dao.getLocation(rs.getString("location"));
				frequencyDevice = new FrequencyDevice(rs.getInt("id"), rs.getString("frequency"), rs.getString("dbm"),
						rs.getString("typeid"), rs.getString("devkey"), locationjuti, rs.getString("parentip"),
						rs.getString("mac"), rs.getString("type"), rs.getString("savetime"));
				list.add(frequencyDevice);
			}
			JSONArray jsonArray = JSONArray.fromObject(list);
			result = jsonArray.toString();
			System.out.println("selectLastTenData:" + result);
			return result;
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
		return result;
	}
	
	/**
	 * 根据设备key获得800条频率和场强数据
	 * 
	 * @param devkey
	 * @return
	 */
	public String getTenTimePowerAndTimeBydevkeyAndFre(String devkey) {
		String time = selectTimeFromFre(devkey);
		if (time == null || "".equals(time)) {
			return "";
		} else {
			String result = "";
			FPT fpt = null;
			List<FPT> list = new ArrayList<>();
			String[] strings = time.split("=");
			for (int i = 0; i < strings.length; i++) {
				// System.out.println(strings[i]);
				String sql = "select * from(select frequency,dbm from w_frequency where savetime=?)where  rownum<801";
				try {
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setTimestamp(1, Timestamp.valueOf(strings[i]));
					rs = pstmt.executeQuery();
					while (rs.next()) {
						fpt = new FPT(rs.getString("frequency"), rs.getString("dbm"), strings[i]);
						// System.out.println(oldData);
						list.add(fpt);
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
			}
			JSONArray jsonArray = JSONArray.fromObject(list);
			result = jsonArray.toString();
			System.out.println(list.size());
			System.out.println("getTenTimePowerAndTimeBydevkeyAndFre: " + result);
			return result;
		}
	}

	/**
	 * 获得所有频谱设备的信息
	 * 
	 * @param devkey
	 * @return
	 */
	public String getFrequencyDevice() {
		String result = "";
		FrequencyDevice frequencyDevice;
		List<FrequencyDevice> list = new ArrayList<>();
		// select * from (select * from w_frequency t where savetime=(select
		// max(savetime) from w_frequency where devkey=t.devkey))where rownum
		// =1;
		// select * from(select * from w_frequency where devkey=? order by
		// savetime desc) where rownum<2
		String sql = "select * from w_frequency t where savetime=(select max(savetime)from w_frequency where devkey=t.devkey)and id=(select max(id)from w_frequency where devkey=t.devkey)";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			// pstmt.setString(1, devkey);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String locationjuti = dao.getLocation(rs.getString("location"));
				frequencyDevice = new FrequencyDevice(rs.getInt("id"), rs.getString("frequency"), rs.getString("dbm"),
						rs.getString("typeid"), rs.getString("devkey"), locationjuti, rs.getString("parentip"),
						rs.getString("mac"), rs.getString("type"), rs.getString("savetime"));
				list.add(frequencyDevice);
			}
			JSONArray jsonArray = JSONArray.fromObject(list);
			result = jsonArray.toString();
			System.out.println("getFrequencyDevice: " + result);
			return result;
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
		return result;
	}
}
