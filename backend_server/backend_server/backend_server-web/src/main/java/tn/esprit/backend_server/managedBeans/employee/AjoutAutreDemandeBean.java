package tn.esprit.backend_server.managedBeans.employee;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.servlet.http.HttpSession;

import esprit.tn.backend_server.Entities.AutreDemande;
import esprit.tn.backend_server.Entities.Etat;
import esprit.tn.backend_server.Entities.EtatDemande;
import esprit.tn.backend_server.Entities.Role;
import esprit.tn.backend_server.Entities.TypeDemande;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.DemandeService;
import esprit.tn.backend_server.Impl.UserService;
import tn.esprit.backend_server.managedBeans.SessionUtils;


@ManagedBean
@ViewScoped
public class AjoutAutreDemandeBean {

	@EJB
	UserService userService;
	
	@EJB
	DemandeService demandeService;
	
	private AutreDemande demande;
	
	private String raison;
	
	private TypeDemande typeDemande;
	
	private int idUser;
	
	@PostConstruct
	public void init(){
		
		
		
		HttpSession session = SessionUtils.getSession();
		idUser = (int) session.getAttribute("userid");
		
		
	
		
	}
	
	public String doAjout() {
		
		String navigateTo="null";
		
		System.out.println("i'm in doAjout");
		if(!(raison.isEmpty())&&(typeDemande!=null)) {
		
			demande = new AutreDemande(raison, EtatDemande.EN_ATTENTE, typeDemande);
			
			//navigateTo="/pages/admin/ConsulterUtilisateur?faces-redirect=true";
			demandeService.ajouterAutreDemande(demande, idUser);
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Demande envoyée !"));
			navigateTo ="/pages/employes/ConsulterAutreDemande?faces-redirect=true";
			
		}		
	else {
				FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Erreur, verifiez les champs"));
			}	
	
		return navigateTo;
	

		}
	
	
	

	public DemandeService getDemandeService() {
		return demandeService;
	}

	public void setDemandeService(DemandeService demandeService) {
		this.demandeService = demandeService;
	}

	public AutreDemande getDemande() {
		return demande;
	}

	public void setDemande(AutreDemande demande) {
		this.demande = demande;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}

	public TypeDemande getTypeDemande() {
		return typeDemande;
	}

	public void setTypeDemande(TypeDemande typeDemande) {
		this.typeDemande = typeDemande;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public DemandeService getAutreDemandeService() {
		return demandeService;
	}

	public void setAutreDemandeService(DemandeService autreDemandeService) {
		this.demandeService = autreDemandeService;
	}
	
		
	
}
