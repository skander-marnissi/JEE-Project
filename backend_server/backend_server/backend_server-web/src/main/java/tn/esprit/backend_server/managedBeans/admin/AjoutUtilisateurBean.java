package tn.esprit.backend_server.managedBeans.admin;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Null;

import esprit.tn.backend_server.Entities.Role;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.UserService;


@ManagedBean
@ViewScoped
public class AjoutUtilisateurBean {

	private String nom;
	private String prenom;
	private String tel;
	private String adresse;
	private String email;
	private String mdp;
	private Role role;
	private User User;
	
	//injection de dépendences

	@EJB
	UserService userService;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public String doAjout(){
		String navigateTo="null";
		System.out.println("i'm in doAjout");
		if(!(nom.isEmpty()) && !(prenom.isEmpty()) && !(email.isEmpty()) && !(adresse.isEmpty()) && !(mdp.isEmpty()) && !(tel.isEmpty())) {
		
			User = new User(prenom,nom,mdp,tel,adresse,email,role);
			navigateTo="/pages/admin/ConsulterUtilisateur?faces-redirect=true";
			userService.ajouterUser(User);
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Ajout effectuer"));
			
			
		}		
	else {
				FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Erreur"));
			}	
	
		return navigateTo;
	

		}

}
