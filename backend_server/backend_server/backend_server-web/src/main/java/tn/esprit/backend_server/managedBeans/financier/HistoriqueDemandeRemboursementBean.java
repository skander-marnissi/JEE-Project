package tn.esprit.backend_server.managedBeans.financier;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import esprit.tn.backend_server.Entities.DemandeRemboursement;
import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.DemandeService;
import esprit.tn.backend_server.Impl.MissionService;
import esprit.tn.backend_server.Impl.UserService;
import tn.esprit.backend_server.managedBeans.SessionUtils;



@ManagedBean
@ViewScoped
public class HistoriqueDemandeRemboursementBean {

	
	private List<DemandeRemboursement> filteredDemandes;
	private List<DemandeRemboursement> selectedDemandes;
	private List<DemandeRemboursement> demandes;
	private List<DemandeRemboursement> demandesTraiter;
	
	private Mission selectedMission;
	private User selectedUser;
	private DemandeRemboursement selectedDemande;
	
	private User user ;
	private Mission mission;
	private DemandeRemboursement demande;
	
	private int idFinancier;

	@EJB
	UserService userService;
	
	@EJB
	DemandeService demandeService;
	
	@EJB
	MissionService missionService;


	@PostConstruct
	public void init(){
		
		demandesTraiter = demandeService.getAllDemandeDeRemboursementTraiter();
	
		HttpSession session = SessionUtils.getSession();
		idFinancier = (int) session.getAttribute("userid");
		
		
		for(DemandeRemboursement d : demandesTraiter) {
		
			System.out.println("demandes: "+ d.getId());
		}
		
		
	
		
	}
	 


    
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        
       //System.out.println(users);
        
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);
 
        DemandeRemboursement demande = (DemandeRemboursement) value;
        return demande.getId() < filterInt
                || demande.getRaison().toLowerCase().contains(filterText)
                || demande.getTypeFrais().toString().toLowerCase().contains(filterText);
        	 
               
               
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
 
    
   

	public String formatDate(Calendar date) {
			
		
		    SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd 'a' HH:mm:ss");
		    String formatted = format1.format(date.getTime());
		    
		    return formatted;
		}
    
    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }
    
    

    
	public MissionService getMissionService() {
		return missionService;
	}

	public void setMissionService(MissionService missionService) {
		this.missionService = missionService;
	}

	public List<DemandeRemboursement> getFilteredDemandes() {
		return filteredDemandes;
	}

	public void setFilteredDemandes(List<DemandeRemboursement> filteredDemandes) {
		this.filteredDemandes = filteredDemandes;
	}

	public List<DemandeRemboursement> getSelectedDemandes() {
		return selectedDemandes;
	}

	public void setSelectedDemandes(List<DemandeRemboursement> selectedDemandes) {
		this.selectedDemandes = selectedDemandes;
	}

	public List<DemandeRemboursement> getDemandes() {
		return demandes;
	}

	public void setDemandes(List<DemandeRemboursement> demandes) {
		this.demandes = demandes;
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

	public DemandeRemboursement getSelectedDemande() {
		return selectedDemande;
	}

	public void setSelectedDemande(DemandeRemboursement selectedDemande) {
		this.selectedDemande = selectedDemande;
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

	public DemandeRemboursement getDemande() {
		return demande;
	}

	public void setDemande(DemandeRemboursement demande) {
		this.demande = demande;
	}

	public int getIdFinancier() {
		return idFinancier;
	}

	public void setIdFinancier(int idFinancier) {
		this.idFinancier = idFinancier;
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

	public List<DemandeRemboursement> getDemandesTraiter() {
		return demandesTraiter;
	}

	public void setDemandesTraiter(List<DemandeRemboursement> demandesTraiter) {
		this.demandesTraiter = demandesTraiter;
	}




}
