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
    "3rdparty",
    "duration",
    "origin",
    "auto",
    "recipient"
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


def _generate_new_group() -> dict:
    transparency_tags = dict()
    for tag_name in TRANSPARENCY_CATEGORIES:
        transparency_tags[tag_name] = list()
    return transparency_tags


def _get_transparency_groups_from_service(service: str) -> dict:
    data = _get_traces_from_service(service)

    groups = dict()
    group_count = 0
    for trace in data["data"]:
        for span in trace["spans"]:
            span_groups = list()
            span_groups.append(_generate_new_group())
            seen_groups = [0]
            for tag in span["tags"]:
                tag_name_parts = tag["key"].split("_")

                if len(tag_name_parts) not in [2, 3]:
                    continue

                group_number = int(tag_name_parts[0])
                tag_key = tag_name_parts[1]
                tag_number = int(tag_name_parts[2]) if len(tag_name_parts) == 3 else 0

                if tag_key not in TRANSPARENCY_CATEGORIES:
                    continue

                if group_number not in seen_groups:
                    for i in range(group_number+1):
                        if i not in seen_groups:
                            span_groups.append(_generate_new_group())
                            seen_groups.append(i)

                span_groups[group_number][tag_key].insert(tag_number, tag["value"])

            for g in seen_groups:
                if span_groups[g]:
                    groups[group_count] = span_groups[g]
                    group_count += 1
    return groups


def get_all_for_services() -> dict:
    all_transparency_info = dict()
    for service in get_services():
        all_transparency_info[service] = _get_transparency_groups_from_service(service)
    return all_transparency_info


def _get_purposes_from_service(service: str) -> list:
    transparency_groups = _get_transparency_groups_from_service(service)
    purposes = list()
    for group in transparency_groups:
        if transparency_groups[group]["purpose"] and transparency_groups[group]["purpose"] not in purposes:
            purposes.append(transparency_groups[group]["purpose"])

    return purposes


def get_purposes_for_services() -> dict:
    purposes = dict()
    for service in get_services():
        purposes[service] = _get_purposes_from_service(service)
    return purposes


def get_groups_for_services() -> dict:
    groups = dict()
    for service in get_services():
        groups[service] = _get_transparency_groups_from_service(service)
    return groups


def _get_list_without_duplicates_from_dict(dic: dict) -> list:
    list_without_duplicates = list()
    for i in dic:
        if dic[i] not in list_without_duplicates:
            list_without_duplicates.append(dic[i])
    return list_without_duplicates


def get_groups_for_services_without_duplicates() -> dict:
    lists = dict()
    for service in get_services():
        lists[service] = _get_list_without_duplicates_from_dict(_get_transparency_groups_from_service(service))
    return lists
