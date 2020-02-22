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

package declivia.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import declivia.modelo.vo.CourseVo;
import declivia.modelo.vo.FileDeclivia;
import declivia.modelo.vo.StandardUserVo;
import declivia.modelo.vo.SubjectVo;
import declivia.modelo.vo.SuperUserVo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class InterfaceController implements Initializable {
    
    private StandardUserVo user;
    private SuperUserVo superUser;
    private static final int COORDXCOURSE = -20;
    private static final int COORDYCOURSE = 20;
    private int subjectCounter;
    private int fileCounter;
    
    @FXML 
    private AnchorPane profilePane;
    
    @FXML
    private CourseVo lastCourse;
    @FXML
    private SubjectVo lastSubject;
    @FXML
    private AnchorPane lastPane;
    
    @FXML
    private SubjectVo everySubject[];
    
    @FXML
    private FileDeclivia everyFile[];
    
    @FXML
    private BarChart numFiles_Control;
    
    @FXML
    private Label everyLabel[];
    
    @FXML
    private Label everyLabel_Control[];
    
    private int everyLabelControlCounter;
    
    private int labelCounter;
    
    @FXML
    private PieChart pieChart;
    @FXML
    private PieChart pieChartSubjects;
    @FXML
    private Label percentDiscUsed;
    @FXML
    private Label freeSpaceDisk;
    
    @FXML
    private Label profileName;
    
    @FXML
    private Label atras;
    
    @FXML
    ImageView profilePicture;
 
    @FXML
    JFXButton inicio;
    @FXML
    JFXButton cursos;
    @FXML
    private JFXButton botonUsuarios;
    @FXML
    JFXButton destacado;
    @FXML
    JFXButton control;
    @FXML
    JFXButton crearElemento_curso;
    @FXML
    JFXButton crearElemento_asignatura;
    @FXML
    JFXButton crearElemento_archivo;
    
    @FXML
    AnchorPane viewColumn;
    @FXML
    AnchorPane settingsRow;
    
    @FXML
    AnchorPane inicioPane;
    @FXML
    AnchorPane cursosPane;
    @FXML
    AnchorPane destacadoPane;
    @FXML
    AnchorPane controlPane;
    
    @FXML
    Text cursosText;
    
    @FXML
    AnchorPane panelDeControl_Cursos;
    @FXML
    AnchorPane panelDeControl_Asignaturas;
    @FXML
    AnchorPane panelDeControl_Archivos;
    @FXML
    private AnchorPane panelDeControl_Usuarios;
    @FXML
    private ScrollPane scrollPane_Usuarios;
    @FXML
    ScrollPane scrollPane_Cursos;
    @FXML
    ScrollPane scrollPane_Asignaturas;
    @FXML
    ScrollPane scrollPane_Archivos;
    
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    
    private Stage actualStage;
    private Stage firstStage;
    
    @FXML
    private AnchorPane crearCurso_Pane;
    @FXML
    private JFXTextField nombreNuevoCurso;
    @FXML
    private JFXTextArea descripcionNuevoCurso;
    
    @FXML
    private AnchorPane crearAsignatura_Pane;
    @FXML
    private JFXTextField nombreAsignaturaNueva;
    @FXML
    private JFXTextField correoProfesorAsignaturaNueva;
    @FXML
    private JFXTextField creditosAsignaturaNueva;
    @FXML
    private JFXTextArea anotacionesAsignaturaNueva;
    
    @FXML
    private AnchorPane crearArchivo_Pane;
    @FXML
    private JFXTextField nombreArchivoNuevo;
    @FXML
    private JFXTextField rutaArchivoNuevo;
    @FXML
    private JFXTextArea descripcionArchivoNuevo;
    
    @FXML
    private AreaChart pruebaArea;
    
    @FXML
    private JFXComboBox cursos_Control;
    @FXML
    private JFXComboBox dificultadComboBox;
    
    @FXML
    private AnchorPane paneAsignaturaRecomendada;
    
    @FXML
    private Label selectedSub;
    
    private int flag;
   
    private int courseCounter;
    private CourseVo everyCourse[];
    
    private int type;
/***********************************************************************************************************************************
* Función Initialize de la clase InterfaceController
* 1. Guardará en un array de tipo File (unidades) los discos duros que encuentre en el ordenador y sacará su espacio total, su 
* espacio libre y su espacio ocupado en GB guardándolo en las variables:
*   -totalSpace
*   -freeSpace
*   -occupied
* 
* 2. Sacará el porcentaje de disco coupado y el total y los guardará en las variables:
*   -percent
*   -total
* 
* 3. Convertirá en enteros los números del porcentaje y del espacio libre, para que sean más visuales guardándolos en:
*   -finalPercent
*   -finalFree
* 
* 4.Estos dos valores los transformará en strings. Variables:
*   -finalPercentString
*   -finalFreeString.
* 
* 5. Inicializará los valores del piechart creado (pieChart) con:
*   -total
*   -percent
* 
* 6. Establecerá el texto de los labels (percentDiscUsed) y (freeSpaceDisk) mediante las strings guardadas en:
*   -finalPercentString
*   -finalFreeString
*
************************************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ImageView iconoAtras = new ImageView(new Image("Imagenes/flechaDef.png"));

        iconoAtras.setScaleX(0.40);
        iconoAtras.setScaleY(0.40);
        atras.setGraphic(iconoAtras);
        
        atras.setLayoutX(10);
        atras.setLayoutY(73);
        atras.setCursor(Cursor.HAND);
        
        atras.setVisible(false);
                
        atras.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {                                        
                irAtras();
            }
        });
        
        File[] unidades = File.listRoots();
                
        float totalSpace = (float) (unidades[0].getTotalSpace() / 1000000000);
        float freeSpace = (float) (unidades[0].getFreeSpace() / 1000000000);
        float occupied = totalSpace - freeSpace;
        
        float percent = (occupied / (totalSpace / 100));

        float total = 100 - percent;
        
        int finalPercent = (int) percent;
        int finalFree = (int) freeSpace;
                
        String finalPercentString = Integer.toString(finalPercent);
        String finalFreeString = Integer.toString(finalFree);
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Total Space", total), new PieChart.Data("Occupied", percent));
        pieChart.setData(pieChartData);
        pieChart.setLabelsVisible(false);
        
        percentDiscUsed.setText(finalPercentString + "%");
        freeSpaceDisk.setText(finalFreeString);    
    }  
    
/***********************************************************************************************************************************
* Función passArguments de InterfaceController
* 1. Inicializa el objeto de tipo usuario con el que se comunicará para establecer el modelo-vista-controlador
* 
* 2. Inicializa las variables:
*   -subjectCounter -> guardará el número de asignaturas del último curso al que se ha accedido. (Cada vez que accedamos a un curso
*   esta variable cambiará). Esto nos sirve para que, cuando cambiemos entre cursos, podamos invisibilizar todos los elementos del 
*   anterior curso del panel. De no hacerlo, los componentes se superpondrían.
*   -fileCounter -> Más de lo mismo.
* 
* 3. Inicializmos los arrays:
*   -everySubject -> Relacionado con lo anterior. Cuando accedemos a un curso se irán pegando en el panel sus diferente asignaturas.
*   Cada asignatura pegada se guardará en el array, para que luego, cuando queramos ponerlas invisibles, simplemente tengamos que
*   recorrer el array el número de veces guardado en subjectCounter. Cada vez que accedemos a un curso, sus asignaturas se guardan
*   en este array, donde antes había otras, teniendo así siempre guardadas las asignaturasa del último curso accedido.
*   -everyFile -> Más de los mimso
*   -everySubjectImage -> Como a cada Label acompañará un icono, también nos interesa poder poner estos invisibles, por lo que  
*   en este array guardaremos los iconos que acompañan a los labels del último curso accedido.
*   -everyFileImage -> Más de lo mismo
* 
* 4. Calcularemos cuánto ocupará el texto del nombre teniendo en cuenta que, con este tamaño de letra, cada letra ocupa 9 píxeles.
* De esta forma podemos calcular dónde empieza el nombre de usuario para que no se salga del panel y que el icono de usuario esté
* bien posicionado. Variables:
*   -length -> longitud original del nombre
*   -namePosition -> La longitud multiplicada por 9 (píxeles) + 10 píxeles adicionales (para que no esté pegada al borde
*   -picturePositon -> 40 píxeles más a la izquierda que el nombre
* 
* 5. Colocamos en el layout cada unos de los componentes
* 
* 6. Por último cargamos SOLO los datos QUE NECESITAREMOS del usuario desde la base de datos mediante el método loadCourses() de 
* nuestro objeto de tipo StandardUser
************************************************************************************************************************************/
    public void passArguments(StandardUserVo user, Stage actualStage, Stage firstStage, int type, SuperUserVo superUser)
    {        
        subjectCounter = 0;
        fileCounter = 0;
        labelCounter = 0;
        courseCounter = 0;
        everyLabelControlCounter = 0;
        
        selectedSub = new Label();
        
        everySubject = new SubjectVo[100];
        
        everyFile = new FileDeclivia[100];
        
        everyLabel = new Label[100];
        
        everyCourse = new CourseVo[100];
        
        this.type = type;
        
        flag = 0;
        
        everyLabel_Control = new Label[100];
        
        this.actualStage = actualStage;
        this.firstStage = firstStage;
        
        if(type == 1)
        {
            this.user = user;
          
            double length = user.getName().length();
            double namePosition = 1060 - (length * 9 + 10);
            double picturePosition = namePosition - 40;

            profilePicture.setLayoutX(picturePosition);

            profileName.setText(user.getName());
            profileName.setGraphic(profilePicture);        
            profileName.setLayoutX(picturePosition);

            user.loadCourses();    
            user.loadStatistics();
            user.insertNewStatistic();
        }
        else
        {
            cursos.setVisible(false);
            destacado.setVisible(false);
            control.setVisible(false);
            botonUsuarios.setVisible(true);
            
            this.superUser = superUser;
            
            double length = superUser.getName().length();
            double namePosition = 1060 - (length * 9 + 10);
            double picturePosition = namePosition - 40;

            profilePicture.setLayoutX(picturePosition);

            profileName.setText(superUser.getName());
            profileName.setGraphic(profilePicture);        
            profileName.setLayoutX(picturePosition);

            superUser.loadUsers();
        }
    }
    
/***********************************************************************************************************************************
* Función toInicio de InterfaceController
* 1. Cuando pulsemos el botón inicio se ponen invisibles todos los panes y se pone visible el pane de inicio (inicioPane)
************************************************************************************************************************************/
    @FXML
    private void toInicio(ActionEvent event)
    {
        hideEverything(inicioPane);
    }

/***********************************************************************************************************************************
* Función toCursos de InterfaceController
* 1. Al pulsar el botón de cursos, como pasa con cualquier otro botón, llamamos al hideEverythin para que sólo sea visible cursos.
* A mayores también llamaremos a hideEverythinInCourses pasándole el panelDeControl_Cursos, para que cualquier otro panel visible
* desparezca y nos quedemos con el que queremos.
* 
* 2. Cambiamos el path que acompaña al panel de cursos para saber en qué carpeta estamos para que sea la raíz (cursos)
* 
* 3. Cogemos el tamaño del array de cursos dentro de nuestro objeto usuario y cargamos la imagen del icono de cursos.
* 
* 4. Vamos inicializando cada label con el icono de cursos y el nombre del curso para cada posición del arrayList de cursos del
* usuario y los agregamos al panel (panelDeControl_Cursos) haciéndolos visibles.
* 
* 5. Por último a cada label le damos un MouseEvent para que puedas pinchar en ellos.
*   -Cuando pinchamos guardamos en una string el nombre del curso en el que hemos pinchado.
*   -Cuando pinchamos el path cambia para indicar que estamos dentro de una carpeta concreta.
*   -Guardamos al posición del arrayList del curso en el que hemos pinchado al llamar almétodo de user que carga la información que
*   necesitamos del curso.
*   -Llamamos al método toSubjects y le pasamos la posición del arrayList del curso en el que hemos pinchado.
*   
************************************************************************************************************************************/
    @FXML
    private void toCursos(/*ActionEvent event*/)
    {
        hideEverything(cursosPane);
        hideEverythingInCourses(panelDeControl_Cursos);
        
        labelCounter = 0;
        
        crearElemento_curso.setVisible(true);
        crearElemento_asignatura.setVisible(false);
        crearElemento_archivo.setVisible(false);
        
        crearCurso_Pane.setVisible(false);
        crearAsignatura_Pane.setVisible(false);
        crearArchivo_Pane.setVisible(false);
        
        scrollPane_Asignaturas.setVisible(false);
        scrollPane_Archivos.setVisible(false);
        scrollPane_Cursos.setVisible(true);
        scrollPane_Usuarios.setVisible(false);
                
        atras.setVisible(false);
            
        cursosText.setText("Cursos");
       
        int size = user.courses.size();
        int i = 0;
        int distanceYValue = 120;
                   
        while(i < size)
        {       
            CourseVo cursoNuevo = user.courses.get(i);
            Label numSubjects = new Label();
            Label numFiles = new Label();
            Label descripcionText = new Label();
            Label papelera = new Label();
            Image papeleraImage = new Image("Imagenes/eliminar2.png");
            
            numSubjects.setText("Número de asignaturas: " + cursoNuevo.getNumSubjects());
            numFiles.setText("Número de archivos totales: 0");
            
            descripcionText.setText(cursoNuevo.getDescription());
            
            ImageView papeleraImageView = new ImageView(papeleraImage);
            papeleraImageView.setScaleX(0.45);
            papeleraImageView.setScaleY(0.45);
            
            papelera.setGraphic(papeleraImageView);
            papelera.setCursor(Cursor.HAND);
            
            if(cursoNuevo.alreadyAdded == false)
            {                   
                panelDeControl_Cursos.getChildren().add(cursoNuevo);
            }
            
            panelDeControl_Cursos.getChildren().add(numSubjects);
            panelDeControl_Cursos.getChildren().add(numFiles);
            panelDeControl_Cursos.getChildren().add(descripcionText);
            panelDeControl_Cursos.getChildren().add(papelera);
            
            cursoNuevo.setVisible(true);
            papelera.setVisible(true);
            
            cursoNuevo.setLayoutX(COORDXCOURSE);
            cursoNuevo.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 30);
            
            numSubjects.setLayoutX(COORDXCOURSE + 85);
            numSubjects.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 40);
            
            numFiles.setLayoutX(COORDXCOURSE + 85);
            numFiles.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 60);
            
            descripcionText.setLayoutX(COORDXCOURSE + 300);
            descripcionText.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 44);
            
            papelera.setLayoutX(COORDXCOURSE + 218);
            papelera.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 4);
            
            everyLabel[labelCounter] = numSubjects;
            labelCounter++;
            everyLabel[labelCounter] = numFiles;
            labelCounter++;
            everyLabel[labelCounter] = descripcionText;
            labelCounter++;
            everyLabel[labelCounter] = papelera;
            labelCounter++;
            
            if(cursoNuevo.alreadyAdded == false)
            {                   
                cursoNuevo.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        user.addClick();
                        
                        System.out.println("Mi id es " + cursoNuevo.getCourseID());
                        
                        String newText = cursosText.getText();

                        System.out.println(newText);

                        cursosText.setText(newText + " > " + cursoNuevo.getName());

                        cursoNuevo.loadSubjects(); //Cargará sus asignaturas de la base de datos (sólo si es la primera vez)

                        toSubjects(cursoNuevo);
                    }
                });
            }
            
            papelera.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    user.addClick();
                    
                    cursoNuevo.setVisible(false);
                    user.deleteCourse(cursoNuevo);
                    toCursos();
                }
            });
             
            cursoNuevo.alreadyAdded = true;
            
            i++;
        }
        
        System.out.println("Hasta aquí");
        
        scrollPane_Cursos.setContent(panelDeControl_Cursos);
    }
    
    
    @FXML
    private void toCursos(StandardUserVo user2)
    {
        courseCounter = 0;
        hideEverything(cursosPane);
        hideEverythingInCourses(panelDeControl_Cursos);
        
        labelCounter = 0;
        
        crearElemento_curso.setVisible(true);
        crearElemento_asignatura.setVisible(false);
        crearElemento_archivo.setVisible(false);
        
        crearCurso_Pane.setVisible(false);
        crearAsignatura_Pane.setVisible(false);
        crearArchivo_Pane.setVisible(false);
        
        scrollPane_Asignaturas.setVisible(false);
        scrollPane_Archivos.setVisible(false);
        scrollPane_Cursos.setVisible(true);
        scrollPane_Usuarios.setVisible(false);
                
        atras.setVisible(false);
       
        int size = user2.courses.size();
        int i = 0;
        int distanceYValue = 120;
                   
        while(i < size)
        {       
            CourseVo cursoNuevo = user2.courses.get(i);
            Label numSubjects = new Label();
            Label numFiles = new Label();
            Label descripcionText = new Label();
            Label papelera = new Label();
            Image papeleraImage = new Image("Imagenes/eliminar2.png");
            
            numSubjects.setText("Número de asignaturas: " + cursoNuevo.getNumSubjects());
            numFiles.setText("Número de archivos totales: 0");
            
            descripcionText.setText(cursoNuevo.getDescription());
            
            ImageView papeleraImageView = new ImageView(papeleraImage);
            papeleraImageView.setScaleX(0.45);
            papeleraImageView.setScaleY(0.45);
            
            papelera.setGraphic(papeleraImageView);
            papelera.setCursor(Cursor.HAND);
            
            if(cursoNuevo.alreadyAdded == false)
            {                   
                panelDeControl_Cursos.getChildren().add(cursoNuevo);
            }
            
            panelDeControl_Cursos.getChildren().add(numSubjects);
            panelDeControl_Cursos.getChildren().add(numFiles);
            panelDeControl_Cursos.getChildren().add(descripcionText);
            panelDeControl_Cursos.getChildren().add(papelera);
            
            cursoNuevo.setVisible(true);
            papelera.setVisible(true);
            
            cursoNuevo.setLayoutX(COORDXCOURSE);
            cursoNuevo.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 30);
            
            numSubjects.setLayoutX(COORDXCOURSE + 85);
            numSubjects.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 40);
            
            numFiles.setLayoutX(COORDXCOURSE + 85);
            numFiles.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 60);
            
            descripcionText.setLayoutX(COORDXCOURSE + 300);
            descripcionText.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 44);
            
            papelera.setLayoutX(COORDXCOURSE + 218);
            papelera.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 4);
            
            everyCourse[i] = cursoNuevo;
            
            everyLabel[labelCounter] = numSubjects;
            labelCounter++;
            everyLabel[labelCounter] = numFiles;
            labelCounter++;
            everyLabel[labelCounter] = descripcionText;
            labelCounter++;
            everyLabel[labelCounter] = papelera;
            labelCounter++;
            
            if(cursoNuevo.alreadyAdded == false)
            {                   
                cursoNuevo.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {                     
                        String newText = cursosText.getText();

                        System.out.println(newText);

                        cursosText.setText(newText + " > " + cursoNuevo.getName());

                        cursoNuevo.loadSubjects(); //Cargará sus asignaturas de la base de datos (sólo si es la primera vez)

                        toSubjects(cursoNuevo);
                    }
                });
            }
            
            papelera.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    
                    cursoNuevo.setVisible(false);
                    user.deleteCourse(cursoNuevo);
                    toCursos();
                }
            });
             
            cursoNuevo.alreadyAdded = true;
            
            i++;
            courseCounter++;
        }
        
        System.out.println("Hasta aquí");
        
        scrollPane_Cursos.setContent(panelDeControl_Cursos);
    }
    
    @FXML
    private void toUsers()
    {
        cursosText.setText("Usuarios");
        hideEverything(cursosPane);
        hideEverythingInCourses(panelDeControl_Usuarios);
                
        labelCounter = 0;
        
        scrollPane_Asignaturas.setVisible(false);
        scrollPane_Archivos.setVisible(false);
        scrollPane_Cursos.setVisible(false);
        scrollPane_Usuarios.setVisible(true);
        
        int size = superUser.users.size();
        int i = 0;
        int distanceYValue = 120;
        
        while(i < size)
        {
            Image papeleraImage = new Image("Imagenes/eliminar2.png");
            Label papelera = new Label();
            StandardUserVo newUser = superUser.users.get(i);
            
            ImageView papeleraImageView = new ImageView(papeleraImage);
            papeleraImageView.setScaleX(0.45);
            papeleraImageView.setScaleY(0.45);
            
            papelera.setGraphic(papeleraImageView);
            papelera.setCursor(Cursor.HAND);
            
            if(newUser.alreadyAdded == false)
            {                   
                panelDeControl_Usuarios.getChildren().add(newUser);
            }
            panelDeControl_Usuarios.getChildren().add(papelera);
            
            newUser.setVisible(true);
            
            newUser.setLayoutX(COORDXCOURSE);
            newUser.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 30);
          
            papelera.setVisible(true);
            
            papelera.setLayoutX(COORDXCOURSE + 218);
            papelera.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 4);
            
            everyLabel[labelCounter] = newUser;
            labelCounter++;
            everyLabel[labelCounter] = papelera;
            labelCounter++;
            
            if(newUser.alreadyAdded == false)
            {                   
                newUser.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {                                                
                        String newText = cursosText.getText();

                        System.out.println(newText);

                        cursosText.setText(newText + " > " + newUser.getName());

                        newUser.loadCourses(); //Cargará sus asignaturas de la base de datos (sólo si es la primera vez)

                        toCursos(newUser);
                    }
                });
            }
            papelera.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    
                    newUser.setVisible(false);
                    superUser.deleteUser(newUser);
                    toUsers();
                }
            });
            
            i++;
            newUser.alreadyAdded = true;
        }
        
        
        
    }
    
/***********************************************************************************************************************************
* Función toDestacado de InterfaceController
* 1. Cuando pulsemos el botón destacados se ponen invisibles todos los panes y se pone visible el pane de destacado (destacadoPane)
************************************************************************************************************************************/
    @FXML
    private void toDestacado(ActionEvent event)
    {
        hideEverything(destacadoPane);
    }

/***********************************************************************************************************************************
* Función toControl de InterfaceController
* 1. Cuando pulsemos el botón control se ponen invisibles todos los panes y se pone visible el pane de control (controlPane)
************************************************************************************************************************************/    
    @FXML
    private void toControl(ActionEvent event)
    {
        hideEverything(controlPane);
                
        ObservableList<String> list = FXCollections.observableArrayList();
        
        for(int i = 0; i < user.courses.size(); i++)
        {
            list.add(user.courses.get(i).getName());
        }
        
        cursos_Control.setItems(list);
        
        XYChart.Series S = new XYChart.Series<>();
        
        for(int i = 0; i < user.statistics.size(); i++)
        {
            String num = Integer.toString(i);
            
            S.getData().add(new XYChart.Data<>(num, user.statistics.get(i)));
        }
        
        pruebaArea.getData().add(S);     
    }
    
    @FXML
    private void changeControlCourse()
    {                
        System.out.println("Hola");
        String cours = (String) cursos_Control.getSelectionModel().getSelectedItem().toString();
        CourseVo selectedCourse = null;
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        
        for(int i = 0; i < user.courses.size(); i++)
        {
            if(user.courses.get(i).getName().equals(cours))
            {
                selectedCourse = user.courses.get(i);
                selectedCourse.loadSubjects();
                break;
            }
        }
        
        int puntuaciones[] = selectedCourse.calculateAllPoints();
        SubjectVo selectedSubject = selectedCourse.calculateMaximumPoints();
                  
        for(int i = 0; i < puntuaciones.length; i++)
        {
            System.out.println(puntuaciones[i]);
            pieChartData.add(new PieChart.Data(selectedCourse.subjects.get(i).getName(), puntuaciones[i]));
        }

        pieChartSubjects.setData(pieChartData);
        pieChartSubjects.setVisible(true);
        
        selectedSub.setGraphicTextGap(-30);
        
        selectedSub.setScaleX(1.8);
        selectedSub.setScaleY(1.8);
        
        selectedSub.setLayoutX(2);
        selectedSub.setLayoutY(10);
        
        selectedSub.setText(selectedSubject.getText());
        //selectedSub.setGraphic(selectedSubject.getGraphic());
        
        if(flag == 0)
        {
            paneAsignaturaRecomendada.getChildren().add(selectedSub);
            flag = 1;
        }
        //Actualizamos el diagrama de barras
        
        XYChart.Series set1 = new XYChart.Series<>();
        
        for(int i = 0; i < selectedCourse.subjects.size(); i++)
        {
            set1.getData().add(new XYChart.Data(selectedCourse.subjects.get(i).getName(), selectedCourse.subjects.get(i).getFileCounter()));   
  
        }
        
        numFiles_Control.getData().addAll(set1);
    }

/***********************************************************************************************************************************
* Función hideEverything de InterfaceController
* Servirá para poner invisibles todos los paneles y luego poner visible el panel indicado por parámetro
************************************************************************************************************************************/ 
    @FXML
    private void hideEverything(AnchorPane pane)
    {
        cursosPane.setVisible(false);
        inicioPane.setVisible(false);
        destacadoPane.setVisible(false);
        controlPane.setVisible(false);
        
        //Después de haber puesto todos los panes invisibles, ponemos el que nos interesa visbible
        pane.setVisible(true);
        lastPane =  pane;
    }
    
/***********************************************************************************************************************************
* Función hideEverythingInCourses de InterfaceController
* 1. El panel de cursos tiene a su vez paneles más pequeños. Uno para los propios cursos (panelDeControl_Cursos), para las asignaturas
* (panelDeControl_Asignaturas) y para los archivos (panelDeControl_Archivos). Igual que hideEverything, esta función pondrá 
* invisibles los paneles dentro del panel de cursos y pondrá visible el indicado.
* 
* 2. A mayores pondrá invisibles todas las asignaturas e iconos que las acompañan del último curso accedido, así como los archivos e 
* iconos que los acompañan de la última asignatura accedida
************************************************************************************************************************************/
    @FXML
    private void hideEverythingInCourses(AnchorPane pane)
    {
        panelDeControl_Cursos.setVisible(false);
        panelDeControl_Asignaturas.setVisible(false);
        panelDeControl_Archivos.setVisible(false);
        panelDeControl_Usuarios.setVisible(false);
        
        //Después de haber puesto todos lo panes invisibles, ponemos el que nos interesa visible.
        pane.setVisible(true);
        
        for(int i = 0; i < courseCounter; i++)
        {
            everyCourse[i].setVisible(false);
        }
        
        for(int i = 0; i < subjectCounter; i++)
        {
            everySubject[i].setVisible(false);
        }
                
        for(int i = 0; i < fileCounter; i++)
        {
            everyFile[i].setVisible(false);
        }    
        
        for(int i = 0; i < labelCounter; i++)
        {
            everyLabel[i].setVisible(false);
        } 
    }
    
    @FXML
    private void hideEverythingInControl()
    {
        for(int i = 0; i < everyLabelControlCounter; i++)
        {
            everyLabel_Control[i].setVisible(false);
        }
    }
    
    @FXML 
    private void toSubjects(CourseVo course)
    {
        hideEverythingInCourses(panelDeControl_Asignaturas);
        subjectCounter = 0;
        labelCounter = 0;
        lastCourse = course;
        
        crearElemento_curso.setVisible(false);
        crearElemento_asignatura.setVisible(true);
        crearElemento_archivo.setVisible(false);
        
        scrollPane_Asignaturas.setVisible(true);
        scrollPane_Archivos.setVisible(false);
        scrollPane_Cursos.setVisible(false);
        
        crearCurso_Pane.setVisible(false);
        crearAsignatura_Pane.setVisible(false);
        crearArchivo_Pane.setVisible(false);
        
        atras.setVisible(true);
        
        int size = course.subjects.size();
        int i = 0;
        int distanceYValue = 120;
                
        while(i < size)
        {
            SubjectVo asignaturaNueva = course.subjects.get(i);
            Label numCreditos = new Label();
            Label correoProfe = new Label();
            Label notas = new Label();
            Label fechaText = new Label();
            Label papelera = new Label();
            Image papeleraImage = new Image("Imagenes/eliminar2.png");
            
            numCreditos.setText("Créditos de la asignatura: " + asignaturaNueva.getNumCredits());
            correoProfe.setText("Correo del profesor: " + asignaturaNueva.getCorreoProfesor());
            notas.setText("Notas");
            
            ImageView papeleraImageView = new ImageView(papeleraImage);
            papeleraImageView.setScaleX(0.45);
            papeleraImageView.setScaleY(0.45);
            
            papelera.setGraphic(papeleraImageView);
            papelera.setCursor(Cursor.HAND);
            
            if(asignaturaNueva.alreadyAdded == false)
            {
                panelDeControl_Asignaturas.getChildren().add(asignaturaNueva);
            }
            
            panelDeControl_Asignaturas.getChildren().add(numCreditos);
            panelDeControl_Asignaturas.getChildren().add(correoProfe);
            panelDeControl_Asignaturas.getChildren().add(notas);
            panelDeControl_Asignaturas.getChildren().add(papelera);

            asignaturaNueva.setVisible(true);
            numCreditos.setVisible(true);
            correoProfe.setVisible(true);
            notas.setVisible(true);
            papelera.setVisible(true);
            
            asignaturaNueva.setLayoutX(COORDXCOURSE);
            asignaturaNueva.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 30);
            
            papelera.setLayoutX(COORDXCOURSE + 200);
            papelera.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 5);
            
            numCreditos.setLayoutX(COORDXCOURSE + 85);
            numCreditos.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 40);
            
            correoProfe.setLayoutX(COORDXCOURSE + 85);
            correoProfe.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 60);
            
            notas.setLayoutX(COORDXCOURSE + 500);
            notas.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 44);
            
            fechaText.setLayoutX(COORDXCOURSE + 800);
            
            everySubject[i] = asignaturaNueva;
            
            everyLabel[labelCounter] = numCreditos;
            labelCounter++;
            everyLabel[labelCounter] = correoProfe;
            labelCounter++;
            everyLabel[labelCounter] = notas;
            labelCounter++;
            everyLabel[labelCounter] = papelera;
            labelCounter++;
            
            if(asignaturaNueva.alreadyAdded == false)
            {
                asignaturaNueva.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {    
                        if(type == 1)
                        {
                            user.addClick();
                        }
                        asignaturaNueva.updateSubjectAccesses();
                        String newText = cursosText.getText();
                        cursosText.setText(newText + " > " + asignaturaNueva.getName());

                        asignaturaNueva.loadFiles();

                        toFiles(asignaturaNueva);
                    }
                });
            }
            
            papelera.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    if(type == 1)
                    {
                        user.addClick();
                    }
                    asignaturaNueva.setVisible(false);
                    lastCourse.deleteSubject(asignaturaNueva);
                    toSubjects(lastCourse);
                }
            });
            
            asignaturaNueva.alreadyAdded = true;
            
            subjectCounter ++;
            i++;
        }
        
        scrollPane_Asignaturas.setContent(panelDeControl_Asignaturas);
    }
    
    @FXML
    private void toFiles(SubjectVo subject)
    {
        hideEverythingInCourses(panelDeControl_Archivos);
        fileCounter = 0;
        labelCounter = 0;
        
        lastSubject = subject;
        
        crearElemento_curso.setVisible(false);
        crearElemento_asignatura.setVisible(false);
        crearElemento_archivo.setVisible(true);
        
        scrollPane_Asignaturas.setVisible(false);
        scrollPane_Archivos.setVisible(true);
        scrollPane_Cursos.setVisible(false);
        
        crearCurso_Pane.setVisible(false);
        crearAsignatura_Pane.setVisible(false);
        crearArchivo_Pane.setVisible(false);
        
        atras.setVisible(true);
        
        int size = subject.files.size();
        int i = 0;
        int distanceYValue = 100;
        
        while(i < size)
        {
            FileDeclivia archivoNuevo = subject.files.get(i);
            Label papelera = new Label();
            Image papeleraImage = new Image("Imagenes/eliminar2.png");
            Label path = new Label(archivoNuevo.getPath());
            
            ImageView papeleraImageView = new ImageView(papeleraImage);
            papeleraImageView.setScaleX(0.45);
            papeleraImageView.setScaleY(0.45);
            
            papelera.setGraphic(papeleraImageView);
            papelera.setCursor(Cursor.HAND);
            
            if(archivoNuevo.alreadyAdded == false)
            {
                panelDeControl_Archivos.getChildren().add(archivoNuevo);
            }
             
            path.setVisible(true);
            archivoNuevo.setVisible(true);
            papelera.setVisible(true);
            
            panelDeControl_Archivos.getChildren().add(papelera);
            panelDeControl_Archivos.getChildren().add(path);

            archivoNuevo.setLayoutX(COORDXCOURSE);
            archivoNuevo.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 30);
            
            path.setLayoutX(COORDXCOURSE + 80);
            path.setLayoutY(COORDYCOURSE + (distanceYValue * i) + 40);
            
            papelera.setLayoutX(COORDXCOURSE + 200);
            papelera.setLayoutY(COORDYCOURSE + (distanceYValue * i) - 7);
            
            everyFile[i] = archivoNuevo;
            everyLabel[labelCounter] = papelera;
            labelCounter++;
            everyLabel[labelCounter] = path; 
            
            if(archivoNuevo.alreadyAdded == false)
            {
                archivoNuevo.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {    
                        if(type == 1)
                        {
                            user.addClick();
                        }
                        archivoNuevo.loadData();

                        archivoNuevo.open();
                    }
                });
            }
            
            papelera.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event) 
                {
                    if(type == 1)
                    {
                        user.addClick();
                    }
                    archivoNuevo.setVisible(false);
                    lastSubject.eliminarArchivo(archivoNuevo);
                    toFiles(lastSubject);
                }
            });
            
            archivoNuevo.alreadyAdded = true;
            
            i++;
            fileCounter++;
            labelCounter++;
        }
        scrollPane_Archivos.setContent(panelDeControl_Archivos);
    }
   
    private void irAtras()
    {
        if(panelDeControl_Asignaturas.isVisible() == true)
        {
            toCursos();
        }
        
        if(panelDeControl_Archivos.isVisible() == true)
        {
            String newText[] = cursosText.getText().split(" > ");
            String newPath = newText[0] + " > " + newText[1];
            cursosText.setText(newPath);
            toSubjects(lastCourse);
        }
    }
    
    
    @FXML
    private void settings()
    {
        if(type == 1)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsInterface.fxml"));
                Parent root = (Parent) loader.load();
                SettingsDecliviaController settingsController = loader.getController();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                settingsController.passArguments(user, stage, actualStage, firstStage, 1, null);

                stage.show(); //Abrimos la interfaz de usuario
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsInterface.fxml"));
                Parent root = (Parent) loader.load();
                SettingsDecliviaController settingsController = loader.getController();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                settingsController.passArguments(null, stage, actualStage, firstStage, 2, superUser);

                stage.show(); //Abrimos la interfaz de usuario
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @FXML 
    private void profileAccount()
    {
        if(type == 1)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileInterface.fxml"));

                Parent root = (Parent) loader.load();

                ProfileDecliviaController profileController = loader.getController();


                Stage stageP = new Stage();
                stageP.setScene(new Scene(root));

                profileController.passArguments(stageP, actualStage, firstStage, user, 1, null);
                //firstStage.close(); //Cerramos el login

                stageP.show(); //Abrimos la interfaz de usuario
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileInterface.fxml"));

                Parent root = (Parent) loader.load();

                ProfileDecliviaController profileController = loader.getController();


                Stage stageP = new Stage();
                stageP.setScene(new Scene(root));

                profileController.passArguments(stageP, actualStage, firstStage, null, 2, superUser);
                //firstStage.close(); //Cerramos el login

                stageP.show(); //Abrimos la interfaz de usuario
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void crearCurso()
    {
        scrollPane_Archivos.setVisible(false);
        crearCurso_Pane.setVisible(true);
        crearAsignatura_Pane.setVisible(false);
        crearElemento_curso.setVisible(false);
        crearElemento_asignatura.setVisible(false);
    }
    
    @FXML
    private void crearCurso_Aceptar()
    {
        String courseName = nombreNuevoCurso.getText();
        String description = descripcionNuevoCurso.getText();
        
        user.createCourse(courseName, description);
        
        toCursos();
    }
    
    @FXML
    private void crearAsignatura()
    {
        scrollPane_Archivos.setVisible(false);
        crearCurso_Pane.setVisible(false);
        crearAsignatura_Pane.setVisible(true);
        crearElemento_curso.setVisible(false);
        crearElemento_asignatura.setVisible(false);
                
        ObservableList<Integer> list = FXCollections.observableArrayList();
        
        for(int i = 0; i <= 10; i++)
        {
            list.add(i);
        }
        
        dificultadComboBox.setItems(list);
    }
    
    @FXML
    private void crearAsignatura_Aceptar()
    {
        String subjectName = nombreAsignaturaNueva.getText();
        String anotation = anotacionesAsignaturaNueva.getText();
        String teacherEmail = correoProfesorAsignaturaNueva.getText();
        String credits = creditosAsignaturaNueva.getText();   
        String dif = dificultadComboBox.getSelectionModel().getSelectedItem().toString();
        
        System.out.println(dif);
        
        if(dif.equals(""))
        {
            dif = "5";
        }
        
        lastCourse.crearAsignatura(subjectName, teacherEmail, anotation, credits, 0, Integer.parseInt(dif));   
        
        toSubjects(lastCourse);
    }
    
    @FXML
    private void crearArchivo()
    {
        crearCurso_Pane.setVisible(false);
        crearAsignatura_Pane.setVisible(false);
        crearArchivo_Pane.setVisible(true);
        crearElemento_curso.setVisible(false);
        crearElemento_asignatura.setVisible(false);
        crearElemento_archivo.setVisible(false);
    }
    
    @FXML
    private void crearArchivo_Aceptar()
    {
        String fileName = nombreArchivoNuevo.getText();
        File newFile = new File(rutaArchivoNuevo.getText());
        String description = descripcionArchivoNuevo.getText();   
        
        lastSubject.crearArchivo(fileName, newFile, description);   
        
        toSubjects(lastCourse);
    }
    
    @FXML
    private void infoInicio()
    {
        int paneSelected = 1; //por defecto
        
        if(lastPane == inicioPane)
        {
            paneSelected = 1;
        }
        else if(lastPane == cursosPane)
        {
            paneSelected = 2;
        }
        else if(lastPane == destacadoPane)
        {
            paneSelected = 3;
        }
        else if(lastPane == controlPane)
        {
            paneSelected = 4;
        }
        
        System.out.println("Panel: " + paneSelected);
               
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Informacion.fxml"));
               
            Parent root = (Parent) loader.load();
            
            ControladorInformacion infoController = loader.getController();
            
            
            Stage stageP = new Stage();
            stageP.setScene(new Scene(root));
            
            infoController.passArguments(paneSelected);
            
            stageP.show(); //Abrimos la interfaz de usuario
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void buscarArchivo()
    {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File ("C:\\Users"));
        
        File selectedFile = fc.showOpenDialog(null);
        
        if(selectedFile != null)
        {
            rutaArchivoNuevo.setText(selectedFile.getAbsolutePath());
        }
        else
        {
            System.out.println("Es null");
        }
    }
}
