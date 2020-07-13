# Benchmark

We want to compare 3 different states:
1. The current state on master, meaning our developed application
2. Our application without Jaeger at all
3. Our application with Jaeger but without the transparency tags

## Procedure

## Case 1

### Prepare
Prepare the benchmark by ensuring that the docker images are uploaded to the
registry and if not do so from your computer. The registry is here:
https://gitlab.tubit.tu-berlin.de/peng/peng-project/container_registry

Commands to upload images for this case:
```
git checkout master
make deploy
```

### Perform
Perform benchmark for case 1 by doing this on the instance/AWS:
```
git checkout master
make aws
```


## Case 2

### Prepare
Do the same as for case 1, if images are not there:
```
git checkout -t benchmark-no-jaeger
make deploy
```

### Perform
Perform benchmark for case 2 by doing this on the instance/AWS:
```
git checkout -t benchmark-no-jaeger
make aws
```
