package sample;
import static sample.LayoutCreator.triangleBase;
import static sample.LayoutCreator.triangles;

@SuppressWarnings("ALL")
public class Controller {


    public Piece makeHandlePiece(double x, double y, PieceType pieceType, int number, Graveyard graveyard) {
        Piece piece = new Piece(x , y, pieceType, number);
        piece.setOnMouseReleased(e -> {
            int distance;
            double newXPosition = (double)((int)(e.getSceneX()/triangleBase))*triangleBase;
            double newYPosition = e.getSceneY();

//            int oldTriangleNumber = findTriangleNumber(piece.getOldX(), piece.getOldY());
            int oldTriangleNumber = piece.getPlaceNumber();
            int newTriangleNumber = findTriangleNumber(newXPosition, newYPosition);
            distance = triangles[newTriangleNumber].getNumber() - piece.getPlaceNumber();


            if((newYPosition > 5*triangleBase && newYPosition < 7*triangleBase) ||
                    (newXPosition == piece.getOldX()) || distance*pieceType.moveDirection < 0){
                piece.abortMove();
            }
            else {

                if (triangles[newTriangleNumber].getTypeOfPieces() != pieceType &&
                        triangles[newTriangleNumber].getTypeOfPieces() != null){
                    if(triangles[newTriangleNumber].getNumberOfPieces() > 1){
                        piece.abortMove();
                    }
                    else {
                        Piece killedPiece = triangles[newTriangleNumber].getPieces().get(0);
                        killedPiece.setPlaceNumber(killedPiece.getPieceType()==pieceType.white?-1:24);
                        killedPiece.setKilled(true);
                        graveyard.addPiece(killedPiece);
                        triangles[newTriangleNumber].killPiece();
                        newYPosition = triangles[newTriangleNumber].findCoordinationOfNewPiece();
                        triangles[newTriangleNumber].addPiece(piece);
                        if(piece.isKilled()){
                            graveyard.removePiece(piece);
                        }
                        else {
                            triangles[oldTriangleNumber].removePiece(piece);
                        }
//                        killedPiece.move(6*triangleBase, 6*triangleBase);
                        piece.setPlaceNumber(triangles[newTriangleNumber].getNumber());
                        piece.move(newXPosition, newYPosition);
                    }
                }
                else {
                    newYPosition = triangles[newTriangleNumber].findCoordinationOfNewPiece();
                    triangles[newTriangleNumber].addPiece(piece);
                    if(piece.isKilled()){
                        graveyard.removePiece(piece);
                    }
                    else {
                        triangles[oldTriangleNumber].removePiece(piece);
                    }
                    piece.setPlaceNumber(triangles[newTriangleNumber].getNumber());
                    piece.move(newXPosition, newYPosition);
                }


            }

        });
        return piece;

    }


    public int findTriangleNumber(double x, double y){
        int xPosition = (int)(x/triangleBase);
        int yPosition = (int)(y/triangleBase);
        int column;
        column = xPosition > 7? 13 - xPosition: 12 - xPosition;
        return yPosition >= 7? 23 - column : column;
    }
}
