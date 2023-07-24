FROM maven:3.9.3-sapmachine-17

WORKDIR /app
COPY pom.xml /app

RUN mvn clean install

COPY . /app

CMD ["mvn", "test"]