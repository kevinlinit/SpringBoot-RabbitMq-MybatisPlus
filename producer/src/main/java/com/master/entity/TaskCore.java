package com.master.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author linqiyuan
 * @since 2020-02-27
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    private String contents;

    private String messageId;

    @TableField(value = "schedule_time", strategy = FieldStrategy.IGNORED)
    private LocalDateTime scheduleTime;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
