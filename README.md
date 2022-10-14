# minimized-oraclexe-image

This project contains a **Minimized Oracle 18.4.0 XE** docker image intended 
to be used for integration testing.

This is a minimized image based on the [official Oracle Docker XE](https://github.com/oracle/docker-images) 
Image but removes files not required for this purpose to minimize image file size and improve 
startup time.


## DockerHub

The docker image is available at [DockerHub](https://hub.docker.com/repository/docker/diemobiliar/minimized-oraclexe-image). Instructions to build it yourself are below.

![Docker Pulls](https://img.shields.io/docker/pulls/diemobiliar/minimized-oraclexe-image)


## Image creation

First you will need to create the official Oracle Docker image

```shell script
$ git clone https://github.com/oracle/docker-images.git
$ cd docker-images/OracleDatabase/SingleInstance/dockerfiles
$ ./buildContainerImage.sh -v 18.4.0 -x

$ docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
oracle/database     18.4.0-xe           79e03b2304a7        About an hour ago   5.89GB
```

Then use the Dockerfile from this project to build the minimized image:

```shell script
$ git clone https://github.com/diemobiliar/minimized-oraclexe-image.git
$ cd minimized-oraclexe-image/18c_xe
$ docker build . -t diemobiliar/minimized-oraclexe-image:18.4.0-xe

$ docker image ls
REPOSITORY                             TAG                 IMAGE ID            CREATED             SIZE
diemobiliar/minimized-oraclexe-image   18c_xe              df...9d             21 seconds ago      3.9GB
oracle/database                        18.4.0-xe           79...a7             2 hours ago         5.89GB
```

The ```examples``` directory contains an example on how to use this image using [testcontainers](https://www.testcontainers.org/).

The test should usually run within 5 seconds (starting up the minimized oracle image and apply one flyway migration).


## Usage and Startup Options

The username and password to connect to the database is: ```AOO_TESTS```

The SID is: ```XE```

The image allows to specify the SGA memory from 288M to 1664M, the default being 1536M. 
Important: don't miss out on the "M".

The default ```system``` user password is "oracle".
It can be customized using the environment variable: ```ORACLE_PWD```.

Example:
 ```shell script
docker run -e "SGA_TARGET=512M"  --name 18c_xe512m -p 1523:1521 diemobiliar/minimized-oraclexe-image:18.4.0-xe
```


## Contributions

A big thank-you goes to the creators of the image:
  * Alain Fuhrer, IT Database Services, [Die Mobiliar](https://www.mobiliar.ch/)
  * Andreas Wyssenbach, Database-Specialist, [Die Mobiliar](https://www.mobiliar.ch/)
