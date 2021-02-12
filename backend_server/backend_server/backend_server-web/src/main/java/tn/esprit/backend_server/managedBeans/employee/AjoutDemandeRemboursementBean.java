package tn.esprit.backend_server.managedBeans.employee;

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
import esprit.tn.backend_server.Entities.DemandeRemboursement;
import esprit.tn.backend_server.Entities.Etat;
import esprit.tn.backend_server.Entities.EtatDemande;
import esprit.tn.backend_server.Entities.Role;
import esprit.tn.backend_server.Entities.TypeDemande;
import esprit.tn.backend_server.Entities.TypeFrais;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.DemandeService;
import esprit.tn.backend_server.Impl.UserService;
import tn.esprit.backend_server.managedBeans.SessionUtils;


@ManagedBean
@ViewScoped
public class AjoutDemandeRemboursementBean {

	@EJB
	UserService userService;
	
	@EJB
	DemandeService demandeService;
	
	private String raison;
	
	private TypeFrais typeFrais;
	
	private int montant;
	
	private int idUser;

	private DemandeRemboursement demande;
	
	
	@PostConstruct
	public void init(){
		
		
		HttpSession session = SessionUtils.getSession();
		idUser = (int) session.getAttribute("userid");
		
	
		
	}
	
	
	
	public String doAjout() {
		
		String navigateTo="null";
		
		System.out.println("i'm in doAjout");
		if(!(raison.isEmpty())&&(typeFrais!=null)&&(montant!=0)) {
		
			
			
			
			
			demande = new DemandeRemboursement(raison, montant,EtatDemande.EN_ATTENTE ,typeFrais);
			//navigateTo="/pages/admin/ConsulterUtilisateur?faces-redirect=true";
			
			
			
			demandeService.ajouterDemandeRemboursement(demande,idUser);
			
			
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Demande Envoyee !"));
			
			navigateTo ="/pages/employes/ConsulterDemandeRemboursement?faces-redirect=true";
			
		}		
	else {
				FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Erreur, Verifiez les champs"));
			}	
	
	
		return navigateTo;
	

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

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public DemandeRemboursement getDemande() {
		return demande;
	}

	public void setDemande(DemandeRemboursement demande) {
		this.demande = demande;
	}

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}

	public TypeFrais getTypeFrais() {
		return typeFrais;
	}

	public void setTypeFrais(TypeFrais typeFrais) {
		this.typeFrais = typeFrais;
	}
		
}
