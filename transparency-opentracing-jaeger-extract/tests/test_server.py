import pytest

from transparency_opentracing_jaeger_extract import server


@pytest.fixture
def client():
    server.app.config["TESTING"] = True
    with server.app.test_client() as client:
        yield client


def test_index_succeeds(client):
    assert client.get("/").status_code == 200


def test_api_redirect_succeeds(client):
    assert client.get("/api").status_code == 302


def test_purposes_succeeds(client):
    response = client.get("/api/purposes")
    assert response.status_code == 200
    assert response.is_json is True


def test_recipients_succeeds(client):
    response = client.get("/api/recipients")
    assert response.status_code == 200
    assert response.is_json is True


def test_origins_succeeds(client):
    response = client.get("/api/origins")
    assert response.status_code == 200
    assert response.is_json is True


def test_data_categories_succeeds(client):
    response = client.get("/api/categories")
    assert response.status_code == 200
    assert response.is_json is True


def test_durations_succeeds(client):
    response = client.get("/api/durations")
    assert response.status_code == 200
    assert response.is_json is True


def test_automated_succeeds(client):
    response = client.get("/api/automated")
    assert response.status_code == 200
    assert response.is_json is True


def test_3rdparty_succeeds(client):
    response = client.get("/api/3rdparty")
    assert response.status_code == 200
    assert response.is_json is True


def test_all_succeeds(client):
    response = client.get("/api/all")
    assert response.status_code == 200
    assert response.is_json is True
