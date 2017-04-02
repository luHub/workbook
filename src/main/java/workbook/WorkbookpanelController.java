package workbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import commons.WorkbookIO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import util.FxmlLoader;
import util.WorkbookLayout;

public class WorkbookpanelController {
	
	private final static String QUESTION_ID ="question";

	private static final String INFO_ID = "info";

	private static final String CODE_ID = "code";

	FxmlLoader fxmlLoader = new FxmlLoader();
	
    @FXML
    private AnchorPane workAreaAnchorPane;
    
    @FXML
    private VBox workAreaVbox;
    
    @FXML
    private HBox workAreaHbox;
    
    @FXML
    private ToggleButton infoToggleButton;

    @FXML
    private ToggleButton questionToggleButton;

    @FXML
    private ToggleButton codeToggleButton;
 
    //TODO Add Config File
    //For Last Config
    
    @FXML
    private void initialize(){
    	
    	try {
			WorkbookIO.createWorkbookIfNotExists("user", "workbook");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    	codeToggleButton.setDisable(true);
    	
			workAreaHbox.setId("base"); 
			WorkbookLayout.setDropForLayers(workAreaHbox,workAreaVbox);
			WorkbookLayout.setDropForLayers(workAreaVbox,workAreaHbox);

			//ToggleButtons
			Map<String,ToggleButton> buttonsMap = new HashMap<String,ToggleButton>();
			buttonsMap.put(INFO_ID, infoToggleButton);
			buttonsMap.put(QUESTION_ID, questionToggleButton);
			buttonsMap.put(CODE_ID,codeToggleButton);
			buttonsMap.entrySet().stream().forEach((eks)->{
				initializeToggleButtons(eks.getValue(), eks.getKey());
			});
	}

	private void initializeToggleButtons(ToggleButton toggleButton,String panelId) {
		// TODO Auto-generated method stub
		toggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(true)) {
					addPanel(panelId);
				} else {
					removePanel(panelId);
				}
			}
	
		});
	}

	private void addPanel(String panelId) {
		try {
			if(panelId.equals(QUESTION_ID)){
				final Pane ap = fxmlLoader.loadQuestionPane();
				ap.setId(panelId);
				workAreaHbox.setHgrow(ap, Priority.ALWAYS);
				workAreaVbox.setVgrow(ap, Priority.ALWAYS);
				workAreaHbox.getChildren().addAll(ap);
				ap.setPadding(new Insets(6, 6, 6, 6));
				WorkbookLayout.setDragForLayerLayer(ap);	
			}else 
				if(panelId.equals(INFO_ID)){
				final Pane ap = fxmlLoader.loadInfoPane();
				ap.setId(panelId);
				workAreaHbox.setHgrow(ap, Priority.ALWAYS);
				workAreaVbox.setVgrow(ap, Priority.ALWAYS);
				workAreaHbox.getChildren().addAll(ap);
				ap.setPadding(new Insets(6, 6, 6, 6));
				WorkbookLayout.setDragForLayerLayer(ap);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void removePanel(String questionId) {
		workAreaHbox.getChildren().removeAll(workAreaHbox.lookup("#" + questionId));
		workAreaVbox.getChildren().removeAll(workAreaVbox.lookup("#" + questionId));

	}
}