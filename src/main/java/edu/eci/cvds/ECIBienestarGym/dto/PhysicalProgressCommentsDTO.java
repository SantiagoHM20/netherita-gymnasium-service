package edu.eci.cvds.ECIBienestarGym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalProgressCommentsDTO {
    private String comment;
    private LocalDate commentDate;
}
