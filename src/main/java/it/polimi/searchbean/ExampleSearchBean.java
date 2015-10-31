
package it.polimi.searchbean;

import java.sql.Time;
import java.util.Date;
import it.polimi.model.Sex;

public class ExampleSearchBean {

    public Integer exampleId;
    public String name;
    public Long etaFrom;
    public Long etaTo;
    public Boolean male;
    public Date birthDateFrom;
    public Date birthDateTo;
    public Time birthTime;
    public Sex sex;

    public Integer getExampleId() {
        return this.exampleId;
    }

    public void setExampleId(Integer exampleId) {
        this.exampleId=exampleId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Long getEtaFrom() {
        return this.etaFrom;
    }

    public void setEtaFrom(Long etaFrom) {
        this.etaFrom=etaFrom;
    }

    public Long getEtaTo() {
        return this.etaTo;
    }

    public void setEtaTo(Long etaTo) {
        this.etaTo=etaTo;
    }

    public Boolean getMale() {
        return this.male;
    }

    public void setMale(Boolean male) {
        this.male=male;
    }

    public Date getBirthDateFrom() {
        return this.birthDateFrom;
    }

    public void setBirthDateFrom(Date birthDateFrom) {
        this.birthDateFrom=birthDateFrom;
    }

    public Date getBirthDateTo() {
        return this.birthDateTo;
    }

    public void setBirthDateTo(Date birthDateTo) {
        this.birthDateTo=birthDateTo;
    }

    public Time getBirthTime() {
        return this.birthTime;
    }

    public void setBirthTime(Time birthTime) {
        this.birthTime=birthTime;
    }

    public Sex getSex() {
        return this.sex;
    }

    public void setSex(Sex sex) {
        this.sex=sex;
    }

}
