package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class LayoutCreator {
    public static int row = 2;
    public static int column = 12;
    public static double triangleBase = 50;
    private final double diceBoardWidth = DiceBoard.width;
    private final double diceBoardHeight = DiceBoard.height;
    public static final double stockpileWidth = Stockpile.pileWidth + triangleBase;
    public static final double stockpileHeight = Stockpile.pileHeight;
    public static double boardWidth = 15 * triangleBase + ScoreBoard.scoreBoardWidth + stockpileWidth;
    public static double boardHeight = 12 * triangleBase;
    private final double scoreboardX = boardWidth - ScoreBoard.scoreBoardWidth - triangleBase / 2;
    private final double scoreboardY = triangleBase / 2;
    private final double diceBoardX = scoreboardX;
    private final double diceBoardY = scoreboardY + ScoreBoard.scoreBoardHeight + triangleBase / 3;
//    public static final double stockpileX = boardWidth - ScoreBoard.scoreBoardWidth - stockpileWidth;
    public static final double stockpileX = 15*triangleBase;
    public static final double redStockpileY = triangleBase / 2;
    public static final double whiteStockPileY = redStockpileY + stockpileHeight + triangleBase;
    private ScoreBoard scoreBoard = new ScoreBoard(scoreboardX, scoreboardY);
    private Group trianglesGroup = new Group();
    public static Group pieces = new Group();
    private DiceBoard diceBoard = new DiceBoard();
    private Graveyard graveyard = new Graveyard(scoreBoard);
    private Border border = new Border();
    private Controller controller;
    public static Triangle[] triangles = new Triangle[row * column];
    private Stockpile redStockpile = new Stockpile(stockpileX, redStockpileY, PieceType.red, scoreBoard);
    private Stockpile whiteStockpile = new Stockpile(stockpileX, whiteStockPileY, PieceType.white, scoreBoard);
    private UserStatistics redStats = new UserStatistics(PieceType.red);
    private UserStatistics whiteStats = new UserStatistics(PieceType.white);
    private StartPage startPage = new StartPage();


    public LayoutCreator() {
        this.controller = new Controller(graveyard, diceBoard, redStockpile, whiteStockpile);
    }


    public Pane layout() {
        Pane board = new Pane();
        scoreBoard.init(redStockpile, whiteStockpile, diceBoard, redStats, whiteStats, graveyard);
        Pane diceBoardPane = diceBoard.layoutCreator(diceBoardX, diceBoardY, scoreBoard);
        board.setPrefSize(boardWidth, boardHeight);
        startPage.startButton.setOnMouseClicked(event -> {
            board.getChildren().remove(startPage);
            board.getChildren().addAll(border, graveyard, this.trianglesGroup,
                    diceBoardPane, redStockpile, whiteStockpile, scoreBoard, pieces);
        });
        board.getChildren().addAll(startPage);
        board.setStyle("-fx-background-color: #21242E");
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

}
