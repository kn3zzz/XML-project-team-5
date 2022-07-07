package com.dislinkt.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchProfileResponseDTO {
    public String username;
    public String name;
    public String lastname;
    public String biography;
    public String interests;
    public long id;
    public boolean privateProfile;
}
