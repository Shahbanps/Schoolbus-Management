from flask import *
from database import *

aayas=Blueprint('aayas',__name__)

@aayas.route('/aayas_home')
def aayas_home():
	return render_template('aayas_home.html')

@aayas.route('/aayas_view_assigned_bus')
def aayas_view_assigned_bus():
	data={}
	aid=session['aaya_id']

	q="SELECT * FROM `aayas` INNER JOIN `buses` USING(bus_id) WHERE aaya_id='%s'"%(aid)
	res=select(q)
	data['assign']=res

	return render_template('aayas_view_assigned_bus.html',data=data)


@aayas.route('/aayas_view_student_details')
def aayas_view_student_details():
	data={}

	if 'action' in request.args:
		action=request.args['action']
		ids=request.args['ids']
	else:
		action=None

	if action=="present":
		q="SELECT * FROM `attendance` WHERE `student_id`='%s' AND `date`=CURDATE() "%(ids)
		res=select(q)
		if res:
			q="UPDATE `attendance` SET `astatus`='%s' WHERE `attendance_id`='%s'"%(action,res[0]['attendance_id'])
			update(q)
			flash('PRESENT MARKED')
			return redirect(url_for('aayas.aayas_view_student_details'))
		else:

			q="INSERT INTO `attendance`(`student_id`,`date`,`astatus`)VALUES('%s',CURDATE(),'present')"%(ids)
			insert(q)
			flash('PRESENT MARKED')
			return redirect(url_for('aayas.aayas_view_student_details'))
	if action=="absent":
		q="SELECT * FROM `attendance` WHERE `student_id`='%s' AND `date`=CURDATE() "%(ids)
		res=select(q)
		if res:
			q="UPDATE `attendance` SET `astatus`='%s' WHERE `attendance_id`='%s'"%(action,res[0]['attendance_id'])
			update(q)
			flash('ABSENT MARKED')
			return redirect(url_for('aayas.aayas_view_student_details'))
		else:
			q="INSERT INTO `attendance`(`student_id`,`date`,`astatus`)VALUES('%s',CURDATE(),'absent')"%(ids)
			insert(q)
			flash('ABSENT MARKED')
			return redirect(url_for('aayas.aayas_view_student_details'))

	q="SELECT *,CONCAT(`students`.`first_name`,' ',`students`.`last_name`)AS sname,CONCAT(`parents`.`first_name`,' ',`parents`.`last_name`)AS pname FROM `students` INNER JOIN `parents` USING(`parent_id`) INNER JOIN `routes` USING(`route_id`) INNER JOIN `places` USING(`place_id`)"
	res=select(q)
	data['std']=res
	return render_template('aayas_view_student_details.html',data=data)

@aayas.route('/aayas_send_messages_parent',methods=['get','post'])
def aayas_send_messages_parent():
	data={}

	ids=request.args['ids']

	if 'submit' in request.form:
		msg=request.form['msg']

		q="INSERT INTO `messages`(`parent_id`,`message_description`,`reply_description`,`message_date`) VALUES('%s','%s','pending',NOW())"%(ids,msg)
		insert(q)
		flash("sent")
		return redirect(url_for('aayas.aayas_send_messages_parent',ids=ids))

	q="SELECT * FROM `messages` WHERE `parent_id`='%s'"%(ids)
	res=select(q)
	data['msg']=res

	return render_template('aayas_send_messages_parent.html',data=data)


@aayas.route('/aayas_report_childline',methods=['get','post'])
def aayas_report_childline():
	data={}
	aid=session['aaya_id']
	ids=request.args['ids']

	if 'submit' in request.form:
		comp=request.form['comp']

		q="INSERT INTO `complaints`(`aaya_id`,`student_id`,`complaint`,`date`)VALUES('%s','%s','%s',CURDATE())"%(aid,ids,comp)
		insert(q)
		flash("reported")
		return(redirect(url_for('aayas.aayas_report_childline',ids=ids)))

	q="SELECT *,CONCAT(`first_name`,' ',`last_name`)AS sname FROM `complaints` INNER JOIN `students` USING(`student_id`) WHERE `student_id`='%s' AND `aaya_id`='%s'"%(ids,aid)
	res=select(q)
	data['cmp']=res

	return render_template('aayas_report_childline.html',data=data)