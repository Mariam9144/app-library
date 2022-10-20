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

import com.example.gestion_librarie.model.Categorie;
import com.example.gestion_librarie.model.Categorie;
import com.example.gestion_librarie.repository.CategorieRepository;
import com.example.gestion_librarie.repository.CategorieRepository;
import com.example.gestion_librarie.service.CategorieService;
import com.example.gestion_librarie.service.CategorieService;

import lombok.Data;

@Component
@ManagedBean
@SessionScoped
@Data
public class CategorieBean {

	private List<Categorie> categories;

    private Categorie selectedCategorie;

    private List<Categorie> selectedCategories;

    @Autowired
    private CategorieService categorieService;
    
    @Autowired
    private CategorieRepository categorieRepository;

    @PostConstruct
    public void init() {
        this.categories = this.categorieService.getCategorieRepository().findAll();
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public Categorie getSelectedCategorie() {
        return selectedCategorie;
    }

    public void setSelectedCategorie(Categorie selectedCategorie) {
        this.selectedCategorie = selectedCategorie;
    }

    public List<Categorie> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<Categorie> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public void openNew() {
        this.selectedCategorie = new Categorie();
    }

    public void saveCategorie() {
        if (this.selectedCategorie.getId() == null) {
           // this.selectedCategorie.setCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9));
            //this.categories.add(this.selectedCategorie);
        	categorieService.saveCategorie(selectedCategorie);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categorie ajouté"));
        }
        else {
        	categorieService.saveCategorie(selectedCategorie);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" La categorie a été modifié"));
        }
        categories = categorieService.getCategories();
        PrimeFaces.current().executeScript("PF('manageCategorieDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }

    public void deleteCategorie() {
        //this.categories.remove(this.selectedCategorie);
    	//categories = categorieService.getCategorieRepository().findAll();
    	this.categorieService.deleteCategorie(selectedCategorie.getId());
    	//recharge
    	categories = categorieService.getCategorieRepository().findAll();
        this.selectedCategorie = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categorie supprimé"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCategories()) {
            int size = this.selectedCategories.size();
            return size > 1 ? size + " categories selected" : "1 categorie selected";
        }

        return "Delete";
    }

    public boolean hasSelectedCategories() {
        return this.selectedCategories != null && !this.selectedCategories.isEmpty();
    }

    public void deleteSelectedCategories() {
        this.categories.removeAll(this.selectedCategories);
        this.categorieRepository.deleteAll(this.selectedCategories);
        this.selectedCategories = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categories supprimées"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-categories");
        PrimeFaces.current().executeScript("PF('dtCategories').clearFilters()");
    }

}
