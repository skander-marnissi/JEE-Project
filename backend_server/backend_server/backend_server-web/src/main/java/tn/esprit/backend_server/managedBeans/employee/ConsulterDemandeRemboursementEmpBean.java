package tn.esprit.backend_server.managedBeans.employee;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import esprit.tn.backend_server.Entities.DemandeRemboursement;
import esprit.tn.backend_server.Entities.Mission;
import esprit.tn.backend_server.Impl.DemandeService;
import esprit.tn.backend_server.Impl.UserService;
import tn.esprit.backend_server.managedBeans.SessionUtils;

@ManagedBean
@ViewScoped
public class ConsulterDemandeRemboursementEmpBean {
	
	private List<DemandeRemboursement> filteredDemandes;
	private List<DemandeRemboursement> selectedDemandes;
	private List<DemandeRemboursement> demandes;
	
	
	private Mission mission;
	private DemandeRemboursement demande;
	
	private DemandeRemboursement selectedDemande;
	private Mission selectedMission;
	
	private int idEmploye;
	
	@EJB
	UserService userService;
	
	@EJB
	DemandeService demandeService;
	
	
	@PostConstruct
	public void init(){
		
		
		
		HttpSession session = SessionUtils.getSession();
		idEmploye = (int) session.getAttribute("userid");
		
		demandes = demandeService.getAllDemandeDeRemboursement(idEmploye);
		
		System.out.println("demandes: "+ demandes);
		
	
		
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
    
    
	public String deleteDemande(int demandeId)
	{
		
		demandeService.deleteDemandeRemboursementById(demandeId);
		
		this.init();
		
		FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Demande supprimée !"));
		
		return "/pages/employees/ConsulterDemandeRemboursement?faces-redirect=true";
	
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



	public DemandeRemboursement getSelectedDemande() {
		return selectedDemande;
	}



	public void setSelectedDemande(DemandeRemboursement selectedDemande) {
		this.selectedDemande = selectedDemande;
	}



	public Mission getSelectedMission() {
		return selectedMission;
	}



	public void setSelectedMission(Mission selectedMission) {
		this.selectedMission = selectedMission;
	}



	public int getIdEmploye() {
		return idEmploye;
	}



	public void setIdEmploye(int idEmploye) {
		this.idEmploye = idEmploye;
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
