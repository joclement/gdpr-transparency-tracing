"""Simple jaeger extract json api server."""

from flask import Flask, redirect, render_template, url_for
import requests

from simple_jaeger_extract import extract


app = Flask(__name__)


@app.route("/api/purposes", methods=["GET"])
def purposes():
    try:
        purposes = extract.get_purposes_for_services()
    except requests.RequestException:
        error_message = {"error": "Jaeger not reachable"}
        return error_message
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
