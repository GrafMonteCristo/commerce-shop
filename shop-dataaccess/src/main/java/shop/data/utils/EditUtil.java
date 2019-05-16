/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.data.utils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

/**
 * @author user
 */
public class EditUtil {
    public static void setString(PreparedStatement stm, int num, String value) throws SQLException {
        if (null == value || value.isEmpty()) {
            stm.setNull(num, Types.VARCHAR);
        } else {
            stm.setString(num, value);
        }
    }

    public static void setBoolean(PreparedStatement stm, int num, Boolean value) throws SQLException {
        if (null == value) {
            stm.setNull(num, Types.BOOLEAN);
        } else {
            stm.setBoolean(num, value);
        }
    }

    public static void setLong(PreparedStatement stm, int num, Long value) throws SQLException {
        if (null == value) {
            stm.setNull(num, Types.BIGINT);
        } else {
            stm.setLong(num, value);
        }
    }

    public static void setInt(PreparedStatement stm, int num, Integer value) throws SQLException {
        if (null == value) {
            stm.setNull(num, Types.INTEGER);
        } else {
            stm.setInt(num, value);
        }
    }

    public static void setFloat(PreparedStatement stm, int num, Float value) throws SQLException {
        if (null == value) {
            stm.setNull(num, Types.REAL);
        } else {
            stm.setFloat(num, value);
        }
    }

    public static void setBigDecimal(PreparedStatement stm, int num, Double value) throws SQLException {
        if (null == value) {
            stm.setNull(num, Types.NUMERIC);
        } else {
            stm.setBigDecimal(num, BigDecimal.valueOf(value));
        }
    }

    public static void setDate(PreparedStatement stm, int num, Date value) throws SQLException {
        if (null == value) {
            stm.setNull(num, Types.DATE);
        } else {
            stm.setDate(num, new java.sql.Date(value.getTime()));
        }
    }

    public static void setTimestamp(PreparedStatement stm, int num, Date value) throws SQLException {
        if (null == value) {
            stm.setNull(num, Types.TIMESTAMP);
        } else {
            stm.setTimestamp(num, new Timestamp(value.getTime()));
        }
    }
}
