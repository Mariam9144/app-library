package com.example.gestion_librarie.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.example.gestion_librarie.repository.EmpruntRepository;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.gestion_librarie.model.Livre;
import com.example.gestion_librarie.model.Membre;
import com.example.gestion_librarie.repository.MembreRepository;
import com.example.gestion_librarie.service.MembreService;

import lombok.Data;

@Component
@ManagedBean
@SessionScoped
@Data
public class MembreBean {
	
	private List<Membre> membres;

    private Membre selectedMembre;

    private List<Membre> selectedMembres;
    	
    @Autowired
    private MembreService membreService;
    
    @Autowired
    private MembreRepository membreRepository;
    @Autowired
    private EmpruntRepository empruntRepository;

    @PostConstruct
    public void init() {
        this.membres = this.membreService.getMembreRepository().findAll();
    }

    public List<Membre> getMembres() {
        return membres;
    }

    public Membre getSelectedMembre() {
        return selectedMembre;
    }

    public void setSelectedMembre(Membre selectedMembre) {
        this.selectedMembre = selectedMembre;
    }

    public List<Membre> getSelectedMembres() {
        return selectedMembres;
    }

    public void setSelectedMembres(List<Membre> selectedMembres) {
        this.selectedMembres = selectedMembres;
    }

    public void openNew() {
        this.selectedMembre = new Membre();
    }

    public void saveMembre() {
        if (this.selectedMembre.getId() == null) {
           // this.selectedMembre.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            //this.membres.add(this.selectedMembre);
        	membreService.saveMembre(selectedMembre);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Membre ajouté"));
        }
        else {
        	membreService.saveMembre(selectedMembre);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Le Membre est mis a jour"));
        }
        membres = membreService.getMembres();
        PrimeFaces.current().executeScript("PF('manageMembreDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-membres");
    }

    public void deleteMembre() {
        //this.membres.remove(this.selectedMembre);
    	//membres = membreService.getMembreRepository().findAll();
        if (empruntRepository.existsByMembre(this.selectedMembre)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impossible de supprimer le Membre"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-membres");
            return;

        }
    	this.membreService.deleteMembre(selectedMembre.getId());
    	//recharge
    	membres = membreService.getMembreRepository().findAll();
        this.selectedMembre = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Membre supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-membres");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMembres()) {
            int size = this.selectedMembres.size();
            return size > 1 ? size + " membres selected" : "1 membre selected";
        }

        return "Delete";
    }

    public boolean hasSelectedMembres() {
        return this.selectedMembres != null && !this.selectedMembres.isEmpty();
    }

    public void deleteSelectedMembres() {
        this.membres.removeAll(this.selectedMembres);
        this.membreRepository.deleteAll(this.selectedMembres);
        this.selectedMembres = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Membres Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-membres");
        PrimeFaces.current().executeScript("PF('dtMembres').clearFilters()");
    }

}
