from flask import Flask, redirect, render_template, url_for
import requests

from transparency_opentracing_jaeger_extract import extract


app = Flask(__name__)

JAEGER_NOT_REACHABLE_EXCEPTION = {"error": "Jaeger not reachable"}


@app.route("/api/all", methods=["GET"])
def all_infos():
    try:
        all_transparency_info = extract.get_all_for_services()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return all_transparency_info


@app.route("/api/purposes", methods=["GET"])
def purposes():
    try:
        purposes = extract.get_purposes_for_services()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return purposes


@app.route("/api/categories", methods=["GET"])
def categories():
    try:
        categories = extract.get_categories_for_services()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return categories


@app.route("/api/origins", methods=["GET"])
def origins():
    try:
        origins = extract.get_origins_for_services()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return origins


@app.route("/api/recipients", methods=["GET"])
def recipients():
    try:
        recipients = extract.get_recipients_for_services()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return recipients


@app.route("/api/durations", methods=["GET"])
def durations():
    try:
        durations = extract.get_durations_for_services()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return durations


@app.route("/api/automated", methods=["GET"])
def autos():
    try:
        autos = extract.get_autos_for_services()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return autos

@app.route("/api/3rdparty", methods=["GET"])
def thirdparties():
    try:
        thirdparties = extract.get_3rdparty_for_services()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return thirdparties


@app.route("/api", methods=["GET"])
def apihelp():
    return redirect(url_for("index"))


@app.route("/", methods=["GET"])
def index():
    return render_template("index.html")


def main():
    app.run()


if __name__ == "__main__":
    main()
