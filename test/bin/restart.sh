pid="WILAS_SMPP_DLG_CONN"
export COLUMNS=1000
numOfProcs=`ps -ef | grep "MOCGid=$pid" | grep "/usr/bin/java" | wc -l`
procHung=0
restartProc=0;

if [ $numOfProcs -gt 0 ];then
	procHung=`tail -2 ../logs/SMPP.log | grep "SMPP Connection disconnecteded"|wc -l`
        if [ $procHung -gt 0 ]; then
		restartProc=1;
	fi
else
	restartProc=1;
fi

if [ $restartProc -gt 0 ]; then
	list=`ps -u tssadmin -o pid -o args | grep "MOCGid=$pid" | grep "/usr/bi
n/java" | cut -b1-6`

	for i in $list
	do
  		echo "killing process id $i"
		`kill -9 $i`
		sleep 3
	done

	nowtime=`date`
	echo "Date: $nowtime"
	loc=/home/tssadmin/dialogconn
	cd $loc
	libloc=$loc/lib

        numOfProcs=`ps -ef |grep "MOCGid=$pid" | grep "/usr/bin/java" | wc -l`

	if [ $numOfProcs -gt 0 ]; then
        	echo "Process $pid is still running"
	else
        	nohup /usr/bin/java -Xmx64m -DMOCGid=$pid -classpath $libloc/activation.jar:$libloc/activemq-client-5.9.0.jar:$libloc/commons-dbcp-1.2.1.jar:$libloc/commons-logging-1.0.3.jar:$libloc/geronimo-j2ee-management_1.1_spec-1.0.1.jar:$libloc/geronimo-jms_1.1_spec-1.1.1.jar:$libloc/jackson-all-1.6.0.jar:$libloc/log4j-1.2.8.jar:$libloc/mail.jar:$libloc/slf4j-api-1.7.5.jar:$libloc/spice-jndikit-20050704.053804.jar:$libloc/optional/log4j-1.2.17.jar:$libloc/optional/slf4j-log4j12-1.7.5.jar:$libloc/wilasconn.jar startSMPP 2>&1 >> wilasconn.debug &
	fi
fi

