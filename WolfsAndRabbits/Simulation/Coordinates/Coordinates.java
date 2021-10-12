package Task1.Simulation.Coordinates;

public class Coordinates {
    private int x;
    private int y;
    //----
    public Coordinates(int iX, int iY)
    {
        x = iX;
        y = iY;
    }
    public int GetX(){
        return x;
    }
    public int GetY() {
        return y;
    }
    public void SetX(int iX){
        x = iX;
    }
    public void SetY(int iY){
        y = iY;
    }
    public void ChangeCoordinates(int dX, int dY){
        x += dX;
        y += dY;
    }
}
