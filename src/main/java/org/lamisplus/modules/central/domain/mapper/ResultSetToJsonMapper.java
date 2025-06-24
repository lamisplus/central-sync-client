package org.lamisplus.modules.central.domain.mapper;

// convenient JDBC result set to JSON array mapper

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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

        while(resultSet.next())
        {
            jsonObject = mapData(resultSet, excludedColumn, rsmd, columnCount);
            jArray.put(jsonObject);
        }

        return jArray;
    }

    private static JSONObject mapData(ResultSet resultSet, String excludedColumn, ResultSetMetaData rsmd, int columnCount) throws SQLException {
        JSONObject jsonObject = new JSONObject();
        for (int index = 1; index <= columnCount; index++) {
            String column = rsmd.getColumnName(index);
            //mask excluded columns
            if (excludedColumn != null && excludedColumn.contains(column)) {
                jsonObject.put(column, "");
                continue;
            }

            Object value = resultSet.getObject(column);
            getMappedJson(rsmd, jsonObject, index, column, value);
        }
        return jsonObject;
    }

    private static void getMappedJson(ResultSetMetaData rsmd, JSONObject jsonObject, int index, String column, Object value) throws SQLException {
        if (value == null) {
            jsonObject.put(column, "");
        } else if (value instanceof Integer) {
            jsonObject.put(column, value);
        } else if (value instanceof String) {
            jsonObject.put(column, value);
        } else if (value instanceof Boolean) {
            jsonObject.put(column, value);
        } else if (value instanceof Date) {
            jsonObject.put(column, value.toString());
        } else if (value instanceof Long) {
            jsonObject.put(column, value);
        } else if (value instanceof Double) {
            jsonObject.put(column, value);
        } else if (value instanceof Float) {
            jsonObject.put(column, value);
        } else if (value instanceof BigDecimal) {
            jsonObject.put(column, value);
        } else if (value instanceof Byte) {
            jsonObject.put(column, value);
        } else if (value instanceof byte[]) {
            jsonObject.put(column, value);
        } else if (rsmd.getColumnType(index) == 1111) {
            jsonObject.put(column, value);
        } else {
            throw new IllegalArgumentException("Unmappable object type: " + value.getClass());
        }
    }

    public static List<List> getPages(List list, Integer pageSize) {
        if (list == null || list.isEmpty()) return Collections.emptyList();
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
        int numPages = (int) Math.ceil((double)list.size() / (double)pageSize);
        List<List> pages = new ArrayList<>(numPages);
        for (int pageNum = 0; pageNum < numPages;)
            pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        return pages;
    }
}