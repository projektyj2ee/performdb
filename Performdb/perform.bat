set MAVEN_OPTS=-DappContext=applicationContext.xml
mvn -e clean package exec:java -Dexec.mainClass="com.performgroup.interview.cmd.VideoTaskRunner"