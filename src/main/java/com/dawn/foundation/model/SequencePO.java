package com.dawn.foundation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "t_sequence")
public class SequencePO {

    @Column(name = "code")
    private String code;

    @Column(name = "template")
    private String template;

    @Column(name = "nextValue")
    private Long nextValue;

}
