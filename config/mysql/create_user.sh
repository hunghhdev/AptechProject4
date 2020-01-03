#!/bin/bash

# Functions
ok() { echo -e '\e[32m'$1'\e[m'; } # Green

EXPECTED_ARGS=3
E_BADARGS=65
MYSQL=`which mysql`
 
Q1="CREATE DATABASE IF NOT EXISTS $1;"
Q2="CREATE USER '$2'@'%' IDENTIFIED BY '$3';"
Q3="GRANT ALL ON $1.* TO '$2'@'%';"
Q4="FLUSH PRIVILEGES;"
SQL="${Q1}${Q2}${Q3}${Q4}"

if [ $# -ne $EXPECTED_ARGS ]
then
  echo "Usage: $0 dbname dbuser dbpass"
  exit $E_BADARGS
fi
 
$MYSQL -h172.17.0.2 -uroot -p12345678 -e "$SQL"

ok "Database $1 and user $2 created with a password $3"
