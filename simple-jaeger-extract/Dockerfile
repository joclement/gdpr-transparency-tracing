FROM python:3.8-alpine3.12 as base

ENV PYTHONFAULTHANDLER=1 \
    PYTHONHASHSEED=random \
    PYTHONUNBUFFERED=1

WORKDIR /app

FROM base as builder

ENV PIP_DEFAULT_TIMEOUT=100 \
    PIP_DISABLE_PIP_VERSION_CHECK=1 \
    PIP_NO_CACHE_DIR=1 \
    POETRY_VERSION=1.0.9

RUN apk add gcc libffi-dev musl-dev openssl-dev
RUN pip install "poetry==$POETRY_VERSION"
RUN python -m venv /venv

COPY pyproject.toml poetry.lock ./
RUN poetry export -f requirements.txt | /venv/bin/pip install -r /dev/stdin

COPY . .
RUN poetry build
RUN /venv/bin/pip install dist/simple_jaeger_extract-0.1.1-py3-none-any.whl


FROM base as final
COPY --from=builder /venv /venv
COPY docker-entrypoint.sh app.py ./
CMD ["./docker-entrypoint.sh"]
