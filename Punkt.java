package po2proj;

public class Punkt
{
    private int x;
    private int y;

    public Punkt(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    //nadpisanie domyslnej funkcji equals
    @Override
    public boolean equals(Object obj)
    {
        if(obj == this)
        {
            return true;
        }
        if(!(obj instanceof Punkt punkt))
        {
            return false;
        }

        return (this.x == punkt.x) && (this.y == punkt.y);
    }

    public int WezX()
    {
        return x;
    }
    public int WezY()
    {
        return y;
    }
    public void UstawX(int x)
    {
        this.x = x;
    }
    public void UstawY(int y)
    {
        this.y = y;
    }
}