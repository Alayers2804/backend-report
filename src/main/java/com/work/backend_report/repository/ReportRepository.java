/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.work.backend_report.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.backend_report.entity.Report;

/**
 *
 * @author ferdi
 */
public interface ReportRepository extends JpaRepository<Report, UUID> {
}

