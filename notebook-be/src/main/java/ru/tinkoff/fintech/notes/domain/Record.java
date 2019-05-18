package ru.tinkoff.fintech.notes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class Record {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @NotEmpty
    @Column(length = 150)
    @Size(min = 1, max = 150)
    private String title;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd-MM-yyyy")
    private Date date;

    @NotNull
    @NotEmpty
    @Column(length = 700)
    @Size(min = 1, max = 700)
    private String text;
}
