from flask import Blueprint,request,render_template,redirect,session,url_for
from database import *
public=Blueprint('public',__name__)

@public.route('/',methods=['get','post'])
def login():
	if 'submit' in request.form:
		username=request.form['username']
		password=request.form['password']
		q="select * from login where username='%s' and password='%s'" %(username,password)
		res=select(q)
		if res:
			session['login_id']=res[0]['login_id']
			if res[0]['user_type']=="admin":
				return redirect(url_for('admin.adminhome'))

			elif res[0]['user_type']=="aayas":
				q="select aaya_id from aayas where login_id='%s'"%(session['login_id'])
				res=select(q)
				session['aaya_id']=res[0]['aaya_id']
				return redirect(url_for('aayas.aayas_home'))

			elif res[0]['user_type']=="child_line":
				
				return redirect(url_for('child_line.child_line_home'))
			
		
	return render_template('index.html')
	
