### usage

#### package

mvn clean package -Dmaven.test.skip=true -U -P test

#### start one

./server.sh start

#### start two

java -jar -Dspring.profiles.active=dev deploy-1.0.0.jar

java -jar -Dspring.profiles.active=dev --logging.config=$BASE_DIR/config/logback-spring-@profile@.xml deploy-1.0.0.jar
