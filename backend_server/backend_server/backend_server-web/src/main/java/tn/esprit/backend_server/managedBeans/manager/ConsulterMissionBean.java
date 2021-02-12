package tn.esprit.backend_server.managedBeans.manager;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.MissionService;
import esprit.tn.backend_server.Impl.UserService;
import tn.esprit.backend_server.managedBeans.SessionUtils;

@ManagedBean
@SessionScoped
public class ConsulterMissionBean {

	//injection de dépendences
	
	private List<Mission> filteredMissions;
	private List<Mission> selectedMissions;
	private List<Mission> missions;
	
	private List<User> filteredUsers;
	private List<User> selectedUsers;
	private List<User> users;
	
	private Mission selectedMission;
	private User user ;
	private Mission mission;
	private User selectedUser;
	
	private String pays;
	
	

	@EJB
	UserService userService;
	
	@EJB
	MissionService missionService;


	
	@PostConstruct
	public void init(){
		
		
		users= userService.getAllEmployeesDisponible();
		
		HttpSession session = SessionUtils.getSession();
		int idManager = (int) session.getAttribute("userid");
		
		missions= missionService.getMissionNonFinishedByManagerId(idManager);
		
		for(Mission m  : missions) {
			System.out.println("----------");
		}
		
	}
	 
	public String removeMission(int missionId)
	{
		
	String navigateTo="/pages/manager/ConsulterMission?faces-redirect=true";
	missionService.deleteMissionById(missionId);
	FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Mission supprimée!"));
	
	
		this.init();
		return navigateTo;
	}
	
	public String editMission()
	{
		
	String navigateTo="/pages/manager/EditMission?faces-redirect=true";
	
	
	return navigateTo;
	}


    
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        
       //System.out.println(users);
        
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);
 
        Mission mission = (Mission) value;
        return mission.getId() < filterInt
                || mission.getName().toLowerCase().contains(filterText)
                || mission.getLieuMission().toLowerCase().contains(filterText);
                
               
               
    }
    
    public boolean globalUserFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        
       //System.out.println(users);
        
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);
 
        User user = (User) value;
        return user.getId() < filterInt
                || user.getNom().toLowerCase().contains(filterText)
                || user.getPrenom().toLowerCase().contains(filterText)
                || user.getAdresse().toLowerCase().contains(filterText)
                || user.getTel().toLowerCase().contains(filterText)
                || (user.isActif() ? "Actif" : "Bloquer").contains(filterText);
    }
 
    public String removeUserFromMission(int userId) {
    	
    	String navigateTo="/pages/manager/ConsulterMission?faces-redirect=true";
    	missionService.deleteUserFromMission(userId);
    	FacesContext.getCurrentInstance().addMessage("form:btn2", new FacesMessage("Employer supprimé de la mission !"));
		
		
    		
    	this.init();
    	
    	return navigateTo;
    	
    }
    
public String removeUserFromMissionUpdate(int userId) {
    	
    	String navigateTo="/pages/manager/EditMission?faces-redirect=true";
    	missionService.deleteUserFromMission(userId);
    	
    	selectedMission = missionService.getMissionById(selectedMission.getId());
    	
    	FacesContext.getCurrentInstance().addMessage("form:btn3", new FacesMessage("Employe supprimé de la mission !"));
		
    		
    	this.init();
    	
    	return navigateTo;
    	
    }
    
    
    
    public String doUpdate(int missionId){
		String navigateTo="/pages/manager/ConsulterMission?faces-redirect=true";
		System.out.println("i'm in doAjout");
		//System.out.println(selectedUsers.toString());
		
		
		if(!(selectedMission.getName().isEmpty()) && !(selectedMission.getDescription().isEmpty()) && !(selectedMission.getLieuMission().isEmpty()) && (selectedMission.getDateDebut()!=null) && (selectedMission.getDateFin()!=null)) {
		
			mission = missionService.getMissionById(missionId);
			
			System.out.println(" - - - - - - - - selectedUser From Edit : "+ selectedMission.getName());
			mission.setName(selectedMission.getName());
			mission.setDescription(selectedMission.getDescription());
			mission.setLieuMission(selectedMission.getLieuMission());
			mission.setDateDebut(selectedMission.getDateDebut());
			mission.setDateFin(selectedMission.getDateFin());
			
			missionService.updateMission(mission);
			
			if(selectedUsers.size()>0) {
				
				for (User user : selectedUsers) {
					System.out.println("in boucle for");
					userService.affecterUserAMission2(user.getId(), missionId);
				}
				
			}
			
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Update effectuer"));
			
			this.init();
		}		
	else {
		
		FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Erreur"));
		return "null";
		
	}	
	
		return navigateTo;
	

		}

    
    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }


    
    
    
	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public User getSelectedUser() {
		return selectedUser;
	}



	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}



	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public Mission getSelectedMission() {
		return selectedMission;
	}


	public void setSelectedMission(Mission selectedMission) {
		this.selectedMission = selectedMission;
	}


	public List<Mission> getFilteredMissions() {
		return filteredMissions;
	}


	public void setFilteredMissions(List<Mission> filteredMissions) {
		this.filteredMissions = filteredMissions;
	}


	public List<Mission> getSelectedMissions() {
		return selectedMissions;
	}


	public void setSelectedMissions(List<Mission> selectedMissions) {
		this.selectedMissions = selectedMissions;
	}


	public List<Mission> getMissions() {
		return missions;
	}


	public void setMissions(List<Mission> missions) {
		this.missions = missions;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Mission getMission() {
		return mission;
	}


	public void setMission(Mission mission) {
		this.mission = mission;
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

	

    
	
	

}
