package com.example.gestion_librarie.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
@Data
@Entity
public class Retour {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date date_retour;
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "membre_id", referencedColumnName = "id")
	private Membre membre;
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "livre_id", referencedColumnName = "id")
	private Livre livre;

	
	//modification
		public boolean equals(Object other) {
			if ((this == other))
				return true;
			if ((other == null))
				return false;
			if (!(other instanceof Retour))
				return false;
			Retour castOther = (Retour) other;

			return ((this.getId() == castOther.getId())
					|| (this.getId() != null && castOther.getId() != null && this.getId().equals(castOther.getId())));
		}

		public int hashCode() {
			int result = 17;

			result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());

			return result;
		}

	
}
