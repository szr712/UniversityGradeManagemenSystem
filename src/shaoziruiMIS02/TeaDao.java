package shaoziruiMIS02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeaDao extends BaseDao {

	public TeaDao() {
		// TODO Auto-generated constructor stub
	}

	public TeacherBean getTeacher(String num) {
		String sql = "SELECT * FROM shaozr_教师02  where szr_教师编号02=?";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			TeacherBean teacherBean = new TeacherBean();
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				teacherBean.setNum(resultSet.getString("szr_教师编号02").trim());
				teacherBean.setName(resultSet.getString("szr_姓名02").trim());
				teacherBean.setSex(resultSet.getString("szr_性别02").trim());
				teacherBean.setAge(resultSet.getInt("szr_年龄02"));
				teacherBean.setRank(resultSet.getString("szr_职称02").trim());
				teacherBean.setPhone(resultSet.getString("szr_电话02"));

			}
			connection.close();
			return teacherBean;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<CourseBean> getCourse(String num, String year) {
		String sql = "SELECT * FROM shaozr_教师任课查询02,shaozr_课程02 where szr_教师编号02=? and shaozr_教师任课查询02.szr_课程编号02=shaozr_课程02.szr_课程编号02 and szr_开课学年02=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			preparedStatement.setString(2, year);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CourseBean> cList = new ArrayList<>();
			while (resultSet.next()) {
				CourseBean courseBean = new CourseBean();
				courseBean.setCoursenum(resultSet.getString("szr_课程编号02").trim());
				courseBean.setCoursename(resultSet.getString("szr_课程名称02").trim());
				courseBean.setTeacher(resultSet.getString("szr_姓名02").trim());
				courseBean.setYear(resultSet.getString("szr_开课学年02").trim());
				courseBean.setClassname(resultSet.getString("szr_班级名称02").trim());
				if (resultSet.getBoolean("szr_开课学期02")) {
					courseBean.setTerm("上学期");
				} else {
					courseBean.setTerm("下学期");
				}

				courseBean.setTime(resultSet.getInt("szr_学时02"));
				if (resultSet.getBoolean("szr_考试或考查02")) {
					courseBean.setTestway("考查");
				} else {
					courseBean.setTestway("考试");
				}
				courseBean.setTime(resultSet.getInt("szr_学时02"));
				courseBean.setCredit(resultSet.getFloat("szr_学分02"));
				cList.add(courseBean);
			}
			return cList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getAllClass() {
		String sql = "select szr_班级编号02,szr_班级名称02 from shaozr_班级02";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<String> aList = new ArrayList<>();
			while (resultSet.next()) {
				String aclass = resultSet.getString("szr_班级名称02").trim() + "（"
						+ resultSet.getString("szr_班级编号02").trim() + "）";
				aList.add(aclass);
			}
			connection.close();
			return aList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<StudentBean> getGpa(String num, int flag) {
		String sql = "";
		if (flag == 1) {
			sql = "select * from shaozr_均绩02,shaozr_学生02 where shaozr_均绩02.学号=shaozr_学生02.szr_学号02 and shaozr_学生02.szr_班级编号02=?";
		} else if (flag == 2) {
			sql = "select * from shaozr_均绩02,shaozr_学生02 where shaozr_均绩02.学号=shaozr_学生02.szr_学号02 and shaozr_学生02.szr_班级编号02=?\r\n"
					+ "order by 均绩 asc";
		} else if (flag == 3) {
			sql = "select * from shaozr_均绩02,shaozr_学生02 where shaozr_均绩02.学号=shaozr_学生02.szr_学号02 and shaozr_学生02.szr_班级编号02=?\r\n"
					+ "order by 均绩 DESC";
		}
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<StudentBean> sList = new ArrayList<>();
			while (resultSet.next()) {
				StudentBean studentBean = new StudentBean();
				studentBean.setName(resultSet.getString("szr_姓名02").trim());
				studentBean.setNum(resultSet.getString("学号"));
				studentBean.setGpa(resultSet.getFloat("均绩"));
				sList.add(studentBean);
			}
			connection.close();
			return sList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<CourseBean> getAvg(String num,String year) {
		String sql = "select distinct 课程编号,平均成绩,szr_姓名02,shaozr_课程02.szr_课程名称02,szr_开课学年02 from shaozr_每门课平均成绩02,shaozr_教师任课查询02,shaozr_课程02 where shaozr_每门课平均成绩02.课程编号=shaozr_教师任课查询02.szr_课程编号02 and szr_教师编号02=? and 课程编号=shaozr_课程02.szr_课程编号02 and szr_开课学年02=? and 平均成绩 is not null";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			preparedStatement.setString(2, year);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CourseBean> cList = new ArrayList<>();
			while(resultSet.next()) {
				CourseBean courseBean=new CourseBean();
				courseBean.setCoursenum(resultSet.getString("课程编号").trim());
				courseBean.setCoursename(resultSet.getString("szr_课程名称02").trim());
				courseBean.setTeacher(resultSet.getString("szr_姓名02").trim());
				courseBean.setAvg(resultSet.getFloat("平均成绩"));
				courseBean.setYear(resultSet.getString("szr_开课学年02"));
				cList.add(courseBean);
				
			}
			connection.close();
			return cList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
	public ArrayList<String> getACourse(String num){
		String sql="select distinct szr_课程名称02,szr_课程编号02 from shaozr_教师任课查询02 where szr_教师编号02=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<String> aList = new ArrayList<>();
			while (resultSet.next()) {
				String aclass = resultSet.getString("szr_课程名称02").trim() + "（"
						+ resultSet.getString("szr_课程编号02").trim() + "）";
				aList.add(aclass);
			}
			connection.close();
			return aList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public ArrayList<StudentBean> getSingle(String teanum,String counum,String year,int flag){
		String sql="";
		if(flag==1) {
			sql="select * from shaozr_学生成绩统计02,shaozr_学生02,shaozr_教师任课查询02\r\n" + 
					"where 学号=szr_学号02 and shaozr_教师任课查询02.szr_班级编号02=shaozr_学生02.szr_班级编号02 and shaozr_教师任课查询02.szr_课程编号02=shaozr_学生成绩统计02.课程编号 and szr_教师编号02=? and 课程编号=?  and 学年=?" ;
		}else if(flag==2) {
			sql="select * from shaozr_学生成绩统计02,shaozr_学生02,shaozr_教师任课查询02\r\n" + 
					"where 学号=szr_学号02 and shaozr_教师任课查询02.szr_班级编号02=shaozr_学生02.szr_班级编号02 and shaozr_教师任课查询02.szr_课程编号02=shaozr_学生成绩统计02.课程编号 and szr_教师编号02=? and 课程编号=?  and 学年=? \r\n" + 
					"order by 成绩 asc";
		}else if(flag==3) {
			sql="select * from shaozr_学生成绩统计02,shaozr_学生02,shaozr_教师任课查询02\r\n" + 
					"where 学号=szr_学号02 and shaozr_教师任课查询02.szr_班级编号02=shaozr_学生02.szr_班级编号02 and shaozr_教师任课查询02.szr_课程编号02=shaozr_学生成绩统计02.课程编号 and szr_教师编号02=? and 课程编号=?  and 学年=? \r\n" + 
					"order by 成绩 DESC";
		}
		
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, teanum);
			preparedStatement.setString(2, counum);
			preparedStatement.setString(3, year);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<StudentBean> sList = new ArrayList<>();
			while(resultSet.next()) {
				StudentBean studentBean=new StudentBean();
				studentBean.setNum(resultSet.getString("学号"));
				studentBean.setName(resultSet.getString("姓名").trim());
				studentBean.setCoursename(resultSet.getString("课程名称").trim());
				studentBean.setYear(resultSet.getString("学年").trim());
				studentBean.setGpa(resultSet.getFloat("成绩"));
				sList.add(studentBean);
			}
			connection.close();
			return sList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
