package controller;

import backend.Event;
import backend.LinkDownloadController;
import backend.Sess1on;
import com.itextpdf.text.DocumentException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Weekpage extends VBox {

    private final Group group = new Group();
    private double cellheight = 67.2 / 3;
    private final LocalTime firstSlotStart = LocalTime.of(0, 0);
    private final Duration slotLength = Duration.ofMinutes(15);
    private final LocalTime lastSlotStart = LocalTime.of(23, 59);
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");
    private final List<TimeSlot> timeSlots = new ArrayList<>();
    private List<customHbox> hboxes[] = new ArrayList[7];

    LocalDate today = LocalDate.now();
    LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
    LocalDate endOfWeek = startOfWeek.plusDays(6);
    GridPane calendarView = new GridPane();
    ScrollPane scrollPane = new ScrollPane();

    public Weekpage(LocalDate selectedDay) {
        super();
        for (int i = 0; i < 7; i++)
            hboxes[i] = new ArrayList<customHbox>();
        today=selectedDay;
        startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        endOfWeek = startOfWeek.plusDays(6);
        setScene();
    }

    public void setScene() {
        refreshAgenda();
        // headers:
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");

        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            Label label = new Label(date.format(dayFormatter));
            label.setPadding(new Insets(1));
            label.setTextAlignment(TextAlignment.CENTER);
            GridPane.setHalignment(label, HPos.CENTER);
            calendarView.add(label, date.getDayOfWeek().getValue(), 0);
        }

        int slotIndex = 1;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (LocalDateTime startTime = today.atTime(firstSlotStart); !startTime
                .isAfter(today.atTime(lastSlotStart)); startTime = startTime.plus(slotLength)) {
            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            GridPane.setHalignment(label, HPos.RIGHT);
            calendarView.add(label, 0, slotIndex);
            slotIndex++;
        }

        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);


        group.getChildren().add(calendarView);
        FlowPane fp = new FlowPane();
        customButton addEventBtn = new customButton();
        addEventBtn.setText("New Event");
        Button backBtn = new Button("Back");
        Button pdfBtn = new Button("PDF");
        Button textBtn = new Button("Text");
        backBtn.setOnAction(e-> {
            try {
                new ControllerMonth().ChangeView(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pdfBtn.setOnAction(e->{
            List<Event> listWeekEvent=new Sess1on().gettEventInWeek(today);
            try {
                new LinkDownloadController().btnDownloadClicked(today,pdfBtn.getText());
            } catch (DocumentException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        textBtn.setOnAction(e->{
            List<Event> listWeekEvent=new Sess1on().gettEventInWeek(today);
            try {
                new LinkDownloadController().btnDownloadClicked(today,textBtn.getText());
            } catch (DocumentException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        addEventBtn.setOnAction(e -> {
            try {
                addEventBtn.addEvent();
                addEvent();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        fp.getChildren().add(group);
        scrollPane.setContent(fp);
        HBox hb=new HBox();
        hb.getChildren().addAll(backBtn,addEventBtn,pdfBtn,textBtn);
        this.getChildren().addAll(hb, scrollPane);
        for (Event event:Sess1on.eventList)
        {
            addEventToGrid(event);
        }
    }

    public static class TimeSlot {

        private final LocalDateTime start;
        private final Duration duration;
        private final Region view;

        private final BooleanProperty selected = new SimpleBooleanProperty();

        public final BooleanProperty selectedProperty() {
            return selected;
        }


        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start;
            this.duration = duration;

            view = new Region();
            view.setMinSize(80, 20);
            view.getStyleClass().add("time-slot");

            selectedProperty().addListener((obs, wasSelected, isSelected) -> view
                    .pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected));

        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalTime getTime() {
            return start.toLocalTime();
        }

        public DayOfWeek getDayOfWeek() {
            return start.getDayOfWeek();
        }

        public Duration getDuration() {
            return duration;
        }

        public Node getView() {
            return view;
        }
    }

    public void addEvent() {
        Event event = new Event(Sess1on.tempEvent);
        Sess1on.eventList.add(event);
        addEventToGrid(event);
    }

    public void refreshAgenda() {
        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            int slotIndex = 1;

            for (LocalDateTime startTime = date.atTime(firstSlotStart); !startTime
                    .isAfter(date.atTime(lastSlotStart)); startTime = startTime.plus(slotLength)) {

                TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                timeSlots.add(timeSlot);

                calendarView.add(timeSlot.getView(), timeSlot.getDayOfWeek().getValue(), slotIndex);
                slotIndex++;
            }
        }
    }

    public void addEventToGrid(Event event) {
        LocalDate temp=event.getDate().toLocalDate();
        if ((temp.isAfter(startOfWeek)|| temp.isEqual(startOfWeek)) && (temp.isBefore(endOfWeek)||temp.isEqual(endOfWeek))) {
            int colId, rowId, span;
            colId = event.getDate().getDayOfWeek().getValue()-1;
            rowId = event.getDate().getHour();
            rowId += rowId * 4 + event.getDate().getMinute() / 15;
            span = event.getDuration() / 15 + 1;
            customButton btn = new customButton(event);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setMaxHeight(cellheight * span);
            btn.setOnAction(e->{
                try {
                    btn.editEvent();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            customHbox.setHgrow(btn, Priority.ALWAYS);

            boolean flag = false;
            if (hboxes[colId].size() > 0)
                for (customHbox chb : hboxes[colId]) {
                    if (rowId >= chb.firstRow && rowId <= chb.lastRow) {
                        chb.getChildren().add(btn);
                        if (rowId + span - 1 > chb.lastRow) {
                            chb.lastRow = rowId + span;
                            GridPane.setRowSpan(chb, chb.lastRow - chb.firstRow + 1);
                        }
                        flag = true;
                        break;
                    }
                }
            if (!flag) {
                customHbox hb = new customHbox();
                hb.setMaxHeight(Double.MAX_VALUE);
                hb.setMaxWidth(Double.MAX_VALUE);
                hb.getChildren().addAll(btn);

                GridPane.setRowSpan(hb, span);
                GridPane.setColumnIndex(hb, colId+1);
                GridPane.setRowIndex(hb, rowId+1);

                hb.setFirstRow(rowId);
                hb.setLastRow(rowId + span - 1);
                hboxes[colId].add(hb);
                calendarView.getChildren().add(hboxes[colId].get(hboxes[colId].size() - 1));
            }
        }
    }
}
