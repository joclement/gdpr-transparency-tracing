import os

import requests


if os.getenv("JAEGER_QUERY_HOST") is None:
    jaeger_query_host = "localhost"
else:
    jaeger_query_host = os.environ["JAEGER_QUERY_HOST"]

JAEGER_QUERY_ADDRESS = "http://" + jaeger_query_host + ":16686/api/"


def get_services() -> set:
    """Simple jaeger extract json api server."""
    with requests.get(f"{JAEGER_QUERY_ADDRESS}services") as response:
        response.raise_for_status()
        data = response.json()

    services = data["data"]
    services = set(services)
    services.remove("jaeger-query")
    return services


def _get_purposes_from_service(service: str) -> set:
    with requests.get(f"{JAEGER_QUERY_ADDRESS}traces?service={service}") as response:
        response.raise_for_status()
        data = response.json()

    purposes = set()
    for trace in data["data"]:
        for span in trace["spans"]:
            for tag in span["tags"]:
                if tag["key"] == "purpose":
                    purposes.add(tag["value"])
    return purposes


def get_purposes_for_services() -> dict:
    purposes = dict()
    for service in get_services():
        purposes[service] = list(_get_purposes_from_service(service))

    return purposes
