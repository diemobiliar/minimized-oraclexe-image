#!/bin/bash

echo -e "sga_target=${SGA_TARGET}" >> ${ORACLE_HOME}/dbs/initXE.ora

${ORACLE_HOME}/bin/lsnrctl start

echo create spfile from pfile\; | ${ORACLE_HOME}/bin/sqlplus / as sysdba
echo startup | ${ORACLE_HOME}/bin/sqlplus / as sysdba
${ORACLE_HOME}/bin/lsnrctl status

# Auto generate ORACLE PWD if not passed on
export ORACLE_PWD=${ORACLE_PWD:-"oracle"}
echo "ORACLE PASSWORD FOR SYS AND SYSTEM: $ORACLE_PWD";

echo "ALTER USER SYS IDENTIFIED BY \"$ORACLE_PWD\";" | ${ORACLE_HOME}/bin/sqlplus / as sysdba
echo "ALTER USER SYSTEM IDENTIFIED BY \"$ORACLE_PWD\";" | ${ORACLE_HOME}/bin/sqlplus / as sysdba

tail -f $ORACLE_BASE/diag/rdbms/*/*/trace/alert*.log
