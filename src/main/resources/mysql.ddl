create table `sharding-jdbc`.tb_user_4
(
    id   bigint unsigned auto_increment comment '主键'
        primary key,
    name varchar(255) null comment '姓名',
    sex  varchar(255) null comment '性别'
)
    charset = utf8
    row_format = DYNAMIC;

