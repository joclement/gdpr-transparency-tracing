# Description

This folder contains our simple jaeger extract. It is written in Python.

## How to get it started

In order to run this service accessible on [localhost:5000](http://localhost:5000)
you should only have to run `docker-compose up --build` or more specifically to
just start this service and its dependency Jaeger `docker-compose up --build
simple-jaeger-extract`.


## How to develop/contribute

You will need a modern python version. We use python3.8. A lower one might also
be sufficient. We don't know.

The following steps need to be done to continue development.

1. Get a modern python, e.g. by installing pyenv to install python like this `curl https://pyenv.run | bash`
2. Then for pyenv add this to your shell environment e.g. `~/.bashrc`:
  ```
    export PATH="~/.pyenv/bin:$PATH"
    eval "$(pyenv init -)"
    eval "$(pyenv virtualenv-init -)"
  ```
3. Install necessary dependencies, e.g. for Ubuntu:
```
sudo apt update && sudo apt install -y make build-essential libssl-dev zlib1g-dev \
libbz2-dev libreadline-dev libsqlite3-dev wget curl llvm libncurses5-dev \
libncursesw5-dev xz-utils tk-dev libffi-dev liblzma-dev python-openssl git
```
4. Install python 3.8, e.g. `pyenv install 3.8.3`
4. Activate python 3.8, e.g. `pyenv local 3.8.3`
5. Install poetry, e.g. `pip install poetry`
6. Activate project with poetry, `poetry install`
7. Run tests `poetry run pytest --cov -m "not e2e"`
7. To run tests when other components are running: `poetry run pytest --cov`
8. Run application `poetry run simple-jaeger-extract`
