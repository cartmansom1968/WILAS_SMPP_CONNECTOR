nowtime=`date`
echo "Date: $nowtime"
loc=/Users/somnath/IdeaProjects/WILAS_SMPP_CONNECTOR/test
cd $loc
pid="WILAS_SEND_SMS"
libloc=$loc/lib
export COLUMNS=1000

        /usr/bin/java -Xmx64m -DMOCGid=$pid -classpath $loc/../out/production/WILAS_SMPP_CONNECTOR/:$libloc/activation.jar:$libloc/activemq-client-5.9.0.jar:$libloc/commons-dbcp-1.2.1.jar:$libloc/commons-logging-1.0.3.jar:$libloc/geronimo-j2ee-management_1.1_spec-1.0.1.jar:$libloc/geronimo-jms_1.1_spec-1.1.1.jar:$libloc/jackson-all-1.6.0.jar:$libloc/log4j-1.2.8.jar:$libloc/mail.jar:$libloc/slf4j-api-1.7.5.jar:$libloc/spice-jndikit-20050704.053804.jar:$libloc/optional/log4j-1.2.17.jar:$libloc/optional/slf4j-log4j12-1.7.5.jar:$libloc/wilasconn.jar SendSMS send $1 $2 $3 
