package Task1.Simulation.Animals;

import Task1.Simulation.Animals.Direction.Direction;
import Task1.Simulation.Coordinates.Coordinates;

public class Rabbit extends Animal{
    public Rabbit(Coordinates iCoordinates, Direction iDirection, int iAge, int  iConstancy)
    {
        super(iCoordinates, iDirection, iAge, iConstancy);
    }

    @Override
    public Animal Reproduce() {
        if(age == 5 || age == 10)
            return new Rabbit(new Coordinates(coordinates.GetX(), coordinates.GetY()), direction, 0, constancy);
        return null;
    }

    @Override
    public void Eat() {
    }

    @Override
    public boolean Die() {
        if(age == 10)
            return true;
        return false;
    }

    @Override
    public AnimalsType GetType() {
        return AnimalsType.rabbitType;
    }
}
