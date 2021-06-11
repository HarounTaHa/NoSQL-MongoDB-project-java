package Project;

public class DimensionalProduct extends Product{
    private int width;
    private int height;
    
    public DimensionalProduct(){
        
    }
    
    public DimensionalProduct(int id, String name, String description,
            int price, int width, int height) {
        super(id, name, description, price);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return getId() + ", " + getName() + ", " + getDescription() 
                + ", " + getPrice() + ", " + width + ", " + height + ", D";
    }
}