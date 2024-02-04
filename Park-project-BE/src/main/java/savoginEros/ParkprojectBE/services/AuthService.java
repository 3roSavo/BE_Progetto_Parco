package savoginEros.ParkprojectBE.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import savoginEros.ParkprojectBE.entities.User;
import savoginEros.ParkprojectBE.exceptions.BadRequestException;
import savoginEros.ParkprojectBE.exceptions.UnauthorizedException;
import savoginEros.ParkprojectBE.payloads.login.NewLoginDTO;
import savoginEros.ParkprojectBE.security.JWTTools;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    public String userAuthenticate(NewLoginDTO loginDTO) {

        // 1 Controllare presenza mail nel DB
        // 2 Verificare correttezza password fornita
        // 3 creiamo e ritorniamo il token

        User user = userService.findByEmail(loginDTO.email());

        if (user.getPassword().equals(loginDTO.password())) {

            return jwtTools.createToken(user);

        } else {

            throw new UnauthorizedException("Password errata");
        }

    }
}
