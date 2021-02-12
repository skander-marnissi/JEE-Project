package tn.esprit.backend_server.managedBeans.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.button.Button;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.neovisionaries.i18n.CountryCode;

import esprit.tn.backend_server.Entities.Etat;
import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.Progress;
import esprit.tn.backend_server.Entities.User;
import javax.servlet.http.HttpSession;
import esprit.tn.backend_server.Impl.MissionService;
import esprit.tn.backend_server.Impl.UserService;
import tn.esprit.backend_server.managedBeans.SessionUtils;


@ManagedBean
@ViewScoped
public class AjoutMissionBean {
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
	
	private String pays;
	private int idManager;
	
	
	private boolean isOk = false;
	private List<String> countryName;
	
	//injection de dépendences

	@EJB
	UserService userService;
	
	@EJB
	MissionService missionService;


	
	@PostConstruct
	public void init(){
		users= userService.getAllEmployeesDisponible();
		HttpSession session = SessionUtils.getSession();
		idManager = (int) session.getAttribute("userid");
	}
	 
    
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
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
 
    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }
	
    public String redirectToConsulter() {
    	
    	if(!this.isOk) {
    		
    		
    		return "null";
    	
    	}
    		
    	
    	return "/pages/manager/ConsulterMission?faces-redirect=true";
    }
	
	public void doAjout(){
	
		System.out.println("i'm in doAjout");
		//System.out.println(selectedUsers.toString());
		
		if(checkDate(dateDebut,dateFin)) {
		
			
			System.out.print("-----------countryName: -------------->"+pays);
			mission = new Mission(name,description,lieuxMission,dateDebut,dateFin,pays);
			
			mission.setIdManager(idManager);
			
			int mission_id = missionService.ajouterMission(mission);
			// = missionService.get
			
			for (User user : selectedUsers) {
				System.out.println("in boucle for");
				userService.affecterUserAMission2(user.getId(), mission_id);
			}
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Mission Ajoutée ! "));
			
			this.isOk = true;
			
		}		
	else {
		FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Erreur veuillez vérifier vos champs"));
	
		this.isOk = false;
	}	
	
	
	

		}

	public boolean checkDate(Date dateDebut,Date dateFin) {
		System.out.println(dateDebut.before(dateFin));
		return dateDebut.before(dateFin);
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


	public List<User> getFilteredUsers() {
		return filteredUsers;
	}


	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
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




	public int getIdManager() {
		return idManager;
	}


	public void setIdManager(int idManager) {
		this.idManager = idManager;
	}


	public boolean isOk() {
		return isOk;
	}


	public void setOk(boolean isOk) {
		this.isOk = isOk;
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}




	public String getPays() {
		return pays;
	}


	public void setPays(String pays) {
		this.pays = pays;
	}


	public List<String> getCountryName() {
		return countryName;
	}


	public void setCountryName(List<String> countryName) {
		this.countryName = countryName;
	}




	

}
