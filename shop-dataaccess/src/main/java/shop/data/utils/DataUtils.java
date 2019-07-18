/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.data.utils;

import shop.api.entity.annot.FieldName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Сервисные функции для перелива результата в списки
 *
 * @author user
 */
public class DataUtils {
    /**
     * Прочитать результат в список
     *
     * @param res   - результирующий список
     * @param rs    - открытый результат
     * @param clazz - класс элемента списка
     * @throws java.sql.SQLException
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static void read(Collection res, ResultSet rs, Class clazz) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        //Читаем список полей результата
        Map<String, Integer> fieldsName = new HashMap<>();
        int columnCount = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            fieldsName.put(rs.getMetaData().getColumnName(i).toUpperCase(), i);
        }
        //Формируем список полей класса с аннотациями @FieldName и именем из результата
        Map<String, Field> fields = new HashMap<>();
        Field[] allFields = clazz.getDeclaredFields();
        for (Field field : allFields) {
            FieldName fieldAnn = field.getAnnotation(FieldName.class);
            if (null != fieldAnn && fieldsName.containsKey(fieldAnn.name().toUpperCase())) {
                fields.put(fieldAnn.name().toUpperCase(), field);
            }
        }
        //Получаем get методы
        Map<String, Method> methods = new HashMap<>();
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            String key = entry.getKey();
            Field value = entry.getValue();
            String methodName = "set" + Character.toUpperCase(value.getName().charAt(0)) + value.getName().substring(1);
            Method method = clazz.getMethod(methodName, value.getType());
            methods.put(key, method);
        }

        //Формируем результат
        while (rs.next()) {
            Object row = clazz.newInstance();
            for (Map.Entry<String, Method> entry : methods.entrySet()) {
                String key = entry.getKey();
                Method value = entry.getValue();
                Field fld = fields.get(key);
                if (String.class.getName().equals(fld.getType().getName())) {
                    value.invoke(row, rs.getString(key));
                } else if (Long.class.getName().equals(fld.getType().getName())) {
                    value.invoke(row, rs.getLong(key));
                    if (rs.wasNull()) {
                        value.invoke(row, (Object) null);
                    }
                } else if (Integer.class.getName().equals(fld.getType().getName())) {
                    value.invoke(row, rs.getInt(key));
                    if (rs.wasNull()) {
                        value.invoke(row, (Object) null);
                    }
                } else if (Float.class.getName().equals(fld.getType().getName())) {
                    value.invoke(row, rs.getFloat(key));
                    if (rs.wasNull()) {
                        value.invoke(row, (Object) null);
                    }
                } else if (Double.class.getName().equals(fld.getType().getName())) {
                    value.invoke(row, rs.getDouble(key));
                    if (rs.wasNull()) {
                        value.invoke(row, (Object) null);
                    }
                } else if (Date.class.getName().equals(fld.getType().getName())) {
                    Integer colNum = fieldsName.get(key);
                    int columnType = rs.getMetaData().getColumnType(colNum);
                    if (columnType == Types.DATE) {
                        value.invoke(row, rs.getDate(key));
                    } else {
                        value.invoke(row, rs.getTimestamp(key));
                    }
                } else if (fld.getType().isArray()) {
                    if (byte.class.getName().equals(fld.getType().getComponentType().getName())) {
                        value.invoke(row, rs.getBytes(key));
                    }
                }
            }
            res.add(row);
        }

    }

    /**
     * Прочитать результат в список
     *
     * @param row    - Заполняемый объект
     * @param rs     - открытый результат
     * @param prefix - префикс поля в результате
     * @throws java.sql.SQLException
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public static void readObject(Object row, ResultSet rs, String prefix) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (null == prefix) {
            prefix = "";
        } else {
            prefix = prefix.toUpperCase();
        }
        //Читаем список полей результата
        Map<String, Integer> fieldsName = new HashMap<>();
        int columnCount = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String fieldName = rs.getMetaData().getColumnName(i).toUpperCase();
            if (!prefix.isEmpty()) {
                if (fieldName.startsWith(prefix)) {
                    fieldsName.put(fieldName.substring(prefix.length()), i);
                }
            } else {
                fieldsName.put(rs.getMetaData().getColumnName(i).toUpperCase(), i);
            }

        }
        //Формируем список полей класса с аннотациями @FieldName и именем из результата
        Map<String, Field> fields = new HashMap<>();
        Class clazz = row.getClass();
        Field[] allFields = clazz.getDeclaredFields();
        for (Field field : allFields) {
            FieldName fieldAnn = field.getAnnotation(FieldName.class);
            if (null != fieldAnn && fieldsName.containsKey(fieldAnn.name().toUpperCase())) {
                fields.put(fieldAnn.name().toUpperCase(), field);
            }
        }
        //Получаем get методы
        Map<String, Method> methods = new HashMap<>();
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            String key = entry.getKey();
            Field value = entry.getValue();
            String methodName = "set" + Character.toUpperCase(value.getName().charAt(0)) + value.getName().substring(1);
            Method method = clazz.getMethod(methodName, value.getType());
            methods.put(key, method);
        }

        //Формируем результат
        for (Map.Entry<String, Method> entry : methods.entrySet()) {
            String key = entry.getKey();
            Method value = entry.getValue();
            Field fld = fields.get(key);
            if (String.class.getName().equals(fld.getType().getName())) {
                value.invoke(row, rs.getString(fieldsName.get(key)));
            } else if (Long.class.getName().equals(fld.getType().getName())) {
                value.invoke(row, rs.getLong(fieldsName.get(key)));
                if (rs.wasNull()) {
                    value.invoke(row, (Object) null);
                }
            } else if (Integer.class.getName().equals(fld.getType().getName())) {
                value.invoke(row, rs.getInt(fieldsName.get(key)));
                if (rs.wasNull()) {
                    value.invoke(row, (Object) null);
                }
            } else if (Float.class.getName().equals(fld.getType().getName())) {
                value.invoke(row, rs.getFloat(fieldsName.get(key)));
                if (rs.wasNull()) {
                    value.invoke(row, (Object) null);
                }
            } else if (Double.class.getName().equals(fld.getType().getName())) {
                value.invoke(row, rs.getDouble(fieldsName.get(key)));
                if (rs.wasNull()) {
                    value.invoke(row, (Object) null);
                }
            } else if (Date.class.getName().equals(fld.getType().getName())) {
                Integer colNum = fieldsName.get(key);
                int columnType = rs.getMetaData().getColumnType(colNum);
                if (columnType == Types.DATE) {
                    value.invoke(row, rs.getDate(fieldsName.get(key)));
                } else {
                    value.invoke(row, rs.getTimestamp(fieldsName.get(key)));
                }
            }
        }
    }


}
