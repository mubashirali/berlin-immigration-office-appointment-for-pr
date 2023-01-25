#!/bin/bash
	COUNTER=0
  while [  $COUNTER -lt 100000 ]; do
          echo The counter is $COUNTER
          timeout 5m mvn clean test
          rc=$?
          if [[ $rc -ne 0 ]] ; then
            echo 'Test failed! will retry shortly!';
          fi
          sleep 600;
       let COUNTER=COUNTER+1
  done
