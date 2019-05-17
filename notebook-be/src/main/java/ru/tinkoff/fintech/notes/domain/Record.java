package ru.tinkoff.fintech.notes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class Record {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd-MM-yyyy")
    private Date date;

    @NotNull
    @NotEmpty
    private String text;
}
