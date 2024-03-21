# Build Docker image
```
docker build -t="hello-world-java" .
```

##### Run Docker Container
```
docker run -p 8080:8080 -it --rm hello-world-java
```
test 
```
curl localhost:8080
```

response should be:
```
Hello World
```

