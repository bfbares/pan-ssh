****
**** Create databases:
****

mysqladmin -u root create panssh
mysqladmin -u root create pansshtest

****
**** Create database user panssh with password panssh
****

mysql -u root 
	GRANT ALL PRIVILEGES ON panssh.* to panssh@localhost IDENTIFIED BY 'panssh';
	GRANT ALL PRIVILEGES ON pansshtest.* to panssh@localhost IDENTIFIED BY 'panssh';
exit;

****
**** Create tables
****

mysql -u panssh --password=panssh panssh
mysql -u panssh --password=panssh pansshtest

\.MySQLCreateTables.sql

exit;