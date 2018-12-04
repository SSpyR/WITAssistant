package application;

import java.io.IOException;

import gpaCalculator.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class AnchorPaneController {
	
	@FXML private SplitPane splitPane;
	@FXML private AnchorPane leftSection;
	@FXML private Label currentDateLabel;
	@FXML private HBox lowerSection;
	@FXML private Button btnPrev;
	@FXML private Button btnNext;
	@FXML private Button btnRefresh;
	@FXML private VBox vContainer;
	@FXML private TabPane rightSection;
	@FXML private Tab tabAddEvent;
	@FXML private Tab tabCalc;
	@FXML private AnchorPane calcPanel;
	@FXML private AnchorPane addEventPanel;
	@FXML private Tab tabAssistant;
	@FXML private AnchorPane assistantPanel;
	
	private FXMLLoader fxmlLoader = new FXMLLoader();
	private SingleSelectionModel<Tab> selectionModel;
	
	public void drawCalendar() {
		vContainer.getChildren().clear();
		int counter = 0;
		HBox rowContainer = new HBox();
		for(int col = 0; col < 5; col++) {
			for(int row = 0; row < 7; row++) {
				if(counter == EventTime.getNumDays()) {
					break;
				}
				counter++;
				DayBox dayBox = new DayBox(counter); 
				dayBox.widthProperty().bind(leftSection.widthProperty().divide(7.1));
				dayBox.heightProperty().bind(leftSection.heightProperty().divide(6));
				
				dayBox.getEvents();
				rowContainer.getChildren().add(dayBox);
				setDayProperties(dayBox);
			}
			vContainer.getChildren().add(rowContainer);
			rowContainer = new HBox();
		}
	}
	
	public void setDayProperties(DayBox dayBox) {
		dayBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				EventTime.setDay(dayBox.getDay());
				if(dayBox.isInPlusBounds(event.getX(), event.getY())) {
					
					try {
						addEventPanel = fxmlLoader.load(getClass().getResource("EventForm.fxml"));
						tabAddEvent.setContent(addEventPanel);
						
					} catch (IOException e) {
						e.printStackTrace();
					}		
					selectionModel.select(2);
				}
			}
		});
	}
	
	public void nextButtonEvent() {
		EventTime.nextMonth();
		drawCalendar();
		setDateLabel();
	}
	
	public void previousButtonEvent() {
		EventTime.previousMonth();
		drawCalendar();
		setDateLabel();
	}
	
	public void setDateLabel() {
		currentDateLabel.setText(EventTime.getMonthName() + " " + EventTime.getYear());
	}

	public void initialize() {
		selectionModel = rightSection.getSelectionModel();	
		splitPane.setDividerPosition(0, .75);
		EventTime.initializeDate();
		try {
			addEventPanel = fxmlLoader.load(getClass().getResource("EventForm.fxml"));
			assistantPanel = fxmlLoader.load(getClass().getResource("Assistant.fxml"));
			calcPanel = fxmlLoader.load(getClass().getResource("Calculator.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addEventPanel.setDisable(true);
		tabAssistant.setContent(assistantPanel);
		tabCalc.setContent(calcPanel);
		tabAddEvent.setContent(addEventPanel);
		currentDateLabel.setText(EventTime.getMonthName() + " " + EventTime.getYear());
		drawCalendar();
		
	}
}

