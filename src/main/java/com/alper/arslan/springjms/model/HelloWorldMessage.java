package com.alper.arslan.springjms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HelloWorldMessage implements Serializable {
    static final long serialVersionUID = 7520410967786321516L;

    private UUID id;
    private String message;
}
