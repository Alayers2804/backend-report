/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.work.backend_report.record;

import java.util.Date;

import com.work.backend_report.enumeration.Project;

/**
 *
 * @author ferdi
 */

public record ReportSummary(String uid, String partnerName, int supportTime, Project project, Date supportDate, String notes) {
    
}
