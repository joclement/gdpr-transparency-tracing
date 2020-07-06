import os

import requests


if os.getenv("JAEGER_QUERY_HOST") is None:
    jaeger_query_host = "localhost"
else:
    jaeger_query_host = os.environ["JAEGER_QUERY_HOST"]

JAEGER_QUERY_ADDRESS = "http://" + jaeger_query_host + ":16686/api/"

TRANSPARENCY_CATEGORIES = [
    "purpose",
    "category",
    "recipients",
    "3rdparty",
    "duration",
    "origin",
    "auto",
]


def get_services() -> set:
    """Simple jaeger extract json api server."""
    with requests.get(f"{JAEGER_QUERY_ADDRESS}services") as response:
        response.raise_for_status()
        data = response.json()

    services = data["data"]
    services = set(services)
    if "jaeger-query" in services:
        services.remove("jaeger-query")
    return services


def _get_traces_from_service(service: str):
    with requests.get(f"{JAEGER_QUERY_ADDRESS}traces?service={service}") as response:
        response.raise_for_status()
        data = response.json()

    return data


def _get_transparency_tags_from_service(service: str) -> dict:
    data = _get_traces_from_service(service)

    transparency_tags = dict()
    for tag_name in TRANSPARENCY_CATEGORIES:
        tags = set()
        for trace in data["data"]:
            for span in trace["spans"]:
                for tag in span["tags"]:
                    if tag["key"] == tag_name:
                        tags.add(tag["value"])
        transparency_tags[tag_name] = list(tags)
    return transparency_tags


def get_all_for_services() -> dict:
    all_transparency_info = dict()
    for service in get_services():
        all_transparency_info[service] = _get_transparency_tags_from_service(service)

    return all_transparency_info


def _get_purposes_from_service(service: str) -> list:
    transparency_tags = _get_transparency_tags_from_service(service)
    return transparency_tags["purpose"]


def get_purposes_for_services() -> dict:
    purposes = dict()
    for service in get_services():
        purposes[service] = _get_purposes_from_service(service)

    return purposes
