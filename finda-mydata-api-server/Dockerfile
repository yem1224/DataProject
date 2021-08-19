FROM 579321569587.dkr.ecr.ap-northeast-2.amazonaws.com/openjdk-8-jre-finda:latest

# copy build file
COPY ./target/finda-mydata-api-server-1.0-*.jar /

# defualt spring boot profile setting
# ENV SPRING_PROFILES_ACTIVE stg,log-debug

# log directory setting
RUN mkdir -p /var/log/finda \
&& chmod 777 /var/log/finda

VOLUME /var/log/finda

# set timezone to Asia/Seoul and character set to en_US.UTF-8
ENV TZ=Asia/Seoul
ENV LANG=en_US.UTF-8

# run jar
ENTRYPOINT java -Duser.timezone="Asia/Seoul" -Djava.net.preferIPv4Stack=true -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar finda-mydata-api-server-1.0-*.jar >> /var/log/finda/console.out 2>&1
