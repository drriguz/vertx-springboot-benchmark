# vertx-springboot-benchmark
A benchmark test (CPU-bound system) of vertx/springboot

## How to run it

First, start the containers:

```bash
cd frameworks
./gradlew build
docker compose up --build
```

After that, servers will be available on:

* Vert.x: 8082
* Springboot: 8081

Then use jmeter to test each of them:

```bash
jmeter -n -t benchmark.jmx \
    -J threads=3000 \
    -J seconds=0 \
    -J loop=100 \
    -l result.jtl \
    -j result.log
```