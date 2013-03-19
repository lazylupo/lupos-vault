package messages;

import java.util.*;

/**
 * This class Messages_en_GB is used to retrieve labels and messages for the Web-Editor in English
 * @author Wolfgang Ostermeier
 *
 */
public class Messages_en_GB extends ListResourceBundle {
    static final Object[][] contents = {
            
	    	{"Save","Save"},
	    	{"Save Changes","Save Changes"},
	    	{"Mandatory","All *-fields are mandatory."},
	        {"Name", "Name"},
	        {"Firstname", "Firstname"},
	        {"Email", "Email"},
	        {"Password", "Password"},
	        {"Phone", "Phone"},
	        {"Organisation", "Organisation"},
	        {"Institution", "Institution"},
	        {"Country", "Country"},
	        {"Login", "Login"},
	        {"NoEnabledProjects", "There are no enabled Projects for your account, yet."},
	        {"ContactAdministrator", "Contact Administrator"},
	        {"Logout", "Logout"},
	        {"Loginform", "User-Login"},
	        {"AccountSettings", "Account-Settings"},
	        {"To Overview", "Go to your Experiments-Overview"},
	        {"User", "User"},
	        {"Login failed", "Login failed! Check your Login-Data"},
	        {"Registration", "Registration"},
	        {"Registration failed", "Registration failed! Possible reason: Email+Password are already used."},
	        {"AvailProjects", "Available Projects"},
	        {"AvailExperiments", "Available Experiments"},
	        {"AvailExperimentScripts", "Available Experiment-Scripts"},
	        {"ChangesSaved", "Changes have been successfully saved."},
	        {"ChangesNotSaved", "Saving of changes failed."},
	        {"Submit", "Submit"},
	        {"Author", "Author"},
	        {"ExpProjects", "Experiment-Projects"},
	        {"Experiments", "Experiments"},
	        {"Experiment", "Experiment"},
	        {"ExpScripts", "Experiment-Scripts"},
	        {"ExpScript", "Experiment-Script"},
	        {"ExpScriptSections", "Experiment-Script-Sections"},
	        {"ExpScriptSection", "Experiment-Script-Section"},
	        {"ExpItems", "Experiment-Items"},
	        {"ExpItem", "Experiment-Item"},
	        {"DetailsView", "Details-View"},
	        {"Description", "Description"},
	        {"Ordering", "Ordering"},
	        {"Sequential", "sequential"},
	        {"Random", "random"},
	        {"Maxreplay", "Maximum Replays"},
	        {"Edit", "Edit"},
	        {"Status", "Status"},
	        {"active", "active"},
	        {"Position", "Position"},
	        {"URL", "URLs"},
	        {"Label", "Label"},
	        {"Options", "Options"},
	        {"moreURLs", "Add URL-Fields"},
	        {"moreOptions", "Add Option-Fields"},
	        {"AssessOptions", "Assessment-Options"},
	        {"moreAssessOptions", "Add Assessment-Option-Fields"},
	        {"expected", "Expected Anwer"},
	        {"Rename", "Rename"},
	        {"Copy", "Copy"},
            {"Paste", "Paste"},
	        {"Edit", "Edit"},
	        {"Add", "Add"},
	        {"Delete", "Delete"},
	        {"CheckAllUrls", "Check all URLs"},
	        {"Project", "Project"},
	        {"TestAudio", "Test Audio-File"},
	        {"New", "New..."},
	        {"ConfirmDeleteSelection", "Do you really want to delete your selection?"},
	        {"New Experiment", "New Experiment"},
	        {"New Experiment-Script", "New Experiment-Script"},
	        {"New Experiment-Script-Section", "New Experiment-Script-Section"},
	        {"New Experiment-Item", "New Experiment-Item"},
	        {"Advanced", "New... Advanced"},
	        {"itemsFromDir", "Create Experiment-Items using a URL-Directory"},
	        {"itemsFromCSV", "Create Experiment-Items using a CSV-File"},
	        {"multipleItems", "Create Experiment-Items without Audio-URL"},
	        {"itemsFromDirTab", "URL-Directory"},
	        {"itemsFromCSVTab", "CSV-File"},
	        {"multipleItemsTab", "Without URL"},
	        {"URLDirectory", "URL-Directory"},
	        {"URLDirfailed", "Loading of URL-Directory failed"},
	        {"NoURLDirSelected", "No URL-Directory is selected!"},
	        {"NoCSVFileSelected", "No CSV-File is selected!"},
	        {"NoAudioFileSelected", "No Audio-File is selected!"},
	        {"SelectTemplate", "Select Template"},
	        {"NoTemplateSelected", "No Template is selected!"},
	        {"CreateTemplate", "Please create at least one Experiment-Item in the associated Section."},
	        {"audioPerItem", "Audio-Files per Experiment-Item"},
	        {"SelectCSV", "Select CSV-File"},
	        {"NotRightFormat", "The File is not formatted correctly."},
	        {"SelectAll", "Select All"},
	        {"Number", "Number"},
	        {"NoNumber", "The value of 'Number' needs to be a number."},
	        {"NoUrlNumber", "The value of 'Audio-Files per Experiment-Item' needs to be a number."},
	        {"ExpItemsCreated", "Experiment-Items have been successfully created."},
	        {"ExpItemsAdvanced", "Creating new Experiment-Items - Advanced"},
	        {"Cant Change ExpProject","You are not allowed to create or edit Experiment-Projects."},
	        {"Create new Experiment-Item", "Create new Experiment-Item"},
	        {"Create new Experiment-Script-Section", "Create new Experiment-Script-Section"},
	        {"Create new Experiment-Script", "Create new Experiment-Script"},
	        {"Create new Experiment", "Create new Experiment"},
	        {"ClickForDetail", "Click on an item in the Treeview on the right side to get a detailed view of it here."},
	        {"CopyNotAllowed", "Copying cannot be applied. All copied items must be of the same type."},
	        {"DeleteNotAllowed", "Deleting cannot be applied. All to-be-deleted items must be of the same type."},
	        {"PasteNotAllowed", "Pasting cannot be applied. Copied items can only be pasted to directions that contain items of the same type."},
	        {"Loading", "Still loading...please wait."},
	        
        };

    public Object[][] getContents() {
        return contents;
    }
}