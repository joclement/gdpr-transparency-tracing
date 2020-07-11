from flask import Flask, redirect, render_template, url_for
import requests

from simple_jaeger_extract import extract


app = Flask(__name__)

JAEGER_NOT_REACHABLE_EXCEPTION = {"error": "Jaeger not reachable"}


@app.route("/api/groups/no-duplicates", methods=["GET"])
def groups_without_duplicates():
    try:
        all_groups = extract.get_groups_for_services_without_duplicates()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return all_groups


@app.route("/api/groups", methods=["GET"])
def groups():
    try:
        all_groups = extract.get_groups_for_services()
    except requests.RequestException:
        return JAEGER_NOT_REACHABLE_EXCEPTION
    return all_groups


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
