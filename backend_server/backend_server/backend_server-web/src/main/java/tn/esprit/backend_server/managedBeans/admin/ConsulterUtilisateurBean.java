package tn.esprit.backend_server.managedBeans.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import esprit.tn.backend_server.Entities.Etat;
import esprit.tn.backend_server.Entities.Role;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.UserService;


@ManagedBean
@ViewScoped
public class ConsulterUtilisateurBean {
	
	@EJB
	UserService UserService;
	
	private String prenom;
	private String nom;
	private String password;
	private String email;
	private Etat etat;
	private Role role;


	private List<User> users;

	public List<User> getUsers() {
	users = UserService.getAllUsers(); 
	return users;
	} 

	public void setUsers(List<User> Users) {
		this.users = Users;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserService getUserService() {
		return UserService;
	}

	public void setUserService(UserService UserService) {
		this.UserService = UserService;
	}

	
	
	private Integer UserIdToBeUpdated;

	public Integer getUserIdToBeUpdated() {
		return UserIdToBeUpdated;
	}

	public void setUserIdToBeUpdated(Integer UserIdToBeUpdated) {
		this.UserIdToBeUpdated = UserIdToBeUpdated;
	}

	public void displayUser(User empl) 
	{
	this.setPrenom(empl.getPrenom());
	this.setNom(empl.getNom());
	this.setEtat(empl.getEtat()); 
	this.setRole(empl.getRole());
	this.setEmail(empl.getEmail());
	this.setPassword(empl.getMdpLogin());
	this.setUserIdToBeUpdated(empl.getId());
	} 
/*
	public String addUser(){
		String navigateTo="null";
	//public void addUser(){
//		UserService.ajouterUser(new User(nom, prenom, email,role, isActif));
		// User(String nom, String prenom, String email, boolean isActif)
		
		UserService.ajouterUser(new User(nom, prenom, email, isActif));

		//navigateTo = "/login?faces-redirect=true";
	// navigateTo = "/pages/welcome?faces-redirect=true";
		return navigateTo;
	}
	
	*/
	public String removeUser(int UserId)
	{
		 
	UserService.deleteUserById(UserId); 
	
	return "null";
	}

	public void updateUser() 
	{ 
		User user = new User(UserIdToBeUpdated, prenom, nom , role , etat, email);
		System.out.println(user);
		
		UserService.updateUserIdJPQL(user);
		} 

		

}
