package messages;

import java.util.*;

/**
 * This class Messages_de_DE is used to retrieve labels and messages for the Web-Editor in German
 * @author Wolfgang Ostermeier
 *
 */
public class Messages_de_DE extends ListResourceBundle {
    static final Object[][] contents = {
            
    		{"Save","Speichern"},
    		{"Save Changes","�nderungen Speichern"},
    		{"Mandatory","Alle *-Felder m�ssen ausgef�llt werden."},
            {"Name", "Name"},
            {"Firstname", "Vorname"},
            {"Email", "Email"},
            {"Password", "Passwort"},
            {"Phone", "Telefon"},
            {"Organisation", "Organisation"},
            {"Institution", "Institution"},
            {"Country", "Land"},
            {"Login", "Anmelden"},
            {"NoEnabledProjects", "F�r Ihr Benutzerkonto sind noch keine Projekte freigeschaltet."},
	        {"ContactAdministrator", "Administrator kontaktieren"},
            {"Logout", "Abmelden"},
            {"Loginform", "Benutzer-Anmeldung"},
            {"AccountSettings", "Benutzerkonto-Einstellungen"},
            {"To Overview", "Zur Experimente-�bersicht"},
            {"User", "Benutzer"},
            {"Login failed", "Anmeldung fehlgeschlagen! Bitte �berpr�fen Sie Passwort und Email-Adresse."},
            {"Registration", "Registrierung"},
            {"Registration failed", "Registrierung fehlgeschlagen! M�glicher Grund: Email+Passwort sind bereits vergeben."},
            {"AvailProjects", "Verf�gbare Projekte"},
            {"AvailExperiments", "Verf�gbare Experimente"},
            {"AvailExperimentScripts", "Verf�gbare Experiment-Skripte"},
            {"ChangesSaved", "�nderungen wurden erfolgreich gespeichert."},
            {"ChangesNotSaved", "Das Speichern der �nderungen ist fehlgeschlagen."},
            {"Submit", "Absenden"},
            {"Author", "Autor"},
            {"ExpProjects", "Experiment-Projekte"},
            {"Experiments", "Experimente"},
            {"Experiment", "Experiment"},
            {"ExpScripts", "Experiment-Skripte"},
            {"ExpScript", "Experiment-Skript"},
            {"ExpScriptSections", "Experiment-Skript-Sektionen"},
            {"ExpScriptSection", "Experiment-Skript-Sektion"},
            {"ExpItems", "Experiment-Elemente"},
            {"ExpItem", "Experiment-Element"},
            {"DetailsView", "Detail-Ansicht"},
            {"Description", "Beschreibung"},
            {"Ordering", "Anordnung"},
            {"Sequential", "sequentiell"},
            {"Maxreplay", "Maximale Wiederholungen"},
            {"Edit", "Bearbeiten"},
	        {"Random", "randomisiert"},
            {"Status", "Status"},
            {"active", "aktiv"},
            {"Position", "Position"},
            {"URL", "URLs"},
            {"Label", "Beschriftung"},
            {"Options", "Antwortm�glichkeiten"},
            {"moreURLs", "URL-Felder hinzuf�gen"},
            {"moreOptions", "Antwortm�glichkeits-Felder hinzuf�gen"},
            {"AssessOptions", "Beurteilungsm�glichkeiten"},
            {"moreAssessOptions", "Beurteilungsm�glichkeits-Felder hinzuf�gen"},
            {"expected", "Erwartete Antwort"},
            {"Rename", "Umbenennen"},
            {"Copy", "Kopieren"},
            {"Paste", "Einf�gen"},
	        {"Edit", "Bearbeiten"},
            {"Add", "Hinzuf�gen"},
            {"Delete", "L�schen"},
            {"CheckAllUrls", "Teste alle URLs"},
            {"Project", "Projekt"},
            {"TestAudio", "Teste Audiodatei"},
            {"New", "Neu..."},
            {"ConfirmDeleteSelection", "Auswahl wirklich l�schen?"},
            {"New Experiment", "Neues Experiment"},
	        {"New Experiment-Script", "Neues Experiment-Skript"},
	        {"New Experiment-Script-Section", "Neue Experiment-Skript-Sektion"},
	        {"New Experiment-Item", "Neues Experiment-Element"},
	        {"Advanced", "Neu... Erweitert"},
	        {"itemsFromDir", "Experiment-Elemente mithilfe eines URL-Verzeichnisses erzeugen"},
	        {"itemsFromCSV", "Experiment-Elemente mithilfe einer CSV-Datei erzeugen"},
	        {"multipleItems", "Experiment-Elemente ohne Audio-URL erzeugen"},
	        {"itemsFromDirTab", "URL-Verzeichnis"},
	        {"itemsFromCSVTab", "CSV-Datei"},
	        {"multipleItemsTab", "Ohne URL"},
	        {"URLDirectory", "URL-Verzeichnis"},
	        {"URLDirfailed", "Laden des URL-Verzeichnisses fehlgeschlagen."},
	        {"NoURLDirSelected", "Kein URL-Verzeichnis ist ausgew�hlt!"},
	        {"NoCSVFileSelected", "Keine CSV-Datei ist ausgew�hlt!"},
	        {"NoAudioFileSelected", "Keine Audio-Datei ist ausgew�hlt!"},
	        {"SelectTemplate", "Vorlage ausw�hlen"},
	        {"NoTemplateSelected", "Keine Vorlage ist ausgew�hlt!"},
	        {"CreateTemplate", "Erzeugen Sie mindestens ein Experiment-Element in der zugeh�rigen Sektion."},
	        {"audioPerItem", "Audio Dateien pro Experiment-Element"},
	        {"SelectCSV", "CSV-Datei ausw�hlen"},
	        {"NotRightFormat", "Die Datei ist nicht richtig formatiert."},
	        {"SelectAll", "Alle ausw�hlen"},
	        {"Number", "Anzahl"},
	        {"NoNumber", "Der Wert von 'Anzahl' muss eine Zahl sein."},
	        {"NoUrlNumber", "Der Wert von 'Audio Dateien pro Experiment-Element' muss eine Zahl sein."},
	        {"ExpItemsCreated", "Experiment-Elemente wurden erfolgreich erzeugt."},
	        {"ExpItemsAdvanced", "Experiment-Elemente erzeugen - Erweitert"},
	        {"Cant Change ExpProject","Sie haben keine Berechtigung, Experiment-Projekte zu erzeugen oder zu bearbeiten."},
	        {"Create new Experiment-Item", "Erzeuge neues Experiment-Element"},
	        {"Create new Experiment-Script-Section", "Erzeuge neue Experiment-Skript-Sektion"},
	        {"Create new Experiment-Script", "Erzeuge neues Experiment-Skript"},
	        {"Create new Experiment", "Erzeuge neues Experiment"},
	        {"ClickForDetail", "Klicken Sie auf ein Element in der Baumanzeige auf der rechten Seite, um hier eine detaillierte Ansicht davon zu bekommen."},
	        {"CopyNotAllowed", "Kopiervorgang nicht m�glich. Alle kopierten Elemente m�ssen vom selben Typ sein."},
	        {"DeleteNotAllowed", "L�schvorgang nicht m�glich. Alle zu l�schenden Elemente m�ssen vom selben Typ sein."},
	        {"PasteNotAllowed", "Einf�gevorgang nicht m�glich. Kopierte Elemente k�nnen nur in Verzeichnisse kopiert werden, die Elemente vom selben Typ enthalten."},
	        {"Loading", "Seite l�dt... bitte warten."},
            
        };

    public Object[][] getContents() {
        return contents;
    }
}
