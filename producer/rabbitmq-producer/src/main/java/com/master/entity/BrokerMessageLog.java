package com.master.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author linqiyuan
 * @since 2020-02-27
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrokerMessageLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("Id")
    private String id;

    private String message;

    private Integer tryCount;

    private String status;

    private LocalDateTime nextRetry;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public BrokerMessageLog(String id, String status) {
        this.id = id;
        this.status = status;
    }
}
