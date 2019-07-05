package sample;
import static sample.LayoutCreator.triangleBase;
import static sample.LayoutCreator.triangles;

@SuppressWarnings("ALL")
public class Controller {


    public Piece makeHandlePiece(double x, double y, PieceType pieceType, int number, Graveyard graveyard, DiceBoard diceBoard) {
        Piece piece = new Piece(x , y, pieceType, number);
        piece.setOnMouseReleased(e -> {
            int distance;
            double newXPosition = (double)((int)(e.getSceneX()/triangleBase))*triangleBase;
            double newYPosition = e.getSceneY();

            int oldTriangleNumber = piece.getPlaceNumber();
            int newTriangleNumber = findTriangleNumber(newXPosition, newYPosition);
            distance = triangles[newTriangleNumber].getNumber() - piece.getPlaceNumber();

            MoveType moveType = findMoveType(newXPosition, newYPosition, newTriangleNumber, piece, distance, diceBoard);

            switch (moveType){
                case None:{
                    piece.abortMove();
                    break;
                }
                case Normal:{
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
                    break;
                }
                case kill:{
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
                    piece.setPlaceNumber(triangles[newTriangleNumber].getNumber());
                    piece.move(newXPosition, newYPosition);
                    break;
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

    public MoveType findMoveType(double newXPosition, double newYPosition,int newTriangleNumber,
                                 Piece piece, int distance, DiceBoard diceBoard){

        if((newYPosition > 5*triangleBase && newYPosition < 7*triangleBase) ||
                newYPosition > 12*triangleBase ||
                newYPosition < 0 ||
                newXPosition < triangleBase ||
                (newXPosition < 8*triangleBase && newXPosition > 7*triangleBase) ||
                (newXPosition == piece.getOldX()) ||
                distance*piece.getPieceType().moveDirection < 0){
            return MoveType.None;
        }

        if (triangles[newTriangleNumber].getTypeOfPieces() != piece.getPieceType() &&
                        triangles[newTriangleNumber].getTypeOfPieces() != null){
            if(triangles[newTriangleNumber].getNumberOfPieces() > 1){
                return MoveType.None;
            }
            if(!diceBoard.canMove(Math.abs(distance))){
                return MoveType.None;
            }
            return MoveType.kill;
        }
        else {
            if(!diceBoard.canMove(Math.abs(distance))){
                return MoveType.None;
            }
            return MoveType.Normal;
        }

    }
}
