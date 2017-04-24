package util;

import java.io.IOException;

import flashcardfx.QuestionPane;
import info.InfoPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import workbook.LayerController;

public class FxmlLoader {

	static final String layer="layer"; 

//	public Pane	loadFXML(String fxmlFile) throws IOException{
//		return FXMLLoader.load(getClass().getResource("/"+fxmlFile+".fxml"));
//	}

	public Pane	loadQuestionPane() throws IOException{
		QuestionPane qp = new QuestionPane();
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/"+layer+".fxml"));
		 Pane pane =(Pane)fxmlLoader.load();
		 LayerController layerController =fxmlLoader.getController();
		 Pane paneInAnchorPane = layerController.getScrollAnchorPane();
		 paneInAnchorPane.getChildren().add(qp.load());
		return pane;
	}
	
	public Pane loadInfoPane() throws IOException
	{
		 InfoPanel ip = new InfoPanel();
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/"+layer+".fxml"));
		 Pane pane =(Pane)fxmlLoader.load();
		 LayerController layerController =fxmlLoader.getController();
		 Pane paneInAnchorPane = layerController.getScrollAnchorPane();
		 paneInAnchorPane.getChildren().add(ip.load());
		return pane;
	}

	
	
	
}