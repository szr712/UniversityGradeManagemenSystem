package shaoziruiMIS02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdmDao extends BaseDao {

	public AdmDao() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<TeacherBean> getTeacher() {
		String sql = "SELECT * FROM shaozr_教师02";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);	
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<TeacherBean> tList = new ArrayList<>();
			while (resultSet.next()) {
				TeacherBean teacherBean = new TeacherBean();
				teacherBean.setNum(resultSet.getString("szr_教师编号02").trim());
				teacherBean.setName(resultSet.getString("szr_姓名02").trim());
				teacherBean.setSex(resultSet.getString("szr_性别02").trim());
				teacherBean.setAge(resultSet.getInt("szr_年龄02"));
				teacherBean.setRank(resultSet.getString("szr_职称02").trim());
				teacherBean.setPhone(resultSet.getString("szr_电话02"));
				tList.add(teacherBean);
			}
			connection.close();
			return tList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public boolean addTeacher(TeacherBean teacherBean) {
		String sql="insert into shaozr_教师02 values(?,?,?,?,?,?,'123456')";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, teacherBean.getNum());
			preparedStatement.setString(2, teacherBean.getName());
			preparedStatement.setString(3, teacherBean.getSex());
			preparedStatement.setInt(4, teacherBean.getAge());
			preparedStatement.setString(5, teacherBean.getRank());
			preparedStatement.setString(6, teacherBean.getPhone());
			preparedStatement.executeUpdate();
			connection.close();
			return true;
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
		
	} 
	
	public boolean deleteTeacher(ArrayList<TeacherBean>tList,int i) {
		String sql="delete from shaozr_教师02 where szr_教师编号02=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,tList.get(i).getNum());
			preparedStatement.executeUpdate();
			connection.close();
			return true;	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
		
	} 
	
	public boolean changTeacher(TeacherBean teacherBean) {
		String sql="update shaozr_教师02\r\n" + 
				"set szr_姓名02=?,szr_性别02=?,szr_年龄02=?,szr_职称02=?,szr_电话02=?\r\n" + 
				"where szr_教师编号02=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, teacherBean.getName());
			preparedStatement.setString(2, teacherBean.getSex());
			preparedStatement.setInt(3, teacherBean.getAge());
			preparedStatement.setString(4, teacherBean.getRank());
			preparedStatement.setString(5, teacherBean.getPhone());
			preparedStatement.setString(6, teacherBean.getNum());
			preparedStatement.executeUpdate();
			connection.close();
			return true;	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
		
	}

}
