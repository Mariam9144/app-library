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

import com.example.gestion_librarie.model.Auteur;
import com.example.gestion_librarie.repository.AuteurRepository;
import com.example.gestion_librarie.service.AuteurService;

import lombok.Data;

@Component
@ManagedBean
@SessionScoped
@Data
public class AuteurBean {

	private List<Auteur> auteurs;

    private Auteur selectedAuteur;

    private List<Auteur> selectedAuteurs;

    @Autowired
    private AuteurService auteurService;
    
    @Autowired
    private AuteurRepository auteurRepository;

    @PostConstruct
    public void init() {
        this.auteurs = this.auteurService.getAuteurRepository().findAll();
    }

    public List<Auteur> getAuteurs() {
        return auteurs;
    }

    public Auteur getSelectedAuteur() {
        return selectedAuteur;
    }

    public void setSelectedAuteur(Auteur selectedAuteur) {
        this.selectedAuteur = selectedAuteur;
    }

    public List<Auteur> getSelectedAuteurs() {
        return selectedAuteurs;
    }

    public void setSelectedAuteurs(List<Auteur> selectedAuteurs) {
        this.selectedAuteurs = selectedAuteurs;
    }

    public void openNew() {
        this.selectedAuteur = new Auteur();
    }

    public void saveAuteur() {
        if (this.selectedAuteur.getId() == null) {
           // this.selectedAuteur.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            //this.auteurs.add(this.selectedAuteur);
        	auteurService.saveAuteur(selectedAuteur);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Auteur ajouté"));
        }
        else {
        	auteurService.saveAuteur(selectedAuteur);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Le Auteur est mis a jour"));
        }
        auteurs = auteurService.getAuteurs();
        PrimeFaces.current().executeScript("PF('manageAuteurDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-auteurs");
    }

    public void deleteAuteur() {
        //this.auteurs.remove(this.selectedAuteur);
    	//auteurs = auteurService.getAuteurRepository().findAll();
    	this.auteurService.deleteAuteur(selectedAuteur.getId());
    	//recharge
    	auteurs = auteurService.getAuteurRepository().findAll();
        this.selectedAuteur = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Auteur supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-auteurs");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedAuteurs()) {
            int size = this.selectedAuteurs.size();
            return size > 1 ? size + " auteurs selected" : "1 auteur selected";
        }

        return "Delete";
    }

    public boolean hasSelectedAuteurs() {
        return this.selectedAuteurs != null && !this.selectedAuteurs.isEmpty();
    }

    public void deleteSelectedAuteurs() {
        this.auteurs.removeAll(this.selectedAuteurs);
        this.auteurRepository.deleteAll(this.selectedAuteurs);
        this.selectedAuteurs = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Auteurs Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-auteurs");
        PrimeFaces.current().executeScript("PF('dtAuteurs').clearFilters()");
    }

}
