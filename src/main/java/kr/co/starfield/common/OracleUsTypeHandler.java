package kr.co.starfield.common;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class OracleUsTypeHandler implements TypeHandler<String> {
	
	@Override
	public String getResult(ResultSet rs, String columnName) throws SQLException {
		String resultStr = null;
		try {
			resultStr = rs.getString(columnName);
			
			if(resultStr != null){
				resultStr = new String(resultStr.getBytes("8859_1"), "KSC5601");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultStr;
	}

	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		String resultStr = null;
		try {
			resultStr = rs.getString(columnIndex);
			
			if(resultStr != null){
				resultStr = new String(resultStr.getBytes("8859_1"), "KSC5601");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultStr;
	}

	@Override
	public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String resultStr = null;
		try {
			resultStr = cs.getString(columnIndex);
			if(resultStr != null) {
				resultStr = new String(resultStr.getBytes("8859_1"), "KSC5601");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultStr;
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		try {
			if(parameter != null){
				ps.setString(i, new String(parameter.getBytes("KSC5601") ,"8859_1"));
			}else{
				ps.setString(i, parameter);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
