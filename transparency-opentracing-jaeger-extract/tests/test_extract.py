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
                "traceID": "f37aa340e7461d7f",
                "spans": [
                    {
                        "traceID": "f37aa340e7461d7f",
                        "spanID": "f37aa340e7461d7f",
                        "flags": 1,
                        "operationName": "hello world",
                        "references": [],
                        "startTime": 1594058149000000,
                        "duration": 9713,
                        "tags": [
                            {"key": "duration", "type": "string", "value": "1h"},
                            {"key": "auto", "type": "bool", "value": False},
                            {
                                "key": "purpose",
                                "type": "string",
                                "value": "dummy purpose",
                            },
                            {"key": "sampler.type", "type": "string", "value": "const"},
                            {
                                "key": "recipients",
                                "type": "string",
                                "value": "recipient1, recipient2",
                            },
                            {"key": "sampler.param", "type": "bool", "value": True},
                            {"key": "origin", "type": "string", "value": "world"},
                            {
                                "key": "category",
                                "type": "string",
                                "value": "dummy data category",
                            },
                            {"key": "3rdparty", "type": "bool", "value": False},
                            {
                                "key": "internal.span.format",
                                "type": "string",
                                "value": "proto",
                            },
                        ],
                        "logs": [],
                        "processID": "p1",
                        "warnings": None,
                    }
                ],
                "processes": {
                    "p1": {
                        "serviceName": "helloworld",
                        "tags": [
                            {
                                "key": "hostname",
                                "type": "string",
                                "value": "43932b2a616b",
                            },
                            {"key": "ip", "type": "string", "value": "172.18.0.6"},
                            {
                                "key": "jaeger.version",
                                "type": "string",
                                "value": "Java-0.35.1",
                            },
                        ],
                    }
                },
                "warnings": None,
            },
            {
                "traceID": "1d52717b07840539",
                "spans": [
                    {
                        "traceID": "1d52717b07840539",
                        "spanID": "1d52717b07840539",
                        "flags": 1,
                        "operationName": "hello world",
                        "references": [],
                        "startTime": 1594058151931000,
                        "duration": 43,
                        "tags": [
                            {"key": "duration", "type": "string", "value": "1h"},
                            {"key": "auto", "type": "bool", "value": False},
                            {
                                "key": "purpose",
                                "type": "string",
                                "value": "dummy purpose",
                            },
                            {"key": "sampler.type", "type": "string", "value": "const"},
                            {
                                "key": "recipients",
                                "type": "string",
                                "value": "recipient1, recipient2",
                            },
                            {"key": "sampler.param", "type": "bool", "value": True},
                            {"key": "origin", "type": "string", "value": "world"},
                            {
                                "key": "category",
                                "type": "string",
                                "value": "dummy data category",
                            },
                            {"key": "3rdparty", "type": "bool", "value": False},
                            {
                                "key": "internal.span.format",
                                "type": "string",
                                "value": "proto",
                            },
                        ],
                        "logs": [],
                        "processID": "p1",
                        "warnings": None,
                    }
                ],
                "processes": {
                    "p1": {
                        "serviceName": "helloworld",
                        "tags": [
                            {
                                "key": "hostname",
                                "type": "string",
                                "value": "43932b2a616b",
                            },
                            {"key": "ip", "type": "string", "value": "172.18.0.6"},
                            {
                                "key": "jaeger.version",
                                "type": "string",
                                "value": "Java-0.35.1",
                            },
                        ],
                    }
                },
                "warnings": None,
            },
            {
                "traceID": "9616ad0001d2ec4c",
                "spans": [
                    {
                        "traceID": "9616ad0001d2ec4c",
                        "spanID": "9616ad0001d2ec4c",
                        "flags": 1,
                        "operationName": "hello world",
                        "references": [],
                        "startTime": 1594058158556000,
                        "duration": 54,
                        "tags": [
                            {"key": "duration", "type": "string", "value": "1h"},
                            {"key": "auto", "type": "bool", "value": False},
                            {
                                "key": "purpose",
                                "type": "string",
                                "value": "dummy purpose",
                            },
                            {"key": "sampler.type", "type": "string", "value": "const"},
                            {
                                "key": "recipients",
                                "type": "string",
                                "value": "recipient1, recipient2",
                            },
                            {"key": "sampler.param", "type": "bool", "value": True},
                            {"key": "origin", "type": "string", "value": "world"},
                            {
                                "key": "category",
                                "type": "string",
                                "value": "dummy data category",
                            },
                            {"key": "3rdparty", "type": "bool", "value": False},
                            {
                                "key": "internal.span.format",
                                "type": "string",
                                "value": "proto",
                            },
                        ],
                        "logs": [],
                        "processID": "p1",
                        "warnings": None,
                    }
                ],
                "processes": {
                    "p1": {
                        "serviceName": "helloworld",
                        "tags": [
                            {
                                "key": "hostname",
                                "type": "string",
                                "value": "43932b2a616b",
                            },
                            {"key": "ip", "type": "string", "value": "172.18.0.6"},
                            {
                                "key": "jaeger.version",
                                "type": "string",
                                "value": "Java-0.35.1",
                            },
                        ],
                    }
                },
                "warnings": None,
            },
        ],
        "total": 0,
        "limit": 0,
        "offset": 0,
        "errors": None,
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
    assert len(result) == 1
    assert result[0]["purpose"] == "dummy purpose"
    assert result[0]["category"] == "dummy data category"
    assert result[0]["recipients"] == ("recipient1", "recipient2")


def test_get_value_from_service_with_key_succeeds(mock_requests_get_traces_of_service):
    result = extract._get_value_from_service_with_key("purpose", "helloworld")
    assert result == ["dummy purpose"]


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
