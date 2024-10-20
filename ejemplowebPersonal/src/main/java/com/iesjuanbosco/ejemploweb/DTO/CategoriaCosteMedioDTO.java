package com.iesjuanbosco.ejemploweb.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaCosteMedioDTO {
    private Long id;
    private String nombreCategoria;
    private Double costeMedio;
    private Long numeroProductos;
}
