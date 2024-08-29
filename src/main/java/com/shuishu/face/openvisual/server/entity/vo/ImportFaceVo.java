package com.shuishu.face.openvisual.server.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author : LvShanYi
 * @date : 2024-06-25 18:14:27
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImportFaceVo {

    private Integer code;
    private String barcode;
    private String msg;
}

