# GDPR Transparency Tracing

## Overview
This project was developed during particiation of the course
[Privacy Engineering](https://www.ise.tu-berlin.de/menue/lehre/module/privacy_engineering/)
at the Technische Universität Berlin.
The topic of this project was to integrate transparency regarding the usage of
personal data into tracing tools such as Jaeger.
With this it could be achieved that transparency reports respectively privacy
policy documents are more specific and also more accurate.
The idea for this came from [Dr. Frank Pallas](https://www.ise.tu-berlin.de/menue/team/dr_ing_frank_pallas/),
who instructed the Privacy Engineering course, and from
[Dominik Ernst](https://www.ise.tu-berlin.de/menue/team/dominik_ernst/).
This repository contains an initial implementation of this idea.

## Get started
You need the following dependencies to build and run the programs contained in
this repository:
* maven
* docker
* docker-compose
* python
* pip
* curl or alternatives
* make

The project has been tested for Ubuntu18.04. It will most likely run on other
Linux distributions and also on MacOS. We don't know about Windows.

### Quickstart
Run the command `make` or `make up`.

## Licenses
This project contains different licenses for different services and files.
It includes the [secure gateway](gateway/) of spring.io, which can be accessed at
https://github.com/benwilcock/spring-cloud-gateway-demo/tree/master/security-gateway,
and which is licensed under the [Apache 2.0 License](gateway/LICENSE).
The 2 services [transparency-opentracing-helper](transparency-opentracing-helper)
and [transparency-opentracing-jaeger-extract](transparency-opentracing-jaeger-extract)
are licensed the MIT license.

## Authors
The authors are stated in [AUTHORS.md](AUTHORS.md).
