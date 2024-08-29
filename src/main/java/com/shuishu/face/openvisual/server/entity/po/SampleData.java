package com.shuishu.face.openvisual.server.entity.po;


import com.baomidou.mybatisplus.annotation.TableName;
import com.shuishu.face.openvisual.server.model.ColumnValue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.List;


/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-28 16:07
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
//@TableName("sample_data")
@Comment(value = "样本")
public class SampleData {
    /**
     * 自增主键自增
     */
    private Long id;

    /**
     * 样本ID
     */
    private String sampleId;

    /**
     * 数据信息
     */
    private List<ColumnValue> columnValues;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
