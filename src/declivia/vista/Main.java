/**
 ***********************************************************************
 * All rights reserved © Declivia
 * Alejandro Abad Peláez
 * Javier Darío Castrillo Rodríguez
 * Naím González Sánchez
 * 
 *
 *
 * Declivia v1.0.0
 ***********************************************************************
 */

package declivia.vista;

import declivia.controlador.ControladorLogin;
import declivia.modelo.conexion.back;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/*******************************************************************************************************************
* Función main de la aplicación. 
* 1. Crea un nuevo FXMLLoader indicando el nombre del documento .fxml (para la ventana gráfica de la aplicación.
* 
* Variable doc -> contendrá el nombre del controloador de dichar interfaz (FXMLDocumentController.java en este caso
* 
* Una vez tenemos el controlador, podemos pasarle a este argumentos. Le pasamos su propia stage para que el propio
* controlador pueda manipular con mayor libertad la pestaña (cerrarla, etc).
*
********************************************************************************************************************/
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        back backObj = new back();
        
        backObj.createDatabase();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = (Parent) loader.load();
        System.out.println("Hasta aquí");
        ControladorLogin doc = loader.getController();
        
        stage.setScene(new Scene(root));
        
        doc.initStage(stage);
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
