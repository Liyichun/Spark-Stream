#!/bin/sh
echo "start auto deploy shell"

mvn clean package
#
if [ $? -eq 0 ];then
{
    echo "******build success! start to transfer to spark master server ******"
    expect script/scp.expect
    if [ $? -eq 0 ]; then
    {
        echo "***** transfer success! *****"
        expect script/submit.expect
    }
    fi

}
else
  echo "******build failed!******"
fi

#spark-submit --class "example.WordCount" --master "spark://ubuntu:7077" PDS-RA-1.0-SNAPSHOT-fat.jar

#./~/MR.mao Shell/autoLogin
