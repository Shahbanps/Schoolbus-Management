from flask import *
from database import *
import demjson


api = Blueprint('api',__name__)

# @api.before_request
# def beep():
# 	import winsound
# 	winsound.Beep(2500,100)

@api.route('/login',methods=['get','post'])
def login():
	data={}
	username = request.args['username']
	password = request.args['password']
	q = "select * from login where username='%s' and password='%s'" % (username,password)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)

@api.route('/view_journeystatus',methods=['get','post'])
def view_journeystatus():
	data={}
	logid = request.args['logid']
	q = "SELECT * FROM driver_journey INNER JOIN routes USING (route_id) WHERE driver_journey.driver_id=(SELECT driver_id FROM drivers WHERE login_id='%s')" % (logid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'view_journeystatus'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)

@api.route('/update_journeystatus',methods=['get','post'])
def update_journeystatus():
	data={}
	logid = request.args['logid']
	status = request.args['status']
	q="update driver_journey set status='%s' where driver_id=(SELECT driver_id FROM drivers WHERE login_id='%s')" % (status,logid)
	c=update(q)
	if(c>0):
		data['status']  = 'success'
		if status=="Running":
			data['method']  ='journeystart'
		else:
			data['method']  ='journeystop'
	else:
		data['status']	= 'failed'
	return demjson.encode(data)

@api.route('/driver_studentlist',methods=['get','post'])
def driver_studentlist():
	data={}
	logid = request.args['logid']
	q = "SELECT * FROM driver_journey INNER JOIN students USING (route_id) WHERE driver_journey.driver_id=(SELECT driver_id FROM drivers WHERE login_id='%s')" % (logid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'driver_studentlist'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)

@api.route('/driver_studdetails',methods=['get','post'])
def driver_studdetails():
	data={}
	student_id = request.args['student_id']
	q = "SELECT s.`student_id`,CONCAT(s.`first_name`,' ',s.last_name) AS sname,s.`image`,CONCAT(p.`first_name`,' ',p.last_name) AS pname,p.`phone`  FROM students s INNER JOIN parents p USING (parent_id)WHERE s.`student_id`='%s'" % (student_id)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'driver_studdetails'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)

@api.route('/driver_addattendance',methods=['get','post'])
def driver_addattendance():
	data={}
	sid = request.args['sid']
	astatus= request.args['astatus']
	q="select * from attendance where date=CURDATE() and student_id='%s'"%(sid)
	res=select(q)
	if res:
		data['status']	= 'failed'
		data['method']  ='driver_addattendance'
	else:
		q="INSERT INTO attendance VALUE(NULL,'%s',CURDATE(),'%s')"%(sid,astatus)
		id=insert(q)
		if(id>0):
			data['status']  ='success'
			data['method']  ='driver_addattendance'
	return demjson.encode(data)

@api.route('/driver_viewattendance',methods=['get','post'])
def driver_viewattendance():
	data={}
	student_id = request.args['student_id']
	q = "SELECT * FROM attendance WHERE student_id='%s' AND DATE=CURDATE()" % (student_id)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'driver_viewattendance'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'driver_viewattendance'
	return  demjson.encode(data)

@api.route('/driver_viewstudavailability',methods=['get','post'])
def driver_viewstudavailability():
	data={}
	student_id = request.args['student_id']
	q = "SELECT * FROM availablity WHERE student_id='%s' AND DATE=CURDATE()" % (student_id)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'driver_viewstudavailability'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'driver_viewstudavailability'
	return  demjson.encode(data)

@api.route('/parent_studentlist',methods=['get','post'])
def parent_studentlist():
	data={}
	logid = request.args['logid']
	q = "SELECT students.* FROM parents INNER JOIN students USING (parent_id) WHERE students.parent_id=(SELECT parent_id FROM parents WHERE login_id='%s')" % (logid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'parent_studentlist'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'parent_studentlist'
	return  demjson.encode(data)
@api.route('/parent_studentdetails',methods=['get','post'])
def parent_studentdetails():
	data={}
	sid = request.args['sid']
	q = "SELECT CONCAT(students.`first_name`,' ',students.`last_name`) AS sname,students.`image`,CONCAT(drivers.`first_name`,' ',drivers.`last_name`) AS dname,drivers.`contact_no`,driver_journey.status,buses.`latitude`,buses.`longitude` FROM students INNER JOIN `driver_journey` USING (route_id) INNER JOIN drivers USING(driver_id) INNER JOIN buses USING(bus_id) WHERE students.`student_id`='%s'" % (sid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'parent_studentdetails'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'parent_studentdetails'
	return  demjson.encode(data)

@api.route('/par_addavailability',methods=['get','post'])
def par_addavailability():
	data={}
	sid = request.args['sid']
	astatus= request.args['astatus']
	q="select * from availablity where date=CURDATE() and student_id='%s'"%(sid)
	res=select(q)
	if res:
		data['status']	= 'failed'
		data['method']  ='par_addavailability'
	else:
		q="INSERT INTO availablity VALUE(NULL,'%s',CURDATE(),'%s')"%(sid,astatus)
		id=insert(q)
		if(id>0):
			data['status']  ='success'
			data['method']  ='par_addavailability'
	return demjson.encode(data)

@api.route('/parent_sendcomplaint',methods=['get','post'])
def parent_sendcomplaint():
	data={}
	logid = request.args['logid']
	com = request.args['com']
	q="INSERT INTO complaints VALUE(NULL,(SELECT parent_id FROM parent WHERE log_id='%s'),CURDATE(),'%s','pending',NULL)" % (logid,com)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  ='parent_sendcomplaint'
	else:
		data['status']	= 'failed'
		data['method']  ='parent_sendcomplaint'
	print(data)
	return demjson.encode(data)

@api.route('/parent_viewreply',methods=['get','post'])
def parent_viewreply():
	data={}
	logid = request.args['logid']
	q="select * from complaints where status='replied' and parent_id=(SELECT parent_id FROM parent WHERE log_id='%s')"%(logid)
	res=select(q)
	if res:
		data['status']	= 'success'
		data['method']  ='parent_viewreply'
		data['data']=res
	else:
		data['status']  ='failed'
		data['method']  ='parent_viewreply'
	return demjson.encode(data)

@api.route('/parent_viewstudents',methods=['get','post'])
def parent_viewstudents():
	data={}
	logid = request.args['logid']
	q="select * from students where parent_id=(SELECT parent_id FROM parents WHERE log_id='%s')"%(logid)
	res=select(q)
	if res:
		data['status']	= 'success'
		data['method']  ='parent_viewstudents'
		data['data']=res
	else:
		data['status']  ='failed'
		data['method']  ='parent_viewstudents'
	return demjson.encode(data)

@api.route('/parent_sendnoti',methods=['get','post'])
def parent_sendnoti():
	data={}
	logid = request.args['logid']
	noti = request.args['noti']
	sid = request.args['sid']
	q="INSERT INTO notification VALUE(NULL,(SELECT parent_id FROM parent WHERE log_id='%s'),'%s',CURDATE(),curtime(),'%s','pending')" % (logid,sid,noti)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  ='parent_sendnoti'
	else:
		data['status']	= 'failed'
		data['method']  ='parent_sendnoti'
	return demjson.encode(data)

@api.route('/parent_viewnoti',methods=['get','post'])
def parent_viewnoti():
	data={}
	logid = request.args['logid']
	q="select * from notification INNER JOIN student using(student_id) where notification.parent_id=(SELECT parent_id FROM parent WHERE log_id='%s')"%(logid)
	res=select(q)
	if res:
		data['status']	= 'success'
		data['method']  ='parent_viewnoti'
		data['data']=res
	else:
		data['status']  ='failed'
		data['method']  ='parent_viewnoti'
	return demjson.encode(data)

@api.route('/driver_viewnoti',methods=['get','post'])
def driver_viewnoti():
	data={}
	logid = request.args['logid']
	q="SELECT * FROM notification INNER JOIN student USING(student_id) INNER JOIN driver_journey USING(journey_id) WHERE notification.status='pending' and driver_journey.`driver_id`=(SELECT driver_id FROM driver WHERE log_id='%s')"%(logid)
	res=select(q)
	if res:
		data['status']	= 'success'
		data['method']  ='driver_viewnoti'
		data['data']=res
	else:
		data['status']  ='failed'
		data['method']  ='driver_viewnoti'
	return demjson.encode(data)
@api.route('/driver_updatenotification',methods=['get','post'])
def driver_updatenotification():
	data={}
	not_id = request.args['not_id']
	q="update notification set status='Viewed by driver' where notification_id='%s'" % (not_id)
	c=update(q)
	if(c>0):
		data['status']  = 'success'
		data['method']  ='driver_updatenotification'
	else:
		data['status']	= 'failed'
		data['method']  ='driver_updatenotification'
	return demjson.encode(data)

@api.route('/update_buslocation',methods=['get','post'])
def update_buslocation():
	data={}
	logid = request.args['logid']
	lati = request.args['lati']
	longi = request.args['longi']
	q="UPDATE buses SET latitude='%s',longitude='%s' WHERE bus_id=(SELECT bus_id FROM drivers WHERE driver_id=(SELECT driver_id FROM drivers WHERE login_id='%s'))"%(lati,longi,logid)
	c=update(q)
	if(c>0):
		data['status']  = 'success'
		data['method']  ='update_buslocation'
	else:
		data['status']	= 'failed'
		data['method']  ='update_buslocation'
	return demjson.encode(data)
@api.route('/par_viewmsg',methods=['get','post'])
def par_viewmsg():
	data={}
	logid = request.args['logid']
	q="select * from messages where reply_description='pending' and parent_id=(SELECT parent_id FROM parents WHERE login_id='%s')"%(logid)
	res=select(q)
	if res:
		data['status']	= 'success'
		data['method']  ='par_viewmsg'
		data['data']=res
	else:
		data['status']  ='failed'
		data['method']  ='par_viewmsg'
	return demjson.encode(data)
@api.route('/par_msgreply',methods=['get','post'])
def par_msgreply():
	data={}
	mid = request.args['mid']
	reply_description=request.args['reply']
	q="UPDATE messages SET reply_description='%s' WHERE message_id='%s'"%(reply_description,mid)
	id=update(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  ='par_msgreply'
	else:
		data['status']	= 'failed'
		data['method']  ='par_msgreply'
	return demjson.encode(data)
@api.route('/par_viewholiday',methods=['get','post'])
def par_viewholiday():
	data={}
	q="select * from holidays"
	res=select(q)
	if res:
		data['status']	= 'success'
		data['method']  ='par_viewholiday'
		data['data']=res
	else:
		data['status']  ='failed'
		data['method']  ='par_viewholiday'
	return demjson.encode(data)
@api.route('/par_viewclasses',methods=['get','post'])
def par_viewclasses():
	data={}
	q="select * from classes"
	res=select(q)
	if res:
		data['status']	= 'success'
		data['method']  ='par_viewclasses'
		data['data']=res
	else:
		data['status']  ='failed'
		data['method']  ='par_viewclasses'
	return demjson.encode(data)
@api.route('/par_viewdrivers',methods=['get','post'])
def par_viewdrivers():
	data={}
	q="select * from drivers inner join buses using(bus_id)"
	res=select(q)
	if res:
		data['status']	= 'success'
		data['method']  ='par_viewdrivers'
		data['data']=res
	else:
		data['status']  ='failed'
		data['method']  ='par_viewdrivers'
	return demjson.encode(data)
@api.route('/par_makepayment',methods=['get','post'])
def par_makepayment():
	data={}
	sid = request.args['sid']
	amt=request.args['amt']
	q="insert into payment values(null,'%s','%s',CURDATE())"%(sid,amt)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  ='par_makepayment'
	else:
		data['status']	= 'failed'
		data['method']  ='par_makepayment'
	return demjson.encode(data)