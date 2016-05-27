#!/bin/sh
echo "start auto deploy shell"

mvn clean package
#
if [ $? -eq 0 ];then
  {
    echo "******build success! start to commit to spark ******"
    spark-submit --class "SimpleApp" --master "local[4]" target/PRA-1.0-SNAPSHOT-fat.jar
  }

else
  echo "******build failed!******"
fi

#./~/MR.mao Shell/autoLogin
