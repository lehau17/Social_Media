package com.booking.infrastructure.model.entity;

import com.booking.infrastructure.model.enums.JwtType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class JwtPayload {
    private long id;
    private String sub; // username
    private String email;
    private String[] roles;
    private Date exp;
    private Date iat;
    private JwtType type;


    //



    public JwtPayload(int id, String sub, String email, String[] roles,  JwtType type) {
        this.id = id;
        this.sub = sub;
        this.email = email;
        this.roles = roles;
        this.type = type;
//        this.exp = exp;
//        this.iat = iat;
    }


    public Map<String, Object> toPayloadMap() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", id);
        payload.put("sub", sub);
        payload.put("email", email);
        payload.put("roles", roles);
        payload.put("exp", exp);
        payload.put("iat", iat);
        payload.put("type", type.name());
        return payload;
    }

}
