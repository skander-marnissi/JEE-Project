package tn.esprit.backend_server.managedBeans.manager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import esprit.tn.backend_server.Entities.AutreDemande;
import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.DemandeService;
import esprit.tn.backend_server.Impl.MissionService;
import esprit.tn.backend_server.Impl.UserService;
import tn.esprit.backend_server.managedBeans.SessionUtils;

@ManagedBean
@ViewScoped
public class HistoriqueAutreDemandeBean {

	private List<AutreDemande> filteredDemandes;
	private List<AutreDemande> selectedDemandes;
	private List<AutreDemande> demandes;
	private List<AutreDemande> demandesTraiter;
	
	private Mission selectedMission;
	private User selectedUser;
	private AutreDemande selectedDemande;
	
	private User user ;
	private Mission mission;
	private AutreDemande demande;
	
	private int idManager;

	@EJB
	UserService userService;
	
	@EJB
	DemandeService demandeService;
	
	@EJB
	MissionService missionService;


	@PostConstruct
	public void init(){
		
		
	
		HttpSession session = SessionUtils.getSession();
		idManager = (int) session.getAttribute("userid");
		
		demandesTraiter = demandeService.getAllAutreDemandeTraiter(idManager);
		
		for(AutreDemande d : demandesTraiter)
		System.out.println("---demande id in Historique autre demandes :  "+ d.getId());
		
	
		
	}
	 
	


    
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        
       //System.out.println(users);
        
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);
 
        AutreDemande demande = (AutreDemande) value;
        return demande.getId() < filterInt
                || demande.getRaison().toLowerCase().contains(filterText)
                || demande.getTypeDemande().toString().toLowerCase().contains(filterText);
        	 
               
               
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
 
    
   

    
    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }
    
	public String formatDate(Calendar date) {
			
			
		    SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd 'a' HH:mm:ss");
		    String formatted = format1.format(date.getTime());
		    
		    return formatted;
		}

    
    
	public List<AutreDemande> getDemandesTraiter() {
		return demandesTraiter;
	}

	public void setDemandesTraiter(List<AutreDemande> demandesTraiter) {
		this.demandesTraiter = demandesTraiter;
	}

	public List<AutreDemande> getFilteredDemandes() {
		return filteredDemandes;
	}

	public void setFilteredDemandes(List<AutreDemande> filteredDemandes) {
		this.filteredDemandes = filteredDemandes;
	}

	public List<AutreDemande> getSelectedDemandes() {
		return selectedDemandes;
	}

	public void setSelectedDemandes(List<AutreDemande> selectedDemandes) {
		this.selectedDemandes = selectedDemandes;
	}

	public List<AutreDemande> getDemandes() {
		return demandes;
	}

	public void setDemandes(List<AutreDemande> demandes) {
		this.demandes = demandes;
	}

	public AutreDemande getSelectedDemande() {
		return selectedDemande;
	}

	public void setSelectedDemande(AutreDemande selectedDemande) {
		this.selectedDemande = selectedDemande;
	}

	public AutreDemande getDemande() {
		return demande;
	}

	public void setDemande(AutreDemande demande) {
		this.demande = demande;
	}

	public int getIdManager() {
		return idManager;
	}

	public void setIdManager(int idManager) {
		this.idManager = idManager;
	}

	public MissionService getMissionService() {
		return missionService;
	}

	public void setMissionService(MissionService missionService) {
		this.missionService = missionService;
	}

	
	public Mission getSelectedMission() {
		return selectedMission;
	}

	public void setSelectedMission(Mission selectedMission) {
		this.selectedMission = selectedMission;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
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



	public DemandeService getDemandeService() {
		return demandeService;
	}

	public void setDemandeService(DemandeService demandeService) {
		this.demandeService = demandeService;
	}

}
