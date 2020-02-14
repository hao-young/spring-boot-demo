
DROP TABLE IF EXISTS schedule_job;
CREATE TABLE schedule_job (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  job_name varchar(255) DEFAULT NULL COMMENT '任务名称',
  job_group varchar(255) DEFAULT NULL COMMENT '任务分组',
  cron varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  bean_name varchar(255) DEFAULT NULL COMMENT '服务名称',
  method_name varchar(255) DEFAULT NULL COMMENT '方法名称',
  status varchar(6) NOT NULL DEFAULT 'START' COMMENT '状态 START:启动, PAUSE:暂停',
  created_time datetime(0) DEFAULT NULL COMMENT '创建时间',
  updated_time datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
INSERT INTO schedule_job VALUES ('1',  'firstJob', 'helloworld', '0/1 * * * * ?', 'task1', 'execute', 'START', null, null);
commit;
