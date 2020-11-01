#!/bin/bash

	CONNECTION_AVAILABLE=false

	echo "Trying to connect to XE 18C Database with sqlplus."
        $ORACLE_HOME/bin/sqlplus -S aoo_tests/AOO_TESTS@//localhost:1521/XE <<EOF
        



EOF

	if [ "$?" == "0" ]; then
		echo "Successfully connected to database with AOO_TESTS"
		CONNECTION_AVAILABLE=true
	fi

	if [ "$CONNECTION_AVAILABLE" != true ] ; then
		echo 'Could not connect to database with sqlplus.'
		# exit unhealthy
		exit 1
	fi


# exit healthy
exit 0
