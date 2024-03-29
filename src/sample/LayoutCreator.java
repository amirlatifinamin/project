package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LayoutCreator {
    public static int row = 2;
    public static int column = 12;
    public static double triangleBase = 50;
    public static final double stockpileWidth = Stockpile.pileWidth + triangleBase;
    public static final double stockpileHeight = Stockpile.pileHeight;
//    public static double boardWidth = 15 * triangleBase + ScoreBoard.scoreBoardWidth + stockpileWidth + ControlPanel.width;
    public static double boardWidth = 17 * triangleBase + ScoreBoard.scoreBoardWidth + stockpileWidth;
    public static double boardHeight = 12 * triangleBase;
    public static double controlPanelX = boardWidth - 2.25* triangleBase;
//    public static double controlPanelY = 12*triangleBase;
    public static double controlPanelY = 0;
    public static final double scoreboardX = boardWidth - ScoreBoard.scoreBoardWidth - triangleBase / 2 - ControlPanel.width;
    public static final double scoreboardY = triangleBase / 2;
    private final double diceBoardX = scoreboardX;
    private final double diceBoardY = scoreboardY + ScoreBoard.scoreBoardHeight + triangleBase / 3;
//    public static final double stockpileX = boardWidth - ScoreBoard.scoreBoardWidth - stockpileWidth;
    public static final double stockpileX = 15*triangleBase;
    public static final double redStockpileY = triangleBase / 2;
    public static final double whiteStockPileY = redStockpileY + stockpileHeight + triangleBase;
    public static ScoreBoard scoreBoard = new ScoreBoard(scoreboardX, scoreboardY);
    private Group trianglesGroup = new Group();
    public static Group pieces = new Group();
    private DiceBoard diceBoard = new DiceBoard();
    public static Graveyard graveyard = new Graveyard(scoreBoard);
    private Border border = new Border();
    public static Controller controller;
    public static Triangle[] triangles = new Triangle[row * column];
    private Stockpile redStockpile = new Stockpile(stockpileX, redStockpileY, PieceType.red, scoreBoard);
    private Stockpile whiteStockpile = new Stockpile(stockpileX, whiteStockPileY, PieceType.white, scoreBoard);
    private UserStatistics redStats = new UserStatistics(PieceType.red);
    private UserStatistics whiteStats = new UserStatistics(PieceType.white);
    private StartPage startPage = new StartPage();
    public static Pane board = new Pane();
    private ControlPanel controlPanel = new ControlPanel(controlPanelX, controlPanelY);
    private static Rectangle pausePage;
    public TimerClass timer = new TimerClass();

    public LayoutCreator() {
        controller = new Controller(graveyard, diceBoard, redStockpile, whiteStockpile);
    }


    public Pane layout() {
        pausePage = rectangleInit(0,0, controlPanelX, boardHeight, "#4e2400");
        scoreBoard.init(redStockpile, whiteStockpile, diceBoard, redStats, whiteStats, graveyard);
        Pane diceBoardPane = diceBoard.layoutCreator(diceBoardX, diceBoardY, scoreBoard);
        board.setPrefSize(boardWidth, boardHeight);
        startPage.startButton.setOnMouseClicked(event -> {
            startPage.startGame();
            board.getChildren().remove(startPage);
            board.getChildren().addAll(border, graveyard, this.trianglesGroup,
                    diceBoardPane, redStockpile, whiteStockpile, scoreBoard, pieces, controlPanel);
            timer.start(startPage.getRoundDuration() * 60);
        });
        controlPanel.restartButton.setOnMouseClicked(event -> {
            this.reset();
            timer.reset = true;
        });
        controlPanel.pauseButton.setOnMouseClicked(event -> {
            if (controlPanel.pauseButton.isActive()){
                board.getChildren().addAll(pausePage);
                controlPanel.pauseButton.deactiveButton();
                controlPanel.resumeButton.activeButton();
                timer.pause = true;
            }
        });
        controlPanel.resumeButton.setOnMouseClicked(event -> {
            if (controlPanel.resumeButton.isActive()){
                board.getChildren().remove(pausePage);
                controlPanel.resumeButton.deactiveButton();
                controlPanel.pauseButton.activeButton();
                timer.pause = false;
            }
        });
        controlPanel.timer.setOnMouseClicked(event -> {
            if (controlPanel.timer.isActive()){
                controlPanel.pauseButton.activeButton();
                controlPanel.timer.deactiveButton();
                try {
                    timer.timer1.cancel();
                } catch (Error e){

                }
                timer = new TimerClass();
                timer.start(startPage.getRoundDuration() * 60);
                ControlPanel.finished = false;
            }
        });
        board.getChildren().addAll(startPage);
        board.setStyle("-fx-background-color: #4e2400");
        initializeTriangles();
        return board;
    }

    private int findInitialNumOfPieces(int index) {
        int initialNumOfPieces;
        switch (index) {
            case 1: {
                initialNumOfPieces = 5;
                break;
            }
            case 5: {
                initialNumOfPieces = 3;
                break;
            }
            case 7: {
                initialNumOfPieces = 5;
                break;
            }
            case 12: {
                initialNumOfPieces = 2;
                break;
            }
            default: {
                initialNumOfPieces = 0;
                break;
            }
        }
        return initialNumOfPieces;
    }


    public void initializeTriangles() {
        for (int index = 1; index <= column; index++) {
            int initialNumOfPieces = findInitialNumOfPieces(index);
            Triangle upTriangle = new Triangle((index <= column / 2 ? index : index + 1) * triangleBase, 0.0,
                    index % 2 == 0 ? TriangleType.upRed : TriangleType.upWhite, this.controller, 12 - index,
                    graveyard, diceBoard, redStockpile, whiteStockpile, this.pieces);
            triangles[12 - index] = upTriangle;
            Triangle downTriangle = new Triangle((index <= column / 2 ? index : index + 1) * triangleBase, boardHeight / 2 + triangleBase,
                    index % 2 == 0 ? TriangleType.downWhite : TriangleType.downRed, this.controller, 11 + index,
                    graveyard, diceBoard, redStockpile, whiteStockpile, this.pieces);
            triangles[11 + index] = downTriangle;
            Group upPieces = upTriangle.initializePieces(initialNumOfPieces, pieces);
            Group downPieces = downTriangle.initializePieces(initialNumOfPieces, pieces);
            pieces.getChildren().addAll(upPieces, downPieces);
            this.trianglesGroup.getChildren().addAll(upTriangle, downTriangle);

        }
    }

    private void reset () {
        board.getChildren().removeAll(graveyard, this.trianglesGroup, pieces);
        graveyard = new Graveyard(scoreBoard);
        pieces = new Group();
        trianglesGroup = new Group();
        triangles = new Triangle[row * column];
        initializeTriangles();
        scoreBoard.reset();
        diceBoard.reset();
        redStockpile.reset();
        whiteStockpile.reset();
        redStats.reset();
        whiteStats.reset();
        ControlPanel.finished = false;
        try {
            timer.timer1.cancel();
        } catch (Error e){

        }
        timer = new TimerClass();
        timer.start(startPage.getRoundDuration() * 60);
        board.getChildren().addAll(graveyard, this.trianglesGroup, pieces);
    }

    private Rectangle rectangleInit (double x, double y,double width, double height, String color) {
        Rectangle temp = new Rectangle();
        temp.relocate(x, y);
        temp.setWidth(width);
        temp.setHeight(height);
        temp.setFill(Color.valueOf(color));
        return temp;
    }

    private void resetPieces(){
        this.trianglesGroup.getChildren().removeAll();
        pieces.getChildren().removeAll();
        System.out.println("salam");
        this.initializeTriangles();
    }

    public static Pane getBoard(){
        return board;
    }

    public static Rectangle getPausePage(){
        return pausePage;
    }
}
