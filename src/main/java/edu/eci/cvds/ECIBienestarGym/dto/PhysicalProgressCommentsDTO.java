package edu.eci.cvds.ECIBienestarGym.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PhysicalProgressCommentsDTO {
    private String comment;
    private LocalDate comment_date;
}
