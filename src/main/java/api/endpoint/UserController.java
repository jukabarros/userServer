package api.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.dao.UserDAO;
import api.model.Error;
import api.model.User;
import api.util.JsonParser;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public ResponseEntity<?> getUsers(HttpServletRequest request,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "dateBegin", required = false) String dateBegin,
			@RequestParam(value = "dateEnd", required = false) String dateEnd) {
		
		List<User> users = new ArrayList<User>();
		String source = request.getHeader("Referer");
		String ip = request.getRemoteAddr();
	 	try {
	 		if (name != null){
	 			users = userDAO.findByName(name);
	 			
	 		}else if (gender != null){
	 			users = userDAO.findByGender(gender);
	 			
	 		}else if (dateBegin != null && dateEnd != null){
	 			users = userDAO.findBy2DateOfBirth(dateBegin, dateEnd);
	 			
	 		}
	 		else{
	 			users = userDAO.getUsers();
	 			
	 		}
	 		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	 		
	 	} catch (Exception e) {
	 		System.err.println(e.getMessage());
	 		return new ResponseEntity<Error>(new Error(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	 	}	 	
	}
	
	@RequestMapping(value = "/{idUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public ResponseEntity<?> getUserByID(HttpServletRequest request, 
			@PathVariable int idUser) {
	 	try {
	 		User user = userDAO.findByID(idUser);
	 		if (user.getName() == null){
	 			return new ResponseEntity<Error>(new Error(404, "Usuário não encontrado"), HttpStatus.NOT_FOUND); 
	 		}
	 		
	 		return new ResponseEntity<User>(user, HttpStatus.OK);
	 		
	 	} catch (Exception e) {
	 		System.err.println(e.getMessage());
	 		return new ResponseEntity<Error>(new Error(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	 	} 	
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public ResponseEntity<?> postUser(HttpServletRequest request, @RequestBody String json) {
		
	 	try {
	 		JsonParser jsonParser = new JsonParser();
	 		
	 		User user = jsonParser.jsonToUser(json);
	 		this.userDAO.addUser(user);
	 		
	 		return new ResponseEntity<String>(HttpStatus.OK);
	 		
	 	} catch (Exception e) {
	 		System.err.println(e.getMessage());
	 		return new ResponseEntity<Error>(new Error(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	 	}	 	
	}
	
	@RequestMapping(value = "/{idUser}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public ResponseEntity<?> deleteUserByID(HttpServletRequest request, 
			@PathVariable int idUser) {
	 	try {
	 		
	 		User user = this.userDAO.findByID(idUser);
	 		if (user.getName() != null){
	 			this.userDAO.deleteUser(idUser);
	 			return new ResponseEntity<String>(HttpStatus.OK);
	 			
	 		}
	 		
	 		return new ResponseEntity<Error>(new Error(404, "Usuário não encontrado"), HttpStatus.NOT_FOUND); 
	 		
	 	} catch (Exception e) {
	 		System.err.println(e.getMessage());
	 		return new ResponseEntity<Error>(new Error(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	 	} 	
	}
	
	@RequestMapping(value = "/{idUser}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
	public ResponseEntity<?> putUser(HttpServletRequest request, @RequestBody String json,
			@PathVariable int idUser) {
		
	 	try {
	 		JsonParser jsonParser = new JsonParser();
	 		
	 		
	 		User checkUser = this.userDAO.findByID(idUser);
	 		if (checkUser.getName() == null){
	 			return new ResponseEntity<Error>(new Error(404, "Usuário não encontrado"), HttpStatus.NOT_FOUND); 
	 		}
	 		
	 		User userParser = jsonParser.jsonToUser(json);
	 		userParser.setId(idUser);
	 		
	 		this.userDAO.updateUser(userParser);
	 		
	 		return new ResponseEntity<String>(HttpStatus.OK);
	 		
	 	} catch (Exception e) {
	 		System.err.println(e.getMessage());
	 		return new ResponseEntity<Error>(new Error(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	 	}	 	
	}
}
