from flask import *
from database import *

child_line=Blueprint('child_line',__name__)

@child_line.route('/child_line_home')
def child_line_home():
	return render_template('child_line_home.html')

@child_line.route('/child_line_view_complaints')
def child_line_view_complaints():
	data={}

	q="SELECT *,CONCAT(`students`.`first_name`,' ',`students`.`last_name`)AS sname,CONCAT(`aayas`.`first_name`,' ',`aayas`.`last_name`)AS aname FROM `complaints` INNER JOIN `aayas` USING(`aaya_id`) INNER JOIN `students` USING(`student_id`)"
	res=select(q)
	data['comp']=res
	return render_template('child_line_view_complaints.html',data=data)

@child_line.route('/child_line_message_parents',methods=['get','post'])
def child_line_message_parents():
	data={}
	ids=request.args['ids']

	if 'submit' in request.form:
		msg=request.form['msg']

		q="INSERT INTO `messages`(`parent_id`,`message_description`,`reply_description`,`message_date`) VALUES('%s','%s','pending',NOW())"%(ids,msg)
		insert(q)
		flash("sent")
		return redirect(url_for('child_line.child_line_message_parents',ids=ids))

	q="SELECT * FROM `messages` WHERE `parent_id`='%s'"%(ids)
	res=select(q)
	data['msg']=res
	return render_template('child_line_message_parents.html',data=data)
