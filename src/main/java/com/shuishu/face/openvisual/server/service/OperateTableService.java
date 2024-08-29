package com.shuishu.face.openvisual.server.service;


import com.shuishu.face.openvisual.server.entity.extend.TableColumn;

import java.util.List;

/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-27 22:57
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public interface OperateTableService {

    /**
     * 判断 表 是否存在
     *
     * @param table 表名
     * @return -
     */
    boolean exist(String table);

    /**
     * 删除表
     *
     * @param table 表名
     * @return -
     */
    boolean dropTable(String table);

    /**
     * 创建图片表
     *
     * @param table 创建图片表
     * @return
     */
    boolean createImageTable(String table);

    /**
     * 创建样本表
     *
     * @param table 样本表 名称
     * @param columns 样本表字段
     * @return -
     */
    boolean createSampleTable(String table, List<TableColumn> columns);

    /**
     * 创建人脸表
     *
     * @param table 人脸表 名称
     * @param columns 人脸表字段
     * @return -
     */
    boolean createFaceTable(String table, List<TableColumn> columns);
}
