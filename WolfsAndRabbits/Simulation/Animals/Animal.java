package Task1.Simulation.Animals;
import Task1.Simulation.Coordinates.Coordinates;
import Task1.Simulation.Animals.Direction.Direction;

public abstract class Animal {
    protected Coordinates coordinates;
    protected Direction direction;
    protected int age;
    protected int constancy;
    protected boolean isMoving;
// -------------------------
    public enum AnimalsType
    {
        wolfType,
        rabbitType
    }

    public Animal(Coordinates iCoordinates, Direction iDirection, int iAge, int iConstancy) {
        coordinates = iCoordinates;
        direction = iDirection;
        age = iAge;
        constancy = iConstancy;
        isMoving = false;
    }

    public Coordinates GetCoordinates(){
        return coordinates;
    }

    public Direction GetDirection() {
        return direction;
    }

    public int GetAge() {
        return age;
    }

    public boolean IsMoving() {
        return isMoving;
    }

    public void Move(Coordinates iCoordinates){
        coordinates = iCoordinates;
        isMoving = true;
    }

    public abstract void Eat();
    public void GettingOld(){
        ++age;
    }
    public abstract Animal Reproduce();
    public abstract boolean Die();
    public void ChangeDirection(){
        switch (direction){
            case Up -> direction = Direction.Right;
            case Right -> direction = Direction.Down;
            case Down -> direction = Direction.Left;
            case Left -> direction = Direction.Up;
        }
    }
    public void GoToNextLoop(){
        isMoving = false;
        GettingOld();
        if(age % constancy == 0)
            ChangeDirection();
    }
    public abstract AnimalsType GetType();
}
