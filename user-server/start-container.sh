chmod +x start-container.sh
mvn -f . clean install
docker-compose build
docker-compose up -d