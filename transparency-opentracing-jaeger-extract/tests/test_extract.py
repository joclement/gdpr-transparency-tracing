import pytest

from transparency_opentracing_jaeger_extract import extract


@pytest.fixture
def mock_requests_get_services(mocker):
    mock = mocker.patch("requests.get")
    mock.return_value.__enter__.return_value.json.return_value = {
        "data": ["jaeger-query", "helloworld"],
        "total": 2,
        "limit": 0,
        "offset": 0,
        "errors": None,
    }
    return mock


@pytest.fixture
def mock_requests_get_services_no_jaeger_query(mocker):
    mock = mocker.patch("requests.get")
    mock.return_value.__enter__.return_value.json.return_value = {
        "data": ["helloworld"],
        "total": 1,
        "limit": 0,
        "offset": 0,
        "errors": None,
    }
    return mock


@pytest.fixture
def mock_requests_get_traces_of_service(mocker):
    mock = mocker.patch("requests.get")
    mock.return_value.__enter__.return_value.json.return_value = {
        "data": [
            {
                "traceID": "66703e117cc594d8",
                "spans": [
                    {
                        "traceID": "66703e117cc594d8",
                        "spanID": "66703e117cc594d8",
                        "flags": 1,
                        "operationName": "hello world",
                        "references": [],
                        "startTime": 1594976900567000,
                        "duration": 11309,
                        "tags": [
                            {
                                "key": "0_purpose_0",
                                "type": "string",
                                "value": "DEVELOPMENT"
                            },
                            {
                                "key": "sampler.type",
                                "type": "string",
                                "value": "const"
                            },
                            {
                                "key": "sampler.param",
                                "type": "bool",
                                "value": True
                            },
                            {
                                "key": "0_recipient_1",
                                "type": "string",
                                "value": "RECIPIENT2"
                            },
                            {
                                "key": "0_recipient_0",
                                "type": "string",
                                "value": "RECIPIENT1"
                            },
                            {
                                "key": "0_3rdparty",
                                "type": "bool",
                                "value": False
                            },
                            {
                                "key": "0_category_0",
                                "type": "string",
                                "value": "DUMMY DATA CATEGORY"
                            },
                            {
                                "key": "0_origin_0",
                                "type": "string",
                                "value": "PERRY_THE_PLATYPUS"
                            },
                            {
                                "key": "0_duration",
                                "type": "string",
                                "value": "NO_RETENTION"
                            },
                            {
                                "key": "0_auto",
                                "type": "bool",
                                "value": False
                            },
                            {
                                "key": "internal.span.format",
                                "type": "string",
                                "value": "proto"
                            }
                        ],
                        "logs": [],
                        "processID": "p1",
                        "warnings": None
                    }
                ],
                "processes": {
                    "p1": {
                        "serviceName": "helloworld",
                        "tags": [
                            {
                                "key": "hostname",
                                "type": "string",
                                "value": "0b4b9814ba97"
                            },
                            {
                                "key": "ip",
                                "type": "string",
                                "value": "172.19.0.5"
                            },
                            {
                                "key": "jaeger.version",
                                "type": "string",
                                "value": "Java-0.35.1"
                            }
                        ]
                    }
                },
                "warnings": None
            },
            {
                "traceID": "7c54005b8fa9ae40",
                "spans": [
                    {
                        "traceID": "7c54005b8fa9ae40",
                        "spanID": "7c54005b8fa9ae40",
                        "flags": 1,
                        "operationName": "hello world",
                        "references": [],
                        "startTime": 1594977022423000,
                        "duration": 62,
                        "tags": [
                            {
                                "key": "0_purpose_0",
                                "type": "string",
                                "value": "DEVELOPMENT"
                            },
                            {
                                "key": "sampler.type",
                                "type": "string",
                                "value": "const"
                            },
                            {
                                "key": "sampler.param",
                                "type": "bool",
                                "value": True
                            },
                            {
                                "key": "0_recipient_1",
                                "type": "string",
                                "value": "RECIPIENT2"
                            },
                            {
                                "key": "0_recipient_0",
                                "type": "string",
                                "value": "RECIPIENT1"
                            },
                            {
                                "key": "0_3rdparty",
                                "type": "bool",
                                "value": False
                            },
                            {
                                "key": "0_category_0",
                                "type": "string",
                                "value": "DUMMY DATA CATEGORY"
                            },
                            {
                                "key": "0_origin_0",
                                "type": "string",
                                "value": "PERRY_THE_PLATYPUS"
                            },
                            {
                                "key": "0_duration",
                                "type": "string",
                                "value": "NO_RETENTION"
                            },
                            {
                                "key": "0_auto",
                                "type": "bool",
                                "value": False
                            },
                            {
                                "key": "internal.span.format",
                                "type": "string",
                                "value": "proto"
                            }
                        ],
                        "logs": [],
                        "processID": "p1",
                        "warnings": None
                    }
                ],
                "processes": {
                    "p1": {
                        "serviceName": "helloworld",
                        "tags": [
                            {
                                "key": "hostname",
                                "type": "string",
                                "value": "0b4b9814ba97"
                            },
                            {
                                "key": "ip",
                                "type": "string",
                                "value": "172.19.0.5"
                            },
                            {
                                "key": "jaeger.version",
                                "type": "string",
                                "value": "Java-0.35.1"
                            }
                        ]
                    }
                },
                "warnings": None
            },
        {
            "traceID": "59c9dc53c0e1f88b",
            "spans": [
                {
                    "traceID": "59c9dc53c0e1f88b",
                    "spanID": "59c9dc53c0e1f88b",
                    "flags": 1,
                    "operationName": "hello world",
                    "references": [],
                    "startTime": 1594977023320000,
                    "duration": 56,
                    "tags": [
                        {
                            "key": "0_purpose_0",
                            "type": "string",
                            "value": "DEVELOPMENT"
                        },
                        {
                            "key": "sampler.type",
                            "type": "string",
                            "value": "const"
                        },
                        {
                            "key": "sampler.param",
                            "type": "bool",
                            "value": True
                        },
                        {
                            "key": "0_recipient_1",
                            "type": "string",
                            "value": "RECIPIENT2"
                        },
                        {
                            "key": "0_recipient_0",
                            "type": "string",
                            "value": "RECIPIENT1"
                        },
                        {
                            "key": "0_3rdparty",
                            "type": "bool",
                            "value": False
                        },
                        {
                            "key": "0_category_0",
                            "type": "string",
                            "value": "DUMMY DATA CATEGORY"
                        },
                        {
                            "key": "0_origin_0",
                            "type": "string",
                            "value": "PERRY_THE_PLATYPUS"
                        },
                        {
                            "key": "0_duration",
                            "type": "string",
                            "value": "NO_RETENTION"
                        },
                        {
                            "key": "0_auto",
                            "type": "bool",
                            "value": False
                        },
                        {
                            "key": "internal.span.format",
                            "type": "string",
                            "value": "proto"
                        }
                    ],
                    "logs": [],
                    "processID": "p1",
                    "warnings": None
                }
            ],
            "processes": {
                "p1": {
                    "serviceName": "helloworld",
                    "tags": [
                        {
                            "key": "hostname",
                            "type": "string",
                            "value": "0b4b9814ba97"
                        },
                        {
                            "key": "ip",
                            "type": "string",
                            "value": "172.19.0.5"
                        },
                        {
                            "key": "jaeger.version",
                            "type": "string",
                            "value": "Java-0.35.1"
                        }
                    ]
                }
            },
            "warnings": None
        }
    ],
        "total": 0,
        "limit": 0,
        "offset": 0,
        "errors": None
    }
    return mock


def test_services_succeeds(mock_requests_get_services):
    result = extract.get_services()
    assert type(result) is set
    assert type(result.pop()) is str


def test_services_drops_jaegerquery(mock_requests_get_services):
    result = extract.get_services()
    assert "jaeger-query" not in result
    assert "helloworld" in result


def test_services_checks_jaeger_query(mock_requests_get_services_no_jaeger_query):
    result = extract.get_services()
    assert "jaeger-query" not in result
    assert "helloworld" in result


@pytest.mark.e2e
def test_services_succeeds_in_production():
    result = extract.get_services()
    assert type(result) is set


def test_get_traces_from_service(mock_requests_get_traces_of_service):
    result = extract._get_traces_from_service("helloworld")
    assert type(result) is dict


def test_get_transparency_groups_from_service(mock_requests_get_traces_of_service):
    result = extract._get_transparency_groups_from_service("helloworld")
    assert len(result) == 3
    print(result)
    assert result[0]["purpose"] == ["DEVELOPMENT"]
    assert result[0]["category"] == ["DUMMY DATA CATEGORY"]
    assert result[0]["recipient"] == ["RECIPIENT1", "RECIPIENT2"]


def test_get_value_from_service_with_key_succeeds_list(mock_requests_get_traces_of_service):
    result = extract._get_value_from_service_with_key("purpose", "helloworld")
    assert result == [["DEVELOPMENT"]]

def test_get_value_from_service_with_key_succeeds_single(mock_requests_get_traces_of_service):
    result = extract._get_value_from_service_with_key("duration", "helloworld")
    assert result == ["NO_RETENTION"]

@pytest.mark.e2e
def test_get_all_for_services_succeeds_in_production():
    result = extract.get_all_for_services()
    assert type(result) is dict


@pytest.mark.e2e
def test_get_purposes_from_services_succeeds_in_production():
    result = extract.get_purposes_for_services()
    assert type(result) is dict
    for key, value in result.items():
        assert type(key) is str
        assert type(value) is list
