package com.shuishu.face.openvisual.server.entity.extend;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-27 23:23
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Schema(description = "定义字段类型")
public enum FiledDataType {
    /**
     * Undefined data type
     */
    UNDEFINED(0),
    /**
     * String data type
     */
    STRING(1),
    /**
     * Bool data type
     */
    BOOL(2),
    /**
     * Int32 data type
     */
    INT(3),
    /**
     * Float data type
     */
    FLOAT(4),
    /**
     * Double data type
     */
    DOUBLE(5);

    private int value;

    FiledDataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static FiledDataType valueOf(int value) {
        switch (value) {
            case 1:
                return STRING;
            case 2:
                return BOOL;
            case 3:
                return INT;
            case 4:
                return FLOAT;
            case 5:
                return DOUBLE;
            default:
                return UNDEFINED;
        }
    }

}
