package tn.esprit.backend_server.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.MissionService;
import esprit.tn.backend_server.Impl.UserService;


@Path("/MissionService")
public class MissionWebService {

	@EJB
	MissionService missionService;	
	@EJB
	UserService userService;	

	//Tester le WebService 
	//url: http://localhost:9080/backend_server-web/rest/MissionService/status
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("status")
		public Response getStatus() {
			return Response.ok("{\"status\":\"Mission service is working\"}").build();
		}


	//retourne tous les missions
	//url: http://localhost:9080/backend_server-web/rest/MissionService/allMissions/*IDManager*
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("allMissions/{managerId}")
			public Response getMissions(@PathParam("managerId") int managerId) {
			String response = null;
			
			
			
			try {
						
				List<Mission> missions = missionService.getMissionByManagerIdRest(managerId);
				 
				 
				
				
				response = toJSONString(missionsAdapater(missions));
				 
				 
				
			} catch (Exception err) {
				response = "{\"status\":\"401\","
							+ "\"message\":\"No content found \""
							+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
					
					return Response.status(401).entity(response).build();
				}
				return Response.ok(response).build();
			
			
		}

	//Chercher mission par ID 
	//url: http://localhost:9080/backend_server-web/rest/MissionService/mission/*ID*

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("mission/{id}")
		public Response getMission(@PathParam("id") int missionId) {
			String response = null;
			
			try{
				
				
				Mission mission = missionService.getMissionById(missionId);
				
			
				response = toJSONString(missionAdapater(mission));
				
			}catch(Exception err){
				
				response = "{\"status\":\"401\"message\":\"No content found \"developerMessage\":\""+err.getMessage();
					
					return  Response.status(401).entity(response).build(); 
			
				}
				return Response.ok(response).build();
		}

	// Crée nouvelle mission 
	// url: http://localhost:9080/backend_server-web/rest/MissionService/create/*idManager

		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("create/{managerId}")
		public Response createMission(@PathParam("managerId") int managerId, String payload) {
			
			System.out.println("payload - " + payload);
			
			// Preparer le Gson pour lire l'objet a cree 
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyy-MM-dd");
			Gson gson = gsonBuilder.create();
			
			// Parser User en String avec Gson
			Mission mission = gson.fromJson(payload, Mission.class);
			String returnCode = "200";
			
			mission.setIdManager(managerId);
			
			// Insertion de l'User via JTA persistance avec Hibernate
			try {
				
				missionService.ajouterMission(mission);
				
				returnCode = "{"
						+ "\"href\":\"http://localhost:9080/backend_server-web/rest/MissionService/mission/"+mission.getId()+"\","
						+ "\"message\":\"New Mission successfully updated.\""
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


	// Update  mission 
	// url: http://localhost:9080/backend_server-web/rest/MissionService/edit/*IDmission*

		@PUT
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("edit/{id}")
		public Response updateUser(@PathParam("id") int missionId,
				String payload) {
			
				System.out.println("payload - " + payload);
				
				// Preparer le Gson pour lire l'objet a cree 
				GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.setDateFormat("yyy-MM-dd");
				
				Gson gson = gsonBuilder.create();
				
				
				// Parser User en String avec Gson
				Mission m = gson.fromJson(payload, Mission.class);
				m.setId(missionId);
				
				String returnCode = "200";
				
				System.out.println("Nom Mission : - " + m.getName());
				System.out.println("Description Mission :  - " + m.getDescription());
				
				
				
				// Update using JTA persistance with Hibernate
				try {
					
					
					
					missionService.updateMission(m);
					returnCode = "{"
				+ "\"href\":\"http://localhost:9080/backend_server-web/rest/MissionService/mission/"+m.getId()+"\","
				+ "\"message\":\"New Mission successfully updated.\""
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


	// Delete  mission 
	// url: http://localhost:9080/backend_server-web/rest/MissionService/delete/*IDmission*
		@DELETE
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("delete/{id}")
		public Response deleteMission(@PathParam("id") int missionId) {
		String returnCode = "";
		// Remove a book using JTA persistance with Hibernate
		try {
			
			missionService.deleteMissionById(missionId);
			
			returnCode = "{"
		+ "\"message\":\"mission succesfully deleted\""
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
	
		// Affecter employee a une mission 
		// url: http://localhost:9080/backend_server-web/rest/MissionService/affectUsers/*IDmission*

			@PUT
			@Consumes(MediaType.APPLICATION_JSON)
			@Produces(MediaType.APPLICATION_JSON)
			@Path("affectUsers/{missionId}")
			public Response affecterUserAMission(@PathParam("missionId") int missionId, String payload) {
					
					String returnCode = "";
					System.out.println("Mission Id:  - "+missionId);
					
					// Preparer le Gson pour lire l'objet a cree 
					Gson gson = new Gson();
					
					Pojo usersId = gson.fromJson(payload, Pojo.class);
					
				
					 returnCode = "200";
					
					try {
						
						
						//Mission mission = missionService.getMissionById(missionId);
					
						for(int id : usersId.getTest()) {
							
							
							missionService.affecterUserAMission(id, missionId);
						
						
						}
							
						
						
							
						returnCode = "{"
					+ "\"href\":\"http://localhost:9080/backend_server-web/rest/MissionService/mission/"+missionId+"\","
					+ "\"message\":\"New Users successfully Affected to Mission .\""
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

			// Desafecter employee d'une mission 
			// url: http://localhost:9080/backend_server-web/rest/MissionService/desaffectUsers/*IDmission*

				@PUT
				@Consumes(MediaType.APPLICATION_JSON)
				@Produces(MediaType.APPLICATION_JSON)
				@Path("desaffectUsers/{missionId}")
				public Response desaffecterUserAMission(@PathParam("missionId") int missionId, String payload) {
						
						String returnCode = "";
						System.out.println("Mission Id:  - "+missionId);
						
						// Preparer le Gson pour lire l'objet a cree 
						Gson gson = new Gson();
						
						Pojo usersId = gson.fromJson(payload, Pojo.class);
						
					
						 returnCode = "200";
						
						try {
							
							
							//Mission mission = missionService.getMissionById(missionId);
						
							for(int id : usersId.getTest()) {
								
								
								missionService.deleteUserFromMission(id);
							
							
							}
								
							
							
								
							returnCode = "{"
						+ "\"href\":\"http://localhost:9080/backend_server-web/rest/MissionService/mission/"+missionId+"\","
						+ "\"message\":\"New Users successfully Desaaffected to Mission .\""
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







	//Serializer les objets
		public String toJSONString(Object object) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyy-MM-dd"); // ISO8601 /
				// UTC
			Gson gson = gsonBuilder.create();
			return gson.toJson(object);
	}

			
			
	//Adapter un objets pour Json
		public Mission missionAdapater(Mission m) {
		
			Mission mission = new Mission();
			
			mission.setId(m.getId());
			mission.setName(m.getName());
			mission.setDescription(m.getDescription());
			mission.setDateDebut(m.getDateDebut());
			mission.setDateFin(m.getDateFin());
			mission.setPaysMission(m.getPaysMission());
			mission.setLieuMission(m.getLieuMission());
			mission.setProgression(m.getProgression());
			mission.setIdManager(m.getIdManager());
			
			return mission;
	}

		//Adapter une liste d'objets pour Json
			public List<Mission> missionsAdapater(List<Mission> missions) {
				
				List<Mission>AdaptedMissions= new ArrayList<Mission>() ;
				
				
				
				for(Mission m : missions) {
				
				Mission mission = new Mission();
					
				mission.setId(m.getId());
				mission.setName(m.getName());
				mission.setDescription(m.getDescription());
				mission.setDateDebut(m.getDateDebut());
				mission.setDateFin(m.getDateFin());
				mission.setPaysMission(m.getPaysMission());
				mission.setLieuMission(m.getLieuMission());
				mission.setProgression(m.getProgression());
				mission.setIdManager(m.getIdManager());
				
				/*
					System.out.println("*------------Mission dans Array--------------*");
					System.out.println(m.getId());
					System.out.println("*------------Missson a integré--------------*");
					System.out.println(mission.getId());
				*/
				
				AdaptedMissions.add(mission);
				
				}
				for(Mission m : AdaptedMissions) {
					System.out.println("*------------Result  Adapted_Array--------------*");
					System.out.println(m.getId());
				}
				
				return AdaptedMissions;
			}
			
	
			
	/*Classe provisoire pour methode*/
	class Pojo {
	    private int[] test;
	    public int[] getTest() {
	        return test;
	    }
	    public void setTest(int[] test) {
	        this.test = test;
	    }
	}



}
