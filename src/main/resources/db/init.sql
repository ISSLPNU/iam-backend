create schema iam_isslpnu;
create role iam_isslpnu_role;

grant connect on database iam_isslpnu_db to iam_isslpnu_role;
grant usage on schema iam_isslpnu to iam_isslpnu_role;
grant all on schema iam_isslpnu to su_role;

create user iam_isslpnu_user nosuperuser nocreatedb nocreaterole login encrypted password '12345';
grant iam_isslpnu_role to iam_isslpnu_user;

grant select, insert, delete, update on all tables in schema iam_isslpnu to iam_isslpnu_role;
alter default privileges in schema iam_isslpnu grant select, insert, update, delete on tables to iam_isslpnu_role;