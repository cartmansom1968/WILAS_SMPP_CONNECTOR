#!/usr/bin/expect

spawn sftp root@203.142.17.220
expect "password"
send "<L\$8)2AvX36M~A;=\r" 
interact
