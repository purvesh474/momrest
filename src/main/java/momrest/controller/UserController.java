package momrest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisNoOpBindingRegistry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import momrest.exception.ResourceNotFoundException;
import momrest.model.User;
import momrest.service.IUserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private IUserService userServ;
	
	@GetMapping("all")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> userList=new ArrayList<>();
		try {
			userList=userServ.getAllUsers();
		if(userList.isEmpty() || userList.size()==0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("id/{id}")
	public ResponseEntity<User> getUserByID(@PathVariable(value="id") int id) throws ResourceNotFoundException{
		User user=null;
		try {
		user=userServ.getUserByID(id);
		if (user==null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
		
	}
	
	@PostMapping("add")
	public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder builder){
		boolean flag=false;
		try {
		flag=userServ.addUser(user);
		if(flag==false) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers=new HttpHeaders();
		headers.setLocation(builder.path("/id/{id}").buildAndExpand(user.getUserid()).toUri());
		return new ResponseEntity<Void>(headers,HttpStatus.OK);
	}
	
	
	@PutMapping("update")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		try {
		userServ.updateUser(user);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value="id") int  id){
		try {
		userServ.deleteUser(id);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
	public ResponseEntity<User> checkUserLogin(@RequestBody Map<String, String> userDetails){
		List<User> user=new ArrayList<>();
		try {
		if(userDetails!=null) {
		String email=userDetails.get("email");
		String password=userDetails.get("password");
		
		user=userServ.checkUserLogin(email, password);
		if(user.isEmpty()) {
			System.out.println("Insider");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		}
		catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(user.get(0),HttpStatus.OK);
	}
	
	
}
