call "close Mysql.bat"
set "mysql_home=C:\mysql-5.6.31-winx64"
%mysql_home%/bin/mysqld  --init-file=%mysql_home%/my-default.ini
::pause 
rem comment
%comment%
:c comment