-- Oracle 数据库建表语句备份
drop table if exists config_quartz_job;
drop table if exists data_subscribe_log;
drop table if exists data_publish_log;
drop table if exists CONFIG_DICT;
drop table if exists CONFIG_DATA;

delete from config_quartz_job;
delete from data_subscribe_log;
delete from data_publish_log;
delete from CONFIG_DICT;
delete from CONFIG_DATA;


-- 定时任务配置表
CREATE TABLE config_quartz_job (
  id varchar(32) NOT NULL COMMENT '主键Id',
  job_name varchar(200) DEFAULT NULL COMMENT '任务名称',
  job_group varchar(200) DEFAULT NULL COMMENT '任务分组',
  job_status varchar(40) DEFAULT NULL COMMENT '任务状态',
  cron_expression varchar(40) DEFAULT NULL COMMENT '时间表达式',
  target_object varchar(200) DEFAULT NULL COMMENT '目标执行类',
  target_method varchar(200) DEFAULT NULL COMMENT '目标方法',
  description varchar(200) DEFAULT NULL COMMENT '描述',
  bean_class varchar(400) DEFAULT NULL COMMENT '类名称',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  create_by varchar(32) DEFAULT NULL COMMENT '创建人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  update_by varchar(32) DEFAULT NULL COMMENT '更新人',
  del_flag varchar(2) DEFAULT '0' COMMENT '删除标记(0:正常、1:删除)',
  concurrent varchar(2) DEFAULT NULL COMMENT '是否是并行任务',
  PRIMARY KEY (id)
) ;
 
-- 数据订阅日志信息表
CREATE TABLE data_subscribe_log (
  id varchar(32) NOT NULL COMMENT '主键Id',
  obj_id varchar(32) NOT NULL COMMENT '对象Id',
  obj_name varchar(200) NOT NULL COMMENT '对象名称',
  service_code varchar(32) DEFAULT NULL COMMENT '服务码',
  data_json varchar(2000) DEFAULT NULL COMMENT '数据Json',
  data varchar(2) DEFAULT NULL COMMENT '数据类型(0：机构、1:用户、2:班级、3、账号、4、用户班级)',
  operator_type varchar(2) DEFAULT NULL COMMENT '操作类型(1:新增、2:修改、3:删除)',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  create_by varchar(32) DEFAULT NULL COMMENT '创建人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  update_by varchar(32) DEFAULT NULL COMMENT '更新人',
  data_status varchar(2) DEFAULT '0' COMMENT '状态(0:成功、1:失败)',
  PRIMARY KEY (id)
) ;


-- 数据发布日志信息表
CREATE TABLE data_publish_log (
  id varchar(32) NOT NULL COMMENT '主键Id',
  obj_id varchar(32) NOT NULL COMMENT '对象Id',
  obj_name varchar(200) NOT NULL COMMENT '对象名称',
  service_code varchar(32) DEFAULT NULL COMMENT '服务码',
  data_json varchar(2000) DEFAULT NULL COMMENT '数据Json',
  data varchar(2) DEFAULT NULL COMMENT '数据类型(0：机构、1:用户、2:班级、3、账号、4、用户班级)',
  operator_type varchar(2) DEFAULT NULL COMMENT '操作类型(1:新增、2:修改、3:删除)',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  create_by varchar(32) DEFAULT NULL COMMENT '创建人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  update_by varchar(32) DEFAULT NULL COMMENT '更新人',
  data_status varchar(2) DEFAULT '0' COMMENT '状态(0:成功、1:失败)',
  error_msg varchar(2000) DEFAULT NULL COMMENT '错误消息'，
  PRIMARY KEY (id)
) ;

-- 字典配置表--数据配置项
create table CONFIG_DICT
(
  id          varchar(32) NOT NULL COMMENT '主键Id',
  name        varchar(100) COMMENT '名称',
  d_key         varchar(100) COMMENT '键',
  d_value       varchar(100) COMMENT '值',
  type        varchar(100) COMMENT '类型',
  create_time datetime COMMENT '创建时间',
  create_by   varchar(32) COMMENT '创建人',
  update_time datetime COMMENT '更新时间',
  update_by   varchar(32) COMMENT '更新人',
  del_flag    varchar(2) COMMENT '删除标记(0:正常、1:删除)',
  PRIMARY KEY (id)
);

-- 数据项配置说明
create table CONFIG_DATA
(
  id          varchar(32) NOT NULL COMMENT '主键Id',
  name        varchar(100) COMMENT '数据名称',
  code        varchar(100) COMMENT '数据编码',
  data_type   varchar(2) COMMENT '数据类型',
  create_time datetime COMMENT '创建时间',
  create_by   varchar(32) COMMENT '创建人',
  update_time datetime COMMENT '更新时间',
  update_by   varchar(32) COMMENT '更新人',
  job_status varchar(2) COMMENT '任务状态',
    PRIMARY KEY (id)
);


/**
 * ================
 *   备份数据字典项
 * ================
 */
insert into config_data (ID, NAME, CODE, DATA_TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, JOB_STATUS)
values ('12f4c48e8827442eb9ea67badfba0893', '账号发布数据', 'account_publish', '0', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_data (ID, NAME, CODE, DATA_TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, JOB_STATUS)
values ('bdf7b91da7cf4999adacaa489a274501', '班级发布数据', 'class_publish', '0', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_data (ID, NAME, CODE, DATA_TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, JOB_STATUS)
values ('61865d9522fa4966b7dacddce01eb46e', '用户发布数据', 'user_publish', '0', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_data (ID, NAME, CODE, DATA_TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, JOB_STATUS)
values ('31cf8d087dcd4f5484a33cc78fc8b2ab', '机构发布数据', 'orga_publish', '0', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('ffaf2fbd9fd54b019625071d33ca7e8b', '正在运行', 'jobStatus_run', '0', 'jobStatus', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('6a39e6b1fd954bfa9d9ce9c8ad4f9fbb', '正常', 'delFlag_normal', '0', 'delFlag', '2017-01-09 14:20:00', null, null, null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('8517382b6b684f9b9c2b77f991c591ca', '删除', 'delFlag_delete', '1', 'delFlag', '2017-01-09 14:20:00', null, null, null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('21400a06db1a4768aa84116b4a2f6f04', '停止运行', 'jobStatus_stop', '1', 'jobStatus', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('976e3497ba4a4b4a8b3b438640e53dd2', '账号数据发布服务码', 'serviceCode_account_p', '99f99317a3704153a010492d2fc9555f', 'serviceCode', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('f62d38e66471476f9e3503eeff27a9df', '用户数据发布服务码', 'serviceCode_user_p', 'eab12ee20cd642c691ec8324d829fd20', 'serviceCode', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('a288ecf3c6d84677bca822126ba3bc26', '账号数据订阅服务码', 'serviceCode_account_d', '409b810a5fdf46cab0f13ceb6ac49a00', 'serviceCode', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('983a3757d78b4f8e86dbf18ea5e8305b', '用户数据订阅服务码', 'serviceCode_user_d', '5320efd9766b4890938ce9ae76fbf9f9', 'serviceCode', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('1b634ed3db7b4fba9239cdccd9e0c311', '数据交换-发布url', 'url_publish', 'http://211.153.23.1:8448/dataCenterApi/api/publish', 'url','2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('560af14c68824de7ad56a289da34a225', '数据交换-订阅url', 'url_subscribe', 'http://211.153.23.1:8448/dataCenterApi/api/subscription', 'url', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('eed61bf69c184a1c8776b04cf8ea8201', '机构数据', 'data_tbOrga', '0', 'data', '2017-01-09 14:20:00', null, null, null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('d520c687ca944c6a97ca47839cf29871', '用户数据', 'data_tbUser', '1', 'data', '2017-01-09 14:20:00', null, null, null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('353ffe81812f461a929aff361736ad4d', '用户机构数据', 'data_tbUserOrga', '2', 'data', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('a4df291061f74d3eb297719775a48781', '用户身份数据', 'data_tbUserIdentity', '3', 'data', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('95effcd9c628484a9866599880c054c9', '新增', 'operatorType_new', '1', 'operatorType', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('4e825def56f14cfaad11c3d3f44d292b', '修改', 'operatorType_update', '2', 'operatorType', '2017-01-09 14:20:00', null, null, null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('700c9cba8f434aca8870eb944e2073db', '删除', 'operatorType_delete', '3', 'operatorType', '2017-01-09 14:20:00', null, null, null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('23b99c00ad9e4945873f55cd42c419fe', '成功', 'dataStatus_success', '0', 'dataStatus','2017-01-09 14:20:00', null, null, null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('9ff60e35694040739296aa4564d0c9bc', '失败', 'dataStatus_error', '1', 'dataStatus', '2017-01-09 14:20:00', null, null, null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('38e4b3cb48c74abb869ffc32b5d35eb3', '数据发布', 'data_publish', '0', 'dataType', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('7c9d5a2d327b4213b42bf74ad18006ed', '数据订阅', 'data_subscibe', '1', 'dataType', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('99a3688682b04489a8eed998dc818724', '开放平台appid', 'opengate_appid', '84FC09670AFFDDBE030C4BABDA7BB897', 'opengate', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('7f912f237d934b3e9ed4f25ea067b94a', '开放平台token的url', 'url_token', 'http://opengate.d.huijiaoyun.com/apigateway/getAccessToken', 'url', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('1d355dd9222844edac3b05c3ca1f2be4', '机构数据发布服务码', 'serviceCode_orga_p', '5197015d728746709bcf2f26e63cafd4', 'serviceCode', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_dict (ID, NAME, d_KEY, d_VALUE, TYPE, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG)
values ('fc5a834ae5cd41b89e39a34e59d762a9', '开放平台appkey', 'opengate_appkey', 'C56F5D260DBE96C7D69B4B5284342496', 'opengate', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0');

insert into config_quartz_job (ID, JOB_NAME, JOB_GROUP, JOB_STATUS, CRON_EXPRESSION, TARGET_OBJECT, TARGET_METHOD, DESCRIPTION, BEAN_CLASS, CREATE_TIME, CREATE_BY, UPDATE_TIME, UPDATE_BY, DEL_FLAG, CONCURRENT)
values ('0220cdc6e80f4830921131394134a38a', 'TokenUtils', 'test', '1', '0/10 * * * * ?', null, 'getAccessToken', '测试token，10秒一次', 'com.whty.dmp.utils.encry.TokenUtils', '2017-01-09 14:20:00', null, '2017-01-09 15:18:21', null, '0', null);
