package Project;

public class WeightedProduct extends Product{
    private int weight;

    public WeightedProduct(){
        super();
    }

    public WeightedProduct(int weight, int id, String name
            , String description, int price) {
        super(id, name, description, price);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return getId() + ", " + getName() + ", " + getDescription() 
                + ", " + getPrice() + ", " + weight + ", W";
    }
}