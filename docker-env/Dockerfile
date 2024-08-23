# Use the official MongoDB image as the base image
FROM mongo:7.0

# Install OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk-headless

WORKDIR /app

# Copy the application files (configuration files and JARs)
COPY application/done.jar /app/done.jar

# Expose the ports
EXPOSE 8080

# Start MongoDB and the application
CMD ["sh", "-c", "mongod --bind_ip_all --logpath /var/log/mongod.log --logappend & sleep 5 && java -jar /app/done.jar"]