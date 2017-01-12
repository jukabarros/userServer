package api.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import api.model.User;

@Repository("userDAO")
public class UserDAO {
	
	private List<User> users = this.getAllUsers();
	
	
	public List<User> getAllUsers(){
		List<User> allUsers = new ArrayList<User>();
		allUsers.add(this.createUser(1, "teste0", this.convertDate("14/02/1991"), "M"));
		allUsers.add(this.createUser(2, "teste1", this.convertDate("27/01/2016"), "M"));
		allUsers.add(this.createUser(3, "teste2", this.convertDate("01/02/2015"), "F"));
		allUsers.add(this.createUser(4, "teste3", this.convertDate("22/02/1993"), "F"));
		allUsers.add(this.createUser(6, "ABCDEFFGH ASIJU", this.convertDate("08/06/2001"), "M"));
		allUsers.add(this.createUser(7, "Teeeeeeeeste 7", this.convertDate("08/06/2001"), "F"));
		allUsers.add(this.createUser(8, "Nome de um usuário muito grande mesmo", this.convertDate("08/06/2001"), "M"));
		allUsers.add(this.createUser(9, "Nominho", this.convertDate("08/06/2001"), "F"));
		allUsers.add(this.createUser(10, "Teste 10", this.convertDate("08/06/2001"), "F"));
		
	
		return allUsers;
	}
	
	/*
	 * FILTROS
	 */
	
	/**
	 * filtrando pelo nome
	 * @param name nome
	 * @return lista filtrada
	 */
	public List<User> findByName(String name){
		List<User> allUsersByName = new ArrayList<User>();
		
		for (User user : users) {
			if (user.getName().contains(name)){
				allUsersByName.add(user);
			}
		}
		return allUsersByName;
	}
	
	/**
	 * filtrando pelo sexo
	 * @param gender sexo
	 * @return lista filtrada
	 */
	public List<User> findByGender(String gender){
		List<User> allUsersByGender = new ArrayList<User>();
		
		for (User user : users) {
			if (user.getGender().equals(gender)){
				allUsersByGender.add(user);
			}
		}
		return allUsersByGender;
	}
	
	/**
	 * filtrando entre 2 datas de nascimento
	 * @param gender nome
	 * @return lista filtrada
	 * @throws ParseException parser dateSTR to date
	 */
	public List<User> findBy2DateOfBirth(String dateBeginStr, String dateEndStr) throws ParseException{
		List<User> allUsersByDate = new ArrayList<User>();
		
		Date dateBegin = new SimpleDateFormat("dd-MM-yyyy").parse(dateBeginStr);
		
		Date dateEnd = new SimpleDateFormat("dd-MM-yyyy").parse(dateEndStr);
		
		for (User user : users) {
			if (user.getDateOfBirth().after(dateBegin) && user.getDateOfBirth().before(dateEnd)){
				allUsersByDate.add(user);
			}
		}
		return allUsersByDate;
	}
	
	/*
	 * Consulta Exata
	 */
	
	/**
	 * Consulta pelo ID
	 * @param id id
	 * @return usuario com o id pesquisado
	 */
	public User findByID(int id){
		User userByID = new User();
		for (User user : users) {
			if (user.getId() == id){
				userByID = user;
			}
		}
		return userByID;
	}
	
	/*
	 * ADD, REMOVE AND UPDATE
	 */
	
	public void addUser (User user){
		int userID = (int) (new Date().getTime());
		user.setId(userID);
		
		users.add(user);
	}
	
	public void deleteUser (int id){
		for (User user : users) {
			if (user.getId() == id){
				users.remove(user);
				break;
			}
		}
	}
	
	public void updateUser (User userUpdate){
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == userUpdate.getId()){
				users.get(i).setName(userUpdate.getName());
				users.get(i).setGender(userUpdate.getGender());
				users.get(i).setDateOfBirth(userUpdate.getDateOfBirth());
			}
		}
	}

	
	/*
	 * METODOS DEFAULT
	 */
	private User createUser(int id, String name, Date dateOfbirth, String gender){
		User newUser = new User();
		newUser.setId(id);
		newUser.setName(name);
		newUser.setDateOfBirth(dateOfbirth);
		newUser.setGender(gender);
		
		return newUser;
	}
	
	private Date convertDate(String dateSTR){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = sdf.parse(dateSTR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;


	}
	
	public List<User> getUsers() {
		return users;
	}
	
}
