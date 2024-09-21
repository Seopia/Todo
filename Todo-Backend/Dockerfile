# 베이스 이미로 openJDK 사용
# FROM은 도커 이미지의 기반이 되는 베이스 이미지를 정의한다.
FROM openjdk:17-jdk-alpine

# JAR 파일을 복사
# ARG는 빌드 시에 전달할 변수를 정의한다.
# /target 디렉토리 안에 .jar 파일을 JAR_FILE이란 변수에 할당한 것이다.
ARG JAR_FILE=build/libs/*.jar
# 호스트의 JAR_FILE을 DOCKER 컨테이너 내의 app.jar로 복사하는 명령어다.
COPY ${JAR_FILE} app.jar

# 포트 노출
# 컨테이너가 외부와 통신할 때 열어야 할 포트를 지정한다.
EXPOSE 8080
# JAR 파일 실행
# 컨테이너가 실행될 때 실행할 기본 명령어를 지정한다. java로 자바를 실행하고,
# -jar로 .jar 파일을 실행하고, /app.jar 로 아까 복사한 파일을 실행 대상으로 지정한다.
ENTRYPOINT ["java","-jar","/app.jar"]

