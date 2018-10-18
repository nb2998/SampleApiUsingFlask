import flask
from flask import request, jsonify
import sqlite3

app=flask.Flask(__name__)
app.config["DEBUG"] = True

def dict_factory(cursor, row):
    d= {}
    for idx, col in enumerate(cursor.description):
        d[col[0]] = row[idx]
    return d

@app.route('/', methods=['GET'])
def home():
	return "<h1>Distant Reading Archive</h1><p>This site is a prototype API for distant reading of science fiction novels.</p>"

@app.route('/api/v1/books/all', methods=['GET'])
def books_all():
    conn = sqlite3.connect('books.db')
    conn.row_factory = dict_factory
    curr = conn.cursor()
    all_books = curr.execute('SELECT * FROM books;').fetchall()
    return jsonify(all_books)

@app.route('/api/v1/books/', methods=['GET'])
def api_filter():
    query_params = request.args
    id = query_params.get('id')
    published = query_params.get('published')
    author = query_params.get('author')

    query = "SELECT * FROM BOOKS WHERE"
    to_filter = []

    if id:
        query += " id=? AND"
        to_filter.append(id)
    if published:
        query += " published=? AND"
        to_filter.append(published)
    if author:
        query += " author=? AND"
        to_filter.append(author)

    if not (id or published or author):
        return page_not_found(404)

    query = query[:-4] +";"

    conn = sqlite3.connect('books.db')
    conn.row_factory = dict_factory
    curr = conn.cursor()
    results = curr.execute(query, to_filter).fetchall()
    return jsonify(results)

app.run()

