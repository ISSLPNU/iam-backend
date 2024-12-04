create database iam_isslpnu_db;
\connect iam_isslpnu_db;

create schema liquibase;
create role su_role;
grant connect on database iam_isslpnu_db to su_role;
grant all on schema liquibase to su_role;

create user liquibase_user nosuperuser nocreatedb nocreaterole login encrypted password '1234';
grant su_role to liquibase_user;