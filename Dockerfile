FROM java:8
ARG JAR_FILE=./eladmin-system/target/eladmin-system-2.7.jar*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Shanghai
ENTRYPOINT ["java","-jar","/app.jar"]
