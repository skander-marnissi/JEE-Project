package tn.esprit.backend_server.managedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;




import esprit.tn.backend_server.Entities.Role;
import esprit.tn.backend_server.Entities.User;
import esprit.tn.backend_server.Impl.UserService;


@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 3133319368634230135L;
	
private String login;
private String password;
private User User;
private boolean loggedIn;

//injection de dépendences

@EJB
UserService userService;


@PostConstruct
public void init(){
	login = "";
    password = "";
}


//.....Admin navigation ....
public void toAjouterUtilisateur(){
	
	System.out.println("-----------I'm in redirect ajout utilisateur--------");
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/admin/AjoutUtilisateur?faces-redirect=true");
}

public void toConsulterUtilisateur(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/admin/ConsulterUtilisateur?faces-redirect=true");
	
}

//.....Manager navigation ....
public void toAjouterMission(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/manager/AjoutMission?faces-redirect=true");
}

public void toHistoriqueMission(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/manager/HistoriqueMission?faces-redirect=true");
}

public void toConsulterMission(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/manager/ConsulterMission?faces-redirect=true");
}

public void toConsulterAutreDemande(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/manager/ConsulterAutreDemande?faces-redirect=true");
}

public void toHistoriqueAutreDemande(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/manager/HistoriqueAutreDemande?faces-redirect=true");
}




//.....Financier navigation ....
public void toConsulterDemandeRemboursement(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/financier/ConsulterDemandeRemboursement?faces-redirect=true");
}
public void toHistoriqueDemandeRemboursement(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
  nh.handleNavigation(fc, null, "/pages/financier/HistoriqueDemandeRemboursement?faces-redirect=true");
}


//.....Employee navigation ....

public void toEmployeHome(){ 

	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
  nh.handleNavigation(fc, null, "/pages/employes/Home?faces-redirect=true");
	
}
public void toAjouterDemandeRemboursement(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
  nh.handleNavigation(fc, null, "/pages/employes/AjoutDemandeRemboursement?faces-redirect=true");
}
public void toAjouterAutreDemande(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
  nh.handleNavigation(fc, null, "/pages/employes/AjoutAutreDemande?faces-redirect=true");
}

public void toConsulterAutreDemandeEmp(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
  nh.handleNavigation(fc, null, "/pages/employes/ConsulterAutreDemande?faces-redirect=true");
}
public void toConsulterDemandeRemboursementEmp(){
	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
  nh.handleNavigation(fc, null, "/pages/employes/ConsulterDemandeRemboursement?faces-redirect=true");
}








public void doLogin(){
	String navigateTo="null";
	
	 FacesContext fc = FacesContext.getCurrentInstance();
	  
	User = userService.getUserByLoginAndPassword(login, password);
	
if (User !=null && User.getRole()==Role.ADMIN){
	//navigateTo="/pages/welcome?faces-redirect=true";
	
	HttpSession session = SessionUtils.getSession();
	session.setAttribute("userid", User.getId());
	System.out.println(session.getAttribute("userid"));
	
	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/admin/Home?faces-redirect=true");
	
	navigateTo="/pages/admin/Home?faces-redirect=true";

	loggedIn=true;		
}
else if (User !=null && User.getRole()==Role.MANAGER){
	//navigateTo="/pages/welcome?faces-redirect=true";

	HttpSession session = SessionUtils.getSession();
	session.setAttribute("userid", User.getId());
	System.out.println(session.getAttribute("userid"));
	
	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/manager/Home?faces-redirect=true");
	
	navigateTo="/pages/manager/Home?faces-redirect=true";

	loggedIn=true;		
}
else if (User !=null && User.getRole()==Role.FINANCIER){
	//navigateTo="/pages/welcome?faces-redirect=true";
	
	HttpSession session = SessionUtils.getSession();
	session.setAttribute("userid", User.getId());
	System.out.println(session.getAttribute("userid"));
	
	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/financier/Home?faces-redirect=true");
	
	navigateTo="/pages/financier/Home?faces-redirect=true";

	loggedIn=true;		
}
else if (User !=null && User.getRole()==Role.EMPLOYEE){
	//navigateTo="/pages/welcome?faces-redirect=true";
	
	HttpSession session = SessionUtils.getSession();
	session.setAttribute("userid", User.getId());
	System.out.println(session.getAttribute("userid"));
	
	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/pages/employes/Home?faces-redirect=true");
	
	navigateTo="/pages/employes/Home?faces-redirect=true";

	loggedIn=true;		
}
else {
	FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("bad credentials"));
}
//return navigateTo;
}


public void doLogout(){
	//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	HttpSession session = SessionUtils.getSession();
	session.invalidate();
	
	//session.setAttribute("username", User.getNom());
	
	//System.out.println(session.getAttribute("username"));

	FacesContext fc = FacesContext.getCurrentInstance();

	NavigationHandler nh = fc.getApplication().getNavigationHandler();
    nh.handleNavigation(fc, null, "/login?faces-redirect=true");
}



public String getLogin() {
	return login;
}

public void setLogin(String login) {
	this.login = login;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}


public boolean isLoggedIn() {
	return loggedIn;
}

public void setLoggedIn(boolean loggedIn) {
	this.loggedIn = loggedIn;
}

 
public UserService getUserService() {
	return userService;
}

public void setUserService(UserService userService) {
	this.userService = userService;
}

public User getUser() {
	return User;
}

public void setUser(User User) {
	this.User = User;
}

//logout event, invalidate session





}


