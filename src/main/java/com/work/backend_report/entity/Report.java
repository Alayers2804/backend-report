/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.work.backend_report.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.backend_report.enumeration.Project;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.*;
import java.util.Date;
import java.util.Objects;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ferdi
 */
@Entity
public class Report {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uid;

    private String partnerName;
    private int supportTime;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Jakarta")
    private Date supportDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Jakarta")
    private Date startingHour;  // Store in UTC but show in Asia/Jakarta

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Jakarta")
    private Date endingHour;
    
    private Project project;

    public Report() {
    }

    public Report(UUID uid, String partnerName, int supportTime, Date supportDate, Date startingHour, Date endingHour, Project project) {
        this.uid = uid;
        this.partnerName = partnerName;
        this.supportTime = supportTime;
        this.supportDate = supportDate;
        this.startingHour = startingHour;
        this.endingHour = endingHour;
        this.project = project;

    }

    public UUID getUid() {
        return uid;
    }

    public String getPartnerName() {
        return this.partnerName;
    }

    public Date getSupportDate() {
        return this.supportDate;
    }

    public int getSupportTime() {
        return this.supportTime;
    }

    public Date getstartingHour() {
        return this.startingHour;
    }

    public Date getendingHour() {
        return this.endingHour;
    }
    
    public Project getProject(){
        return this.project;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public void setStartingHour(Instant startingHour) {
        ZonedDateTime jakartaTime = startingHour.atZone(ZoneId.of("Asia/Jakarta"));
        this.startingHour = Date.from(jakartaTime.toInstant()); // Store as Date in Asia/Jakarta time zone
    }

    // Convert and set ending hour in local time zone (Asia/Jakarta)
    public void setEndingHour(Instant endingHour) {
        ZonedDateTime jakartaTime = endingHour.atZone(ZoneId.of("Asia/Jakarta"));
        this.endingHour = Date.from(jakartaTime.toInstant()); // Store as Date in Asia/Jakarta time zone
    }

    public void setSupportTime(int supportTime) {
        this.supportTime = supportTime;
    }
    
    public void setProject(Project project){
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        Report report = (Report) o;
        return Objects.equals(this.uid, report.uid) && Objects.equals(this.partnerName, report.partnerName);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uid, this.partnerName);
    }
}
