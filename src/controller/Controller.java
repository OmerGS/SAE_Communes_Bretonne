package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import view.ConnectionPage;
import view.InscriptionPage;
import data.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which allows linking the view and the model and controlling the information transiting between them.
 * @autor O.Gunes
 */
public class Controller implements EventHandler<ActionEvent> {

    private ConnectionPage connectionPage;
    private InscriptionPage inscriptionPage;

    public Controller(ConnectionPage view) {
        this.connectionPage = view;
        this.inscriptionPage = new InscriptionPage(this);
    }

    public Controller() {
        this.connectionPage = new ConnectionPage();
        this.inscriptionPage = new InscriptionPage(this);
    }
    

    public void setConnectionPage(ConnectionPage connectionPage) {
        this.connectionPage = connectionPage;
    }

    public void setInscriptionPage(InscriptionPage inscriptionPage) {
        this.inscriptionPage = inscriptionPage;
    }

    @Override
    public void handle(ActionEvent e) {
        /* PAGE DE CONNEXION */
        if (e.getSource() == this.connectionPage.getLinkSignUp()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                // Initialisation de la page d'inscription avec le contrôleur actuel
                this.inscriptionPage = new InscriptionPage(this);
                this.inscriptionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == this.connectionPage.getLinkForgotPassword()) {
            System.out.println("Mot de passe oublié !");
        }

        if (e.getSource() == this.connectionPage.getBtnLogin()) {
            String email = this.connectionPage.getEmailField().getText();
            String password = this.connectionPage.getPasswordField().getText();

            // Réinitialiser le message d'erreur
            this.connectionPage.getErrorMessageLabel().setVisible(false);

            if (email.isEmpty() || password.isEmpty()) {
                this.connectionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            } else if (!isValidEmail(email)) {
                this.connectionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            } else {
                UserService userService = new UserService();
                if (userService.validateLogin(email, password)) {
                    System.out.println("Connexion réussie.");
                    // Redirection ou actions après la connexion réussie
                } else {
                    this.connectionPage.getErrorMessageLabel().setText("Identifiants incorrects.");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);
                }
            }
        }











        /* PAGE D'INSCRIPTION */

        if (this.inscriptionPage != null && e.getSource() == this.inscriptionPage.getBtnSignUp()) {
            String firstName = this.inscriptionPage.getFirstNameField().getText();
            String lastName = this.inscriptionPage.getLastNameField().getText();
            String email = this.inscriptionPage.getEmailField().getText();
            String password = this.inscriptionPage.getPasswordField().getText();
            String confirmPassword = this.inscriptionPage.getConfirmPasswordField().getText();

            // Réinitialiser le message d'erreur
            this.inscriptionPage.getErrorMessageLabel().setVisible(false);

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                this.inscriptionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else if (!isValidEmail(email)) {
                this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else if (!password.equals(confirmPassword)) {
                this.inscriptionPage.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else {
                UserService creationUtilisateur = new UserService();
                if (creationUtilisateur.emailExists(email)) {
                    this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail est d\u00e9j\u00e0 utilis\u00e9e.");
                    this.inscriptionPage.getErrorMessageLabel().setVisible(true);
                } else {
                    creationUtilisateur.createUser(lastName, firstName, email, password);
                    System.out.println("Utilisateur cru\00e9\u00e9 avec succ\u00e8s.");
                }
            }
        }

        
        if (e.getSource() == this.inscriptionPage.getLinkLogin()) {
            try{
                Stage stage = (Stage) this.inscriptionPage.getLinkLogin().getScene().getWindow();
                this.connectionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}