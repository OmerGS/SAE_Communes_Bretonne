package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import dao.CommuneService;
import dao.UserService;
import data.Commune;
import data.Utilisateur;
import view.AccountPage;
import view.BretagneApp;
import view.CommuneDetailsPage;
import view.ConnectionPage;
import view.InscriptionPage;
import view.MainPage;
import view.ResetPassword;
import view.TrouverCheminCommune;
import view.misc.CodeAlert;
import view.misc.CustomAlert;
import view.ForgotPassword;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

/**
* Class which allows linking the view and the model and controlling the information transiting between them.
* @autor O.Gunes
*/
public class Controller implements EventHandler<ActionEvent> {

    /**
    * An instance of ConnectionPage 
    */
    private ConnectionPage connectionPage;

    /**
    * An instance of InscriptionPage
    */
    private InscriptionPage inscriptionPage;

    /**
    * An instance of forgotPassword page.
    */
    private ForgotPassword forgotPassword;

    /**
    * An instance of ResetPassword page. 
    */
    private ResetPassword resetPassword;

    /**
    * A boolean check if a mail code is sent or not
    */
    private boolean codeSent;

    /**
    * A String which contain the verification code. 
    */
    private String codeString;

    /**
    * An Instance of MainPage page. 
    */
    private MainPage mainPage;

    /**
    * An Instance of TrouverCheminCommune page. 
    */
    private TrouverCheminCommune trouverCheminCommune;

    /**
    * ArrayList which contain commune. 
    */
    private ArrayList<Commune> communesRecente;

    private List<Commune> communeToute;

    private Utilisateur currentUser;

    private ArrayList<Utilisateur> listeUtilisateur;

    private UserService userServices;

    private AccountPage accountPage;

    private CodeAlert envoieCodeValidation;

    private String pendingNewEmail;

    /**
    * The constructor of Controller. 
    * @param connectionPage
    */
    public Controller(MainPage mainPage) {
        this.userServices = new UserService();
        this.listeUtilisateur = this.userServices.loadAllUsers();
        this.connectionPage = new ConnectionPage(this);
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this); 
        this.resetPassword = new ResetPassword(this);
        this.trouverCheminCommune = new TrouverCheminCommune(this);
        this.accountPage = new AccountPage(this);
        this.envoieCodeValidation = new CodeAlert(this);
        this.mainPage = mainPage;
    }    

    /**
    * The Empty constructor of controller 
    */
    public Controller() {
        this.userServices = new UserService();
        this.listeUtilisateur = this.userServices.loadAllUsers();
        this.connectionPage = new ConnectionPage(this);
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this); 
        this.resetPassword = new ResetPassword(this);
        this.trouverCheminCommune = new TrouverCheminCommune(this);
        this.accountPage = new AccountPage(this);
        this.envoieCodeValidation = new CodeAlert(this);
    }

    public void setMainPage(MainPage mainPage){
        this.mainPage = mainPage;
    }









    /* ---------------------------------- */



    /**
    * Method which check if an event is realized.
    *
    * @param ActionEvent Check if an action is executed 
    */
    @Override
    public void handle(ActionEvent e) {
        // Gestion des actions de la page de connexion
        handleConnectionPageActions(e);

        // Gestion des actions de la page d'inscription
        handleInscriptionPageActions(e);

        // Gestion des actions de la page mot de passe oublié
        handleForgotPasswordPageActions(e);

        // Gestion des actions de la page réinitialisation du mot de passe
        handleResetPasswordPageActions(e);

        //Gestion des actions de la page principal
        handleMainPageActions(e);

        //Trouver Chemin
        handleTrouverCheminCommuneActions(e);

        //Page Utilisateur
        handleAccountPageActions(e);

        handleCodeAlertActions(e);
    }








    private void handleAccountPageActions(ActionEvent e) {
        if(e.getSource() == this.accountPage.getDisconnectButton()){
            this.currentUser = null;
            returnToMainPage();
        }
    
        if(e.getSource() == this.accountPage.getDeleteButton()){
            int code = this.userServices.generateVerificationCode();
            this.codeString = code + "";
    
            try {
                this.userServices.sendVerificationEmail(currentUser.getEmail(), this.codeString);
            } catch (IOException | MessagingException e1) {
                e1.printStackTrace();
            }
    
            this.envoieCodeValidation = new CodeAlert(this);
            envoieCodeValidation.askCode(currentUser.getEmail());
        }
    
        if(e.getSource() == this.accountPage.getModifyButton()){
            this.accountPage.getNameField().setText(this.accountPage.getNameLabel().getText());
            this.accountPage.getFirstNameField().setText(this.accountPage.getFirstNameLabel().getText());
            this.accountPage.getEmailField().setText(this.accountPage.getEmailLink().getText());
    
            this.accountPage.getNameLabel().setVisible(false);
            this.accountPage.getFirstNameLabel().setVisible(false);
            this.accountPage.getEmailLink().setVisible(false);
    
            this.accountPage.getNameField().setVisible(true);
            this.accountPage.getFirstNameField().setVisible(true);
            this.accountPage.getEmailField().setVisible(true);
    
            this.accountPage.getModifyButton().setVisible(false);
            this.accountPage.getSaveButton().setVisible(true);
        }
    
        if(e.getSource() == this.accountPage.getSaveButton()){
            String newEmail = this.accountPage.getEmailField().getText();

            if(isValidEmail(newEmail)){
                if(!this.currentUser.getEmail().equals(newEmail) && !this.userServices.emailExists(newEmail)){
                    int code = this.userServices.generateVerificationCode();
                    this.codeString = code + "";
                    this.pendingNewEmail = newEmail;  // Store the new email temporarily
        
                    try {
                        this.userServices.sendVerificationEmail(newEmail, this.codeString);
                    } catch (IOException | MessagingException e1) {
                        e1.printStackTrace();
                    }
        
                    this.envoieCodeValidation = new CodeAlert(this);
                    envoieCodeValidation.askCode(newEmail);
                } else {
                    this.accountPage.getNameLabel().setText(this.accountPage.getNameField().getText());
                    this.accountPage.getFirstNameLabel().setText(this.accountPage.getFirstNameField().getText());
                    this.accountPage.getEmailLink().setText(this.accountPage.getEmailField().getText());

                    this.accountPage.getNameLabel().setVisible(true);
                    this.accountPage.getFirstNameLabel().setVisible(true);
                    this.accountPage.getEmailLink().setVisible(true);

                    this.accountPage.getNameField().setVisible(false);
                    this.accountPage.getFirstNameField().setVisible(false);
                    this.accountPage.getEmailField().setVisible(false);

                    this.accountPage.getModifyButton().setVisible(true);
                    this.accountPage.getSaveButton().setVisible(false);

                    String surname = this.accountPage.getNameLabel().getText();
                    String name = this.accountPage.getFirstNameLabel().getText();
                    String email = this.accountPage.getEmailLink().getText();
                    this.userServices.updateUser(this.currentUser.getEmail(), surname, name, email);
                    
                    this.currentUser.setNom(surname);
                    this.currentUser.setPrenom(name);
                }
            } else {
                CustomAlert.showAlert("Alerte", "Le Mail est invalide");
            }
        }
    }
    

    public void returnToMainPage(){
        try {
            Stage stage = (Stage) this.accountPage.getEmailLink().getScene().getWindow();
            this.mainPage.start(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }












    private void handleCodeAlertActions(ActionEvent e) {
        if(e.getSource() == this.envoieCodeValidation.getCloseButton()){
            if(this.envoieCodeValidation.getCodeField().getText().equals(this.codeString)){
                if (this.pendingNewEmail != null) {
                    String surname = this.accountPage.getNameLabel().getText();
                    String name = this.accountPage.getFirstNameLabel().getText();
                    
                    this.userServices.updateUser(this.currentUser.getEmail(), surname, name, pendingNewEmail);
                    this.currentUser.setNom(surname);
                    this.currentUser.setPrenom(name);
                    this.currentUser.setEmail(this.pendingNewEmail);

                    this.accountPage.getEmailLink().setText(this.pendingNewEmail);
                    this.pendingNewEmail = null;

                    this.accountPage.getNameLabel().setText(this.accountPage.getNameField().getText());
                    this.accountPage.getFirstNameLabel().setText(this.accountPage.getFirstNameField().getText());
                    this.accountPage.getEmailLink().setText(this.accountPage.getEmailField().getText());

                    this.accountPage.getNameLabel().setVisible(true);
                    this.accountPage.getFirstNameLabel().setVisible(true);
                    this.accountPage.getEmailLink().setVisible(true);

                    this.accountPage.getNameField().setVisible(false);
                    this.accountPage.getFirstNameField().setVisible(false);
                    this.accountPage.getEmailField().setVisible(false);

                    this.accountPage.getModifyButton().setVisible(true);
                    this.accountPage.getSaveButton().setVisible(false);
                } else {
                    // Handle account deletion
                    this.userServices.dropUser(currentUser.getEmail());
                    this.listeUtilisateur.remove(currentUser);
                    this.currentUser = null;
                }
    
                Stage stage = (Stage) this.envoieCodeValidation.getCloseButton().getScene().getWindow();
                this.mainPage.start(stage);
                stage.close();
            } else {
                this.envoieCodeValidation.getAlertLabel().setText("Le code est incorrect !");
                this.envoieCodeValidation.getAlertLabel().setStyle("-fx-text-fill: red;");
            }
        }
    }
    
















    //! ---------------- MAINPAGE METHOD



    /**
    * Handle the MainPage actions. 
    * @param e Event.
    */
    private void handleMainPageActions(ActionEvent e) {
        if(e.getSource() == this.mainPage.getSearchField()){
            String searchText = this.mainPage.getSearchField().getText().trim();
            handleSearchEvent(searchText);
        }

        if(e.getSource() == this.mainPage.getButtonCheminLePlusCourt()){
            try {
                Stage stage = (Stage) this.mainPage.getButtonCheminLePlusCourt().getScene().getWindow();
                this.trouverCheminCommune.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == this.mainPage.getFinistereFilterButton()){
            applyFilter(29);
        }

        if(e.getSource() == this.mainPage.getMorbihanFilterButton()){
            applyFilter(56);
        }

        if(e.getSource() == this.mainPage.getCoteArmorFilterButton()){
            applyFilter(22);
        }

        if(e.getSource() == this.mainPage.getIlleEtVilaineFilterButton()){
            applyFilter(35);
        }

        if(e.getSource() == this.mainPage.getToutesLesCommunes()){
            getCommune();
        }

        if(e.getSource() == this.mainPage.getReloadDatabase()){
            this.mainPage.loadCommunes(getCommunesFromDataBase());
            this.listeUtilisateur = this.userServices.loadAllUsers();

            CustomAlert.showAlert("Chargement Base de Donnees", "Le chargement est fini.");
        }
    }

    /**
    * Allow to search a commune and update the view. 
    * @param searchText The Name of the commune.
    */
    public void handleSearchEvent(String searchText) {
        List<Commune> filteredCommunes = getFilteredCommunes(searchText);
        this.mainPage.updateCommunesListView(filteredCommunes);
        this.mainPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
    }   

    /**
    * Open a new page with the details of the commune.
    * @param commune
    */
    public void showCommuneDetails(Commune commune){
        try {
            CommuneDetailsPage.showCommune(commune, this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
    * Filter the commune List with the commune we search
    * @param searchText the name of the commune we search.
    * @return An ArrayList of commune.
    */
    public ArrayList<Commune> getFilteredCommunes(String searchText) {
        ArrayList<Commune> allCommunes = this.communesRecente;
        ArrayList<Commune> filteredCommunes = new ArrayList<>();
        
        
        String lowerCaseSearchText = searchText.toLowerCase();
        for (Commune commune : allCommunes) {
            if (commune.getNomCommune().toLowerCase().startsWith(lowerCaseSearchText)) {
                filteredCommunes.add(commune);
            }
        }
        this.mainPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
        return filteredCommunes;
    }


    public void applyFilter(int idDep) {
        ArrayList<Commune> filterList = new ArrayList<Commune>();


        for (Commune commune : this.communesRecente) {
            if (commune.getDepartement().getIdDep() == idDep) {
                filterList.add(commune);
            }
        }        


        mainPage.updateCommunesListView(filterList);
        this.mainPage.getNumberOfRow().setText(filterList.size() + " r\u00e9sultats");
    }

    public void getCommune(){
        this.mainPage.updateCommunesListView(this.communesRecente);
        this.mainPage.getNumberOfRow().setText(this.communesRecente.size() + " r\u00e9sultats");
    }
    

    /**
    * Open the login panel when the image of account is clicked.
    */
    public void connectionClicked() {
        try {
            if (this.currentUser != null) {
                System.out.println("Utilisateur actuel : " + this.currentUser.getEmail());
                Stage stage = (Stage) this.mainPage.getSearchField().getScene().getWindow();
                this.accountPage.start(stage);

                this.accountPage.getFirstNameLabel().setText(currentUser.getPrenom());
                this.accountPage.getNameLabel().setText(currentUser.getNom());
                this.accountPage.getEmailLink().setText(currentUser.getEmail());
            } else {
                System.out.println("Utilisateur non connecté.");
                Stage stage = (Stage) this.mainPage.getSearchField().getScene().getWindow();
                this.connectionPage.start(stage);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    




























    //! ---------------- TROUVERCHEMINCOMMUNE METHOD


    /**
    * Handle the action of TrouverCheminCommune. 
    * @param e Event.
    */
    private void handleTrouverCheminCommuneActions(ActionEvent e){
        if(e.getSource() == this.trouverCheminCommune.getButton()){
            String firstCommuneText = this.trouverCheminCommune.getStartCommuneName().getText();
            String endCommuneText = this.trouverCheminCommune.getEndCommuneName().getText();

            findPath(firstCommuneText, endCommuneText);
        }
    }

    /**
    * Allow to found the path between two communes. 
    * @param startCommuneName The names of the first commune.
    * @param endCommuneName The name of the destination commune.
    */
    public void findPath(String startCommuneName, String endCommuneName) {
        CommuneService service = new CommuneService();

        try {
            Commune startCommune = service.getCommuneByName(startCommuneName, this.communeToute);
            Commune endCommune = service.getCommuneByName(endCommuneName, this.communeToute);

            if (startCommune == null || endCommune == null) {
                this.trouverCheminCommune.setResultLabel("Commune de d\u00e9part ou d'arriv\u00e9e introuvable.");
                return;
            }else if(startCommune.getNomCommune().equalsIgnoreCase(endCommune.getNomCommune())){
                this.trouverCheminCommune.setResultLabel("Commune de d\u00e9part ne peut pas \u00eatre la m\u00eame que la commune d'arriv\u00e9e.");
                return;
            } else {
                List<Commune> path = service.cheminEntreCommune(startCommune.getIdCommune(), endCommune.getIdCommune(), this.communeToute);

                if (path.isEmpty()) {
                    this.trouverCheminCommune.setResultLabel("Aucun chemin trouv\u00e9 entre les deux communes.");
                } else {
                    ServerConnectionManager connectionManager = loadConnectionManagerFromProperties("../properties/server.properties");
                    List<Integer> cityIds = new ArrayList<>();
                    for (Commune commune : path) {
                        cityIds.add(commune.getIdCommune());
                    }
                connectionManager.retrieveImage(cityIds);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.trouverCheminCommune.setResultLabel("Erreur lors de la recherche du chemin.");
        }
    }

    /**
    * 
    * @param propertiesFile
    * @return
    */
    private ServerConnectionManager loadConnectionManagerFromProperties(String propertiesFile) {
        try (FileInputStream input = new FileInputStream(propertiesFile)) {
            Properties properties = new Properties();
            properties.load(input);
            String serverURL = properties.getProperty("url");
            return new ServerConnectionManager(trouverCheminCommune, serverURL);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    
























    //! ---------------- CONNECTIONPAGE METHOD


    /**
    * Handle action of the Connection Page. 
    * @param e The Action Event
    */
    private void handleConnectionPageActions(ActionEvent e) {
        if (e.getSource() == this.connectionPage.getLinkSignUp()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                this.inscriptionPage.start(stage);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            
        }

        if (e.getSource() == this.connectionPage.getLinkForgotPassword()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                this.forgotPassword.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == this.connectionPage.getBtnLogin()) {
            String email = this.connectionPage.getEmailField().getText();
            String password = this.connectionPage.getPasswordField().getText();

            this.connectionPage.getErrorMessageLabel().setVisible(false);

            if (email.isEmpty() || password.isEmpty()) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.connectionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            } else if (!isValidEmail(email) || !this.userServices.emailExists(email)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.connectionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            } else {
                if (this.userServices.validateLogin(email, password)){
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.connectionPage.getErrorMessageLabel().setText("Connexion Reussi !");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);
                    
                    this.currentUser = searchList(email);

                    try{
                        CustomAlert.showAlert("Connexion Reussi", "Bonjour " + this.currentUser.getPrenom() + " " + this.currentUser.getNom() + " ! Redirection en cours.");
                        
                        Stage stage = (Stage) connectionPage.getBtnLogin().getScene().getWindow();
                        this.mainPage.start(stage);

                    } catch(Exception e1){
                        System.out.println(e1.getMessage());
                    }
                    

                } else {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.connectionPage.getErrorMessageLabel().setText("Identifiants incorrects.");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);
                }
            }
        }
    }

    private Utilisateur searchList(String email) {
        for (Utilisateur user : this.listeUtilisateur) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    
    public boolean isAdmin(){
        boolean b = false;
        if(this.currentUser == null){
            b = false;
        }else {
            if(this.userServices.userIsAdmin(this.currentUser.getEmail()) == 1){
                b = true;
            }
        }
        return(b);
    }

    public void verifyAdmin(){
        if (!isAdmin()) {
            this.mainPage.getEditData().setDisable(true);
            System.out.println("No admin !");
        }
    }





















    //! ---------------- INSCRIPTIONPAGE METHOD

    /**
    * Handle the action of the Inscription Page. 
    * @param e The Action Event
    */
    private void handleInscriptionPageActions(ActionEvent e){
        if (this.inscriptionPage != null && e.getSource() == this.inscriptionPage.getBtnSignUp()) {
            String firstName = this.inscriptionPage.getFirstNameField().getText();
            String lastName = this.inscriptionPage.getLastNameField().getText();
            String email = this.inscriptionPage.getEmailField().getText();
            String password = this.inscriptionPage.getPasswordField().getText();
            String confirmPassword = this.inscriptionPage.getConfirmPasswordField().getText();

            this.inscriptionPage.getErrorMessageLabel().setVisible(false);

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else if (!isValidEmail(email)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else if (!password.equals(confirmPassword)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);
            } else {
                if (this.userServices.emailExists(email)) {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail est d\u00e9j\u00e0 utilis\u00e9e.");
                    this.inscriptionPage.getErrorMessageLabel().setVisible(true);
                } else {
                    this.userServices.createUser(lastName, firstName, email, password);
                    this.inscriptionPage.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.inscriptionPage.getErrorMessageLabel().setText("Compte cr\u00e9e avec succ\u00e8s !");
                    this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                    this.listeUtilisateur = this.userServices.loadAllUsers();
                }
            }
        }

        if (e.getSource() == this.inscriptionPage.getLinkLogin()) {
            try {
                Stage stage = (Stage) this.inscriptionPage.getLinkLogin().getScene().getWindow();
                this.connectionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


















    //! ---------------- FORGOTPASSWORD METHOD


    /**
    * Handle the action of ForgotPassword page.
    * @param e The Action Event
    */
    private void handleForgotPasswordPageActions(ActionEvent e){
        if (e.getSource() == this.forgotPassword.getLinkForgotPassword()) {
            try {
                Stage stage = (Stage) this.forgotPassword.getBtnLogin().getScene().getWindow();
                this.connectionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == this.forgotPassword.getBtnLogin()){
            String email;
            if(this.forgotPassword.getBtnLogin().getText().equals("Recevoir Code")){
                String mail = this.forgotPassword.getEmailField().getText();
                if(mail.isEmpty()){
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Tous les champs doivent etre rempli");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                } else if(!this.userServices.emailExists(mail)){
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Un mail deja renseignee dans la bdd doit etre entrer");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                } else {

                    email = this.forgotPassword.getEmailField().getText();
                    int code = this.userServices.generateVerificationCode();
                    this.codeString = String.valueOf(code);

                    try {
                        this.userServices.sendVerificationEmail(email, codeString);
                    } catch (IOException e1) {
                        System.out.println(e1.getMessage());
                    } catch (MessagingException e2){
                        System.out.println(e2.getMessage());
                    }
                    this.codeSent = true;
                    updateButtonState();
                    CustomAlert.showAlert("Email envoy\u00e9", "Un email avec le code de v\u00e9rification a \u00e9t\u00e9 envoy\u00e9 \u00e0 " + email);


                    System.out.println(codeString);
                }
            } else if(this.forgotPassword.getBtnLogin().getText().equals("V\u00e9rifier")){
                if(this.forgotPassword.getCodeField().getText().equals(this.codeString)){
                    try {
                        Stage stage = (Stage) this.forgotPassword.getBtnLogin().getScene().getWindow();
                        this.resetPassword.start(stage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Le code specifie est incorrect ! ");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                }
            }
        }
    }

    /**
    * Private method, which change the state of the btnLogin present in forgotPassword page.
    */
    private void updateButtonState() {
        if (this.codeSent) {
            this.forgotPassword.getBtnLogin().setText("V\u00e9rifier");
            this.forgotPassword.getCodeField().setDisable(false);
        } else {
            this.forgotPassword.getBtnLogin().setText("Recevoir Code");
        }
    }




















    //! ---------------- RESETPASSWORD METHOD

    /**
    * Handle the action of ResetPassword page. 
    * @param e The Action Event
    */
    private void handleResetPasswordPageActions(ActionEvent e){
        if (e.getSource() == this.resetPassword.getBtnValidate()) {
            String newPassword = this.resetPassword.getFirstPassword().getText();
            String confirmPassword = this.resetPassword.getSecondPassword().getText();

            if (newPassword.equals(confirmPassword)) {
                if (!newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                    String email = this.forgotPassword.getEmailField().getText();
                    try {
                        this.userServices.updatePassword(email, newPassword);

                        this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: green; -fx-font-size: 15px;");
                        this.resetPassword.getErrorMessageLabel().setVisible(true);

                        this.resetPassword.getErrorMessageLabel().setText("Redirection...");
                        
                        CustomAlert.showAlert("Reinitialisation du mot de passe", "Votre mot de passe est change.");

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    Stage stage = (Stage) resetPassword.getBtnValidate().getScene().getWindow();
                                    connectionPage.start(stage);
                                });
                            }
                        }, 3000);

                    } catch (SQLException ex) {
                        this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                        this.resetPassword.getErrorMessageLabel().setText("Connexion au service momentanément impossible.");
                        this.resetPassword.getErrorMessageLabel().setVisible(true);
                    }
                } else {
                    this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.resetPassword.getErrorMessageLabel().setText("Les champs de mot de passe doivent être remplis !");
                    this.resetPassword.getErrorMessageLabel().setVisible(true);
                }
            } else {
                this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.resetPassword.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas !");
                this.resetPassword.getErrorMessageLabel().setVisible(true);
            }
        }

        if(e.getSource() == this.resetPassword.getLinkWrongMail()){
            Stage stage = (Stage) this.resetPassword.getBtnValidate().getScene().getWindow();
            this.forgotPassword.start(stage);
        }

        if(e.getSource() == this.resetPassword.getLinkConnection()){
            Stage stage = (Stage) this.resetPassword.getBtnValidate().getScene().getWindow();
            this.connectionPage.start(stage);
        }
    }



    

     


    









    //! ---------------- MULTICLASS METHOD

    /**
    * Check if a mail is valid with the format mail@subdomain.extension 
    *
    * @param email The checked mail
    * @return True if valid mail, else return false.
    */
    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
    * Allow to get all of the commune.
    * @return ArrayList<Commune> which contains the most recent year for each commune.
    */
    public ArrayList<Commune> getCommunesFromDataBase(){
        this.communesRecente = new ArrayList<>();
        CommuneService communeService = new CommuneService();
    
        try {
            this.communeToute = communeService.getAllCommunes();
            
            //l'annee la plus recente qu'on a actuellement (-1), 
            //(preventive difficile d'avoir les données pour l'année en dessous de -1).
            int mostRecentYear = -1;
    
           
            for (int i = 0; i < communeToute.size(); i++) {
                Commune commune = communeToute.get(i);
                int currentYear = commune.getlAnnee();
                if (currentYear > mostRecentYear) {
                    mostRecentYear = currentYear;
                }
            }
    
            ArrayList<Commune> recentCommunes = new ArrayList<>();
            for (int i = 0; i < communeToute.size(); i++) {
                Commune commune = communeToute.get(i);
                if (commune.getlAnnee() == mostRecentYear) {
                    recentCommunes.add(commune);
                }
            }
    
            this.communesRecente = recentCommunes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return this.communesRecente;
    }



    /**
    * Allow to get all of available year of data for a Commune 
    * @param commune The commune we look for the data
    *
    * @return ArrayList of Integer which contains all of years of data.
    * 
    */
    public ArrayList<Integer> getYearsForCommune(Commune commune) {
        List<Commune> allCommunes = this.communeToute;
        ArrayList<Integer> returnValue = new ArrayList<Integer>();

        for (Commune currentCommune : allCommunes) {
            if (currentCommune.getNomCommune().equals(commune.getNomCommune())) {
                returnValue.add(currentCommune.getlAnnee());

            }
        }

        return(returnValue);
    }


    /**
    * Allow to get a specific commune which contains data of specified year 
    * @param communeName The name of the commune
    * @param year Year of data
    *
    * @return Data of commune for year passed in parameter
    */
    public Commune getCommuneForYearAndCommune(String communeName, int year){
        List<Commune> allCommunes = this.communeToute;
        Commune returnCommune = null;

        for (Commune currentCommune : allCommunes) {
            if(currentCommune.getNomCommune().equals(communeName) && currentCommune.getlAnnee() == year){
                returnCommune = currentCommune;
                break;
            }
        }

        return(returnCommune);
    }
}