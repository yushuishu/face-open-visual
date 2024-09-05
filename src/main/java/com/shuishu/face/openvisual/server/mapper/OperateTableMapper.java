package com.shuishu.face.openvisual.server.mapper;

import com.shuishu.face.openvisual.server.entity.extend.TableColumn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface OperateTableMapper {

    @Select({"SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_name='${table}'"})
    String showTable(@Param("table") String table);

    @Update({ "DROP TABLE IF EXISTS ${table}"})
    int dropTable(@Param("table") String table);

    @Update({"<script>", """
CREATE TABLE ${table} (
        id BIGSERIAL PRIMARY KEY,
        sample_id VARCHAR(64) NOT NULL,
        <foreach item="item" index="index" collection="columns" separator=",">
            <choose>
                <when test="item.type == 'string'">
                    ${item.name} varchar(255) DEFAULT NULL
                </when>
                <when test="item.type == 'bool'">
                    ${item.name} bool DEFAULT NULL
                </when>
                <when test="item.type == 'int'">
                    ${item.name} int4 DEFAULT NULL
                </when>
                <when test="item.type == 'long'">
                    ${item.name} int8 DEFAULT NULL
                </when>
                <when test="item.type == 'float'">
                    ${item.name} float4 DEFAULT NULL
                </when>
                <when test="item.type == 'double'">
                    ${item.name} float8 PRECISION DEFAULT NULL
                </when>
            </choose>
        </foreach>
        create_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        modify_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
        );
        COMMENT ON COLUMN ${table}.id IS '主键、自增、无意';
        COMMENT ON COLUMN ${table}.sample_id IS '样本ID';
        CREATE UNIQUE INDEX idx_${table}_sample_id ON ${table} (sample_id);
""" , "</script>"})
    int createSampleTable(@Param("table") String table, @Param("columns") List<TableColumn> columns);

    @Update({"<script>", """
CREATE TABLE ${table} (
        id BIGSERIAL PRIMARY KEY,
        sample_id VARCHAR(64) NOT NULL,
        face_id VARCHAR(64) NOT NULL,
        face_score float4 NOT NULL,
        face_vector TEXT NOT NULL,
        <foreach item="item" index="index" collection="columns" separator=",">
            <choose>
                <when test="item.type == 'string'">
                    ${item.name} varchar(255) DEFAULT NULL
                </when>
                <when test="item.type == 'bool'">
                    ${item.name} bool DEFAULT NULL
                </when>
                <when test="item.type == 'int'">
                    ${item.name} int4 DEFAULT NULL
                </when>
                <when test="item.type == 'long'">
                    ${item.name} int8 DEFAULT NULL
                </when>
                <when test="item.type == 'float'">
                    ${item.name} float4 DEFAULT NULL
                </when>
                <when test="item.type == 'double'">
                    ${item.name} float8 PRECISION DEFAULT NULL
                </when>
            </choose>
        </foreach>
        create_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        modify_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
        );
        CREATE INDEX idx_${table}_sample_id ON ${table} (sample_id);
        CREATE INDEX idx_${table}_face_id ON ${table} (face_id);
        CREATE INDEX idx_${table}_create_time ON ${table} (create_time);
""", "</script>"})
    int createFaceTable(@Param("table") String table, @Param("columns") List<TableColumn> columns);

    @Update({"<script>", """
CREATE TABLE ${table} (
        id BIGSERIAL PRIMARY KEY,
        sample_id VARCHAR(64) NOT NULL,
        face_id VARCHAR(64) NOT NULL,
        storage_type VARCHAR(64) NOT NULL,
        image_raw_info TEXT NOT NULL,
        image_ebd_info TEXT NOT NULL,
        image_face_info TEXT NOT NULL,
        create_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        modify_time TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
        );
        COMMENT ON COLUMN ${table}.id IS '主键、自增、无意';
        COMMENT ON COLUMN ${table}.sample_id IS '样本ID';
        COMMENT ON COLUMN ${table}.face_id IS '人脸ID';
        COMMENT ON COLUMN ${table}.storage_type IS '数据存储类型';
        COMMENT ON COLUMN ${table}.image_raw_info IS '原始图片数据';
        COMMENT ON COLUMN ${table}.image_ebd_info IS '用于提取特征的人脸图片';
        COMMENT ON COLUMN ${table}.image_face_info IS '图片人脸检测信息';
        COMMENT ON COLUMN ${table}.create_time IS '创建时间';
        COMMENT ON COLUMN ${table}.modify_time IS '更新时间';
        CREATE INDEX idx_${table}_sample_id ON ${table} (sample_id);
        CREATE INDEX idx_${table}_face_id ON ${table} (face_id);
        CREATE INDEX idx_${table}_storage_type ON ${table} (storage_type);
        CREATE INDEX idx_${table}_create_time ON ${table} (create_time);
""", "</script>"})
    int createImageTable(@Param("table") String table);


}
