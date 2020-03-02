package com.master.mapper;

import com.master.SuperMapper;
import com.master.entity.BrokerMessageLog;
import com.master.entity.TaskCore;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *
 *  BrokerMessageLogMapper 接口
 *
 * @author linqiyuan
 * @since 2020-02-27
 */
public interface BrokerMessageLogMapper extends SuperMapper<BrokerMessageLog> {

    /**
     * 重新发送统计count发送次数 +1
     * @param id
     * @param updateTime
     */
    void updateTryCount(@Param("id") String id, @Param("updateTime") Date updateTime);

}
