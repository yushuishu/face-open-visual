package com.shuishu.face.openvisual.server.mapper.dynamic;


import com.shuishu.face.openvisual.utils.TableUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Author ：谁书-ss
 * @Date ：2024-08-28 17:40
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 * <p></p>
 */
public class DynamicSampleDataProvider {
    public String findSamplePage(@Param("table") String table) {
        // 验证输入参数，防止SQL注入
        if (!TableUtils.isValidTableName(table)) {
            throw new IllegalArgumentException("无效的表名参数");
        }

        // 构建SQL语句，不直接拼接分页参数，分页参数由MyBatis-Plus自动添加
        return new SQL() {{
            SELECT("*");
            FROM(table);
            ORDER_BY("id");
        }}.toString();
    }
}
