import pytest

from simple_jaeger_extract import server


@pytest.fixture
def client():
    server.app.config["TESTING"] = True
    with server.app.test_client() as client:
        yield client


def test_index_succeeds(client):
    assert client.get("/").status_code == 200


def test_purposes_succeeds(client):
    response = client.get("/api/purposes")
    assert response.status_code == 200
    assert response.is_json is True
