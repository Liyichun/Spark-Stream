#!/bin/sh
echo "start auto deploy shell"

mvn clean package
#
if [ $? -eq 0 ];then
  {
    echo "******build success! start to commit to spark ******"
    spark-submit --class "TestBroad" --master "local[4]" target/PDS-RA-1.0-SNAPSHOT-fat.jar
  }

else
  echo "******build failed!******"
fi

#./~/MR.mao Shell/autoLogin
