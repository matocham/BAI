create user matocham IDENTIFIED BY matocham;
GRANT create session TO matocham;
GRANT create table TO matocham;
GRANT create view TO matocham;
GRANT create any trigger TO matocham;
GRANT create any procedure TO matocham;
GRANT create sequence TO matocham;
GRANT create synonym TO matocham;

alter user matocham quota 50m on system;