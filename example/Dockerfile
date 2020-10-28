# ---------------- gRPC Lab Dockerfile ----------------
# Base image documentation: https://access.redhat.com/containers/?tab=tech-details#/registry.access.redhat.com/openjdk/openjdk-11-rhel7
FROM registry.access.redhat.com/openjdk/openjdk-11-rhel7:latest

COPY example/target/grpc.jar .

EXPOSE 8074

ENTRYPOINT ["java", "-jar", "grpc.jar"]