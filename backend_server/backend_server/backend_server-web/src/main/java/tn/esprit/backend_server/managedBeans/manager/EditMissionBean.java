package tn.esprit.backend_server.managedBeans.manager;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.MissionService;
import esprit.tn.backend_server.Impl.UserService;

@ManagedBean
@ViewScoped
public class EditMissionBean {

	private String name;
	private String description;
	private String lieuxMission;
	private Date dateDebut;
	private Date dateFin;
	private Mission mission;
	
	
	private List<User> filteredUsers;
	private List<User> selectedUsers;
	private List<User> users;
	
	private User user ;

	private User selectedUser;
	

	@EJB
	UserService userService;
	
	@EJB
	MissionService missionService;


	
	
	
	

	

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLieuxMission() {
		return lieuxMission;
	}

	public void setLieuxMission(String lieuxMission) {
		this.lieuxMission = lieuxMission;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MissionService getMissionService() {
		return missionService;
	}

	public void setMissionService(MissionService missionService) {
		this.missionService = missionService;
	}
	
	
	 
    
	
}
