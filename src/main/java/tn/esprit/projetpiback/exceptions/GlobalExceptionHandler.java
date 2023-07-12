//package tn.esprit.projetpiback.exceptions;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
////    @ExceptionHandler(MethodArgumentNotValidException.class)
////    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
////        // Récupérer les messages d'erreur de validation
////        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
////                .map(error -> error.getDefaultMessage())
////                .findFirst()
////                .orElse("Une erreur de validation s'est produite");
////
////        // Retourner une réponse avec le message d'erreur
////        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
////    }
//
//    // Autres méthodes de gestion des exceptions...
//}
