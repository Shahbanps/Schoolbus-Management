from flask import Blueprint,render_template,request,redirect,url_for,flash,session
from database import *
from datetime import datetime
import demjson

import uuid
admin=Blueprint('admin',__name__)
@admin.route('/adminhome')
def adminhome():
	return render_template('adminhome.html')
@admin.route('/admin_manage_parent',methods=['get','post'])	
def admin_manage_parent():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from parents where parent_id='%s'"%(id)
		delete(q)
	if action=="update":
		q="select * from parents where parent_id='%s'"%(id)
		res=select(q)
		data['updatedata']=res
	if 'update'in request.form:
		first_name=request.form['first_name']
		last_name=request.form['last_name']
		occupation=request.form['occupation']
		phone=request.form['phone']
		email=request.form['email']
		house_name=request.form['house_name']
		place=request.form['place']
		q="update parents set first_name='%s',last_name='%s',occupation='%s',phone='%s',email='%s',house_name='%s',place='%s'"%(first_name,last_name,occupation,phone,email,house_name,place)
		update(q)

	if 'submit' in request.form:
		first_name=request.form['first_name']
		last_name=request.form['last_name']
		occupation=request.form['occupation']
		phone=request.form['phone']
		email=request.form['email']
		house_name=request.form['house_name']
		place=request.form['place']
		username=request.form['username']
		password=request.form['password']
		q="insert into login values(null,'%s','%s','parents')"%(username,password)
		idd=insert(q)
		q1="insert into parents values(null,'%s','%s','%s','%s','%s','%s','%s','%s')"%(idd,first_name,last_name,occupation,phone,email,house_name,place)
		insert(q1)
	q2="select * from parents"
	res=select(q2)
	data['parents']=res
	return render_template('admin_manage_parent.html',data=data)
@admin.route('admin_manage_student',methods=['get','post'])
def admin_manage_student():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from students where student_id='%s'"%(id)
		delete(q)
	if action=="update":
		q="select * from students where student_id='%s'"%(id)
		res=select(q)
		data['updatedata']=res
	if 'update' in request.form:
		relation_with_parent=request.form['relation_with_parent']
		first_name=request.form['first_name']
		last_name=request.form['last_name']
		image=request.files['image']
		path='static/uploads'+str(uuid.uuid4())+image.filename
		image.save(path)
		course=request.form['course']
		batch=request.form['batch']
		phone=request.form['phone']
		q="update students set relation_with_parent='%s',first_name='%s',last_name='%s',image='%s',course='%s',phone='%s'"%(relation_with_parent,first_name,last_name,path,course,phone,)
		update(q)
		return redirect(url_for('admin.admin_manage_student'))
	if 'submit'in request.form:
		relation_with_parent=request.form['relation_with_parent']
		parent_id=request.form['parent_id']
		route_id=request.form['route_id']
		placename=request.form['place_name']
		first_name=request.form['first_name']
		last_name=request.form['last_name']
		image=request.files['image']
		path='static/uploads/'+str(uuid.uuid4())+image.filename
		image.save(path)
		course=request.form['course']
		batch=request.form['batch']
		dob=request.form['dob']
		gender=request.form['gender']
		phone=request.form['phone']
		q="insert into students values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','active')"%(parent_id,relation_with_parent,route_id,placename,first_name,last_name,path,course,batch,dob,gender,phone)
		insert(q)
		return redirect(url_for('admin.admin_manage_student'))
	q1="SELECT * FROM `parents`"
	res=select(q1)
	data['view_parent']=res
	q2="select * from routes"
	res=select(q2)
	data['view_route']=res
	q3="select * from places"
	res=select(q3)
	data['places']=res
	q="select * from students inner join routes using(route_id) inner join places using(place_id)"
	res=select(q)
	data['students']=res


	return render_template('admin_manage_student.html',data=data)
@admin.route('/getplace',methods=['get','post'])
def getplace():
	route_id=request.args['route_id']
	q="select place_id as id,place_name as name from places where route_id='%s'"%(route_id)
	res=select(q)
	return demjson.encode(res)
@admin.route('/admin_manage_route',methods=['get','post'])
def admin_manage_route():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from routes where route_id='%s'"%(id)
		delete(q)
	if action=="update":
		q="select * from routes where route_id='%s'"%(id)
		res=select(q)
		data['updatedata']=res
	if 'update' in request.form:
		route_name=request.form['route_name']
		route_details=request.form['route_details']
		q="UPDATE routes SET route_name='%s',route_details='%s' WHERE `route_id`='%s'"%(route_name,route_details,id)
		update(q)
	
	if 'submit' in request.form:
		route_name=request.form['route_name']
		route_details=request.form['route_details']
		q="insert into routes values(null,'%s','%s')"%(route_name,route_details)
		insert(q)
	q1="select * from routes"
	res=select(q1)
	data['view_route']=res
	return render_template('admin_manage_route.html',data=data)
@admin.route('/adminplace',methods=['get','post'])
def adminplace():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from places where place_id='%s'"%(id)
		delete(q)
	# if action=="update":
	# 	q="select * from places where place_id='%s'"%(id)
	# 	res=select(q)
	# 	data['updatedata']=res
	# if 'update' in request.args:
	# 	place_name=request.form['place_name']
	# 	latitude=request.form['latitude']
	# 	longitude=request.form['longitude']
	# 	q="update places set place_name='%s',latitude='%S',longitude='%s'"%(place_name,latitude,longitude,id)
	# 	update(q)

	
	if 'submit' in request.form:
		place_name=request.form['place_name']
		latitude=request.form['lat']
		longitude=request.form['lon']
		route_id=request.form['route_id']
		q="insert into places values(null,'%s','%s','%s','%s')"%(place_name,latitude,longitude,route_id)
		insert(q)
	q1="select * from routes"
	res=select(q1)
	data['view_route']=res
	q="select * from places"
	res=select(q)
	data['places']=res
	return render_template('admin_manage_place.html',data=data)
@admin.route('/admin_manage_class',methods=['get','post'])
def admin_manage_class():
	if 'action'in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from classes where class_id='%s'"%(id)
		delete(q)
		
	data={}
	if 'submit' in request.form:
		course=request.form['course']
		batch=request.form['batch']
		subname=request.form['subname']
		tutorname=request.form['tutorname']
		date_time=request.form['date_time']
		q="insert into classes values(null,'%s','%s','%s','%s','%s')"%(course,batch,subname,tutorname,date_time)
		insert(q)
	q1="select * from classes"
	res=select(q1)
	data['classes']=res
	
	return render_template('admin_manage_class.html',data=data)
@admin.route('/admin_manage_holydays',methods=['get','post'])
def admin_manage_holydays():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id= request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from holidays where holiday_id='%s'"%(id)
		delete(q)
		
	
	if 'submit' in request.form:
		holidayname=request.form['holidayname']
		holidaydate=request.form['holidaydate']
		q= "insert into holidays values(null,'%s','%s')"%(holidayname,holidaydate)
		insert(q)
	q1="select * from holidays"
	res=select(q1)
	data['holidays']=res
	return render_template('admin_manage_holydays.html',data=data)
@admin.route('/adm_snd_msg',methods=['get','post'])
def adm_snd_msg():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id= request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from messages where message_id='%s'"%(id)
		delete(q)
	if 'submit' in request.form:
		parent_id=request.form['parent_id']
		message=request.form['message']
		q="insert into messages values(null,'%s','%s','pending',curdate())"%(parent_id,message)
		insert(q)
		return redirect(url_for('admin.adm_snd_msg'))
	q1="select * from parents"
	res=select(q1)
	data['view_parent']=res
	q2="select * from messages inner join parents using(parent_id)"
	res=select(q2)
	data['messages']=res
	
    
	return render_template('adm_snd_msg.html',data=data)
@admin.route('/adm_mng_bus',methods=['get','post'])
def adm_mng_bus():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id= request.args['id']
	else:
		action=None
	if action=="delete":
		q="delete from buses where bus_id='%s'"%(id)
		delete(q)
	if 'submit' in request.form:
		reg_num=request.form['reg_num']
		seatcapacity=request.form['seatcapacity']
		q="insert into buses values(null,'%s','%s','0','0')"%(reg_num,seatcapacity)
		insert(q)
		return redirect(url_for('admin.adm_mng_bus'))
	q2="select * from buses"
	res=select(q2)
	data['buses']=res
	return render_template('adm_mng_bus.html',data=data)
@admin.route('/admin_mng_driver',methods=['get','post'])
def admin_mng_driver():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
		lid=request.args['lid']
	else:
	 action=None
	if action=="delete":
		q="delete from drivers where driver_id='%s'"%(id)
		delete(q)
		q="delete from login where login_id='%s'"%(lid)
		delete(q)
	if action=="update":
		q="select * from drivers where driver_id='%s'"%(id)
		res=select(q)
		data['updatedata']=res
	if 'update' in request.form:
		firstname=request.form['firstname']
		lname=request.form['lname']
		phone=request.form['phone']
		image=request.files['image']
		path='static/uploads/'+str(uuid.uuid4())+image.filename
		image.save(path)
		q="update drivers set first_name='%s',last_name='%s',contact_no='%s',image='%s'"%(firstname,lname,phone,path)
		update(q)
		return redirect(url_for('admin.admin_mng_driver'))
	if 'submit' in request.form:

		bus_id=request.form['bus_id']
		firstname=request.form['firstname']
		lname=request.form['lname']
		phone=request.form['phone']
		image=request.files['image']
		path='static/uploads/'+str(uuid.uuid4())+image.filename
		image.save(path)
		username=request.form['username']
		password=request.form['password']
		q="insert into login values(null,'%s','%s','drivers')"%(username,password)
		idd=insert(q)
		q1="insert into drivers values(null,'%s','%s','%s','%s','%s','%s')"%(idd,bus_id,firstname,lname,phone,path)
		insert(q1)
		return redirect(url_for('admin.admin_mng_driver'))
	q2="select * from buses"
	res=select(q2)
	data['view_bus']=res
	q3="select * from drivers"
	res=select(q3)
	data['drivers']=res

	return render_template('admin_mng_driver.html',data=data)

@admin.route('/assign_journey',methods=['get','post'])
def assign_journey():
	data={}
	if "action" in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action="none"
	if action=="delete":
		q="delete from aaya where driver_journey_id='%s'"%(id)
		delete(q)
		flash('Removed successfully !')
	
	if 'submit' in request.form:
		route_id=request.form['route_id']
		driver_id=request.form['driver_id']
		q="insert into driver_journey values(null,'%s','%s','assigned')"%(driver_id,route_id)
		res=insert(q)
		if res:
			flash('Assigned successfully !')
	q="select * from drivers where driver_id not in (select driver_id from driver_journey)"
	res=select(q)
	data['driver']=res
	q="select * from routes where route_id not in (select route_id from driver_journey)"
	res=select(q)
	data['journey']=res
	q="SELECT	* FROM `driver_journey` INNER JOIN `drivers` USING (driver_id) INNER JOIN routes USING (route_id)"
	res=select(q)
	data['assigndetails']=res
	return render_template("admin_assign_journey.html",data=data)

@admin.route('/admin_manage_aayas',methods=['get','post'])
def admin_manage_aayas():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		ids=request.args['ids']
	else:
		action=None
	if action=="delete":
		q="delete from aayas where aaya_id='%s'"%(ids)
		delete(q)
	if action=="update":
		q="select * from aayas where aaya_id='%s'"%(ids)
		res=select(q)
		data['upaaya']=res

	if 'submits' in request.form:
		bname=request.form['bname']
		fname=request.form['fname']
		lname=request.form['lname']
		hname=request.form['hname']
		place=request.form['place']
		phone=request.form['phone']
		q="UPDATE aayas SET bus_id='%s',first_name='%s',last_name='%s',house_name='%s',place='%s',phone='%s' WHERE `aaya_id`='%s'"%(bname,fname,lname,hname,place,phone,ids)
		update(q)
		return redirect(url_for('admin.admin_manage_aayas'))
	
	if 'submit' in request.form:
		bname=request.form['bname']
		fname=request.form['fname']
		lname=request.form['lname']
		hname=request.form['hname']
		place=request.form['place']
		phone=request.form['phone']
		uname=request.form['uname']
		pwd=request.form['pwd']

		q="insert into login values(null,'%s','%s','aayas')"%(uname,pwd)
		id=insert(q)
		q1="insert into aayas values(null,'%s','%s','%s','%s','%s','%s','%s')"%(id,bname,fname,lname,hname,place,phone)
		insert(q1)
		return redirect(url_for('admin.admin_manage_aayas'))

	q2="select * from buses"
	res=select(q2)
	data['buses']=res
	return render_template('admin_manage_aayas.html',data=data)


@admin.route('/admin_manage_child_line',methods=['get','post'])
def admin_manage_child_line():
	data={}
	if 'action' in request.args:
		action=request.args['action']
		ids=request.args['ids']
	else:
		action=None
	if action=="delete":
		q="delete from child_line where child_line_id='%s'"%(ids)
		delete(q)
	if action=="update":
		q="select * from child_line where child_line_id='%s'"%(ids)
		res=select(q)
		data['upchild']=res

	if 'submits' in request.form:
		place=request.form['place']
		lmark=request.form['lmark']
		phone=request.form['phone']
		email=request.form['email']
		
		q="UPDATE child_line SET office_place='%s',landmark='%s',phone='%s',email='%s' WHERE `aaya_id`='%s'"%(place,lmark,phone,email)
		update(q)
		return redirect(url_for('admin.admin_manage_child_line'))
	
	if 'submit' in request.form:
		place=request.form['place']
		lmark=request.form['lmark']
		phone=request.form['phone']
		email=request.form['email']
		uname=request.form['uname']
		pwd=request.form['pwd']

		q="insert into login values(null,'%s','%s','child_line')"%(uname,pwd)
		id=insert(q)
		q1="insert into child_line values(null,'%s','%s','%s','%s','%s')"%(id,place,lmark,phone,email)
		insert(q1)
		flash("success")
		return redirect(url_for('admin.admin_manage_child_line'))

	q2="select * from child_line"
	res=select(q2)
	data['child']=res
	return render_template('admin_manage_child_line.html',data=data)

@admin.route('/logout',methods=['get','post'])
def logout():
	session.clear()
	return redirect(url_for('public.login'))
	

	
		
	


	



	
	

	


		

	