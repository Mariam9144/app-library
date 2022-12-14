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

import com.example.gestion_librarie.model.Auteur;
import com.example.gestion_librarie.model.Categorie;
import com.example.gestion_librarie.model.Livre;
import com.example.gestion_librarie.repository.LivreRepository;
import com.example.gestion_librarie.service.AuteurService;
import com.example.gestion_librarie.service.CategorieService;
import com.example.gestion_librarie.service.LivreService;

import lombok.Data;

@Component
@ManagedBean
@SessionScoped
@Data
public class LivreBean {

	private List<Livre> livres;

    private Livre selectedLivre;

    private List<Livre> selectedLivres;
    
    
    private List<Categorie> categories;
    @Autowired
	private CategorieService categorieService;

    private List<Auteur> auteurs;
    @Autowired
	private AuteurService auteurService;
    @Autowired
    private LivreService livreService;
    
    @Autowired
    private LivreRepository livreRepository;
    @Autowired
    private EmpruntRepository empruntRepository;

    @PostConstruct
    public void init() {
        this.livres = this.livreService.getLivreRepository().findAll();
    }

    public List<Livre> getLivres() {
        return  this.livreService.getLivreRepository().findAll();
    }

    public Livre getSelectedLivre() {
        return selectedLivre;
    }

    public void setSelectedLivre(Livre selectedLivre) {
        this.selectedLivre = selectedLivre;
    }

    public List<Livre> getSelectedLivres() {
        return selectedLivres;
    }

    public void setSelectedLivres(List<Livre> selectedLivres) {
        this.selectedLivres = selectedLivres;
    }

    public void openNew() {
        this.selectedLivre = new Livre();
    }

    public void saveLivre() {
        if (this.selectedLivre.getId() == null) {
           // this.selectedLivre.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            //this.livres.add(this.selectedLivre);
        	livreService.saveLivre(selectedLivre);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livre ajout??"));
        }
        else {
        	livreService.saveLivre(selectedLivre);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Le Livre est mis ?? jour"));
        }
        livres = livreService.getLivres();
        PrimeFaces.current().executeScript("PF('manageLivreDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-livres");
    }

    public void deleteLivre() {
        if (empruntRepository.existsByLivre(this.selectedLivre)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impossible de supprimer le livre"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-membres");
            return;

        }
        //this.livres.remove(this.selectedLivre);
    	//livres = livreService.getLivreRepository().findAll();
    	this.livreService.deleteLivre(selectedLivre.getId());
    	//recharge
    	livres = livreService.getLivreRepository().findAll();
        this.selectedLivre = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livre supprim??"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-livres");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedLivres()) {
            int size = this.selectedLivres.size();
            return size > 1 ? size + " livres selected" : "1 livre selected";
        }

        return "Delete";
    }

    public boolean hasSelectedLivres() {
        return this.selectedLivres != null && !this.selectedLivres.isEmpty();
    }

    public void deleteSelectedLivres() {
        this.livres.removeAll(this.selectedLivres);
        this.livreRepository.deleteAll(this.selectedLivres);
        this.selectedLivres = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livres supprim??s"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-livres");
        PrimeFaces.current().executeScript("PF('dtLivres').clearFilters()");
    }
}
