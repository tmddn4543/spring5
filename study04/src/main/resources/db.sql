create database test_board;

create table board (
  seq int auto_increment,
  title varchar(100),
  contents text,
  regdate varchar(19),
  primary key(seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table notice (
  seq int auto_increment,
  title varchar(100),
  contents text,
  onoff char(1) default 'Y',
  regdate varchar(19),
  primary key(seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
