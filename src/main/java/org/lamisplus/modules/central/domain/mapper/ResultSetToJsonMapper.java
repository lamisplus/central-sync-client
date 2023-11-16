package org.lamisplus.modules.central.domain.mapper;

// convenient JDBC result set to JSON array mapper

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
public class ResultSetToJsonMapper {


    /**
     * maps ResultSet for database to Json Array.
     * @param resultSet
     * @param excludedColumn
     * @return JSONArray
     */
    public static JSONArray mapResultSet(ResultSet resultSet, String excludedColumn) throws SQLException, JSONException {
        JSONArray jArray = new JSONArray();
        JSONObject jsonObject = null;
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();
        do
        {
            jsonObject = new JSONObject();
            if(resultSet.next()) {
            for (int index = 1; index <= columnCount; index++)
            {
                String column = rsmd.getColumnName(index);
                //exclude column
                if(excludedColumn != null && excludedColumn.contains(column)){
                    continue;
                }

                Object value = resultSet.getObject(column);
                    if (value == null) {
                        jsonObject.put(column, "");
                    } else if (value instanceof Integer) {
                        jsonObject.put(column, (Integer) value);
                    } else if (value instanceof String) {
                        jsonObject.put(column, (String) value);
                    } else if (value instanceof Boolean) {
                        jsonObject.put(column, (Boolean) value);
                    } else if (value instanceof Date) {
                        jsonObject.put(column, value.toString());
                    } else if (value instanceof Long) {
                        jsonObject.put(column, (Long) value);
                    } else if (value instanceof Double) {
                        jsonObject.put(column, (Double) value);
                    } else if (value instanceof Float) {
                        jsonObject.put(column, (Float) value);
                    } else if (value instanceof BigDecimal) {
                        jsonObject.put(column, (BigDecimal) value);
                    } else if (value instanceof Byte) {
                        jsonObject.put(column, (Byte) value);
                    } else if (value instanceof byte[]) {
                        jsonObject.put(column, (byte[]) value);
                    } else if (rsmd.getColumnType(index) == 1111) {
                        jsonObject.put(column, value);
                    } else {
                        throw new IllegalArgumentException("Unmappable object type: " + value.getClass());
                    }
                }
            }
            jArray.put(jsonObject);
        }while(resultSet.next());
        return jArray;
    }
}