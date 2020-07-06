import pytest

from simple_jaeger_extract import extract


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
                "traceID": "fb7de556e3deca1b",
                "spans": [
                    {
                        "traceID": "fb7de556e3deca1b",
                        "spanID": "fb7de556e3deca1b",
                        "flags": 1,
                        "operationName": "hello world",
                        "references": [],
                        "startTime": 1593611666086000,
                        "duration": 5603,
                        "tags": [
                            {"key": "purpose", "type": "string", "value": "fun"},
                            {
                                "key": "category",
                                "type": "string",
                                "value": "dummy category",
                            },
                            {"key": "sampler.type", "type": "string", "value": "const"},
                            {"key": "sampler.param", "type": "bool", "value": True},
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
                                "value": "e567dc2149fd",
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
            }
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


def test_get_transparency_tags_from_service(mock_requests_get_traces_of_service):
    result = extract._get_transparency_tags_from_service("helloworld")
    assert result["purpose"] == ["fun"]
    assert result["category"] == ["dummy category"]


def test_get_purpose_from_service_succeeds(mock_requests_get_traces_of_service):
    result = extract._get_purposes_from_service("helloworld")
    assert result == ["fun"]


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
