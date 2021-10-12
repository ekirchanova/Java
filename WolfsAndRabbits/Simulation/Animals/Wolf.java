package Task1.Simulation.Animals;

import Task1.Simulation.Coordinates.Coordinates;
import Task1.Simulation.Animals.Direction.Direction;

public class Wolf extends Animal{
    private int satiety;
    //----------------
    public Wolf(Coordinates iCoordinates, Direction iDirection, int iAge, int  iConstancy) {
        super(iCoordinates, iDirection, iAge, iConstancy);
        satiety = 0;
    }

    @Override
    public void Eat() {
        ++satiety;
    }

    @Override
    public Animal Reproduce() {
        if(satiety >= 2){
            Wolf child = new Wolf(new Coordinates(coordinates.GetX(), coordinates.GetY()),direction, 0, constancy);
            satiety -= 2;
            return child;
        }
        return null;
    }

    @Override
    public boolean Die() {
        if(age == 15)
            return true;
        return false;
    }

    @Override
    public AnimalsType GetType() {
        return AnimalsType.wolfType;
    }
}
