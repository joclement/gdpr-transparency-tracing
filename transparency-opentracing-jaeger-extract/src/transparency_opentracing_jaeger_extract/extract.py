import os

import requests

if os.getenv("JAEGER_QUERY_HOST") is None:
    jaeger_query_host = "localhost"
else:
    jaeger_query_host = os.environ["JAEGER_QUERY_HOST"]

JAEGER_QUERY_ADDRESS = "http://" + jaeger_query_host + ":16686/api/"

TRANSPARENCY_LIST_CATEGORIES = [
    "purpose",
    "category",
    "origin",
    "recipient",
]
TRANSPARENCY_CATEGORIES = [
    "3rdparty",
    "duration",
    "auto",
]
TRANSPARENCY_CATEGORIES.extend(TRANSPARENCY_LIST_CATEGORIES)


def get_services() -> set:
    with requests.get(f"{JAEGER_QUERY_ADDRESS}services") as response:
        response.raise_for_status()
        data = response.json()

    if data["data"] is None:
        services = set()
    else:
        services = set(data["data"])
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
    for tag_name in TRANSPARENCY_LIST_CATEGORIES:
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

                if tag_key in TRANSPARENCY_LIST_CATEGORIES:
                    span_groups[group_number][tag_key].insert(tag_number, tag["value"])
                else:
                    span_groups[group_number][tag_key] = tag["value"]

            for g in seen_groups:
                if span_groups[g]:
                    groups[group_count] = span_groups[g]
                    group_count += 1
    return groups



def _get_value_from_service_with_key(key: str, service: str) -> list:
    transparency_groups = _get_transparency_groups_from_service(service)
    values = list()
    for group in transparency_groups:
        if transparency_groups[group][key] and transparency_groups[group][key] not in values:
            values.append(transparency_groups[group][key])

    return values


def get_purposes_for_services() -> dict:
    purposes = dict()
    for service in get_services():
        purposes[service] = _get_value_from_service_with_key("purpose", service)
    return purposes

def get_categories_for_services() -> dict:
    categories = dict()
    for service in get_services():
        categories[service] = _get_value_from_service_with_key("category", service)
    return categories

def get_recipients_for_services() -> dict:
    recipients = dict()
    for service in get_services():
        recipients[service] = _get_value_from_service_with_key("recipient", service)
    return recipients

def get_origins_for_services() -> dict:
    origins = dict()
    for service in get_services():
        origins[service] = _get_value_from_service_with_key("origin", service)
    return origins

def get_durations_for_services() -> dict:
    duration = dict()
    for service in get_services():
        duration[service] = _get_value_from_service_with_key("duration", service)
    return duration

def get_3rdparty_for_services() -> dict:
    thirdparty = dict()
    for service in get_services():
        thirdparty[service] = _get_value_from_service_with_key("3rdparty", service)
    return thirdparty

def get_autos_for_services() -> dict:
    autos = dict()
    for service in get_services():
        autos[service] = _get_value_from_service_with_key("auto", service)
    return autos

def _get_list_without_duplicates_from_dict(dic: dict) -> list:
    list_without_duplicates = list()
    for i in dic:
        if dic[i] not in list_without_duplicates:
            list_without_duplicates.append(dic[i])
    return list_without_duplicates


def get_all_for_services() -> dict:
    all_transparency_info = dict()
    for service in get_services():
        all_transparency_info[service] = _get_list_without_duplicates_from_dict(_get_transparency_groups_from_service(service))
    return all_transparency_info
