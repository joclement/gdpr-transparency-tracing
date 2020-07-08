import os

import requests


if os.getenv("JAEGER_QUERY_HOST") is None:
    jaeger_query_host = "localhost"
else:
    jaeger_query_host = os.environ["JAEGER_QUERY_HOST"]

JAEGER_QUERY_ADDRESS = "http://" + jaeger_query_host + ":16686/api/"

TRANSPARENCY_LIST_CATEGORIES = [
    "recipients",
]
TRANSPARENCY_CATEGORIES = [
    "purpose",
    "category",
    "3rdparty",
    "duration",
    "origin",
    "auto",
]
TRANSPARENCY_CATEGORIES.extend(TRANSPARENCY_LIST_CATEGORIES)


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


def _remove_duplicate_dicts_from_list(input_list: list) -> list:
    return [
        dict(tupl) for tupl in set(tuple(sorted(dic.items())) for dic in input_list)
    ]


def _get_transparency_tags_from_service(service: str) -> list:
    data = _get_traces_from_service(service)

    all_transparency_tags = list()
    for trace in data["data"]:
        for span in trace["spans"]:
            transparency_tags = dict()
            for tag in span["tags"]:
                if tag["key"] in TRANSPARENCY_CATEGORIES:
                    if (
                        tag["key"] in TRANSPARENCY_LIST_CATEGORIES
                        and "," in tag["value"]
                    ):
                        transparency_tags[tag["key"]] = tuple(
                            [t.strip() for t in tag["value"].split(",")]
                        )
                    else:
                        transparency_tags[tag["key"]] = tag["value"]
            all_transparency_tags.append(transparency_tags)
    return _remove_duplicate_dicts_from_list(all_transparency_tags)


def get_all_for_services() -> dict:
    all_transparency_info = dict()
    for service in get_services():
        all_transparency_info[service] = _get_transparency_tags_from_service(service)
    return all_transparency_info


def _get_purposes_from_service(service: str) -> list:
    transparency_tags_list = _get_transparency_tags_from_service(service)
    purposes = list()
    for transparency_tags in transparency_tags_list:
        purposes.append(transparency_tags["purpose"])
    return purposes


def get_purposes_for_services() -> dict:
    purposes = dict()
    for service in get_services():
        purposes[service] = _get_purposes_from_service(service)
    return purposes
