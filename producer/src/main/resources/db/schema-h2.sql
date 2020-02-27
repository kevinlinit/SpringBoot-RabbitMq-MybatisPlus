--DROP TABLE IF EXISTS task_core;

-- 通用表 task_core 通用的业务表结构，用json作为一个业务对象存在表字段
CREATE TABLE `task_core` (
  `id` varchar(128) NOT NULL, -- taskID
  `contents` varchar(4000), -- 业务内容
  `message_id` varchar(128) NOT NULL, -- 消息唯一ID
  `schedule_time` timestamp, -- 定时任务执行时间
  `create_time` timestamp, -- 创建时间
  `update_time` timestamp, -- 更新时间
  PRIMARY KEY (`id`)
);

--DROP TABLE IF EXISTS broker_message_log;
-- 表 broker_message_log 消息记录结构
CREATE TABLE `broker_message_log` (
  `id` varchar(128) NOT NULL, -- 消息唯一ID
  `message` varchar(4000) DEFAULT NULL, -- 消息内容
  `try_count` int(4) DEFAULT '0', -- 重试次数
  `status` varchar(10) DEFAULT '', -- 消息投递状态  0 投递中 1 投递成功   2 投递失败
  `next_retry` timestamp ,  -- 下一次重试时间 或 超时时间
  `create_time` timestamp , -- 创建时间
  `update_time` timestamp , -- 更新时间
  PRIMARY KEY (`id`)
);