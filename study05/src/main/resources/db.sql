create database test_board;

create table board (
  seq int auto_increment,
  title varchar(100),
  contents text,
  regdate varchar(19),
  primary key(seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table board add column mod_regdate varchar(19);
alter table board add column written varchar(100);
create index idx_board_title on board(title);

create table notice (
  seq int auto_increment,
  title varchar(100),
  contents text,
  onoff char(1) default 'Y',
  regdate varchar(19),
  primary key(seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table notice add column written varchar(100);



create table member (
  seq int auto_increment,
  id varchar(100),
  pwd varchar(100),
  primary key(seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into member(id, pwd) values('admin', 'admin');
alter table member add column level int default 0;
alter table member change column level level int comment '0: admin, 1: user';
alter table member add column name varchar(100);
