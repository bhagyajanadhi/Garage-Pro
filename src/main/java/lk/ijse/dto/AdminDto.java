package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AdminDto {
    public String email;
    public String password;
    public String username;
    public String type;

}
