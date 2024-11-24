/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.work.backend_report.record;

/**
 *
 * @author ferdi
 */
public record ApiResponse<T>(boolean error, String message, T data) {}