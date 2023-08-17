from flask import Flask
from public import public
from admin import admin
from aayas import aayas
from child_line import child_line
from api import api
app=Flask(__name__)
app.secret_key="aju"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(api,url_prefix='/api')
app.register_blueprint(aayas,url_prefix='/aayas')
app.register_blueprint(child_line,url_prefix='/child_line')
app.run(debug=True,host="192.168.43.215",port="5024")