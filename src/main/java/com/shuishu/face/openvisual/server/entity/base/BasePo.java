package com.shuishu.face.openvisual.server.entity.base;


import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author ：谁书-ss
 * @Date   ： 2024-08-27 16:44
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public class BasePo implements Serializable {

    @TableId(value = "create_date")
    @Comment("创建时间")
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @TableId(value = "create_user_id")
    @Comment("创建用户id")
    @Schema(description = "创建用户id")
    private Long createUserId;

    @TableId(value = "update_date")
    @Comment("更新时间")
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @TableId(value = "update_user_id")
    @Comment("更新用户id")
    @Schema(description = "更新用户id")
    private Long updateUserId;


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }


}
