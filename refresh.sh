chmod +x refresh.sh
mvn -f ./coingecko clean install
mvn -f ./demo-jmeter clean install
docker-compose build
docker-compose up -d