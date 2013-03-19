/*
 * Created on 02.05.2010
 *
 * Project: WebExperiment
 * Original author: draxler
 * 
 */
package model.db;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * This class ExpAdmin belongs to a Java Persistence Unit. 
 * Instances of this class are used to create or change a corresponding database object.
 * ExpAdmin database objects can also be retrieved as instances of this class.
 *
 */
@Entity
@XmlRootElement
@XmlType(name = "expadmin", propOrder = {
        "name",
        "firstName",
        "email",
        "password",
        "phone",
        "organisation",
        "experiments"})
public class ExpAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="keys")
    private int id;
    private String name = "";
    private String firstName = "";
    private String email = "";
    private String password = ""; 
    private String phone = "";
    private String organisation = "";

    
    @OneToMany(mappedBy="expAdmin")
    private Set<ExpProject> expProjects;
    @OneToMany(mappedBy="expAdmin")
    private Set<Experiment> experiments;
    
    public ExpAdmin() {
        expProjects = new HashSet<ExpProject>();
        experiments = new HashSet<Experiment>();
    }

    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    @XmlTransient
    public Set<ExpProject> getExpProjects() {
        return expProjects;
    }

    public void setExpProjects(Set<ExpProject> projects) {
        this.expProjects = projects;
    }

    @XmlElementWrapper(name="experiments")
    @XmlElements(@XmlElement(name="experiment"))
    public Set<Experiment> getExperiments() {
        return experiments;
    }

    public void setExperiments(Set<Experiment> experiments) {
        this.experiments = experiments;
    }
}
