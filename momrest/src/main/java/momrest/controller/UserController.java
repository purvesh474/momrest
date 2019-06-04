package momrest.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		List<User> userList=userServ.getAllUsers();
		if(userList.isEmpty() || userList.size()==0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No User Found!");
		}
		return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("id/{id}")
	public ResponseEntity<User> getUserByID(@PathVariable(value="id") int id) throws ResourceNotFoundException{
		//User user=userServ.getUserByID(id);
		User user=userServ.getUserByID(id);
		if (user==null)
		{
		throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found!");
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
		
	}
	
	@PostMapping("add")
	public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder builder){
		
		boolean flag=userServ.addUser(user);
		if(flag==false) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,"User Already exists!");
		}
		HttpHeaders headers=new HttpHeaders();
		headers.setLocation(builder.path("/id/{id}").buildAndExpand(user.getUserid()).toUri());
		return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
	}
	
	
	@PutMapping("update")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		try {
		userServ.updateUser(user);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Something Went Wrong");
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value="id") int  id){
		try {
		userServ.deleteUser(id);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Requested User Not Found!");
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
	@PostMapping("login")
	public ResponseEntity<User> checkUserLogin(@RequestBody Map<String, String> userDetails){
		List<User> user=null;
		if(userDetails!=null) {
		String email=userDetails.get("email");
		String password=userDetails.get("password");
		
		user=userServ.checkUserLogin(email, password);
		if(user.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid Login credential");
			
		}
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Data");
		}
		
		
		return new ResponseEntity<User>(user.get(0),HttpStatus.OK);
	}
	
	
}
