
#### Todo-Backend/src/main/resources/application.yml 필요함
#### Todo-Frontend/.env 필요함

### application.yml 세팅 예시
```
server:
  port: 8080

file:
  upload-dir: C:\uploads\profile-image

spring:
  mail:
    host: smtp.naver.com
    port: 465
    username: 네이버아이디
    password: 네이버비밀번호
    properties:
      mail:
        smtp:
          auth: true
          ssl:
            enable: true
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB

  # db config
  jwt:
    secret: vmfhaltmskdlstkfkdfdfkgfjsjdfgjsghewkwenbfehfdjhckdkdfjfsfdhjsfduh
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb: 데이터베이스 URL
    username: 데이터베이스 username
    password: 데이터베이스 password
  devtools:
    restart:
      enabled: false


  # jpa config
  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect
```
### .env 세팅 예시
```
REACT_APP_IP=localhost:8080
```
