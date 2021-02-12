package tn.esprit.backend_server.webservice;

import java.util.ArrayList;
import java.util.Calendar;
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

import esprit.tn.backend_server.Entities.AutreDemande;
import esprit.tn.backend_server.Entities.DemandeRemboursement;
import esprit.tn.backend_server.Entities.EtatDemande;
import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Impl.DemandeService;
import esprit.tn.backend_server.Impl.UserService;

@Path("/DemandeService")
public class DemandeWebService {

	@EJB
	UserService userService;	

	@EJB
	DemandeService demandeService;
	

		//Tester le WebService 
		//url: http://localhost:9080/backend_server-web/rest/DemandeService/status
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("status")
			public Response getStatus() {
				return Response.ok("{\"status\":\"Demande service is working\"}").build();
			}
		

/*-------------------------------------------------Demande de Remboursement--------------------------------------------------------*/		

		
		//retourne tous les demandes de remboursement
		//url: http://localhost:9080/backend_server-web/rest/DemandeService/allDemandeRemboursementEnAttentes
			
			@GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("allDemandeRemboursementEnAttentes")
				public Response getAllDemandeRemboursementEnAttentes() {
				String response = null;
				
				
				
				try {
							
					List<DemandeRemboursement> demandes = demandeService.getAllDemandeDeRemboursementEnAttentes();
					 
					 response = toJSONString(demandesRemboursementAdapater(demandes));
					
				} catch (Exception err) {
					response = "{\"status\":\"401\","
								+ "\"message\":\"No content found \""
								+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
						
						return Response.status(401).entity(response).build();
					}
					return Response.ok(response).build();
				
				
			}
			
			//retourne tous les demandes de remboursement traiter
			//url: http://localhost:9080/backend_server-web/rest/DemandeService/allDemandeRemboursementTraiter/*IDfinancier*
				
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				@Path("allDemandeRemboursementTraiter/{financierId}")
					public Response getAllDemandeRemboursementTraiter(@PathParam("financierId") int financierId) {
					String response = null;
					
					
					
					try {
								
						List<DemandeRemboursement> demandes = demandeService.getAllDemandeDeRemboursementTraiterForRest(financierId);
						 
						 response = toJSONString(demandesRemboursementAdapater(demandes));
						
					} catch (Exception err) {
						response = "{\"status\":\"401\","
									+ "\"message\":\"No content found \""
									+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
							
							return Response.status(401).entity(response).build();
						}
						return Response.ok(response).build();
					
					
				}
				
				
		
					//Chercher demande remboursement par ID 
					//url: http://localhost:9080/backend_server-web/rest/DemandeService/demandeRemboursement/*ID*

						@GET
						@Produces(MediaType.APPLICATION_JSON)
						@Path("demandeRemboursement/{id}")
						public Response getDemandeRemboursement(@PathParam("id") int demandeId) {
							String response = null;
							
							try{
								
								
								DemandeRemboursement demande = demandeService.getDemandeRemboursementById(demandeId);
								
							
								response = toJSONString(demandeRemboursementAdapater(demande));
								
							}catch(Exception err){
								
								response = "{\"status\":\"401\"message\":\"No content found \"developerMessage\":\""+err.getMessage();
									
									return  Response.status(401).entity(response).build(); 
							
								}
								return Response.ok(response).build();
						}

					// Crée nouvelle demande remboursement 
					// url: http://localhost:9080/backend_server-web/rest/DemandeService/demandeRemboursement/create/*IdUser*

						@POST
						@Consumes(MediaType.APPLICATION_JSON)
						@Produces(MediaType.APPLICATION_JSON)
						@Path("demandeRemboursement/create/{userId}")
						public Response createDemandeRemboursement(@PathParam("userId") int userId, String payload) {
							
							System.out.println("payload - " + payload);
							
							// Preparer le Gson pour lire l'objet a cree 
							GsonBuilder gsonBuilder = new GsonBuilder();
							gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss");
							Gson gson = gsonBuilder.create();
							
							// Parser User en String avec Gson
							DemandeRemboursement demande = gson.fromJson(payload, DemandeRemboursement.class);
							String returnCode = "200";
							
							demande.setEtatDemande(EtatDemande.EN_ATTENTE);
							demande.setDateEnvoie(Calendar.getInstance());
							
							
							// Insertion de l'User via JTA persistance avec Hibernate
							try {
								
								demandeService.ajouterDemandeRemboursement(demande, userId);
								
								returnCode = "{"
							+ "\"href\":\"http://localhost:9080/backend_server-web/rest/DemandeService/demandeRemboursement/"+demande.getId()+"\","
							+ "\"message\":\"New demande de remboursement successfully created.\""
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


					
					// Delete  demande de remboursement 
					// url: http://localhost:9080/backend_server-web/rest/DemandeService/demandeRemboursement/delete/*IDdemandeR*
						@DELETE
						@Consumes(MediaType.APPLICATION_JSON)
						@Produces(MediaType.APPLICATION_JSON)
						@Path("demandeRemboursement/delete/{id}")
						public Response deleteDemandeRemboursement(@PathParam("id") int demandeId) {
						String returnCode = "";
						// Remove a book using JTA persistance with Hibernate
						try {
							
							demandeService.deleteDemandeRemboursementById(demandeId);
							
							returnCode = "{"
						+ "\"message\":\"demande de rouboursement succesfully deleted\""
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
						
				
						// Accpeter  demande de remboursement 
						// url: http://localhost:9080/backend_server-web/rest/DemandeService/demandeRemboursement/accept/*IDFinancier*
							@PUT
							@Consumes(MediaType.APPLICATION_JSON)
							@Produces(MediaType.APPLICATION_JSON)
							@Path("demandeRemboursement/accept/{idFinancier}")
							public Response accepterDemandeRemboursement(@PathParam("idFinancier") int idFinancier, String payload) {
					
							
							
							System.out.println("payload - " + payload);
							
							// Preparer le Gson pour lire l'objet a cree 
							GsonBuilder gsonBuilder = new GsonBuilder();
							gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss");
							Gson gson = gsonBuilder.create();
							
							// Parser User en String avec Gson
							DemandeRemboursement demande = gson.fromJson(payload, DemandeRemboursement.class);
							String returnCode = "200";
							
							
							try {
								
							demandeService.acceptDemandeDeRemboursement(demande.getId(), idFinancier);
								
								returnCode = "{"
							+ "\"message\":\"demande de rouboursement succesfully Accepted\""
							+ "}";
							} catch (Exception err) {
								err.printStackTrace();
								returnCode = "{\"status\":\"500\",\"message\":\"Resource not deleted.\"\"developerMessage\":\""+err.getMessage()+"\""+
							"}";
									return  Response.status(500).entity(returnCode).build(); 
								}
								return Response.ok(returnCode).build();
							}
							
							// Rejeter  demande de remboursement 
							// url: http://localhost:9080/backend_server-web/rest/DemandeService/demandeRemboursement/reject/*IDFinancier*
								@PUT
								@Consumes(MediaType.APPLICATION_JSON)
								@Produces(MediaType.APPLICATION_JSON)
								@Path("demandeRemboursement/reject/{idFinancier}")
								public Response rejeterDemandeRemboursement(@PathParam("idFinancier") int idFinancier, String payload) {
						
								
								
								System.out.println("payload - " + payload);
								
								// Preparer le Gson pour lire l'objet a cree 
								GsonBuilder gsonBuilder = new GsonBuilder();
								gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss");
								Gson gson = gsonBuilder.create();
								
								// Parser User en String avec Gson
								DemandeRemboursement demande = gson.fromJson(payload, DemandeRemboursement.class);
								String returnCode = "200";
								
								
								try {
									
								demandeService.rejectDemandeDeRemboursement(demande.getId(), idFinancier);
									
									returnCode = "{"
								+ "\"message\":\"demande de rouboursement succesfully Rejected\""
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
								
				
							
		
/*-------------------------------------------------Autre Demande--------------------------------------------------------*/		
	
		//retourne tous les autres demandes
		//url: http://localhost:9080/backend_server-web/rest/DemandeService/allAutreDemandeAttentes
			
			@GET
			@Produces(MediaType.APPLICATION_JSON)
			@Path("allAutreDemandeAttentes")
				public Response getAllAutreDemandeEnAttentes() {
				String response = null;
				
				
				
				try {
							
					List<AutreDemande> demandes = demandeService.getAllAutreDemandeEnAttentes();
					 
					 response = toJSONString(autreDemandesAdapater(demandes));
					
				} catch (Exception err) {
					response = "{\"status\":\"401\","
								+ "\"message\":\"No content found \""
								+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
						
						return Response.status(401).entity(response).build();
					}
					return Response.ok(response).build();
				
				
			}
			
			//retourne tous les autres demandes traiter
			//url: http://localhost:9080/backend_server-web/rest/DemandeService/allAutreDemandeTraiter/*IDfinancier*
				
				@GET
				@Produces(MediaType.APPLICATION_JSON)
				@Path("allAutreDemandeTraiter/{managerId}")
					public Response getAllAutreDemandeTraiter(@PathParam("managerId") int managerId) {
					String response = null;
					
					
					
					try {
								
						List<AutreDemande> demandes = demandeService.getAllAutreDemandeTraiterForRest(managerId);
						 
						 response = toJSONString(autreDemandesAdapater(demandes));
						
					} catch (Exception err) {
						response = "{\"status\":\"401\","
									+ "\"message\":\"No content found \""
									+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
							
							return Response.status(401).entity(response).build();
						}
						return Response.ok(response).build();
					
					
				}
				
				
		
					//Chercher autre demande par ID 
					//url: http://localhost:9080/backend_server-web/rest/DemandeService/autreDemande/*ID*

						@GET
						@Produces(MediaType.APPLICATION_JSON)
						@Path("autreDemande/{id}")
						public Response getAutreDemande(@PathParam("id") int demandeId) {
							String response = null;
							
							try{
								
								
								AutreDemande demande = demandeService.getAutreDemandeById(demandeId);
								
							
								response = toJSONString(autreDemandeAdapater(demande));
								
							}catch(Exception err){
								
								response = "{\"status\":\"401\"message\":\"No content found \"developerMessage\":\""+err.getMessage();
									
									return  Response.status(401).entity(response).build(); 
							
								}
								return Response.ok(response).build();
						}

					// Crée nouvelle autre demande 
					// url: http://localhost:9080/backend_server-web/rest/DemandeService/autreDemande/create/*IdUser*

						@POST
						@Consumes(MediaType.APPLICATION_JSON)
						@Produces(MediaType.APPLICATION_JSON)
						@Path("autreDemande/create/{userId}")
						public Response createAutreDemande(@PathParam("userId") int userId, String payload) {
							
							System.out.println("payload - " + payload);
							
							// Preparer le Gson pour lire l'objet a cree 
							GsonBuilder gsonBuilder = new GsonBuilder();
							gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss");
							Gson gson = gsonBuilder.create();
							
							// Parser User en String avec Gson
							AutreDemande demande = gson.fromJson(payload, AutreDemande.class);
							String returnCode = "200";
							
							demande.setEtatDemande(EtatDemande.EN_ATTENTE);
							demande.setDateEnvoie(Calendar.getInstance());
							
							// Insertion de la demande via JTA persistance avec Hibernate
							try {
								
								demandeService.ajouterAutreDemande(demande, userId);
								
								returnCode = "{"
							+ "\"href\":\"http://localhost:9080/backend_server-web/rest/DemandeService/autreDemande/"+demande.getId()+"\","
							+ "\"message\":\"New autre demande successfully created.\""
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


					
						// Delete autre demande 
						// url: http://localhost:9080/backend_server-web/rest/DemandeService/autreDemande/delete/*IDAutredemande*
							@DELETE
							@Consumes(MediaType.APPLICATION_JSON)
							@Produces(MediaType.APPLICATION_JSON)
							@Path("autreDemande/delete/{id}")
							public Response deleteAutreDemande(@PathParam("id") int demandeId) {
							String returnCode = "";
							// Remove a book using JTA persistance with Hibernate
							try {
								
								demandeService.deleteAutreDemandeById(demandeId);
								
								returnCode = "{"
							+ "\"message\":\"demande de rouboursement succesfully deleted\""
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
							
				
						// Accpeter autre demande 
						// url: http://localhost:9080/backend_server-web/rest/DemandeService/autreDemande/accept/*IDManager*
							@PUT
							@Consumes(MediaType.APPLICATION_JSON)
							@Produces(MediaType.APPLICATION_JSON)
							@Path("autreDemande/accept/{idManager}")
							public Response accepterAutreDemande(@PathParam("idManager") int idManager, String payload) {
					
							
							
							System.out.println("payload - " + payload);
							
							// Preparer le Gson pour lire l'objet a cree 
							GsonBuilder gsonBuilder = new GsonBuilder();
							gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss");
							Gson gson = gsonBuilder.create();
							
							// Parser User en String avec Gson
							AutreDemande demande = gson.fromJson(payload, AutreDemande.class);
							String returnCode = "200";
							
							
							try {
								
							demandeService.acceptAutreDemande(demande.getId(), idManager);
								
								returnCode = "{"
							+ "\"message\":\"autre demande succesfully Accepted\""
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
							
							// Rejeter autre demande 
							// url: http://localhost:9080/backend_server-web/rest/DemandeService/autreDemande/reject/*IDManager*
								@PUT
								@Consumes(MediaType.APPLICATION_JSON)
								@Produces(MediaType.APPLICATION_JSON)
								@Path("autreDemande/reject/{idManager}")
								public Response rejeterAutreDemande(@PathParam("idManager") int idManager, String payload) {
						
								
								
								System.out.println("payload - " + payload);
								
								// Preparer le Gson pour lire l'objet a cree 
								GsonBuilder gsonBuilder = new GsonBuilder();
								gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss");
								Gson gson = gsonBuilder.create();
								
								// Parser User en String avec Gson
								AutreDemande demande = gson.fromJson(payload, AutreDemande.class);
								String returnCode = "200";
								
								
								try {
									
								demandeService.rejectAutreDemande(demande.getId(), idManager);
									
									returnCode = "{"
								+ "\"message\":\"autre demande succesfully Rejected\""
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
								
		
		
		
								
/*--------------------------------------------------Helpers-----------------------------------------------------------------------*/								

								
								
								
//################################## Demande de Remboursement##########################################										
				//Serializer les objets
				public String toJSONString(Object object) {
					GsonBuilder gsonBuilder = new GsonBuilder();
					gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss"); // ISO8601 /
						// UTC
					Gson gson = gsonBuilder.create();
					return gson.toJson(object);
			}							
				
				
				//Adapter un objets pour Json
				public DemandeRemboursement demandeRemboursementAdapater(DemandeRemboursement d) {
				
					DemandeRemboursement demande = new DemandeRemboursement();
					
					demande.setId(d.getId());
					demande.setDateEnvoie(d.getDateEnvoie());
					demande.setEtatDemande(d.getEtatDemande());
					demande.setRaison(d.getRaison());
					demande.setTypeFrais(d.getTypeFrais());
					demande.setMontant(d.getMontant());
					demande.setIdFinancier(d.getIdFinancier());
				
					return demande;
			}

				//Adapter une liste d'objets pour Json
					public List<DemandeRemboursement> demandesRemboursementAdapater(List<DemandeRemboursement> demandes) {
						
						List<DemandeRemboursement>AdaptedDemandes= new ArrayList<DemandeRemboursement>() ;
						
						
						
						for(DemandeRemboursement d : demandes) {
						
							DemandeRemboursement demande = new DemandeRemboursement();
							
							demande.setId(d.getId());
							demande.setDateEnvoie(d.getDateEnvoie());
							demande.setEtatDemande(d.getEtatDemande());
							demande.setRaison(d.getRaison());
							demande.setTypeFrais(d.getTypeFrais());
							demande.setMontant(d.getMontant());
							demande.setIdFinancier(d.getIdFinancier());
						
						
						
							AdaptedDemandes.add(demande);
						
						}
						
						return AdaptedDemandes;
					}
					
					
//##################################	Autre Demande ##########################################					
					
					//Adapter un objets pour Json
					public AutreDemande autreDemandeAdapater(AutreDemande d) {
					
						AutreDemande demande = new AutreDemande();
						
						demande.setId(d.getId());
						demande.setDateEnvoie(d.getDateEnvoie());
						demande.setEtatDemande(d.getEtatDemande());
						demande.setRaison(d.getRaison());
						demande.setTypeDemande(d.getTypeDemande());
						demande.setIdManager(d.getIdManager());
					
						return demande;
				}

					//Adapter une liste d'objets pour Json
						public List<AutreDemande> autreDemandesAdapater(List<AutreDemande> demandes) {
							
							List<AutreDemande>AdaptedDemandes= new ArrayList<AutreDemande>() ;
							
							
							
							for(AutreDemande d : demandes) {
							
								AutreDemande demande = new AutreDemande();
								
								demande.setId(d.getId());
								demande.setDateEnvoie(d.getDateEnvoie());
								demande.setEtatDemande(d.getEtatDemande());
								demande.setRaison(d.getRaison());
								demande.setTypeDemande(d.getTypeDemande());
								demande.setIdManager(d.getIdManager());
							
							
							
								AdaptedDemandes.add(demande);
							
							}
							
							return AdaptedDemandes;
						}			
					
					
								
								
	
}
