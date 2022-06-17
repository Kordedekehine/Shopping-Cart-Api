package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.config.StringMessages;
import ShoppingCartsAPI.carts.exceptions.AuthenticationFailException;
import ShoppingCartsAPI.carts.model.AuthenticationToken;
import ShoppingCartsAPI.carts.model.User;
import ShoppingCartsAPI.carts.repositories.TokenRepository;
import ShoppingCartsAPI.carts.utils.Checker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServices {

    @Autowired
    TokenRepository tokenRepository;

 public void saveConfirmationToken(AuthenticationToken authenticationToken){
     tokenRepository.save(authenticationToken);
 }

 public AuthenticationToken getToken(User user){
     return tokenRepository.findTokenByUser(user);
 }

 public User getUser(String token){
   AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(token);
   if (Checker.notNull(authenticationToken)){
       if (Checker.notNull(authenticationToken.getUser())){
           return authenticationToken.getUser();
       }
   }
   return null;
 }

 public void authenticate(String token) throws AuthenticationFailException{
     if (!Checker.notNull(token)){
        throw new AuthenticationFailException(StringMessages.AUTH_TOEKN_NOT_PRESENT);
     }
     throw new AuthenticationFailException(StringMessages.AUTH_TOEKN_NOT_VALID);
 }
}
