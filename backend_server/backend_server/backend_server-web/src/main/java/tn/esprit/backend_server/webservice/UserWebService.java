package tn.esprit.backend_server.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.UserService;




@Path("/UserService")
public class UserWebService {


	@EJB
	UserService userService;	
	
	//Tester le WebService 
	//url: http://localhost:9080/backend_server-web/rest/UserService/status
	
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("status")
		public Response getStatus() {
			return Response.ok("{\"status\":\"User service is working\"}").build();
		}
		
	
	

	//retourne tous les utilisateurs
	//url: http://localhost:9080/backend_server-web/rest/UserService/allUsers
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("allUsers")
		public Response getUsers() {
			String response = null;
			
			
			
			try {
						
				List<User> users = userService.getAllUsers();
				 
				 response = toJSONString(usersAdapater(users));
				
			} catch (Exception err) {
				response = "{\"status\":\"401\","
						+ "\"message\":\"No content found \""
						+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
				
				return Response.status(401).entity(response).build();
			}
			return Response.ok(response).build();
			
			
		}

	//retourne tous les employees
	//url: http://localhost:9080/backend_server-web/rest/UserService/allEmployees
			
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("allEmployees")
		public Response getAllEmployees() {
			String response = null;
			
			
			
			try {
						
				List<User> users = userService.getAllEmployees();
				 
				 response = toJSONString(usersAdapater(users));
				
			} catch (Exception err) {
				response = "{\"status\":\"401\","
						+ "\"message\":\"No content found \""
						+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
				
				return Response.status(401).entity(response).build();
			}
			return Response.ok(response).build();
			
			
		}
	
	
	//retourne tous les managers
	//url: http://localhost:9080/backend_server-web/rest/UserService/allManagers
						
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("allManagers")
		public Response getAllManagers() {
			String response = null;
			
			
			
			try {
						
				List<User> users = userService.getAllManagers();
				 
				 response = toJSONString(usersAdapater(users));
				
			} catch (Exception err) {
				response = "{\"status\":\"401\","
						+ "\"message\":\"No content found \""
						+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
				
				return Response.status(401).entity(response).build();
			}
			return Response.ok(response).build();
			
			
		}
		

	
	//retourne tous les financiers
	//url: http://localhost:9080/backend_server-web/rest/UserService/allFinanciers
							
			@GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("allFinanciers")
			public Response getAllFinanciers() {
				String response = null;
				
				
				
				try {
							
					List<User> users = userService.getAllFinanciers();
					 
					 response = toJSONString(usersAdapater(users));
					
				} catch (Exception err) {
					response = "{\"status\":\"401\","
							+ "\"message\":\"No content found \""
							+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
					
					return Response.status(401).entity(response).build();
				}
				return Response.ok(response).build();
				
				
			}
	
		
			//retourne tous les employees disponible
			//url: http://localhost:9080/backend_server-web/rest/UserService/allEmployeesDispo
					
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				@Path("allEmployeesDispo")
				public Response getAllEmployeesDisponible() {
					String response = null;
					
					
					
					try {
								
						List<User> users = userService.getAllEmployeesDisponible();
						 
						 response = toJSONString(usersAdapater(users));
						
					} catch (Exception err) {
						response = "{\"status\":\"401\","
								+ "\"message\":\"No content found \""
								+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
						
						return Response.status(401).entity(response).build();
					}
					return Response.ok(response).build();
					
					
				}
				
				
				//retourne tous les employees indisponible
				//url: http://localhost:9080/backend_server-web/rest/UserService/allEmployeesIndispo
						
					@GET
					@Produces(MediaType.APPLICATION_JSON)
					@Path("allEmployeesIndispo")
					public Response getAllEmployeesIndisponible() {
						String response = null;
						
						
						
						try {
									
							List<User> users = userService.getAllEmployeesIndisponible();
							 
							 response = toJSONString(usersAdapater(users));
							
						} catch (Exception err) {
							response = "{\"status\":\"401\","
									+ "\"message\":\"No content found \""
									+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
							
							return Response.status(401).entity(response).build();
						}
						return Response.ok(response).build();
						
						
					}
		
	
	
	
	
	//Chercher Utilisateur par ID 
	//url: http://localhost:9080/backend_server-web/rest/UserService/user/*ID*
	
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("user/{id}")
		public Response getUser(@PathParam("id") int userId) {
			String response = null;
			
			try{
				
				
				User user = userService.getUserById(userId);
				
			
				response = toJSONString(userAdapater(user));
				
			}catch(Exception err){
				
				response = "{\"status\":\"401\","+
							"\"message\":\"No content found \""+
							"\"developerMessage\":\""+err.getMessage()+"\""+
							"}";
				
				return  Response.status(401).entity(response).build(); 
	
			}
			return Response.ok(response).build();
		}
		
	

	
	
	// Crée nouveau utilisateur 
	// url: http://localhost:9080/backend_server-web/rest/UserService/create
		
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("create")
		public Response createUser(String payload) {
	
			System.out.println("payload - " + payload);
	
			// Preparer le Gson pour lire l'objet a cree 
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Gson gson = gsonBuilder.create();
	
			// Parser User en String avec Gson
			User user = gson.fromJson(payload, User.class);
			String returnCode = "200";
			
	
			// Insertion de l'User via JTA persistance avec Hibernate
			try {
				
				userService.ajouterUser(user);
				
				returnCode = "{"
						+ "\"href\":\"http://localhost:9080/backend_server-web/rest/UserService/user/"+user.getId()+"\","
						+ "\"message\":\"New User successfully created.\""
						+ "}";
			} catch (Exception err) {
				err.printStackTrace();
				returnCode = "{\"status\":\"500\","+
						"\"message\":\"Resource not created.\""+
						"\"developerMessage\":\""+err.getMessage()+"\""+
						"}";
				return  Response.status(404).entity(returnCode).build(); 
	
			}
			return  Response.status(201).entity(returnCode).build(); 
	
		}
		
		
	// Update  utilisateur 
	// url: http://localhost:9080/backend_server-web/rest/UserService/edit/*IDuser*
	
		@PUT
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("edit/{id}")
		public Response updateUser(@PathParam("id") int userId,
				String payload) {
	
			System.out.println("payload - " + payload);
	
			// Preparer le Gson pour lire l'objet a cree 
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			
			Gson gson = gsonBuilder.create();
	
			
			// Parser User en String avec Gson
			User u = gson.fromJson(payload, User.class);
			u.setId(userId);
			
			String returnCode = "200";
	
			System.out.println("Nom User : - " + u.getNom());
			System.out.println("Prenom User :  - " + u.getPrenom());
		
			
	
			// Update using JTA persistance with Hibernate
			try {
				
				
				
				userService.updateUser(u);
				returnCode = "{"
						+ "\"href\":\"http://localhost:9080/backend_server-web/rest/UserService/user/"+u.getId()+"\","
						+ "\"message\":\"New user successfully updated.\""
						+ "}";
			} catch (Exception err) {
				err.printStackTrace();
				returnCode = "{\"status\":\"304\","+
						"\"message\":\"Resource not modified.\""+
						"\"developerMessage\":\""+err.getMessage()+"\""+
						"}";
				return  Response.status(304).entity(returnCode).build(); 
			}
			return  Response.status(200).entity(returnCode).build(); 
		}
		
		
	// Delete  utilisateur 
	// url: http://localhost:9080/backend_server-web/rest/UserService/delete/*IDuser*
		
		@DELETE
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("delete/{id}")
		public Response deleteUser(@PathParam("id") int userId) {
			String returnCode = "";
			// Remove a book using JTA persistance with Hibernate
			try {
				
				userService.deleteUserById(userId);
				
				returnCode = "{"
						+ "\"message\":\"User succesfully deleted\""
						+ "}";
			} catch (Exception err) {
				err.printStackTrace();
				returnCode = "{\"status\":\"500\","+
						"\"message\":\"Resource not deleted.\""+
						"\"developerMessage\":\""+err.getMessage()+"\""+
						"}";
				return  Response.status(500).entity(returnCode).build(); 
			}
			return Response.ok(returnCode).build();
		}
		
		
		
	
	
	
	
	//Serializer les objets
	
	
	public String toJSONString(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // ISO8601 /
																	// UTC
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
	
	//Adapter un objets pour Json
	
	public User userAdapater(User u) {
		
		User user = new User();
		
		user.setId(u.getId());
		user.setNom(u.getNom());
		user.setPrenom(u.getPrenom());
		user.setAdresse(u.getAdresse());
		user.setActif(u.isActif());
		user.setRole(u.getRole());
		user.setTel(u.getTel());
		user.setEmail(u.getEmail());
		user.setEtat(u.getEtat());
		
		return user;
	}
	
	//Adapter une liste d'objets pour Json
		
	public List<User> usersAdapater(List<User> users) {
			
			List<User>AdaptedUsers= new ArrayList<User>() ;
			
			
			
			for(User u : users) {
				
			User user = new User();
			
			user.setId(u.getId());
			user.setNom(u.getNom());
			user.setPrenom(u.getPrenom());
			user.setAdresse(u.getAdresse());
			user.setActif(u.isActif());
			user.setRole(u.getRole());
			user.setTel(u.getTel());
			user.setEmail(u.getEmail());
			user.setEtat(u.getEtat());
			
			AdaptedUsers.add(user);
			
			}
			
			return AdaptedUsers;
		}
	
	
}
