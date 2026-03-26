# payment

Spring Boot repo supporting:
- POST /payments/authorize
- GET /payments/recent?customerId=...&limit=...

## Run
```bash
mvn spring-boot:run
```

## Build
```bash
mvn clean package
```

## Java
- Java 18

## Notes
- Includes RestTemplate bean
- Includes global exception handler
- Package structure is Maven-ready
