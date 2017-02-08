package kr.co.starfield.common;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

public abstract class CodeEnumTypeHandler <E extends Enum <E>> implements TypeHandler <ICommonCode> {
	 
    private Class <E> type;
 
    public CodeEnumTypeHandler(Class <E> type) {
        this.type = type;
    }
 
    @Override
    public void setParameter(PreparedStatement ps, int i, ICommonCode parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCodeCd());
    }
 
    @Override
    public ICommonCode getResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return getCodeEnum(code);
    }
 
    @Override
    public ICommonCode getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return getCodeEnum(code);
    }
 
    @Override
    public ICommonCode getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return getCodeEnum(code);
    }
 
    private ICommonCode getCodeEnum(String code) {
        try {
        	ICommonCode[] enumConstants = (ICommonCode[]) type.getEnumConstants();
            for (ICommonCode codeNum: enumConstants) {          
                if (codeNum.getCodeCd().equals(code)) {
                    return codeNum;
                }
            }
            return null;
        } catch (Exception e) {
            throw new TypeException("Can't make enum object '" + type + "'", e);
        }
    }
}
