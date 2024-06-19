package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Timer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dao.AeroportService;
import dao.AnneeService;
import dao.CommuneService;
import dao.DepartementService;
import dao.GareService;
import dao.UserService;
import data.Aeroport;
import data.Annee;
import data.Commune;
import data.Departement;
import data.Gare;
import data.Utilisateur;
import view.AccountPage;
import view.AdministratorsPage;
import view.CommuneDetailsModifPage;
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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    * A boolean check if a mail code is sent or not.
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
    * ArrayList containing the most recent communes.
    */
    private ArrayList<Commune> communesRecente;

    /**
    * ArrayList containing all of the communes.
    */
    private List<Commune> communeToute;

    /**
    * The current user, otherwise null
    */
    private Utilisateur currentUser;

    /**
    * ArrayList containing all of Users. 
    */
    private ArrayList<Utilisateur> listeUtilisateur;

    /**
    * Instance of userServices. 
    */
    private UserService userServices;

    /**
    * Instance of AccountPage 
    */
    private AccountPage accountPage;

    /**
    * Instance of CodeAlert - Allow to display a pop-up with a TextField for enter the verification code.
    */
    private CodeAlert envoieCodeValidation;

    /**
    * Store the new mail if the user want to change his password in the AccountPage. 
    */
    private String pendingNewEmail;

    private List<Gare> listeGare;

    private GareService gareService;

    private CommuneService communeService;

    private List<Annee> listeAnnee;

    private List<Aeroport> listeAeroport;

    private List<Departement> listeDepartement;

    private AdministratorsPage administratorsPage;

    private CommuneDetailsModifPage communeDetailsModifPage;

    private AnneeService anneeService;

    private DepartementService departementService;

    /**
    * The constructor of Controller. 
    * @param connectionPage
    */
    public Controller(MainPage mainPage) {
        this.anneeService = new AnneeService();
        this.communeDetailsModifPage = new CommuneDetailsModifPage();
        this.userServices = new UserService();
        this.listeUtilisateur = this.userServices.loadAllUsers();
        this.connectionPage = new ConnectionPage(this);
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this); 
        this.resetPassword = new ResetPassword(this);
        this.trouverCheminCommune = new TrouverCheminCommune(this);
        this.accountPage = new AccountPage(this);
        this.envoieCodeValidation = new CodeAlert(this);
        this.administratorsPage = new AdministratorsPage(this);
        this.mainPage = mainPage;

        this.communeService = new CommuneService();
        this.gareService = new GareService();
    }

    /**
    * The Empty constructor of controller 
    */
    public Controller() {
        this.anneeService = new AnneeService();
        this.communeDetailsModifPage = new CommuneDetailsModifPage();
        this.userServices = new UserService();
        this.listeUtilisateur = this.userServices.loadAllUsers();
        this.connectionPage = new ConnectionPage(this);
        this.inscriptionPage = new InscriptionPage(this);
        this.forgotPassword = new ForgotPassword(this); 
        this.resetPassword = new ResetPassword(this);
        this.trouverCheminCommune = new TrouverCheminCommune(this);
        this.accountPage = new AccountPage(this);
        this.administratorsPage = new AdministratorsPage(this);
        this.envoieCodeValidation = new CodeAlert(this);

        this.communeService = new CommuneService();
        this.gareService = new GareService();
    }

    /**
    * Allow to mainPage to set himself in the Controller
    * @param mainPage MainPage instance.
    */
    public void setMainPage(MainPage mainPage){
        this.mainPage = mainPage;
    }









    /* ---------------------------------- */



    /**
    * Method that will detect if a movement is made in one of the views, 
    * and will redirect them to the private handle methods
    * to make the code much more readable.
    *
    * @param ActionEvent Action which is realised.
    */
    @Override
    public void handle(ActionEvent e) {
        // Redirect Actions into ConnectionPage.
        handleConnectionPageActions(e);

        // Redirect Actions into InscriptionPage.
        handleInscriptionPageActions(e);

        // Redirect Actions into ForgotPassword.
        handleForgotPasswordPageActions(e);

        // Redirect Actions into ResetPassword.
        handleResetPasswordPageActions(e);

        // Redirect Actions into MainPage.
        handleMainPageActions(e);

        // Redirect Actions into TrouverCheminCommune.
        handleTrouverCheminCommuneActions(e);

        // Redirect Actions into AccountPage.
        handleAccountPageActions(e);

        // Redirect Actions into CodeAlert.
        handleCodeAlertActions(e);

        handleAdministratorPageActions(e);

        handleCommuneDetailsModifPage(e);
    }






    //! ---------------- ACCOUNT PAGE
    // The following code is for Actions realised in the AccountPage.


    /**
    * Handle the Actions realised in AccountPage
    * @param e The Action Event
    */
    private void handleAccountPageActions(ActionEvent e) {
        // when disconnect button is clicked, user is null and we redirect user into mainPage.
        if(e.getSource() == this.accountPage.getDisconnectButton()){
            this.currentUser = null;
            returnToMainPage();
        }
    
        // when delete button is clicked we generate a code and send to his email
        // and we pop-up a CodeAlert page for ask him the code before deleting his account.
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
    
        // when the modify button is clicked, then we change the Label into TextField 
        // Save Button is Visible and Modify Button is Not Visible.
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
    
        // when save button is clicked, we check if the mail has changed, 
        // if mail is changed we check if mail is valid and doesn't exist in the database
        // if these 2 conditions is valid, we send a verification code to prove the user is the owner of the mail
        
        // Otherwise if mail doesn't changed we change the name and the firstname in the object Utilisateur and in the Database.
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
    
    /**
    * Change the active stage for redirect user into MainPage. 
    */
    public void returnToMainPage(){
        try {
            Stage stage = (Stage) this.accountPage.getEmailLink().getScene().getWindow();
            this.mainPage.start(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }








    //! ---------------- PAGE TO HANDLE CODE SENDING
    // Following code is for actions in CodeAlert.


    /**
    * Handle the actions in CodeAlert 
    * @param e The Action Event
    */
    private void handleCodeAlertActions(ActionEvent e) {
        // when user is validating the code. (The user validate code only if he change his mail or delete his account)
        if(e.getSource() == this.envoieCodeValidation.getCloseButton()){

            // we check if the user input match with the generated code.
            if(this.envoieCodeValidation.getCodeField().getText().equals(this.codeString)){
                //if code match

                // if the user want to change his email.
                // We update user informations and we change the view for replace input by Label.
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
                    // if the user don't want to change his mail, otherwise he wanna delete his account.
                    // drop user from database and from listeUtilisateur containing all of users.
                    this.userServices.dropUser(currentUser.getEmail());
                    this.listeUtilisateur.remove(currentUser);

                    // and so the account of user don't exists anymore the currentUser is now null.
                    this.currentUser = null;
                }

                // Closing the codeValidation page.
                Stage stage2 = (Stage) this.envoieCodeValidation.getCloseButton().getScene().getWindow();
                stage2.close();

                // Launching MainPage.
                Stage stage = (Stage) this.accountPage.getDeleteButton().getScene().getWindow();
                this.mainPage.start(stage);
            } else {
                this.envoieCodeValidation.getAlertLabel().setText("Le code est incorrect !");
                this.envoieCodeValidation.getAlertLabel().setStyle("-fx-text-fill: red;");
            }
        }
    }
    
















    //! ---------------- MAINPAGE METHOD
    // Following code is for handle MainPage Actions.


    /**
    * Handle the MainPage actions. 
    * @param e The Action Event
    */
    private void handleMainPageActions(ActionEvent e) {
        // if search field is triggered.
        if(e.getSource() == this.mainPage.getSearchField()){
            // get the text from search field.
            String searchText = this.mainPage.getSearchField().getText().trim();

            // we handle search event with the text of input.
            handleSearchEvent(searchText);
        }

        // when user clicked to CheminLePlusCourt button we launch trouverCheminCommune page.
        if(e.getSource() == this.mainPage.getButtonCheminLePlusCourt()){
            try {
                Stage stage = (Stage) this.mainPage.getButtonCheminLePlusCourt().getScene().getWindow();
                this.trouverCheminCommune.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // if user clicked on the button finistere we display only the commune in the Finistere department.
        if(e.getSource() == this.mainPage.getFinistereFilterButton()){
            applyFilter(29);
            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            this.mainPage.getFinistereFilterButton().setStyle(buttonStyleFocused);
            this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.mainPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
            this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button Morbihan, we display only the commune in the Morbihan department.
        if(e.getSource() == this.mainPage.getMorbihanFilterButton()){
            applyFilter(56);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

                this.mainPage.getFinistereFilterButton().setStyle(buttonStyle);
                this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyle);
                this.mainPage.getMorbihanFilterButton().setStyle(buttonStyleFocused);
                this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
                this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button CoteArmor, we display only the commune in the CoteArmor department.
        if(e.getSource() == this.mainPage.getCoteArmorFilterButton()){
            applyFilter(22);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

                this.mainPage.getFinistereFilterButton().setStyle(buttonStyle);
                this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyleFocused);
                this.mainPage.getMorbihanFilterButton().setStyle(buttonStyle);
                this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
                this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button IlleEtVillaine, we display only the commune in the IlleEtVillaine department.
        if(e.getSource() == this.mainPage.getIlleEtVilaineFilterButton()){
            applyFilter(35);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            this.mainPage.getFinistereFilterButton().setStyle(buttonStyle);
            this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.mainPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyleFocused);
            this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if the user clicked on all communes, we display all communes.
        if(e.getSource() == this.mainPage.getToutesLesCommunes()){
            getCommune();

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            this.mainPage.getFinistereFilterButton().setStyle(buttonStyle);
            this.mainPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.mainPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.mainPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
            this.mainPage.getToutesLesCommunes().setStyle(buttonStyle);
            this.mainPage.getToutesLesCommunes().setStyle(buttonStyleFocused);
        }

        // If the user clicked on reload database, we call database for had the data stored in the database.
        if(e.getSource() == this.mainPage.getReloadDatabase()){
            this.mainPage.loadCommunes(getCommunesFromDataBase());
            this.administratorsPage.loadCommunes(getCommunesFromDataBase());
            this.listeUtilisateur = this.userServices.loadAllUsers();
            try {
                this.listeGare = this.gareService.getAllGares();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            CustomAlert.showAlert("Chargement Base de Donnees", "Le chargement est fini.");
        }

        if(e.getSource() == this.mainPage.getExportButton()){
            exportData();
            CustomAlert.showAlert("Export des données", "Les données ont bien était exporté");
        }

        if(e.getSource() == this.mainPage.getEditData()){
            Stage stage = (Stage) this.mainPage.getExportButton().getScene().getWindow();
            this.administratorsPage.start(stage);
        }
    }

    /**
    * Allow to search a commune and update the view, from the list of commune. 
    * @param searchText User input
    */
    public void handleSearchEvent(String searchText) {
        List<Commune> filteredCommunes = getFilteredCommunes(searchText);
        this.mainPage.updateCommunesListView(filteredCommunes);
        this.mainPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
    }   

    /**
    * Open a pop-up styled page for had all data from a commune.
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
    * search all communes starts with the users input and put them into an ArrayList
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
        //Change result label with the numbers of commune found.
        this.mainPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
        return filteredCommunes;
    }

    /**
    * Search commune in the department specified in parameters, create an ArrayList containing all of these commune.
    * and change the mainPage list with the filteredCommune list.
    * @param idDep The Department ID.
    */
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

    /**
    * Change the list of commune displayed in the MainPage, with all communes (we display in the mainPage only most recent commune). 
    */
    public void getCommune(){
        this.mainPage.updateCommunesListView(this.communesRecente);
        this.mainPage.getNumberOfRow().setText(this.communesRecente.size() + " r\u00e9sultats");
    }
    

    /**
    * If user clicked on account image, there are two cases.
    * First the user is not connected : We redirect user to the connectionPage.
    * Second the user is connected : We redirect user to account page.
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
                String utilisateurEtat = "";
                if(this.userServices.userIsAdmin(currentUser.getEmail()) == 1){
                    this.currentUser.setAdmin(true);
                    utilisateurEtat = "Administrateur";
                } else {
                    this.currentUser.setAdmin(false);
                    utilisateurEtat = "Utilisateur";
                }
                this.accountPage.getAdmin().setText(utilisateurEtat);
            } else {
                System.out.println("Utilisateur non connecté.");
                Stage stage = (Stage) this.mainPage.getSearchField().getScene().getWindow();
                this.connectionPage.start(stage);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    







































    //! AdministratorPage

    private void handleAdministratorPageActions(ActionEvent e) {
        // if search field is triggered.
        if(e.getSource() == this.administratorsPage.getSearchField()){
            // get the text from search field.
            String searchText = this.administratorsPage.getSearchField().getText().trim();

            // we handle search event with the text of input.
            handleSearchEventAdminPage(searchText);
        }

        // when user clicked to CheminLePlusCourt button we launch trouverCheminCommune page.
        if(e.getSource() == this.administratorsPage.getButtonCheminLePlusCourt()){
            try {
                Stage stage = (Stage) this.administratorsPage.getButtonCheminLePlusCourt().getScene().getWindow();
                this.trouverCheminCommune.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // if user clicked on the button finistere we display only the commune in the Finistere department.
        if(e.getSource() == this.administratorsPage.getFinistereFilterButton()){
            applyFilterAdminPage(29);
            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyleFocused);
            this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button Morbihan, we display only the commune in the Morbihan department.
        if(e.getSource() == this.administratorsPage.getMorbihanFilterButton()){
            applyFilterAdminPage(56);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

                this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyleFocused);
                this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button CoteArmor, we display only the commune in the CoteArmor department.
        if(e.getSource() == this.administratorsPage.getCoteArmorFilterButton()){
            applyFilterAdminPage(22);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

                this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyleFocused);
                this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
                this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if user clicked on the button IlleEtVillaine, we display only the commune in the IlleEtVillaine department.
        if(e.getSource() == this.administratorsPage.getIlleEtVilaineFilterButton()){
            applyFilterAdminPage(35);

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyleFocused);
            this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
        }

        // if the user clicked on all communes, we display all communes.
        if(e.getSource() == this.administratorsPage.getToutesLesCommunes()){
            getCommuneAdminPage();

            String buttonStyleFocused = 
                "-fx-background-color: #C4C5CF; " +
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: red; " +
                "-fx-border-width: 2px;";

            String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                "-fx-text-fill: #000000; " +      
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

            this.administratorsPage.getFinistereFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getCoteArmorFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getMorbihanFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getIlleEtVilaineFilterButton().setStyle(buttonStyle);
            this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyle);
            this.administratorsPage.getToutesLesCommunes().setStyle(buttonStyleFocused);
        }

        // If the user clicked on reload database, we call database for had the data stored in the database.
        if(e.getSource() == this.administratorsPage.getReloadDatabase()){
            this.administratorsPage.loadCommunes(getCommunesFromDataBase());
            this.listeUtilisateur = this.userServices.loadAllUsers();
            try {
                this.listeGare = this.gareService.getAllGares();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            CustomAlert.showAlert("Chargement Base de Donnees", "Le chargement est fini.");
        }

        if(e.getSource() == this.administratorsPage.getExportButton()){
            exportData();
            CustomAlert.showAlert("Export des données", "Les données ont bien était exporté");
        }

        if(e.getSource() == this.administratorsPage.getEditData()){
            Stage stage = (Stage) this.administratorsPage.getExportButton().getScene().getWindow();
            this.mainPage.start(stage);
        }

        if(e.getSource() == this.administratorsPage.getCreateCommune()){
            System.out.println("UZAJDAJDAZJD");
            
        }
    }


    /**
    * Allow to search a commune and update the view, from the list of commune. 
    * @param searchText User input
    */
    public void handleSearchEventAdminPage(String searchText) {
        List<Commune> filteredCommunes = getFilteredCommunesAdminPage(searchText);
        this.administratorsPage.updateCommunesListView(filteredCommunes);
        this.administratorsPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
    }  

    /**
    * Search commune in the department specified in parameters, create an ArrayList containing all of these commune.
    * and change the mainPage list with the filteredCommune list.
    * @param idDep The Department ID.
    */
    public void applyFilterAdminPage(int idDep) {
        ArrayList<Commune> filterList = new ArrayList<Commune>();

        for (Commune commune : this.communesRecente) {
            if (commune.getDepartement().getIdDep() == idDep) {
                filterList.add(commune);
            }
        }        

        this.administratorsPage.updateCommunesListView(filterList);
        this.administratorsPage.getNumberOfRow().setText(filterList.size() + " r\u00e9sultats");
    }

     /**
    * Change the list of commune displayed in the MainPage, with all communes (we display in the mainPage only most recent commune). 
    */
    public void getCommuneAdminPage(){
        this.administratorsPage.updateCommunesListView(this.communesRecente);
        this.administratorsPage.getNumberOfRow().setText(this.communesRecente.size() + " r\u00e9sultats");
    }

     /**
    * search all communes starts with the users input and put them into an ArrayList
    * @param searchText the name of the commune we search.
    * @return An ArrayList of commune.
    */
    public ArrayList<Commune> getFilteredCommunesAdminPage(String searchText) {
        ArrayList<Commune> allCommunes = this.communesRecente;
        ArrayList<Commune> filteredCommunes = new ArrayList<>();
        
        
        String lowerCaseSearchText = searchText.toLowerCase();
        for (Commune commune : allCommunes) {
            if (commune.getNomCommune().toLowerCase().startsWith(lowerCaseSearchText)) {
                filteredCommunes.add(commune);
            }
        }
        //Change result label with the numbers of commune found.
        this.administratorsPage.getNumberOfRow().setText(filteredCommunes.size() + " r\u00e9sultats");
        return filteredCommunes;
    }

    /**
    * Open a pop-up styled page for had all data from a commune.
    * @param commune
    */
    public void editCommuneDetails(Commune commune){
        try {
            this.communeDetailsModifPage.showCommune(commune, this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }



















    // ! ----- CommuneDetailsPage




    private void handleCommuneDetailsModifPage(ActionEvent e){
        if (e.getSource() == this.communeDetailsModifPage.getSaveButton()) {
            try {
                // Vérification des champs obligatoires
                if (this.communeDetailsModifPage.getIdValue() == null || this.communeDetailsModifPage.getIdValue().isEmpty() ||
                    this.communeDetailsModifPage.getNbMaisonsText() == null || this.communeDetailsModifPage.getNbMaisonsText().isEmpty() ||
                    this.communeDetailsModifPage.getNbAppartementsText() == null || this.communeDetailsModifPage.getNbAppartementsText().isEmpty() ||
                    this.communeDetailsModifPage.getPrixMoyenText() == null || this.communeDetailsModifPage.getPrixMoyenText().isEmpty() ||
                    this.communeDetailsModifPage.getPrixM2MoyenText() == null || this.communeDetailsModifPage.getPrixM2MoyenText().isEmpty() ||
                    this.communeDetailsModifPage.getSurfaceMoyenneText() == null || this.communeDetailsModifPage.getSurfaceMoyenneText().isEmpty() ||
                    this.communeDetailsModifPage.getDepCulturellesTextFieldValue() == null || this.communeDetailsModifPage.getDepCulturellesTextFieldValue().isEmpty() ||
                    this.communeDetailsModifPage.getBudgetTotalField().getText() == null || this.communeDetailsModifPage.getBudgetTotalField().getText().isEmpty() ||
                    this.communeDetailsModifPage.getPopulationTextFieldValue() == null || this.communeDetailsModifPage.getPopulationTextFieldValue().isEmpty() ||
                    this.communeDetailsModifPage.getAnneeTextFieldValue() == null || this.communeDetailsModifPage.getAnneeTextFieldValue().isEmpty()) 
                {
                    CustomAlert.showAlert("Erreur", "Tous les champs doivent être remplis");
                } else {
                    // Essayer de parser tous les champs d'entrée pour s'assurer qu'ils sont valides
                    try {
                        int id = Integer.parseInt(this.communeDetailsModifPage.getIdValue());
                        int nbMaisons = Integer.parseInt(this.communeDetailsModifPage.getNbMaisonsText());
                        int nbAppartements = Integer.parseInt(this.communeDetailsModifPage.getNbAppartementsText());
                        int prixMoyen = Integer.parseInt(this.communeDetailsModifPage.getPrixMoyenText());
                        int prixM2Moyen = Integer.parseInt(this.communeDetailsModifPage.getPrixM2MoyenText());
                        int surfaceMoyenne = Integer.parseInt(this.communeDetailsModifPage.getSurfaceMoyenneText());
                        int depCulturelles = Integer.parseInt(this.communeDetailsModifPage.getDepCulturellesTextFieldValue());
                        int budgetTotal = Integer.parseInt(this.communeDetailsModifPage.getBudgetTotalField().getText());
                        int population = Integer.parseInt(this.communeDetailsModifPage.getPopulationTextFieldValue());
                        int annee = Integer.parseInt(this.communeDetailsModifPage.getAnneeTextFieldValue());
        
                        // Récupération des années existantes pour la commune
                        Commune communeAvantModif = this.communeDetailsModifPage.getCommuneAvantModif();
                        ArrayList<Integer> existingYears = getYearsForCommune(communeAvantModif);
        
                        // Vérification si l'année existe déjà
                        if (existingYears.contains(annee)) {
                            // Mise à jour de la commune et des données annuelles
                            this.communeService.updateCommuneEtDonneesAnnuelles(id, nbMaisons, nbAppartements, prixMoyen, prixM2Moyen, surfaceMoyenne, depCulturelles, budgetTotal, population, annee);
        
                            Commune communeWithGoodYear = getCommuneForYearAndCommune(communeAvantModif.getNomCommune(), annee);
        
                            communeWithGoodYear.setNbMaison(nbMaisons);
                            communeWithGoodYear.setNbAppart(nbAppartements);
                            communeWithGoodYear.setPrixMoyen(prixMoyen);
                            communeWithGoodYear.setPrixM2Moyen(prixM2Moyen);
                            communeWithGoodYear.setSurfaceMoy(surfaceMoyenne);
                            communeWithGoodYear.setDepCulturellesTotales(depCulturelles);
                            communeWithGoodYear.setBudgetTotal(budgetTotal);
                            communeWithGoodYear.setPopulation(population);
        
                            Annee anneeAvantModif = communeWithGoodYear.getAnnee();
                            anneeAvantModif.setAnnee(annee);
                            communeWithGoodYear.setAnnee(anneeAvantModif);
        
                            this.administratorsPage.updateCommunesListView(this.communesRecente);
                            this.mainPage.updateCommunesListView(this.communesRecente);
        
                            CustomAlert.showAlert("Modification Commune", "Commune modifiée avec succès !");
                        } else {
                            // Nouvelle année, affichage du message "New Year"
                            CustomAlert.showAlert("New Year", "Une nouvelle année a été ajoutée !");

                        }
                    } catch (NumberFormatException e1) {
                        CustomAlert.showAlert("Erreur", "Les nombres doivent être des entiers valides");
                    }
                }
            } catch (Exception ex) {
                CustomAlert.showAlert("Erreur", "Une erreur inattendue s'est produite");
            }
        }        
    }


















































    //! ---------------- TROUVERCHEMINCOMMUNE METHOD


    /**
    * Handle the action of TrouverCheminCommune. 
    * @param e The Action Event
    */
    private void handleTrouverCheminCommuneActions(ActionEvent e){
        // when button is clicked, we send those two communes in findPath method.
        if(e.getSource() == this.trouverCheminCommune.getButton()){
            String firstCommuneText = this.trouverCheminCommune.getStartCommuneName().getText();
            String endCommuneText = this.trouverCheminCommune.getEndCommuneName().getText();

            findPath(firstCommuneText, endCommuneText);
        }

        if(e.getSource() == this.trouverCheminCommune.getExportDataButton()){
            exportData();
            CustomAlert.showAlert("Export des données", "Les données ont bien était exporté");
        }

        if(e.getSource() == this.trouverCheminCommune.getPagePrincipalButton()){
            try {
                Stage stage = (Stage) this.trouverCheminCommune.getPagePrincipalButton().getScene().getWindow();
                this.mainPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == this.trouverCheminCommune.getReloadDatabaseButton()){
            this.communeToute = getCommunesFromDataBase();
            this.listeUtilisateur = this.userServices.loadAllUsers();
            try {
                this.listeGare = this.gareService.getAllGares();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            CustomAlert.showAlert("Chargement Base de Donnees", "La base de données a etait recharge");
        }



        
    }

    /**
    * Finds the path between two communes.
    * 
    * This method searches for a path between the start and end communes specified
    * by their names. It updates the result label with appropriate messages based 
    * on the outcome of the search. If an error occurs during the search, it catches
    * the SQLException and updates the result label with an error message.
    * 
    * @param startCommuneName The name of the starting commune.
    * @param endCommuneName The name of the destination commune.
    */
    public void findPath(String startCommuneName, String endCommuneName) {
        try {
            // Step 1: Retrieve the starting and ending communes by name
            Commune startCommune = this.communeService.getCommuneByName(startCommuneName, this.communeToute);
            Commune endCommune = this.communeService.getCommuneByName(endCommuneName, this.communeToute);

            // Step 2: Check if either the start or end commune is not found
            if (startCommune == null || endCommune == null) {
                this.trouverCheminCommune.setResultLabel("Commune de départ ou d'arrivée introuvable.");
                return;
            }

            // Step 3: Check if the start and end communes are the same
            if (startCommune.getNomCommune().equalsIgnoreCase(endCommune.getNomCommune())) {
                this.trouverCheminCommune.setResultLabel("Commune de départ ne peut pas être la même que la commune d'arrivée.");
                return;
            }

            // Step 4: Find the path between the start and end communes
            List<Commune> path = this.communeService.cheminEntreCommune(startCommune.getIdCommune(), endCommune.getIdCommune(), this.communeToute);

            // Step 5: Check if a path is found
            if (path.isEmpty()) {
                this.trouverCheminCommune.setResultLabel("Aucun chemin trouvé entre les deux communes.");
            } else {
                // Step 6: Retrieve images for the communes along the path
                ServerConnectionManager connectionManager = loadConnectionManagerFromProperties("../properties/server.properties");
                List<Integer> cityIds = new ArrayList<>();
                for (Commune commune : path) {
                    cityIds.add(commune.getIdCommune());
                }
                connectionManager.retrieveImage(cityIds);
            }
        } catch (SQLException e) {
            // Step 7: Catch any SQLException that occurs during the process
            e.printStackTrace();
            this.trouverCheminCommune.setResultLabel("Erreur lors de la recherche du chemin.");
        }
    }



    /**
    * Allow to initiate a connection with the PythonServer and get the url in the Properties file.
    * @param propertiesFile The file containing the url to the server.
    * @return A Connection.
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

    public void connectionClickedTrouverCheminCommune() {
        try {
            if (this.currentUser != null) {
                System.out.println("Utilisateur actuel : " + this.currentUser.getEmail());
                Stage stage = (Stage) this.trouverCheminCommune.getButton().getScene().getWindow();
                this.accountPage.start(stage);

                this.accountPage.getFirstNameLabel().setText(currentUser.getPrenom());
                this.accountPage.getNameLabel().setText(currentUser.getNom());
                this.accountPage.getEmailLink().setText(currentUser.getEmail());
                String utilisateurEtat = "";
                if(this.userServices.userIsAdmin(currentUser.getEmail()) == 1){
                    this.currentUser.setAdmin(true);
                    utilisateurEtat = "Administrateur";
                } else {
                    this.currentUser.setAdmin(false);
                    utilisateurEtat = "Utilisateur";
                }
                this.accountPage.getAdmin().setText(utilisateurEtat);
            } else {
                System.out.println("Utilisateur non connecté.");
                Stage stage = (Stage) this.trouverCheminCommune.getButton().getScene().getWindow();
                this.connectionPage.start(stage);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
























    //! ---------------- CONNECTIONPAGE METHOD


    /**
    * Handle action of the Connection Page. 
    * @param e The Action Event
    */
    private void handleConnectionPageActions(ActionEvent e) {
        // if user clicked on link signup we redirect it in the InscriptionPage.
        if (e.getSource() == this.connectionPage.getLinkSignUp()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                this.inscriptionPage.start(stage);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        // if user clicked on forgotPassword we redirect it in the ForgotPassword.
        if (e.getSource() == this.connectionPage.getLinkForgotPassword()) {
            try {
                Stage stage = (Stage) this.connectionPage.getBtnLogin().getScene().getWindow();
                this.forgotPassword.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // if user clicked on login button
        if (e.getSource() == this.connectionPage.getBtnLogin()) {
            // We get the email and password from the user's input.
            String email = this.connectionPage.getEmailField().getText();
            String password = this.connectionPage.getPasswordField().getText();

            // error message is not visible while error not happening.
            this.connectionPage.getErrorMessageLabel().setVisible(false);

            // if user's input is empty we display an error message with ErrorMessage Label.
            if (email.isEmpty() || password.isEmpty()) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.connectionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);
            
                // else if the emails isn't valid (email already exists or not in the format mail@example.com)
                // we display an error message with ErrorMessage Label.
            } else if (!isValidEmail(email) || !this.userServices.emailExists(email)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.connectionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.connectionPage.getErrorMessageLabel().setVisible(true);

                // else the connection is valid and we check if the (email, password) matches with the database.
            } else {
                // if matches we display a succesful message and we set the current user and show an alert.
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
                    
                    // if (email, password) don't match with the databse we display an Error message with ErrorMessage Label.
                } else {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.connectionPage.getErrorMessageLabel().setText("Identifiants incorrects.");
                    this.connectionPage.getErrorMessageLabel().setVisible(true);
                }
            }
        }
    }

    /**
    * Private method searching the user with his mail and return it. 
    * @param email The email of searched users
    * @return Utilisateur of the searched user, if isn't exists we return null.
    */
    private Utilisateur searchList(String email) {
        for (Utilisateur user : this.listeUtilisateur) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    
    /**
    * Lookup if the current user is admin or not.
    * @return
    */
    public boolean isAdmin(){
        boolean isAdmin = false;
        if(this.currentUser == null){
            isAdmin = false;
        }else {
            //Lookup in the database if the user is admin or not.
            if(this.userServices.userIsAdmin(this.currentUser.getEmail()) == 1){
                isAdmin = true;
            }
        }
        return(isAdmin);
    }

    /**
    * Check if the current user is admin or not and change the state of EditData button in the MainPage.
    */
    public void verifyAdmin(){
        if (!isAdmin()) {
            this.mainPage.getEditData().setDisable(true);            
            //System.out.println("No admin !");
        } else {
            this.mainPage.getEditData().setDisable(false);
        }
    }


    public void verifyAdminTrouverChemin(){
        if(!isAdmin()){
            this.trouverCheminCommune.getEditData().setDisable(true);
        } else {
            this.trouverCheminCommune.getEditData().setDisable(false);

        }
    }





















    //! ---------------- INSCRIPTIONPAGE METHOD

    /**
    * Handle the action of the Inscription Page. 
    * @param e The Action Event
    */
    private void handleInscriptionPageActions(ActionEvent e){
        // when user click on SignUp button.
        if (this.inscriptionPage != null && e.getSource() == this.inscriptionPage.getBtnSignUp()) {
            String firstName = this.inscriptionPage.getFirstNameField().getText();
            String lastName = this.inscriptionPage.getLastNameField().getText();
            String email = this.inscriptionPage.getEmailField().getText();
            String password = this.inscriptionPage.getPasswordField().getText();
            String confirmPassword = this.inscriptionPage.getConfirmPasswordField().getText();

            this.inscriptionPage.getErrorMessageLabel().setVisible(false);

            // if One of field is Empty we display an ErrorMessage with ErrorMessage Label
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("Tous les champs doivent \u00eatre remplis.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                // if the mail isn't valid we display an Error message with ErrorMessage Label.
            } else if (!isValidEmail(email)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail n'est pas valide.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                // if the passwords fields doesn't matches between we display an ErrorMessage with ErrorMessage Label.
            } else if (!password.equals(confirmPassword)) {
                this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.inscriptionPage.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas.");
                this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                // All of fields are correct we check if the mail exists are not in the database.
            } else {
                // if mail exists we display an ErrorMessage Label.
                if (this.userServices.emailExists(email)) {
                    this.connectionPage.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.inscriptionPage.getErrorMessageLabel().setText("L'adresse e-mail est d\u00e9j\u00e0 utilis\u00e9e.");
                    this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                    // Else we create user and save it into database. And we load all of users of database.
                } else {
                    this.userServices.createUser(lastName, firstName, email, password);
                    this.inscriptionPage.getErrorMessageLabel().setStyle("-fx-text-fill: green;");
                    this.inscriptionPage.getErrorMessageLabel().setText("Compte cr\u00e9e avec succ\u00e8s !");
                    this.inscriptionPage.getErrorMessageLabel().setVisible(true);

                    this.listeUtilisateur = this.userServices.loadAllUsers();
                }
            }
        }

        // if the user click on login link, we redirect him into ConnectionPage.
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
        // if use want to connect we redirect him to ConenctionPage.
        if (e.getSource() == this.forgotPassword.getLinkForgotPassword()) {
            try {
                Stage stage = (Stage) this.forgotPassword.getBtnLogin().getScene().getWindow();
                this.connectionPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // If the user want to get code.
        if(e.getSource() == this.forgotPassword.getBtnLogin()){
            String email;

            // if the button is recevoir code we are sending code to user.
            if(this.forgotPassword.getBtnLogin().getText().equals("Recevoir Code")){
                String mail = this.forgotPassword.getEmailField().getText();

                // if user input for mail is empty we are display an ErrorMessage with error message label.
                if(mail.isEmpty()){
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Tous les champs doivent etre rempli");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);

                    // we check if the user input isn't the database.
                } else if(!this.userServices.emailExists(mail)){
                    // we display an error message.
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Un mail deja renseignee dans la bdd doit etre entrer");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                } else {
                    // Else we can send code to this email.
                    // we get mail, and generate a verification code.

                    email = this.forgotPassword.getEmailField().getText();
                    int code = this.userServices.generateVerificationCode();
                    this.codeString = String.valueOf(code);

                    // we send mail with the method in the userServices.
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

                // if the button is verfied (the mail is already sent)
            } else if(this.forgotPassword.getBtnLogin().getText().equals("V\u00e9rifier")){
                // we check if the user input match with the code.
                if(this.forgotPassword.getCodeField().getText().equals(this.codeString)){
                    try {
                        Stage stage = (Stage) this.forgotPassword.getBtnLogin().getScene().getWindow();
                        this.resetPassword.start(stage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // the code isn't correct so we display an error message.
                    this.forgotPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.forgotPassword.getErrorMessageLabel().setText("Le code specifie est incorrect ! ");
                    this.forgotPassword.getErrorMessageLabel().setVisible(true);
                }
            }
        }
    }

    /**
    * Private method, which change the state of the btnLogin present in forgotPassword page.
    * First State : Get Code
    * Second State : Check
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
        // when user confirm with the validateButton
        if (e.getSource() == this.resetPassword.getBtnValidate()) {
            // we get password and confirm password PasswordField
            String newPassword = this.resetPassword.getFirstPassword().getText();
            String confirmPassword = this.resetPassword.getSecondPassword().getText();

            // we check if the password is the same as confirm password
            if (newPassword.equals(confirmPassword)) {

                // then we check if the password field isn't empty
                if (!newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                    // we get the mail of the user
                    String email = this.forgotPassword.getEmailField().getText();
                    try {
                        // we update the user's password
                        this.userServices.updatePassword(email, newPassword);

                        // we diplay confirmation message.
                        this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: green; -fx-font-size: 15px;");
                        this.resetPassword.getErrorMessageLabel().setVisible(true);

                        this.resetPassword.getErrorMessageLabel().setText("Redirection...");
                        
                        // we display a validation pop-up
                        CustomAlert.showAlert("Reinitialisation du mot de passe", "Votre mot de passe est change.");

                        // Automatic redirection within 2 seconds.
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
                        // if the server don't respond, we assume the server is down and display error.
                        this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                        this.resetPassword.getErrorMessageLabel().setText("Connexion au service momentanément impossible.");
                        this.resetPassword.getErrorMessageLabel().setVisible(true);
                    }
                } else {
                    // we display error message if password field is null
                    this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                    this.resetPassword.getErrorMessageLabel().setText("Les champs de mot de passe doivent être remplis !");
                    this.resetPassword.getErrorMessageLabel().setVisible(true);
                }
            } else {
                // we display an other message if the password and confirm password isn't the same
                this.resetPassword.getErrorMessageLabel().setStyle("-fx-text-fill: red;");
                this.resetPassword.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas !");
                this.resetPassword.getErrorMessageLabel().setVisible(true);
            }
        }

        // link to redirect in forgotPassword
        if(e.getSource() == this.resetPassword.getLinkWrongMail()){
            Stage stage = (Stage) this.resetPassword.getBtnValidate().getScene().getWindow();
            this.forgotPassword.start(stage);
        }

        // link to redirect in ConnectionPage.
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
    public ArrayList<Commune> getCommunesFromDataBase() {
    this.communesRecente = new ArrayList<>();
    try {
        // On récupère toutes les communes avec getAllCommunes du service commune (appelle la base de données)
        this.communeToute = this.communeService.getAllCommunes();
        
        // Utilisation d'une map pour stocker la commune la plus récente par identifiant
        HashMap<Integer, Commune> recentCommunesMap = new HashMap<>();
        
        for (Commune commune : communeToute) {
            int communeId = commune.getIdCommune();
            int currentYear = commune.getAnnee().getAnnee();
            
            // Si la commune est déjà dans la map, on compare les années
            if (recentCommunesMap.containsKey(communeId)) {
                int mostRecentYear = recentCommunesMap.get(communeId).getAnnee().getAnnee();
                if (currentYear > mostRecentYear) {
                    recentCommunesMap.put(communeId, commune);
                }
            } else {
                recentCommunesMap.put(communeId, commune);
            }
        }
        
        this.communesRecente.addAll(recentCommunesMap.values());
        
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
        // we get all communes.
        List<Commune> allCommunes = this.communeToute;
        ArrayList<Integer> returnValue = new ArrayList<Integer>();

        // we get all possible year for a commune and add into an ArrayList
        for (Commune currentCommune : allCommunes) {
            if (currentCommune.getNomCommune().equals(commune.getNomCommune())) {
                returnValue.add(currentCommune.getAnnee().getAnnee());

            }
        }

        // return the arrayList containing the poosible year for a commune.
        return(returnValue);
    }


    /**
    * Allow to get all of available year in the database
    *
    * @return ArrayList of Integer which contains all of years.
    * 
    */
    public List<Annee> getYearsFromDatabase() {
        List<Annee> allYears = new ArrayList<>();
        
        try {
            allYears = this.anneeService.getAllAnnee();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return allYears;
    }


    public List<Departement> getDepartementFromDatabase(){
        List<Departement> allDepartement = new ArrayList<>();

        try{
            allDepartement = this.departementService.getAllDepartement();
        } catch(Exception e){

        }

        return(allDepartement);
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

        // we check if a commune with a specific commune and specific year is exists and return it.
        for (Commune currentCommune : allCommunes) {
            if(currentCommune.getNomCommune().equals(communeName) && currentCommune.getAnnee().getAnnee() == year){
                returnCommune = currentCommune;
                break;
            }
        }

        return(returnCommune);
    }


    /**
    * Allow to get all gare from GareService and put them into listeGare.
    */
    public void getAllGareFromDatabase(){
        try {
            this.listeGare = this.gareService.getAllGares();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }










    //! EXPORT DES DONNEES


    public void exportData() {
        this.listeGare = this.communeService.getListeGare();
        this.listeAnnee = this.communeService.getListeAnnee();
        try {
            this.listeAeroport = new AeroportService().getAllAeroport();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            this.listeDepartement = new DepartementService().getAllDepartement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        communeData();
        gareData();
        anneeData();
        aeroportData();
        departementData();

        createZip();
    }

    public void departementData() {
        String csvFile = "departementData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Écrire l'en-tête du CSV
            writer.println("nomDep;idDep;invesCulture;aeroports");

            // Écrire chaque Departement dans le fichier CSV
            for (Departement departement : this.listeDepartement) {
                writer.println(departmentToCSVRow(departement));
            }

            System.out.println("Données exportées avec succès dans " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String departmentToCSVRow(Departement department) {
        StringBuilder sb = new StringBuilder();

        // Append department fields
        sb.append(department.getNomDep()).append(";");
        sb.append(department.getIdDep()).append(";");
        sb.append(department.getInvesCulture2019()).append(";");

        // Append aeroport names
        for (Aeroport aeroport : this.listeAeroport) {
            if (aeroport.getDepartement().getIdDep() == department.getIdDep()) {
                sb.append(aeroport.getNom()).append(",");
            }
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public void aeroportData() {
        String csvFile = "aeroportData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Écrire l'en-tête du CSV
            writer.println("nom;adresse;departement");

            // Écrire chaque Aeroport dans le fichier CSV
            for (Aeroport aeroport : this.listeAeroport) {
                writer.println(aeroportToCSVRow(aeroport));
            }

            System.out.println("Données exportées avec succès dans " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String aeroportToCSVRow(Aeroport aeroport) {
        return aeroport.getNom() + ";" +
                aeroport.getAdresse() + ";" +
                aeroport.getDepartement().getIdDep();
    }

    public void anneeData() {
        String csvFile = "anneeData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Écrire l'en-tête du CSV
            writer.println("annee;tauxInflation");

            // Écrire chaque Annee dans le fichier CSV
            for (Annee annee : this.listeAnnee) {
                writer.println(anneeToCSVRow(annee));
            }

            System.out.println("Données exportées avec succès dans " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String anneeToCSVRow(Annee annee) {
        return annee.getAnnee() + ";" +
                annee.getTauxInflation();
    }

    public void gareData() {
        String csvFile = "gareData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Écrire l'en-tête du CSV
            writer.println("CodeGare;NomGare;Fret;Voyageur;idCommune");

            // Écrire chaque Gare dans le fichier CSV
            for (Gare gare : this.listeGare) {
                writer.println(gareToCSVRow(gare));
            }

            System.out.println("Données exportées avec succès dans " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String gareToCSVRow(Gare gare) {
        return gare.getCodeGare() + ";" +
                gare.getNomGare() + ";" +
                gare.isEstFret() + ";" +
                gare.isEstVoyageur() + ";" +
                gare.getCommune();
    }

    public void communeData() {
        String csvFile = "communeData.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            // Écrire l'en-tête du CSV
            writer.println("idCommune;nomCommune;Annee;nbMaison;nbAppart;prixMoyen;prixM2Moyen;surfaceMoy;depCulturellesTotales;budgetTotal;population;departement;gare");

            // Écrire chaque Commune dans le fichier CSV
            for (Commune commune : this.communeToute) {
                writer.println(communeToCSVRow(commune));
            }

            System.out.println("Données exportées avec succès dans " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String communeToCSVRow(Commune commune) {
        return commune.getIdCommune() + ";" +
                commune.getNomCommune() + ";" +
                commune.getAnnee().getAnnee() + ";" +
                commune.getNbMaison() + ";" +
                commune.getNbAppart() + ";" +
                commune.getPrixMoyen() + ";" +
                commune.getPrixM2Moyen() + ";" +
                commune.getSurfaceMoy() + ";" +
                commune.getDepCulturellesTotales() + ";" +
                commune.getBudgetTotal() + ";" +
                commune.getPopulation() + ";" +
                commune.getDepartement().getIdDep() + ";" +
                commune.getGare();
    }

    private void createZip() {
        String zipFile = "dataExport.zip";
        String[] csvFiles = {"departementData.csv", "aeroportData.csv", "anneeData.csv", "gareData.csv", "communeData.csv"};

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String csvFile : csvFiles) {
                addToZipFile(csvFile, zos);
                Files.delete(Paths.get(csvFile));  // Delete the CSV file after adding it to the zip
            }

            System.out.println("Fichiers compressés avec succès dans " + zipFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToZipFile(String fileName, ZipOutputStream zos) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
        }
    }
}