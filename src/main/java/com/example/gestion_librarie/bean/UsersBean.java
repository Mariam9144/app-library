package com.example.gestion_librarie.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.gestion_librarie.model.Users;
import com.example.gestion_librarie.service.UsersService;

import lombok.Data;


@Component
@ManagedBean
@SessionScoped
@Data
public class UsersBean {

	private List<Users> userss;

    private Users selectedUsers;

    private List<Users> selectedUserss;

    @Autowired
    private UsersService usersService;

    @PostConstruct
    public void init() {
        this.userss = this.usersService.getUsersRepository().findAll();
       // this.userss = this.usersService.getUsers();
    }

    public List<Users> getUserss() {
        return userss;
    }

    public Users getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(Users selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public List<Users> getSelectedUserss() {
        return selectedUserss;
    }

    public void setSelectedUserss(List<Users> selectedUserss) {
        this.selectedUserss = selectedUserss;
    }

    public void openNew() {
        this.selectedUsers = new Users();
    }

    public void saveUsers() {
        if (this.selectedUsers.getId() == null) {
           // this.selectedUsers.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            //this.userss.add(this.selectedUsers);
        	usersService.saveUsers(selectedUsers);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Users ajouté"));
        }
        else {
        	usersService.saveUsers(selectedUsers);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Le Users est mis a jour"));
        }
        userss = usersService.getUserss();
        PrimeFaces.current().executeScript("PF('manageUsersDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-userss");
    }

    public void deleteUsers() {
        //this.userss.remove(this.selectedUsers);
    	//userss = usersService.getUsersRepository().findAll();
    	this.usersService.deleteUsers(selectedUsers.getId());
    	//recharge
    	userss = usersService.getUsersRepository().findAll();
        this.selectedUsers = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Users supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-userss");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedUserss()) {
            int size = this.selectedUserss.size();
            return size > 1 ? size + " userss selected" : "1 users selected";
        }

        return "Delete";
    }

    public boolean hasSelectedUserss() {
        return this.selectedUserss != null && !this.selectedUserss.isEmpty();
    }

    public void deleteSelectedUserss() {
        this.userss.removeAll(this.selectedUserss);
        this.selectedUserss = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Userss Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-userss");
        PrimeFaces.current().executeScript("PF('dtUserss').clearFilters()");
    }

}
