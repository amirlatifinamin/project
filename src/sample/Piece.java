package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static sample.LayoutCreator.triangleBase;

public class Piece extends StackPane {
    private Circle piece = new Circle();
    private Circle border = new Circle();
    private double mouseX, mouseY;
    private double oldX, oldY;
    private PieceType pieceType;
    private int placeNumber;
    private boolean isKilled = false;

    public Piece(double x, double y, PieceType pieceType, int placeNumber) {
        this.placeNumber = placeNumber;
        piece.setRadius(triangleBase / 2);
        border.setRadius(triangleBase / 2 + 2);
        this.pieceType = pieceType;
        move(x, y);
        getChildren().addAll(border, piece);
        piece.setFill(pieceType == PieceType.red ? Color.valueOf("#770000") : Color.valueOf("#F2CE7C"));
        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });


        setOnMouseDragged(e -> {
            relocate(e.getSceneX() + oldX - mouseX, e.getSceneY() + oldY - mouseY);
        });
    }

    public void move(double x, double y) {
        oldX = x;
        oldY = y;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public void setKilled(boolean killed) {
        isKilled = killed;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}

