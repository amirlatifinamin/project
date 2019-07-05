package sample;
import static sample.LayoutCreator.triangleBase;
import static sample.LayoutCreator.triangles;

public class Controller {
    public Piece makeHandlePiece(double x, double y, PieceType pieceType) {
        Piece piece = new Piece(x , y, pieceType);
        piece.setOnMouseReleased(e -> {

            double newXPosition = (double)((int)(e.getSceneX()/triangleBase))*triangleBase;
            double newYPosition = e.getSceneY();
//            int oldColumn = findColumn(piece.getOldX());
//            int oldRow = findRow(piece.getOldY());
            int oldTriangleNumber = findTriangleNumber(piece.getOldX(), piece.getOldY());

            if(newYPosition > 5*triangleBase && newYPosition < 7*triangleBase){
                piece.abortMove();
            }
            else {
//                int column = findColumn(newXPosition);
//                int row = findRow(newYPosition);
//                newYPosition = triangles[row][column].findCoordinationOfNewPiece();
//                triangles[row][column].addPiece(piece);
//                triangles[oldRow][oldColumn].removePiece(piece);
                int triangleNumber = findTriangleNumber(newXPosition, newYPosition);
                if (triangles[triangleNumber].getTypeOfPieces() != pieceType){
                    piece.abortMove();
                }
                else {
                    newYPosition = triangles[triangleNumber].findCoordinationOfNewPiece();
                    triangles[triangleNumber].addPiece(piece);
                    triangles[oldTriangleNumber].removePiece(piece);
                    piece.move(newXPosition, newYPosition);
                }


            }

        });
        return piece;

    }

    public int findColumn(double x){
        int column;
        int xPosition = (int)(x/triangleBase);
        column = xPosition > 7? 13 - xPosition: 12 - xPosition;
        return column;
    }

    public int findRow(double y){
        int row;
        int yPosition = (int)(y/triangleBase);
        row = yPosition >= 7? 1:0;
        System.out.println(yPosition);
        return row;
    }

    public int findTriangleNumber(double x, double y){
        int xPosition = (int)(x/triangleBase);
        int yPosition = (int)(y/triangleBase);
        int column;
        column = xPosition > 7? 13 - xPosition: 12 - xPosition;
        return yPosition >= 7? 23 - column : column;
    }
}
